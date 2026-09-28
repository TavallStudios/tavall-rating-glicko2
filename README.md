# Tavall Rating Glicko-2

A Java library that exposes rating profiles, match results, and Glicko-2 rating-period calculations.

## Responsibility

### Owns
- Rating, rating deviation, volatility, and match result data types.
- Glicko-2 rating-period calculation.

### Does Not Own
- Match lifecycle, persistence, user identity, matchmaking, or a product runtime.
- A runtime deployment; this is a reusable library.

## Repository Structure

**tavall-rating-glicko2/ ← This Module**
├── docs/
│   ├── progression/
│   └── quality/
├── src/main/java/
└── build.gradle.kts

## Relationships

| Dependency / Consumer | Relationship |
| --- | --- |
| Tavall DI | Declared Gradle API dependency; `IRating` extends its injectable-interface contract. |
| Product runtimes | No current in-scope code consumer was confirmed in this audit. A Tavall MC design document refers to the former Glicko-2 implementation as historical context. |

## Documentation

| Type | Document | Purpose |
| --- | --- | --- |
| Progression / Evidence | [Glicko-2 module Progression](docs/progression/TAVALL_RATING_GLICKO2_PROGRESSION.md) | Audited implementation, integration, validation, and next gates. |
| Development policy | [Repository Git workflow](docs/quality/GIT_WORKFLOW.md) | Repository-local Git workflow routing. |
| Shared policy | [Tavall Module Types](https://github.com/TavallStudios/tavall-docs/blob/main/docs/quality/MODULE_TYPES.md) | Canonical module type and runtime-owner vocabulary. |
| Shared policy | [Progression template](https://github.com/TavallStudios/tavall-docs/blob/main/docs/quality/PROGRESSION_DOCUMENT_TEMPLATE.md) | Canonical module/system Progression structure. |
| Shared policy | [Tavall CI/CD policy](https://github.com/TavallStudios/tavall-docs/blob/main/docs/quality/CI_CD.md) | Shared build and CI ownership. |

## Deployment

This library has no independent runtime deployment. The build declares Maven publication to GitHub Packages when the required credentials are present; this audit did not verify a package publication.

## Development

- **Module Type:** `LIBRARY`
- **Runtime:** `None` — no current single runtime owner was confirmed.
- **CI Definition:** No module-local `.tavallci/ci.yaml` exists at audited `main@acc648d956ec775015cfe4c5b8f896afde0d0fea`; this documentation pass records the gap without changing CI.
- **Current PR Stack:** [PR #3](https://github.com/TavallStudios/tavall-rating-glicko2/pull/3) is an open draft against `staging/platform`. Its body identifies exact Java 25 verification and dependency-lock refresh as pending.
- **Progression:** [Glicko-2 module Progression](docs/progression/TAVALL_RATING_GLICKO2_PROGRESSION.md)
- **Shared workflow:** Organization-wide Git policy remains owned by [Tavall Docs](https://github.com/TavallStudios/tavall-docs/blob/main/docs/quality/GIT_WORKFLOW.md).

## Documentation Update State

<details>
<summary>Documentation Update State</summary>

### Current Locations

| Surface | Sync State | Location | Last Updated | Evidence |
| --- | --- | --- | --- | --- |
| GitHub | PRIMARY | TavallStudios/tavall-rating-glicko2/README.md | 2026-09-27 5:05 PM PDT | Documentation branch based on audited main `acc648d956ec775015cfe4c5b8f896afde0d0fea`. |
| Notion | NOT_APPLICABLE | — | — | Repository README; no 1:1 pairing required. |

### Update History

| Timestamp | Surface | Event | Location | Previous Location | Evidence | Notes |
| --- | --- | --- | --- | --- | --- | --- |
| 2026-09-27 5:05 PM PDT | GitHub | CREATED | TavallStudios/tavall-rating-glicko2/README.md | — | Audit baseline main@acc648d956ec775015cfe4c5b8f896afde0d0fea. | Added module ownership, document routes, current PR context, and deployment boundaries. |

</details>
