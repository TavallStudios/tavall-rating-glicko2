package com.tjxjnoobie.api.internal.utils.glickov2;

import java.util.List;

/**
 * Glicko-2 rating-period calculator using the algorithm's internal scale during computation and the
 * ordinary Glicko scale for published {@link Rating} values.
 *
 * <p>All participants are calculated into working fields before any new rating is published. This
 * prevents later participants in the same period from being evaluated against already-updated
 * opponents. At the end of {@link #updateRatings(RatingPeriodResults)}, working values are finalized
 * for every participant and the period's recorded match results are cleared.</p>
 */
public class RatingCalculator {

    private static final double DEFAULT_RATING = 1500.0;
    private static final double DEFAULT_DEVIATION = 350;
    private static final double DEFAULT_VOLATILITY = 0.06;
    private static final double DEFAULT_TAU = 0.75;
    private static final double MULTIPLIER = 173.7178;
    private static final double CONVERGENCE_TOLERANCE = 0.000001;

    private double tau;
    private double defaultVolatility;

    /**
     * Creates a calculator using the library defaults of volatility {@code 0.06} and tau
     * {@code 0.75}.
     */
    public RatingCalculator() {
        tau = DEFAULT_TAU;
        defaultVolatility = DEFAULT_VOLATILITY;
    }

    /**
     * Creates a calculator with custom volatility defaults and volatility constraint.
     *
     * @param initVolatility volatility assigned to newly created ratings
     * @param tau system constant that constrains how quickly volatility may change between periods
     */
    public RatingCalculator(double initVolatility, double tau) {
        this.defaultVolatility = initVolatility;
        this.tau = tau;
    }

    /**
     * Calculates and publishes the next rating-period state for every known participant.
     *
     * <p>Participants with results receive the complete Glicko-2 update. Participants with no
     * results retain rating and volatility while their rating deviation increases according to the
     * inactivity step of the algorithm. All participant updates are staged in working fields before
     * being finalized so period calculations use a consistent pre-period snapshot.</p>
     *
     * <p>After publishing all new ratings, this method calls {@link RatingPeriodResults#clear()}.
     * That clears recorded match results but intentionally preserves the period's participant set.</p>
     *
     * @param results rating-period results and participants to process
     */
    public void updateRatings(RatingPeriodResults results) {
        for (Rating player : results.getParticipants()) {
            if (results.getResults(player).size() > 0) {
                calculateNewRating(player, results.getResults(player));
            } else {
                player.setWorkingRating(player.getGlicko2Rating());
                player.setWorkingRatingDeviation(calculateNewRD(player.getGlicko2RatingDeviation(), player.getVolatility()));
                player.setWorkingVolatility(player.getVolatility());
            }
        }

        for (Rating player : results.getParticipants()) {
            player.finaliseRating();
        }

        results.clear();
    }

    private void calculateNewRating(Rating player, List<Result> results) {
        double phi = player.getGlicko2RatingDeviation();
        double sigma = player.getVolatility();
        double a = Math.log(Math.pow(sigma, 2));
        double delta = delta(player, results);
        double v = v(player, results);

        double A = a;
        double B;
        if (Math.pow(delta, 2) > Math.pow(phi, 2) + v) {
            B = Math.log(Math.pow(delta, 2) - Math.pow(phi, 2) - v);
        } else {
            double k = 1;
            B = a - (k * Math.abs(tau));

            while (f(B, delta, phi, v, a, tau) < 0) {
                k++;
                B = a - (k * Math.abs(tau));
            }
        }

        double fA = f(A, delta, phi, v, a, tau);
        double fB = f(B, delta, phi, v, a, tau);

        while (Math.abs(B - A) > CONVERGENCE_TOLERANCE) {
            double C = A + (((A - B) * fA) / (fB - fA));
            double fC = f(C, delta, phi, v, a, tau);

            if (fC * fB < 0) {
                A = B;
                fA = fB;
            } else {
                fA = fA / 2.0;
            }

            B = C;
            fB = fC;
        }

        double newSigma = Math.exp(A / 2.0);
        player.setWorkingVolatility(newSigma);

        double phiStar = calculateNewRD(phi, newSigma);
        double newPhi = 1.0 / Math.sqrt((1.0 / Math.pow(phiStar, 2)) + (1.0 / v));

        player.setWorkingRating(
                player.getGlicko2Rating()
                        + (Math.pow(newPhi, 2) * outcomeBasedRating(player, results)));
        player.setWorkingRatingDeviation(newPhi);
        player.incrementNumberOfResults(results.size());
    }

    private double f(double x, double delta, double phi, double v, double a, double tau) {
        return (Math.exp(x) * (Math.pow(delta, 2) - Math.pow(phi, 2) - v - Math.exp(x)) /
                (2.0 * Math.pow(Math.pow(phi, 2) + v + Math.exp(x), 2))) -
                ((x - a) / Math.pow(tau, 2));
    }

    private double g(double deviation) {
        return 1.0 / (Math.sqrt(1.0 + (3.0 * Math.pow(deviation, 2) / Math.pow(Math.PI, 2))));
    }

    private double E(double playerRating, double opponentRating, double opponentDeviation) {
        return 1.0 / (1.0 + Math.exp(-1.0 * g(opponentDeviation) * (playerRating - opponentRating)));
    }

    private double v(Rating player, List<Result> results) {
        double v = 0.0;

        for (Result result : results) {
            v = v + (
                    (Math.pow(g(result.getOpponent(player).getGlicko2RatingDeviation()), 2))
                            * E(player.getGlicko2Rating(),
                            result.getOpponent(player).getGlicko2Rating(),
                            result.getOpponent(player).getGlicko2RatingDeviation())
                            * (1.0 - E(player.getGlicko2Rating(),
                            result.getOpponent(player).getGlicko2Rating(),
                            result.getOpponent(player).getGlicko2RatingDeviation())
                    ));
        }

        return Math.pow(v, -1);
    }

    private double delta(Rating player, List<Result> results) {
        return v(player, results) * outcomeBasedRating(player, results);
    }

    private double outcomeBasedRating(Rating player, List<Result> results) {
        double outcomeBasedRating = 0;

        for (Result result : results) {
            outcomeBasedRating = outcomeBasedRating
                    + (g(result.getOpponent(player).getGlicko2RatingDeviation())
                    * (result.getScore(player) - E(
                    player.getGlicko2Rating(),
                    result.getOpponent(player).getGlicko2Rating(),
                    result.getOpponent(player).getGlicko2RatingDeviation()))
            );
        }

        return outcomeBasedRating;
    }

    private double calculateNewRD(double phi, double sigma) {
        return Math.sqrt(Math.pow(phi, 2) + Math.pow(sigma, 2));
    }

    /**
     * Converts an internal-scale Glicko-2 rating to the ordinary Glicko presentation scale centered
     * on the library default rating of 1500.
     *
     * @param rating rating on the Glicko-2 internal scale
     * @return rating on the ordinary Glicko scale
     */
    public static double convertRatingToOriginalGlickoScale(double rating) {
        return (rating * MULTIPLIER) + DEFAULT_RATING;
    }

    /**
     * Converts an ordinary Glicko rating to the centered/scaled value used by the Glicko-2
     * algorithm.
     *
     * @param rating rating on the ordinary Glicko scale
     * @return rating on the Glicko-2 internal scale
     */
    public static double convertRatingToGlicko2Scale(double rating) {
        return (rating - DEFAULT_RATING) / MULTIPLIER;
    }

    /**
     * Converts an internal-scale Glicko-2 rating deviation to the ordinary Glicko presentation
     * scale.
     *
     * @param ratingDeviation deviation on the Glicko-2 internal scale
     * @return deviation on the ordinary Glicko scale
     */
    public static double convertRatingDeviationToOriginalGlickoScale(double ratingDeviation) {
        return ratingDeviation * MULTIPLIER;
    }

    /**
     * Converts an ordinary Glicko rating deviation to the value used by the Glicko-2 algorithm.
     *
     * @param ratingDeviation deviation on the ordinary Glicko scale
     * @return deviation on the Glicko-2 internal scale
     */
    public static double convertRatingDeviationToGlicko2Scale(double ratingDeviation) {
        return ratingDeviation / MULTIPLIER;
    }

    /**
     * Returns the ordinary-scale rating assigned to new players.
     *
     * @return default rating, currently {@code 1500.0}
     */
    public double getDefaultRating() {
        return DEFAULT_RATING;
    }

    /**
     * Returns the volatility assigned to new players by this calculator instance.
     *
     * @return configured default volatility
     */
    public double getDefaultVolatility() {
        return defaultVolatility;
    }

    /**
     * Returns the ordinary-scale rating deviation assigned to new players.
     *
     * @return default rating deviation, currently {@code 350.0}
     */
    public double getDefaultRatingDeviation() {
        return DEFAULT_DEVIATION;
    }
}
