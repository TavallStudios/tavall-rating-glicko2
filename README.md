# Tavall Rating Glicko2

A Java 25 library for Glicko-2 rating calculations and rating state.

This single-module library exposes Tavall's rating contract and Glicko-2 calculation implementation.

## Why Tavall Rating Glicko2

- Provides a reusable rating interface and Glicko-2 calculation types.
- Keeps rating calculation separate from product-specific match and leaderboard policy.
- Ships as a Java library and depends on Tavall DI for its injectable interface.

## Features

- Glicko-2 rating calculations
- Rating and rating-deviation state
- A caller-facing IRating contract

## Quick Start

Add the published artifact to a Gradle project:

```kotlin
dependencies {
    implementation("com.tjxjnoobie:tavall-rating-glicko2:<version>")
}
```

Use the exact published version and repository access configured for your project. See the links below for API and contribution details.

## Project Structure

This repository is a single Java library module (Module Type: LIBRARY; Runtime: None).

## Documentation

| Document | Purpose |
| --- | --- |
| [Git Workflow](docs/quality/GIT_WORKFLOW.md) | Applicable repository guidance. |

## Requirements / Compatibility

Java 25. This module depends on org.tavall:tavall-di:1.0.0.

## Building From Source

```bash
./gradlew check
```

## Contributing

See the repository's [Git workflow guidance](docs/quality/GIT_WORKFLOW.md).

## License

No tracked license file is present in the current repository tree.

## Documentation Update State

<details>
<summary>Documentation Update State</summary>

### Current Locations

| Surface | Sync State | Location | Last Updated | Evidence |
| --- | --- | --- | --- | --- |
| GitHub | PRIMARY | TavallStudios/tavall-rating-glicko2/README.md | 2026-09-27 12:29 PM PDT | Migration PR. |
| Notion | NOT_APPLICABLE | — | 2026-09-27 12:29 PM PDT | README files are not synchronized as Notion twins. |

### Update History

| Timestamp | Surface | Event | Location | Previous Location | Evidence | Notes |
| --- | --- | --- | --- | --- | --- | --- |
| 2026-09-27 12:29 PM PDT | GitHub | CREATED | TavallStudios/tavall-rating-glicko2/README.md | — | Migration PR. | Reworked the public README to describe the current project, module boundary, usage, and documentation. |

</details>
