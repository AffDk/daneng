# Danish–English Offline Dictionary Constitution

## Core Principles

### I. Offline-Only
- The app must function 100% without internet access.
- Do not request network permissions (no INTERNET, no network state). No telemetry, analytics, ads, or remote services.
- All data and computation live on-device.

### II. Simple, Fast Local Search
- Local database packaged with the app; lookups run entirely on-device.
- Support Danish→English and English→Danish queries with case/diacritics-insensitive matching (æ, ø, å) and basic prefix search.
- Prefer SQLite with FTS via Room; deterministic results for the same input.

### III. Test-First
- Write tests before implementation (red → green → refactor).
- Cover exact match, no-result, diacritics normalization, and both language directions.
- Tests and builds run with networking disabled.

### IV. Simplicity & Versioning
- Minimal dependencies and a straightforward single-screen UX (search, results, entry detail).
- Version code, schema, and data; document any breaking change with a migration.
- Add a gitignore file and add irrelevant and sensitive contents to be ignored and not tracked.

## Constraints & Standards
- Platform: Android app written in Kotlin.
- Data: Prepackaged, read-only SQLite/FTS database bundled with the app; no runtime downloads.
- Privacy/Security: No accounts, no background services, no external calls.
- Accessibility and i18n: Respect system font scaling and right/left language display as appropriate.

## Development Workflow & Quality Gates
- Tests define behavior first; unit and small integration tests for DB and lookup.
- CI gates: build, lint/format, and tests must pass; any schema change includes a tested migration.
- Code review verifies offline-only compliance (no network usage) and basic performance responsiveness.

## Governance
- This Constitution supersedes other practices for this app.
- Amendments require updating this file and a brief migration note when the data schema changes.

**Version**: 1.0.0 | **Ratified**: 2025-11-14 | **Last Amended**: 2025-11-14
