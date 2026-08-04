---
name: publish-backpack-local
description: Publishes backpack-android to Maven local as a fixed SNAPSHOT version and wires skyscanner-app to consume it. Use when the user wants to test unreleased backpack-android changes inside skyscanner-app before a real release.
tools: Bash, Read, Edit
model: haiku
---

Round-trips local backpack-android changes into the skyscanner-app for manual testing, without cutting a real release.

Uses the fixed version `99.9.9-SNAPSHOT` — high enough to never collide with real releases (currently ~83.x.x). The `-SNAPSHOT` suffix is required so Gradle treats it as a changing module and re-resolves on each build.

## 0) Locate the two repos

Check whether `backpack-android` and `skyscanner-app` are sibling directories (same parent folder). Derive both paths from the current working directory — do not hardcode a user-specific path. **Verify before proceeding** — don't assume; if the check fails, stop and ask the user for the path rather than guessing.

```bash
BACKPACK_DIR=$(git rev-parse --show-toplevel)
APP_DIR="$(dirname "$BACKPACK_DIR")/skyscanner-app"
test -d "$APP_DIR" || { echo "NOT FOUND: $APP_DIR — ask user for skyscanner-app path"; false; }
```

If `skyscanner-app` can't be located, stop and ask the user for its path.

Use `$BACKPACK_DIR` and `$APP_DIR` in place of any absolute path in the steps below.

## 1) Publish to Maven local

```bash
cd "$BACKPACK_DIR"
./gradlew publishToMavenLocal -Pversion="99.9.9-SNAPSHOT"
```

Run this directly (no need to hand off to the user — it's non-interactive). If the build fails, stop and report the failure; do not proceed to step 2.

## 2) Point skyscanner-app at the snapshot

Two files to update, both under `$APP_DIR/android/src/`:

**a) `gradle/dependencies-backpack/gradle/libs.versions.toml`**

Update the `backpack` version entry:

```toml
backpack = "99.9.9-SNAPSHOT"
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

## 3) Report

Confirm to the user:
- That `publishToMavenLocal` succeeded with version `99.9.9-SNAPSHOT`.
- That both files in skyscanner-app were updated (or that `mavenLocal()` was already present and skipped).

Remind them to sync/rebuild skyscanner-app next, ideally with `--refresh-dependencies` since Gradle caches changing modules for 24h by default.
