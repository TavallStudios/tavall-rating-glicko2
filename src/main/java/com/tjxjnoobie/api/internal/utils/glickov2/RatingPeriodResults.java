package com.tjxjnoobie.api.internal.utils.glickov2;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Mutable collection of match results and participants for one Glicko-2 rating period.
 *
 * <p>Participants may be registered explicitly even when they have no results, allowing inactivity
 * handling to update their deviation. Calling {@link #getParticipants()} also folds every
 * participant discovered in recorded results into the participant set.</p>
 */
public class RatingPeriodResults {
    private List<Result> results = new ArrayList<Result>();
    private Set<Rating> participants = new HashSet<Rating>();

    /**
     * Creates an empty rating-period result set.
     */
    public RatingPeriodResults() {}

    /**
     * Creates a rating period backed by an existing participant set.
     *
     * <p>The set reference is retained directly rather than copied, so later mutations through
     * either reference are visible to the other.</p>
     *
     * @param participants initial participant set
     */
    public RatingPeriodResults(Set<Rating> participants) {
        this.participants = participants;
    }

    /**
     * Records a decisive match result for the current rating period.
     *
     * @param winner winning participant
     * @param loser losing participant
     * @throws IllegalArgumentException if the result cannot represent the supplied participants
     */
    public void addResult(Rating winner, Rating loser) {
        Result result = new Result(winner, loser);
        results.add(result);
    }

    /**
     * Records a drawn match for the current rating period.
     *
     * @param player1 first participant
     * @param player2 second participant
     * @throws IllegalArgumentException if the result cannot represent the supplied participants
     */
    public void addDraw(Rating player1, Rating player2) {
        Result result = new Result(player1, player2, true);
        results.add(result);
    }

    /**
     * Returns the recorded results in which a participant appears.
     *
     * <p>The returned list is a new mutable list; changing it does not change the rating period.</p>
     *
     * @param player participant whose matches should be selected
     * @return matching results in their original recording order
     */
    public List<Result> getResults(Rating player) {
        List<Result> filteredResults = new ArrayList<Result>();

        for (Result result : results) {
            if (result.participated(player)) {
                filteredResults.add(result);
            }
        }

        return filteredResults;
    }

    /**
     * Returns all participants known to the period, including participants discovered from results.
     *
     * <p>This method mutates the internally held participant set by adding result participants and
     * then returns that same mutable set reference. Callers therefore can change the period's
     * explicit participant membership through the returned set.</p>
     *
     * @return live participant set
     */
    public Set<Rating> getParticipants() {
        for (Result result : results) {
            participants.add(result.getWinner());
            participants.add(result.getLoser());
        }

        return participants;
    }

    /**
     * Adds a participant that should receive a rating-period update even when no match result was
     * recorded for them.
     *
     * @param rating participant to include in the period
     */
    public void addParticipants(Rating rating) {
        participants.add(rating);
    }

    /**
     * Removes every recorded match result while preserving the explicit/discovered participant set.
     *
     * <p>This is intentionally not a full object reset. Participants remain registered for future
     * calculations or result additions.</p>
     */
    public void clear() {
        results.clear();
    }
}
