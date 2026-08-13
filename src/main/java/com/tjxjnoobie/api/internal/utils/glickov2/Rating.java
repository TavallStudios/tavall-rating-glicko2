package com.tjxjnoobie.api.internal.utils.glickov2;

import com.tjxjnoobie.api.interfaces.IRating;

/**
 * Mutable Glicko-2 rating state for one participant.
 *
 * <p>The externally visible rating and deviation fields use the ordinary Glicko presentation scale.
 * During a rating-period calculation, interim values are held separately on the Glicko-2 internal
 * scale so every participant is calculated against the same pre-period ratings. Calling
 * {@link #finaliseRating()} converts and publishes those working values, then clears the working
 * fields.</p>
 */
public class Rating implements IRating {

    public String name; // not actually used by the calculation engine but useful to track whose rating is whose
    public double rating;
    public double ratingDeviation;
    public double volatility;
    public int numberOfResults = 0; // the number of results from which the rating has been calculated

    // the following variables are used to hold values temporarily whilst running calculations
    public double workingRating;
    public double workingRatingDeviation;
    public double workingVolatility;

    /**
     * Creates an uninitialized rating whose numeric fields retain their Java default values.
     *
     * <p>Prefer {@link #Rating(RatingCalculator)} when a rating should begin with the calculator's
     * configured defaults.</p>
     */
    public Rating() {

    }

    /**
     * Creates a rating initialized from a calculator's default rating, deviation, and volatility.
     *
     * @param ratingSystem calculator that supplies the initial rating parameters
     */
    public Rating(RatingCalculator ratingSystem) {
        this.rating = ratingSystem.getDefaultRating();
        this.ratingDeviation = ratingSystem.getDefaultRatingDeviation();
        this.volatility = ratingSystem.getDefaultVolatility();
    }

    /**
     * Creates a rating with explicitly supplied public-scale rating parameters.
     *
     * @param name optional participant label used for inspection/debug output, not calculations
     * @param ratingSystem calculator associated with the caller's rating system; retained for API
     *                     compatibility and not read by this constructor
     * @param initRating initial skill estimate on the ordinary Glicko scale
     * @param initRatingDeviation initial rating uncertainty on the ordinary Glicko scale
     * @param initVolatility initial Glicko-2 volatility
     */
    public Rating(String name, RatingCalculator ratingSystem, double initRating, double initRatingDeviation, double initVolatility) {
        this.name = name;
        this.rating = initRating;
        this.ratingDeviation = initRatingDeviation;
        this.volatility = initVolatility;
    }

    @Override
    public double getRating() {
        return this.rating;
    }

    @Override
    public void setRating(double rating) {
        this.rating = rating;
    }

    /**
     * Returns the current skill estimate converted to the Glicko-2 internal scale used by the
     * calculation algorithm.
     *
     * @return current rating on the Glicko-2 internal scale
     */
    public double getGlicko2Rating() {
        return RatingCalculator.convertRatingToGlicko2Scale(this.rating);
    }

    /**
     * Converts an internal-scale Glicko-2 rating back to the ordinary presentation scale and stores
     * it as the current rating.
     *
     * @param rating rating value on the Glicko-2 internal scale
     */
    public void setGlicko2Rating(double rating) {
        this.rating = RatingCalculator.convertRatingToOriginalGlickoScale(rating);
    }

    @Override
    public double getVolatility() {
        return volatility;
    }

    @Override
    public void setVolatility(double volatility) {
        this.volatility = volatility;
    }

    @Override
    public double getRatingDeviation() {
        return ratingDeviation;
    }

    @Override
    public void setRatingDeviation(double ratingDeviation) {
        this.ratingDeviation = ratingDeviation;
    }

    /**
     * Returns the current rating deviation converted to the Glicko-2 internal scale.
     *
     * @return current rating deviation on the Glicko-2 internal scale
     */
    public double getGlicko2RatingDeviation() {
        return RatingCalculator.convertRatingDeviationToGlicko2Scale(ratingDeviation);
    }

    /**
     * Converts an internal-scale Glicko-2 deviation back to the ordinary presentation scale and
     * stores it as the current deviation.
     *
     * @param ratingDeviation rating deviation on the Glicko-2 internal scale
     */
    public void setGlicko2RatingDeviation(double ratingDeviation) {
        this.ratingDeviation = RatingCalculator.convertRatingDeviationToOriginalGlickoScale(ratingDeviation);
    }

    /**
     * Publishes the working values calculated for the current rating period.
     *
     * <p>Working rating and deviation are converted from the Glicko-2 internal scale to the ordinary
     * presentation scale. Working volatility is copied directly. All three working values are then
     * reset to zero so stale period state cannot be mistaken for another pending calculation.</p>
     */
    public void finaliseRating() {
        this.setGlicko2Rating(workingRating);
        this.setGlicko2RatingDeviation(workingRatingDeviation);
        this.setVolatility(workingVolatility);

        this.setWorkingRatingDeviation(0);
        this.setWorkingRating(0);
        this.setWorkingVolatility(0);
    }

    /**
     * Returns a compact diagnostic representation containing participant label, rating, deviation,
     * volatility, and accumulated result count.
     *
     * @return diagnostic rating summary
     */
    @Override
    public String toString() {
        return name + " / " +
                rating + " / " +
                ratingDeviation + " / " +
                volatility + " / " +
                numberOfResults;
    }

    /**
     * Returns the number of match results that have contributed to completed rating-period updates.
     *
     * @return accumulated contributing result count
     */
    public int getNumberOfResults() {
        return numberOfResults;
    }

    /**
     * Adds a number of contributing match results to this rating's accumulated count.
     *
     * @param increment number of results to add
     */
    public void incrementNumberOfResults(int increment) {
        this.numberOfResults = numberOfResults + increment;
    }

    /**
     * Returns the optional participant label associated with this rating.
     *
     * @return participant label; may be {@code null}
     */
    public String getName() {
        return name;
    }

    /**
     * Stores the volatility calculated for the current in-progress rating period.
     *
     * @param workingVolatility pending volatility value
     */
    public void setWorkingVolatility(double workingVolatility) {
        this.workingVolatility = workingVolatility;
    }

    /**
     * Stores the skill estimate calculated for the current period on the Glicko-2 internal scale.
     *
     * @param workingRating pending internal-scale rating
     */
    public void setWorkingRating(double workingRating) {
        this.workingRating = workingRating;
    }

    /**
     * Stores the rating deviation calculated for the current period on the Glicko-2 internal scale.
     *
     * @param workingRatingDeviation pending internal-scale rating deviation
     */
    public void setWorkingRatingDeviation(double workingRatingDeviation) {
        this.workingRatingDeviation = workingRatingDeviation;
    }
}
