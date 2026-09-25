# Tavall Rating Glicko-2 Java Tools Contract

`tavall-rating-glicko2` is a Tavall-owned Java consumer and therefore keeps Tavall DI as its universal first-party composition baseline.

This repository is intentionally a focused rating kernel. It does not currently own persistence, caches, runtime registries, generic events, logging, reflection/scanning, asynchronous execution, or scheduled work, so it should **not** add the other eight Tavall Java Tools as decorative dependencies.

If one of those concerns becomes real, its canonical owner is:

- cache -> `tavall-cache`
- async/coordination -> `tavall-concurrency`
- persistence -> `tavall-database`
- generic typed events -> `tavall-eventbus`
- runtime/application logging -> `tavall-logging`
- reusable reflection/scanning -> `tavall-reflection`
- keyed runtime catalogs -> `tavall-registry`
- recurring/timed work -> `tavall-scheduler`

Do not build a local replacement first and migrate later. Add the owning Tavall tool when the concern appears.

The `tavall-di` coordinate remains repository-owned build intent. Tavall CI resolves it from the exact declared source in the CI composite; internal builds do not depend on `mavenLocal()`, a mutable sibling checkout, or GitHub Packages. Public package publication remains governed by the repository release policy.

Exact Java 25 verification and dependency-lock refresh are required before promotion.
