package com.tjxjnoobie.api.interfaces;

/**
 * Mutable player-rating state used by the Glicko-2 calculation engine.
 *
 * <p>The public rating and rating-deviation values use the ordinary Glicko presentation scale.
 * Volatility is the Glicko-2 volatility term that controls how quickly rating uncertainty may
 * change between rating periods. Implementations may expose additional working values used only
 * while a rating period is being calculated.</p>
 */
public interface IRating extends org.tavall.dependency.IDependencyInjectableInterface {

    /**
     * Returns the current estimate of player skill on the ordinary Glicko rating scale.
     *
     * @return current skill estimate
     */
    double getRating();

    /**
     * Replaces the current skill estimate on the ordinary Glicko rating scale.
     *
     * @param rating new skill estimate
     */
    void setRating(double rating);

    /**
     * Returns the current uncertainty around the skill estimate on the ordinary Glicko scale.
     *
     * <p>Higher deviation represents less confidence in the current rating.</p>
     *
     * @return current rating deviation
     */
    double getRatingDeviation();

    /**
     * Replaces the uncertainty around the current skill estimate on the ordinary Glicko scale.
     *
     * @param deviation new rating deviation
     */
    void setRatingDeviation(double deviation);

    /**
     * Returns the Glicko-2 volatility term for this rating.
     *
     * <p>Volatility models expected fluctuation in the player's underlying skill and participates in
     * updating rating deviation between periods.</p>
     *
     * @return current volatility value
     */
    double getVolatility();

    /**
     * Replaces the Glicko-2 volatility term used by subsequent rating calculations.
     *
     * @param volatility new volatility value
     */
    void setVolatility(double volatility);
}
