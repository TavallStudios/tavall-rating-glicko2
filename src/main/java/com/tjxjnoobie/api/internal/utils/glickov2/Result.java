package com.tjxjnoobie.api.internal.utils.glickov2;

/**
 * One head-to-head result consumed by a Glicko-2 rating period.
 *
 * <p>A result stores two participant references and either a decisive winner/loser relationship or
 * a draw marker. Scores are exposed from the perspective of a requested participant as 1.0 for a
 * win, 0.5 for a draw, and 0.0 for a loss.</p>
 */
public class Result {
    private static final double POINTS_FOR_WIN = 1.0;
    private static final double POINTS_FOR_LOSS = 0.0;
    private static final double POINTS_FOR_DRAW = 0.5;

    private boolean isDraw = false;
    private Rating winner;
    private Rating loser;

    /**
     * Records a decisive match result.
     *
     * @param winner participant that won the match
     * @param loser participant that lost the match
     * @throws IllegalArgumentException if both arguments refer to equal ratings, because a
     *                                  participant cannot play itself
     */
    public Result(Rating winner, Rating loser) {
        if (!validPlayers(winner, loser)) {
            throw new IllegalArgumentException();
        }

        this.winner = winner;
        this.loser = loser;
    }

    /**
     * Records a drawn match between two participants.
     *
     * <p>The boolean exists only to distinguish this overload from the decisive-result constructor
     * and must therefore be {@code true}.</p>
     *
     * @param player1 first participant; stored in the winner slot for structural compatibility
     * @param player2 second participant; stored in the loser slot for structural compatibility
     * @param isDraw must be {@code true}
     * @throws IllegalArgumentException if {@code isDraw} is false or both participants are equal
     */
    public Result(Rating player1, Rating player2, boolean isDraw) {
        if (!isDraw || !validPlayers(player1, player2)) {
            throw new IllegalArgumentException();
        }

        this.winner = player1;
        this.loser = player2;
        this.isDraw = true;
    }

    private boolean validPlayers(Rating player1, Rating player2) {
        return !player1.equals(player2);
    }

    /**
     * Tests whether a rating is one of the two participants represented by this result.
     *
     * @param player rating to test
     * @return {@code true} when the rating equals either stored participant
     */
    public boolean participated(Rating player) {
        return winner.equals(player) || loser.equals(player);
    }

    /**
     * Returns this result's score from one participant's perspective.
     *
     * @param player participant whose score should be returned
     * @return {@code 1.0} for a win, {@code 0.5} for a draw, or {@code 0.0} for a loss
     * @throws IllegalArgumentException if the supplied rating did not participate in this result
     */
    public double getScore(Rating player) throws IllegalArgumentException {
        double score;

        if (winner.equals(player)) {
            score = POINTS_FOR_WIN;
        } else if (loser.equals(player)) {
            score = POINTS_FOR_LOSS;
        } else {
            throw new IllegalArgumentException("Player " + player.getName() + " did not participate in match");
        }

        if (isDraw) {
            score = POINTS_FOR_DRAW;
        }

        return score;
    }

    /**
     * Resolves the other participant in this result.
     *
     * @param player participant whose opponent should be returned
     * @return the other stored participant
     * @throws IllegalArgumentException if the supplied rating did not participate in this result
     */
    public Rating getOpponent(Rating player) {
        if (winner.equals(player)) {
            return loser;
        } else if (loser.equals(player)) {
            return winner;
        }
        throw new IllegalArgumentException("Player " + player.getName() + " did not participate in match");
    }

    /**
     * Returns the decisive winner, or the first participant for a drawn result.
     *
     * @return participant stored in the winner slot
     */
    public Rating getWinner() {
        return this.winner;
    }

    /**
     * Returns the decisive loser, or the second participant for a drawn result.
     *
     * @return participant stored in the loser slot
     */
    public Rating getLoser() {
        return this.loser;
    }
}
