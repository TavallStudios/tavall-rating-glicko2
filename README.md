# Tavall Rating Glicko-2

A Java library for rating profiles, two-player outcomes, and Glicko-2 rating-period calculations.

## Responsibility

### Owns

- Rating, rating deviation, and volatility profile values.
- Win, loss, and draw outcome types.
- Glicko-2 period calculation and rating-scale conversion.

### Does Not Own

- Match lifecycle, evidence attribution, identity, persistence, matchmaking, grades, leaderboards, or product presentation.
- An independently deployed runtime.

## Repository Structure

**tavall-rating-glicko2/** — one root Gradle module

- `src/main/java/` — library implementation
- `docs/design/` — module design
- `docs/progression/` — implementation and validation evidence
- `docs/quality/` — repository-local workflow routing
- `build.gradle.kts` — Java 25 library and publication configuration

## Relationships

| Dependency / Consumer | Relationship |
| --- | --- |
| Tavall DI | Declared Gradle API dependency; `IRating` extends its injectable-interface contract. |
| Tavall PvP / `novus-ffa` | Product documents describe Glicko-2 behavior. This audit did not establish a current source dependency on this repository's artifact. |
| Product runtimes | Own evidence policy, outcome collection, persistence, grade projection, and presentation. |

## Documentation

| Type | Document | Purpose |
| --- | --- | --- |
| Module Design | [Glicko-2 module DESIGN](docs/design/TAVALL_RATING_GLICKO2_DESIGN.md) | Draft ownership, calculation boundary, consumer shape, and open contract questions. |
| Progression / Evidence | [Glicko-2 module PROGRESSION](docs/progression/TAVALL_RATING_GLICKO2_PROGRESSION.md) | Audited source, build, integration, validation, and next gates. |
| Development policy | [Repository Git workflow](docs/quality/GIT_WORKFLOW.md) | Repository-local Git workflow routing. |
| Shared policy | [Tavall Module Types](https://github.com/TavallStudios/tavall-docs/blob/main/docs/quality/MODULE_TYPES.md) | Canonical module type and runtime-owner vocabulary. |
| Shared policy | [Progression template](https://github.com/TavallStudios/tavall-docs/blob/main/docs/quality/PROGRESSION_DOCUMENT_TEMPLATE.md) | Canonical module/system Progression structure. |
| Shared policy | [Tavall CI/CD policy](https://github.com/TavallStudios/tavall-docs/blob/main/docs/quality/CI_CD.md) | Shared build and CI ownership. |

## Build & Publication

The library uses Java 25 and declares a conditional Maven publication to GitHub Packages. Publication requires package credentials. This audit did not verify a published package. The module has no independent runtime deployment.

## Development

- **Module Type:** `LIBRARY`
- **Gradle Module:** Root project `tavall-rating-glicko2`
- **Runtime:** None confirmed; the library has no deployment.
- **Tests:** No `src/test` tree appeared in the audited current-main repository tree.
- **CI Definition:** No module-local `.tavallci/ci.yaml` was found at audited `main@ec35f280a26b07b5b6340c7ab141e5613715b3a8`. A GitHub Actions Gradle workflow exists; it was not executed as part of this audit.
- **Platform Adoption:** [PR #3](https://github.com/TavallStudios/tavall-rating-glicko2/pull/3) is an open Draft against `staging/platform`; its body lists exact Java 25 verification and dependency-lock refresh as pending.
- **README PR:** [PR #6](https://github.com/TavallStudios/tavall-rating-glicko2/pull/6) remains open from earlier `main@acc648d956ec775015cfe4c5b8f896afde0d0fea`. This documentation branch starts from current main and preserves that PR.
- **Progression:** [Glicko-2 module PROGRESSION](docs/progression/TAVALL_RATING_GLICKO2_PROGRESSION.md)
- **Shared workflow:** Organization-wide Git policy remains owned by [Tavall Docs](https://github.com/TavallStudios/tavall-docs/blob/main/docs/quality/GIT_WORKFLOW.md).

## Documentation Update State

<details>
<summary>Documentation Update State</summary>

### Current Locations

| Surface | Sync State | Location | Last Updated | Evidence |
| --- | --- | --- | --- | --- |
| GitHub | `PENDING_REVIEW` | `TavallStudios/tavall-rating-glicko2/README.md` on `docs/module-design-progression-2026-09-28` | 2026-09-27 7:57 PM PDT | README routes to current-main-backed module docs in the same documentation branch. |
| Notion | `NOT_APPLICABLE` | — | — | Repository README; no 1:1 pairing is required. |

### Update History

| Timestamp | Surface | Event | Location | Previous Location | Evidence | Notes |
| --- | --- | --- | --- | --- | --- | --- |
| 2026-09-27 7:57 PM PDT | GitHub | `UPDATED` | `TavallStudios/tavall-rating-glicko2/README.md` | README DUS based on `acc648d956ec775015cfe4c5b8f896afde0d0fea` | Current main `ec35f280a26b07b5b6340c7ab141e5613715b3a8` | Added the Design route and refreshed source/validation boundaries. |

</details>
