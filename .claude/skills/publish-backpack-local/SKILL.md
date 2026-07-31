---
name: publish-backpack-local
description: Publish backpack-android to Maven local as a SNAPSHOT and wire skyscanner-app to consume it. Use when the user wants to test unreleased backpack-android changes inside the skyscanner-app before a real release.
allowed-tools: Bash(git rev-parse*), Bash(git fetch*), Bash(git tag*), Bash(./gradlew *), Bash(find *), Bash(test *), Read, Edit
---

# Publish Backpack to Maven Local

Round-trips local backpack-android changes into the skyscanner-app for manual testing, without cutting a real release.

## 0) Locate the two repos

Assume `backpack-android` and `skyscanner-app` are sibling directories (same parent folder). Derive both paths from the current working directory — do not hardcode a user-specific path.

```bash
BACKPACK_DIR=$(git rev-parse --show-toplevel)
APP_DIR="$(dirname "$BACKPACK_DIR")/skyscanner-app"
test -d "$APP_DIR" || echo "NOT FOUND: $APP_DIR"
```

If invoked from somewhere other than inside `backpack-android` (e.g. `pwd` isn't that repo), find it by name under the common parent instead, then apply the same sibling logic. If `skyscanner-app` still can't be located, stop and ask the user for its path rather than guessing.

Use `$BACKPACK_DIR` and `$APP_DIR` in place of any absolute path in the steps below.

## 1) Derive the SNAPSHOT version

Fetch tags first — a local clone can be missing recent tags, which silently produces a stale/wrong version:

```bash
git -C "$BACKPACK_DIR" fetch --tags origin
```

Then list and filter to plain `X.Y.Z` tags (older releases used a `Backpack@X.Y.Z` prefix — ignore those and any other non-matching/malformed/test tags), sorting *after* filtering so the two naming schemes don't get interleaved:

```bash
git -C "$BACKPACK_DIR" tag | grep -E '^[0-9]+\.[0-9]+\.[0-9]+$' | sort -V | tail -1
```

That's the latest release. Bump the patch: `X.Y.Z` → `X.Y.(Z+1)`, then append `.SNAPSHOT`.

Example: latest tag `83.1.0` → derived version `83.1.1.SNAPSHOT`.

Don't use CHANGELOG.md as a version source — it can list unreleased entries ahead of the last cut tag.

State the derived version to the user and confirm before proceeding — this is a cheap sanity check in case the tag list looks unexpected.

## 2) Publish to Maven local

```bash
cd "$BACKPACK_DIR"
./gradlew publishToMavenLocal -Pversion="<derived-version>"
```

Run this directly (no need to hand off to the user — it's non-interactive). If the build fails, stop and report the failure; do not proceed to step 3.

## 3) Point skyscanner-app at the snapshot

Two files to update, both under `$APP_DIR/android/src/`:

**a) `gradle/dependencies-backpack/gradle/libs.versions.toml`**

Update the `backpack` version entry:

```toml
backpack = "<derived-version>"
```

**b) `settings.gradle.kts`**

Inside `dependencyResolutionManagement { repositories { ... } }`, add `mavenLocal()` as the first entry, before the existing `maven { url = ... }` block — only if it isn't already there (check first; skip if present):

```kotlin
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)
    repositories {
        mavenLocal()
        maven {
            url = uri("https://artifactory.skyscannertools.net/artifactory/maven")
            ...
        }
    }
    ...
}
```

Do NOT add `mavenLocal()` to the `pluginManagement` repositories block — only the `dependencyResolutionManagement` one is needed.

## 4) Report

Confirm to the user:
- The derived SNAPSHOT version used.
- That `publishToMavenLocal` succeeded.
- That both files in skyscanner-app were updated (or that `mavenLocal()` was already present and skipped).

Remind them to sync/rebuild skyscanner-app next.
