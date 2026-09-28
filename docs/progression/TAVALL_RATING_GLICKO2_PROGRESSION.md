# Tavall Rating Glicko-2 Progression

> **Status:** Active progression record  
> **Document Type:** `PROGRESSION`  
> **Progression Scope:** `MODULE`  
> **Module Type:** `LIBRARY`  
> **Owning System:** Tavall Java Tools / Glicko-2 Rating  
> **Owns:** Audited implementation, integration, validation, and historical progression for the Glicko-2 rating library  
> **Does Not Own:** Match lifecycle, persistence, product behavior, aggregate system status, deployment history, or Git workflow policy  
> **Audited Against:** `TavallStudios/tavall-rating-glicko2@acc648d956ec775015cfe4c5b8f896afde0d0fea`  
> **Last Reconciled:** `2026-09-27 5:05 PM PDT`  
> **Notion Twin:** `TEMPORARY_DRIFT` — the required 1:1 twin was not reconciled in this GitHub-only pass.

## About

This `LIBRARY` owns Tavall's Java Glicko-2 rating types and rating-period calculation. Progression measures the library's API and algorithm implementation, consumer integration, compatibility, and verification; source presence does not establish behavioral correctness.

## Module Context

| Field | Value |
| --- | --- |
| Repository | `TavallStudios/tavall-rating-glicko2` |
| Module | `tavall-rating-glicko2` (single root Gradle project) |
| Module Type | `LIBRARY` |
| Owning System | Tavall Java Tools / Glicko-2 Rating |
| Runtime Owner | `None` — no current in-scope runtime consumer was confirmed. |
| Primary Consumers | None confirmed by this audit. Tavall MC documentation refers to the prior implementation as historical context. |
| Current Branch / PR Stack | Main baseline `acc648d956ec775015cfe4c5b8f896afde0d0fea`; open draft [PR #3](https://github.com/TavallStudios/tavall-rating-glicko2/pull/3) targets `staging/platform`, current head `7062c80bdbbfd1b0dd3833ebd2a1e8aecc1f25c2`. |
| Audited Revision | `acc648d956ec775015cfe4c5b8f896afde0d0fea` |

## Current Status

| Field | State |
| --- | --- |
| Overall State | `PARTIAL` |
| Current Phase | Main-source inventory complete; current-main behavioral validation was not run. |
| Implementation | Five Java source files are present on audited main: `IRating`, `Rating`, `RatingCalculator`, `RatingPeriodResults`, and `Result`. |
| Integration | Tavall DI is declared by the module; no current in-scope runtime consumer was confirmed. |
| Validation | No `src/test` tree or module-local `.tavallci/ci.yaml` exists on audited main. No build or tests were run in this documentation audit; GitHub returned no commit statuses for the audited SHA. |
| Runtime / Consumer Acceptance | Not established; no current product runtime owner was identified. |
| Deployment Verification | Not applicable to runtime deployment. Maven publication is declared in the build, but package publication was not verified. |
| Primary Blocker | Exact Java 25 verification and dependency-lock refresh remain pending per open draft PR #3; current-main tests were not run. |
| Next Slice | Verify the current candidate with Java 25 and refresh dependency locks as recorded in PR #3 before treating the platform-adoption work as validated. |

## Progression Timeline

| Date / Time | State | Progression | Evidence | Result / Remaining Work |
| --- | --- | --- | --- | --- |
| 2026-04-06 5:03 AM PDT | `MERGED_PRODUCTION` | Initial Glicko-2 rating engine | [`45d07e04`](https://github.com/TavallStudios/tavall-rating-glicko2/commit/45d07e04b86859cc8700e432602ea00aaf90a40a) | Introduced the rating engine in the source history; this audit does not establish tests or runtime acceptance for that revision. |
| 2026-07-23 12:03 PM PDT | `MERGED_PRODUCTION` | Gradle and Java 25 migration | [`3472968d`](https://github.com/TavallStudios/tavall-rating-glicko2/commit/3472968d153160099674f70b789dc7e2a2dfe72d) | Migrated the library build; current-main build verification remains outstanding. |
| 2026-07-24 2:08 AM PDT | `MERGED_PRODUCTION` | Rating API contract restored | [`c5178a31`](https://github.com/TavallStudios/tavall-rating-glicko2/commit/c5178a312a57488eab0d8dedc073cd45d69fbc91) | Restored the `IRating` API boundary recorded in the current source tree. |
| 2026-08-15 12:04 PM PDT | `VALID_UNMERGED_PR` | Tavall Java Tools platform-adoption draft opened | [PR #3](https://github.com/TavallStudios/tavall-rating-glicko2/pull/3) | The PR remains a draft against `staging/platform`; its current head is not part of audited main. Its body lists Java 25 verification and dependency-lock refresh as pending. |
| 2026-09-27 5:05 PM PDT | `IN_PROGRESS` | Current-main module and evidence audit | [`acc648d9`](https://github.com/TavallStudios/tavall-rating-glicko2/commit/acc648d956ec775015cfe4c5b8f896afde0d0fea) | Confirmed one root Gradle module, five Java main-source files, no test sources, and no module-local CI definition; no code validation was run. |

Timeline entries are ordered oldest to newest. The historical source commits document changes; they do not imply that tests or runtime acceptance passed.

## Validation State

| Validation | State | Evidence | Remaining Work |
| --- | --- | --- | --- |
| Architecture / module boundary | `INVENTORIED` | One root project in `settings.gradle.kts`; Java library and Maven publication configuration in `build.gradle.kts`. | Review the current API and algorithm against its owning technical contract when available. |
| Unit | `NOT RUN` | No `src/test` path appeared in the audited GitHub tree. | Add/restore appropriate focused tests and run them on the exact candidate. |
| Integration | `NOT RUN` | Tavall DI is declared; no integration test source or current runtime consumer was found in this repository audit. | Validate composition with a real consumer when one is selected. |
| Consumer / Runtime | `NOT ESTABLISHED` | No current in-scope module consumer was confirmed; a Tavall MC design document describes the prior implementation as historical context. | Confirm intended consumer ownership before making runtime claims. |
| End-to-End | `NOT APPLICABLE` | This module is a library and has no independently deployed runtime. | None for this module boundary. |
| CI / artifact boundary | `GAP` | Main has no `.tavallci/ci.yaml`; the build declares a conditional GitHub Packages Maven publication. | Add/verify the required CI definition and verify publication through the authorized workflow; this documentation pass does not modify CI or publish artifacts. |

## Dependencies and Integration

| Dependency / Consumer | Relationship | State | Evidence |
| --- | --- | --- | --- |
| Tavall DI | Declared dependency `org.tavall:tavall-di:1.0.0`; `IRating` extends `IDependencyInjectableInterface`. | Present in build/source on audited main; runtime composition was not tested. | [Build file](https://github.com/TavallStudios/tavall-rating-glicko2/blob/acc648d956ec775015cfe4c5b8f896afde0d0fea/build.gradle.kts); [IRating](https://github.com/TavallStudios/tavall-rating-glicko2/blob/acc648d956ec775015cfe4c5b8f896afde0d0fea/src/main/java/com/tjxjnoobie/api/interfaces/IRating.java). |
| Tavall MC | Historical documentation context for the former Glicko-2 implementation; not confirmed as a current code consumer. | Historical only. | [FFA rating and grading design](https://github.com/TavallStudios/tavall-mc/blob/a3f5b5ab5204236e2ec12db8b50485daf9df6ad5/docs/pvp/FFA_RATING_AND_GRADING_FINAL_DRAFT.md). |
| PR #3 | Platform-adoption changes are on an unmerged draft branch targeting `staging/platform`. | `VALID_UNMERGED_PR`; excluded from current-main implementation status. | [PR #3](https://github.com/TavallStudios/tavall-rating-glicko2/pull/3), current head `7062c80bdbbfd1b0dd3833ebd2a1e8aecc1f25c2`. |

## Blockers

| Blocker | Impact | Resolution |
| --- | --- | --- |
| No current-main module tests or recorded check statuses | Algorithm and compatibility behavior were not verified during this audit. | Run the exact Java 25 candidate validation after tests and CI scope are established. |
| PR #3 remains a draft with lock refresh and Java 25 checks pending | Platform-adoption changes cannot be counted as current-main validated state. | Complete the PR's stated gates and update its base/target through the authorized GitHub workflow. |
| Missing module-local `.tavallci/ci.yaml` | Shared standards require a CI definition for each source/build module. | Track as a separate CI work item; no CI changes were made here. |

## Next Slice

Verify the Java 25 candidate and dependency lock state from PR #3, then re-audit the exact main promotion before recording consumer or package-publication acceptance.

## Related Documentation

| Type | Document |
| --- | --- |
| Module README | [Tavall Rating Glicko-2](../../README.md) |
| Repository Git workflow | [Git workflow](../quality/GIT_WORKFLOW.md) |
| Shared module taxonomy | [Tavall Module Types](https://github.com/TavallStudios/tavall-docs/blob/main/docs/quality/MODULE_TYPES.md) |
| Shared Progression template | [Progression Document Template](https://github.com/TavallStudios/tavall-docs/blob/main/docs/quality/PROGRESSION_DOCUMENT_TEMPLATE.md) |

## Documentation Update State

<details>
<summary>Documentation Update State</summary>

### Current Locations

| Surface | Sync State | Location | Last Updated | Evidence |
| --- | --- | --- | --- | --- |
| GitHub | `PRIMARY` | `TavallStudios/tavall-rating-glicko2/docs/progression/TAVALL_RATING_GLICKO2_PROGRESSION.md` | 2026-09-27 5:05 PM PDT | Created from audited main `acc648d956ec775015cfe4c5b8f896afde0d0fea` on a documentation-only branch. |
| Notion | `TEMPORARY_DRIFT` | Not checked in this GitHub-only pass | Not synchronized in this pass | Required 1:1 twin was not reconciled. |

### Update History

| Timestamp | Surface | Event | Location | Previous Location | Evidence | Notes |
| --- | --- | --- | --- | --- | --- | --- |
| 2026-09-27 5:05 PM PDT | GitHub | `CREATED` | `TavallStudios/tavall-rating-glicko2/docs/progression/TAVALL_RATING_GLICKO2_PROGRESSION.md` | — | Audit baseline `main@acc648d956ec775015cfe4c5b8f896afde0d0fea`. | Added the module-scoped evidence record and direct source/PR links. |

</details>
