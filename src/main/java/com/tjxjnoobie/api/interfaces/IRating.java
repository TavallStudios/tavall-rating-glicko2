package com.tjxjnoobie.api.interfaces;

/**
 * Interface for rating calculations and management.
 */
public interface IRating extends org.tavall.dependency.IDependencyInjectableInterface {

    /**
     * Gets the current rating value.
     *
     * @return the rating value
     */
    double getRating();

    /**
     * Sets the current rating value.
     *
     * @param rating the rating to set
     */
    void setRating(double rating);

    /**
     * Gets the rating deviation.
     *
     * @return the rating deviation
     */
    double getRatingDeviation();

    /**
     * Sets the rating deviation.
     *
     * @param deviation the deviation to set
     */
    void setRatingDeviation(double deviation);

    /**
     * Gets the volatility.
     *
     * @return the volatility value
     */
    double getVolatility();

    /**
     * Sets the volatility.
     *
     * @param volatility the volatility to set
     */
    void setVolatility(double volatility);
}
