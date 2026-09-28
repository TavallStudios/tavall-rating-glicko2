> **Status:** Active progression record
> **Document Type:** `PROGRESSION`
> **Progression Scope:** `MODULE`
> **Module Type:** `LIBRARY`
> **Owning System:** Tavall Java Tools / Glicko-2 Rating
> **Owns:** Audited implementation, integration, validation, and historical progression for the Glicko-2 rating library
> **Does Not Own:** Product evidence policy, persistence, match lifecycle, aggregate system status, deployment history, or Git workflow policy
> **Audited Against:** `TavallStudios/tavall-rating-glicko2@ec35f280a26b07b5b6340c7ab141e5613715b3a8`
> **Last Reconciled:** 2026-09-27 8:06 PM PDT
> **Notion Twin:** [Tavall Rating Glicko-2 — PROGRESSION](https://app.notion.com/p/3e938458ddfd815c93c3caba263b4710)
## About
This record reports what exists in the current main source and what has or has not been verified. The five Java source files and Gradle declarations establish implementation presence; they do not establish algorithm correctness, API stability, package publication, or product adoption.
## Module Context
| Field | Value |
| --- | --- |
| Repository | `TavallStudios/tavall-rating-glicko2` |
| Module | `tavall-rating-glicko2` (single root Gradle project) |
| Module Type | `LIBRARY` |
| Runtime Owner | None confirmed; this library has no independent runtime |
| Current-main baseline | `ec35f280a26b07b5b6340c7ab141e5613715b3a8` |
| Main-source candidate | `src/main/java` |
| Current source files | `IRating`, `Rating`, `RatingCalculator`, `RatingPeriodResults`, `Result` |
| Main consumer evidence | No source dependency on this artifact was confirmed in this audit. Tavall PvP documents describe Glicko-2 product behavior but do not establish use of this repository's library. |
| Design | [Tavall Rating Glicko-2 — DESIGN](https://github.com/TavallStudios/tavall-rating-glicko2/blob/docs/module-design-progression-2026-09-28/docs/design/TAVALL_RATING_GLICKO2_DESIGN.md) |

## Progression Timeline
| Date / Time | State | Progression | Evidence | Result / Remaining Work |
| --- | --- | --- | --- | --- |
| 2026-04-06 5:03 AM PDT | `MERGED_PRODUCTION` | Initial Glicko-2 rating engine | [`45d07e04`](https://github.com/TavallStudios/tavall-rating-glicko2/commit/45d07e04b86859cc8700e432602ea00aaf90a40a) | Introduced the rating engine in source history; this audit does not establish tests or runtime acceptance for that revision. |
| 2026-07-23 12:03 PM PDT | `MERGED_PRODUCTION` | Gradle and Java 25 migration | [`3472968d`](https://github.com/TavallStudios/tavall-rating-glicko2/commit/3472968d153160099674f70b789dc7e2a2dfe72d) | Migrated the library build; current-main build verification remains outstanding. |
| 2026-07-24 2:08 AM PDT | `MERGED_PRODUCTION` | Rating API contract restored | [`c5178a31`](https://github.com/TavallStudios/tavall-rating-glicko2/commit/c5178a312a57488eab0d8dedc073cd45d69fbc91) | Restored the `IRating` API boundary recorded in the current source tree. |
| 2026-08-15 12:04 PM PDT | `VALID_UNMERGED_PR` | Tavall Java Tools platform-adoption draft opened | [PR #3](https://github.com/TavallStudios/tavall-rating-glicko2/pull/3) | The PR remains a draft against `staging/platform`; its current head is not part of audited main. Its body lists Java 25 verification and dependency-lock refresh as pending. |
| 2026-09-27 7:57 PM PDT | `IN_PROGRESS` | Current-main source and documentation reconciliation | [`ec35f280`](https://github.com/TavallStudios/tavall-rating-glicko2/commit/ec35f280a26b07b5b6340c7ab141e5613715b3a8) | Confirmed one root Gradle module, five Java main-source files, no test sources, and no module-local CI definition; no code validation was run. |

Timeline entries are ordered oldest to newest. Historical source commits document changes; they do not imply that tests or runtime acceptance passed.
## Current Status
| Field | State |
| --- | --- |
| Overall State | `PARTIAL` |
| Source inventory | Complete for current main tree |
| Implementation | Five Java source files provide the profile interface, profile implementation, pairwise outcomes, period result collection, and Glicko-2 calculator. |
| Behavioral validation | `NOT_RUN` — current main contains no `src/test` tree in the audited recursive repository tree. |
| Build validation | `NOT_RUN` in the authorized exact-source environment. |
| Dependency-lock validation | Pending; open platform-adoption PR #3 states the lock refresh is pending. |
| CI definition | `MISSING` — no module-local `.tavallci/ci.yaml` was found at the audited revision. A GitHub Actions Gradle workflow exists, but it is not a Tavall-local exact-source result. |
| Consumer adoption | Unconfirmed for this artifact. |
| Publication | Build configuration declares conditional GitHub Packages publication; no package publish was verified. |

## Source Inventory
| Source | Observed responsibility |
| --- | --- |
| `src/main/java/com/tjxjnoobie/api/interfaces/IRating.java` | Rating, rating-deviation, and volatility accessors; extends Tavall DI's injectable-interface contract. |
| `src/main/java/com/tjxjnoobie/api/internal/utils/glickov2/Rating.java` | Mutable profile, result count, internal working values, scale helpers, and finalization. |
| `src/main/java/com/tjxjnoobie/api/internal/utils/glickov2/Result.java` | Win/loss/draw scores and opponent/participant accessors; rejects equal participants and requires `isDraw=true` for the draw constructor. |
| `src/main/java/com/tjxjnoobie/api/internal/utils/glickov2/RatingPeriodResults.java` | Outcome list and participant set; supports inactive participants; `clear()` clears outcomes but retains the set. |
| `src/main/java/com/tjxjnoobie/api/internal/utils/glickov2/RatingCalculator.java` | Glicko-2 period update, volatility iteration, inactive-participant RD growth, conversions, and default values. |

The calculator's current defaults are rating 1500, RD 350, volatility 0.06, `tau = 0.75`, scale multiplier 173.7178, and convergence tolerance 1e-6. These are observed source values, not independently validated behavior.
## Build, Packaging & CI
- Root Gradle project name is `tavall-rating-glicko2`; Java toolchain is 25.
- Build applies `java-library` and `maven-publish`, creates sources/Javadoc JARs, and declares `api("org.tavall:tavall-di:1.0.0")`.
- All configurations are locked. JAR timestamps and ordering are configured for reproducibility.
- `check` depends on `verifyJarContents`, which checks for selected third-party package namespaces in the JAR.
- The GitHub Actions workflow bootstraps Tavall Logging and DI into Maven local, then runs `clean check publishToMavenLocal` on GitHub-hosted Java 25. Its existence is recorded as repository source; it was not executed or accepted as exact-source Tavall validation here.
- The repository tree has no `src/test` path and no `.tavallci/ci.yaml` at the baseline.
## Integration & Open Pull Requests
| Surface | Current evidence |
| --- | --- |
| Tavall DI | Required declared API dependency; source confirms `IRating` extends the DI interface. |
| Tavall PvP / `novus-ffa` | Product-level documentation describes Glicko-2 rating behavior. Artifact dependency from current in-scope source was not established. |
| PR #3 | Open Draft against `staging/platform`; exact Java 25 verification and dependency-lock refresh remain pending in the PR body. |
| PR #6 | Open README documentation PR based on earlier main `acc648d956ec775015cfe4c5b8f896afde0d0fea`; it remains preserved. |
| PR #8 | Open Draft docs-only PR from current main; adds this DESIGN, reconciles PROGRESSION, and routes the README. |

No merge, package publication, runtime deployment, or downstream adoption is recorded by this audit.
## Validation & Blockers
| Gate | State | Evidence / next action |
| --- | --- | --- |
| Exact current-main Java 25 `clean check` | `NOT_RUN` | Run against the exact selected source revision through Tavall's authorized persistent execution lane. |
| Algorithm reference vectors | `NOT_RUN` | Add tests for wins/losses/draws, multiple opponents, inactive participants, period reuse, and numerical edge cases. |
| API/consumer compatibility | `PENDING` | Decide whether the public types in the `internal` package are stable and identify an actual consuming repository. |
| Dependency locks | `PENDING` | Reconcile after the staging change and verify at exact head. |
| Repository-local CI definition | `MISSING` | Establish required `.tavallci/ci.yaml` shape and add it as a separate reviewed change. |
| JAR/package verification | `NOT_RUN` | Run `verifyJarContents` as part of the exact-source build; publication is a separate gate. |

## Next Slice
Resolve the consumer API and rating-period lifecycle questions in DESIGN, add deterministic algorithm tests, and run exact-head Java 25 validation. Keep PR #3 Draft until its validation and lock gates are met. Do not count product-level Glicko-2 documentation as proof that this artifact is consumed.
## Documentation Update State

<details>
<summary>Documentation Update State</summary>

### Current Locations

| Surface | Sync State | Location | Last Updated | Evidence |
| --- | --- | --- | --- | --- |
| GitHub | `SYNCHRONIZED` | [`docs/progression/TAVALL_RATING_GLICKO2_PROGRESSION.md`](https://github.com/TavallStudios/tavall-rating-glicko2/blob/docs/module-design-progression-2026-09-28/docs/progression/TAVALL_RATING_GLICKO2_PROGRESSION.md) | 2026-09-27 8:06 PM PDT | Open Draft [PR #8](https://github.com/TavallStudios/tavall-rating-glicko2/pull/8); reconciled to current main `ec35f280a26b07b5b6340c7ab141e5613715b3a8`. |
| Notion | `SYNCHRONIZED` | [Tavall Rating Glicko-2 — PROGRESSION](https://app.notion.com/p/3e938458ddfd815c93c3caba263b4710) | 2026-09-27 8:06 PM PDT | 1:1 twin under Platform & Infrastructure; same source baseline, open gates, and history as GitHub. |

### Update History

| Timestamp | Surface | Event | Location | Previous Location | Evidence | Notes |
| --- | --- | --- | --- | --- | --- | --- |
| 2026-09-27 7:57 PM PDT | GitHub | `RECONCILED` | `docs/progression/TAVALL_RATING_GLICKO2_PROGRESSION.md` | Main document audited against `acc648d956ec775015cfe4c5b8f896afde0d0fea` | Current main source tree and build files at `ec35f280a26b07b5b6340c7ab141e5613715b3a8`. | Corrected the baseline, source inventory, PR state, and distinction between source presence, validation, and adoption. |
| 2026-09-27 8:06 PM PDT | Notion | `SYNCHRONIZED` | [Module PROGRESSION page](https://app.notion.com/p/3e938458ddfd815c93c3caba263b4710) | New page | Compared with GitHub PROGRESSION in PR #8. | Preserved the historical timeline and current-main evidence. |

</details>

## Related Documentation
| Type | Document |
| --- | --- |
| Module DESIGN | [Tavall Rating Glicko-2 — DESIGN](https://github.com/TavallStudios/tavall-rating-glicko2/blob/docs/module-design-progression-2026-09-28/docs/design/TAVALL_RATING_GLICKO2_DESIGN.md) |
| Module README | [Tavall Rating Glicko-2](../../README.md) |
| Repository Git workflow | [Git workflow](../quality/GIT_WORKFLOW.md) |
| Shared module taxonomy | [Tavall Module Types](https://github.com/TavallStudios/tavall-docs/blob/main/docs/quality/MODULE_TYPES.md) |
| Shared Progression template | [Progression Document Template](https://github.com/TavallStudios/tavall-docs/blob/main/docs/quality/PROGRESSION_DOCUMENT_TEMPLATE.md) |
