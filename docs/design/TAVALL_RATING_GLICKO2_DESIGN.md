> **Document Type:** `DESIGN`
> **Scope:** `MODULE`
> **Lifecycle:** `FINAL_DRAFT`
> **Status:** Validating
> **Owning Product / Platform:** Tavall Java Tools
> **Primary Repository:** `TavallStudios/tavall-rating-glicko2`
> **Owning Module:** `tavall-rating-glicko2`
> **Owning GENERAL:** Module README
> **Technical Document:** Current source and Gradle build files
> **Progression Document:** [Tavall Rating Glicko-2 — PROGRESSION](https://github.com/TavallStudios/tavall-rating-glicko2/blob/docs/module-design-progression-2026-09-28/docs/progression/TAVALL_RATING_GLICKO2_PROGRESSION.md)
> **Last Reconciled:** 2026-09-27 8:06 PM PDT
>
> **Draft note:** This source-backed draft separates the rating library from Tavall PvP product rules. Design questions that the current source does not settle are listed explicitly below.
## About
`tavall-rating-glicko2` is a Java library for rating profiles, two-player outcomes, and Glicko-2 rating-period updates. Its source includes rating, rating-deviation, and volatility values; win, loss, and draw results; and a calculator that updates a staged period of results.
The module gives Java consumers a reusable calculation boundary. It does not define what evidence a product should submit, where profile data is stored, or how ratings are presented. Tavall PvP GENERAL/DESIGN documents describe product-level competitive behavior; they do not by themselves establish that the product consumes this repository's artifact.
## Scope & Ownership
### Owns
- Rating profile values: rating, rating deviation, and volatility.
- Pairwise win, loss, and draw outcome records.
- Glicko-2 rating-period calculation and scale conversion.
- A default calculator configuration and an explicit way to supply volatility and `tau`.
### Does Not Own
- Match or round lifecycle, combat/evidence attribution, player identity, persistence, matchmaking, grades, leaderboards, career history, or product presentation.
- A runtime service or deployment.
- The product policy that turns events, matches, placements, or weighted evidence into rating outcomes.
### Parent / Child Boundaries
This is a `LIBRARY` module within Tavall Java Tools. It declares Tavall DI as an API dependency because `IRating` extends the DI injectable-interface contract. It has no child modules. Consumers own outcome collection and persistence; this library owns only the submitted profile/result calculation boundary.
## Design Contract
This draft proposes the following contract based on the current module boundary and source:
1. A profile contains rating, rating deviation (RD), and volatility.
2. A result compares two distinct rating profiles and represents a win/loss or draw.
3. A rating period can include participants without a recorded result so their uncertainty can grow without an outcome update.
4. A calculator computes all participants against the period's original profile values, then commits the working values after calculation.
5. No persistence, identity, product grade, or runtime authority crosses into this library.
The implementation currently uses a 1500 rating baseline, 350 RD, 0.06 volatility, `tau = 0.75`, scale multiplier 173.7178, and a 1e-6 convergence tolerance. These values and edge-case behaviors remain part of the draft until reference-vector and consumer-compatibility checks confirm the intended contract.
## Public / Consumer Shape
The source currently exposes `IRating` under `com.tjxjnoobie.api.interfaces`. It provides getters and setters for rating, rating deviation, and volatility and extends Tavall DI's `IDependencyInjectableInterface`.
The concrete types `Rating`, `Result`, `RatingPeriodResults`, and `RatingCalculator` are public Java classes under `com.tjxjnoobie.api.internal.utils.glickov2`. The `internal` package location makes their supported consumer status unclear even though the Java classes are public. This draft does not treat that package as a stable API until ownership confirms it.
The current consumer flow is:
1. Construct or obtain rating profiles.
2. Add win/loss or draw outcomes to `RatingPeriodResults`; register no-result participants when their RD should grow.
3. Call `RatingCalculator.updateRatings(...)`.
4. Read committed profile values and let the consumer persist/project them.
## Composition
The root Gradle project is the single module `tavall-rating-glicko2`. It uses Java 25 and declares `api("org.tavall:tavall-di:1.0.0")`. The module does not absorb persistence, storage, cache, scheduling, events, or a product runtime merely to satisfy a generic platform checklist.
A product that uses this module remains responsible for validating evidence, deciding how many outcomes to submit per period, and atomically persisting the resulting values.
## Data & State Boundaries
Profile and rating-period objects are mutable in memory. `Rating` contains current profile fields and temporary working fields used during calculation. `RatingPeriodResults` holds result records and an explicit participant set.
Current source clears result records at the end of `updateRatings` but retains the participant set. Whether that roster retention is intended for later periods, and whether a period should instead be a single-use object, remains an open question. The library has no persistence authority.
## Runtime / Interaction Flows
- **Outcome update:** results identify participants and opponents; the calculator calculates the new values, stages them, commits the staged values, increments result counts, then clears recorded results.
- **No-result participant:** the calculator keeps rating and volatility and increases RD using the current volatility.
- **Consumer persistence:** the caller owns all durable writes and recovery behavior; the library returns no transaction or persistence boundary.
## Integrations
| Integration | Boundary |
| --- | --- |
| Tavall DI | Declared Gradle API dependency; the public `IRating` extends its injectable-interface contract. |
| Tavall PvP / `novus-ffa` | Product documents describe Glicko-2 rating behavior. A current source dependency on this artifact was not confirmed in this audit. |
| GitHub Packages | Gradle declares conditional Maven publication when package credentials are present; publication is not part of the runtime contract. |

## Validation Requirements
Before this draft can be promoted to `FINAL`:
- Agree on stable consumer API packages and whether the concrete classes under `internal` are supported.
- Add deterministic reference vectors for standard updates, draws, inactive participants, multiple opponents, and rating-period reuse.
- Define and test invalid input behavior, including null profiles and numeric ranges.
- Verify no participant is updated against another participant's newly committed values within the same period.
- Run exact-head Java 25 `clean check`, verify dependency locks, and check packaged JAR contents in the authorized Tavall execution environment.
- Establish whether `.tavallci/ci.yaml` is required for this module and add the repository-local definition through its own reviewed change.
Actual results belong in PROGRESSION.
## Design Decisions & Open Questions
### Decisions represented in this draft
- The library owns Glicko-2 calculations and rating profile values; products own evidence policy and persistence.
- It remains a `LIBRARY`, not a deployed runtime.
- Tavall DI remains a declared API boundary already present in the build.
### Open Questions
- Which types and packages are the supported consumer API?
- Should `RatingPeriodResults.clear()` also clear explicitly registered participants?
- What null, range, and non-finite-number behavior is required?
- Are the current defaults and per-result weighting the intended stable policy?
- Which current product repository, if any, consumes this artifact rather than an independent implementation?
## Documentation
- **Parent / Aggregate DESIGN:** Tavall Java Tools & Architecture (Notion)
- **Child DESIGN Documents:** N/A
- **GENERAL:** [Module README](https://github.com/TavallStudios/tavall-rating-glicko2/blob/main/README.md)
- **Technical:** Current source and `build.gradle.kts`
- **PROGRESSION:** [Tavall Rating Glicko-2 — PROGRESSION](https://github.com/TavallStudios/tavall-rating-glicko2/blob/docs/module-design-progression-2026-09-28/docs/progression/TAVALL_RATING_GLICKO2_PROGRESSION.md)
- **Deployment:** N/A — reusable Java library; no independent runtime
- **Implementation:** `src/main/java/com/tjxjnoobie/api`
## Final Rules Summary
- Keep evidence collection, persistence, and product presentation in the consumer.
- Calculate a complete rating period before committing participant values.
- Preserve the distinction between observed source behavior and an accepted stable API.
- Require reference-vector and Java 25 exact-source evidence before calling the algorithm verified.
## Documentation Update State

| Surface | Sync State | Location | Last Updated | Evidence |
| --- | --- | --- | --- | --- |
| GitHub | `SYNCHRONIZED` | [`docs/design/TAVALL_RATING_GLICKO2_DESIGN.md`](https://github.com/TavallStudios/tavall-rating-glicko2/blob/docs/module-design-progression-2026-09-28/docs/design/TAVALL_RATING_GLICKO2_DESIGN.md) | 2026-09-27 8:06 PM PDT | Open Draft [PR #8](https://github.com/TavallStudios/tavall-rating-glicko2/pull/8); DESIGN content synchronized with its Notion twin. |
| Notion | `SYNCHRONIZED` | [Tavall Rating Glicko-2 — DESIGN](https://app.notion.com/p/3e938458ddfd81738fbbe0d45904739d) | 2026-09-27 8:06 PM PDT | 1:1 twin under Platform & Infrastructure; same contract and lifecycle as the GitHub document. |

### Update History

| Timestamp | Surface | Event | Location | Previous Location | Evidence | Notes |
| --- | --- | --- | --- | --- | --- | --- |
| 2026-09-27 8:06 PM PDT | GitHub | `CREATED` | `docs/design/TAVALL_RATING_GLICKO2_DESIGN.md` | — | Draft PR #8 against current main. | Added module ownership, consumer shape, validation gates, and explicit open questions. |
| 2026-09-27 8:06 PM PDT | Notion | `SYNCHRONIZED` | [Module DESIGN page](https://app.notion.com/p/3e938458ddfd81738fbbe0d45904739d) | New page | Compared with GitHub DESIGN in PR #8. | Kept both copies at the same draft lifecycle and contract. |

## DOC TODO
- [ ] Confirm the public consumer packages and period-roster behavior.
- [ ] Add and run the algorithm reference-vector suite.
- [ ] Keep this GitHub document and its Notion twin synchronized 1:1.