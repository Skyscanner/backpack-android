---
name: backpack-local-publisher
description: Publishes backpack-android to Maven local as a fixed SNAPSHOT, wires skyscanner-app to consume it, and verifies the app actually resolves it. Use when the user wants to test unreleased backpack-android changes inside skyscanner-app before a real release.
tools: Bash, Read, Edit
model: haiku
---

Round-trips local backpack-android changes into the skyscanner-app for manual testing, without cutting a real release.

Uses the fixed version `99.9.9-SNAPSHOT`. Two properties matter and neither is arbitrary:

- **`99.9.9`** is far above any version that will realistically be released, so it never collides with a real one.
- **`-SNAPSHOT` with a hyphen, not a dot.** Maven and Gradle only treat a version as a *changing module* when it ends in the literal string `-SNAPSHOT`. Written as `99.9.9.SNAPSHOT` it resolves as a static release version, which Gradle caches indefinitely — so a second publish would leave the app compiling against the previously resolved copy while still reporting success.

## 0) Locate the two repos

Check whether `backpack-android` and `skyscanner-app` are sibling directories (same parent folder). Derive both paths from the current working directory — do not hardcode a user-specific path. **Verify before proceeding** — don't assume; if the check fails, stop and ask the user for the path rather than guessing.

```bash
BACKPACK_DIR=$(git rev-parse --show-toplevel)
APP_DIR="$(dirname "$BACKPACK_DIR")/skyscanner-app"
test -d "$APP_DIR" || { echo "NOT FOUND: $APP_DIR — ask user for skyscanner-app path"; false; }
```

If `skyscanner-app` can't be located, stop and ask the user for its path.

Use `$BACKPACK_DIR` and `$APP_DIR` in place of any absolute path in the steps below.

**Prerequisites** — if step 1 fails, check these before debugging anything else. Both have bitten this workflow:

- Gradle must be able to reach `plugins.gradle.org` and Maven Central. These are not proxied through Artifactory, so a VPN/proxy problem surfaces as an unresolvable plugin rather than a network error. A Gradle wrapper bump invalidates the cached `kotlin-dsl` plugin and forces a fresh download, so a build that worked yesterday can start failing after a wrapper change.
- A working Android SDK is required — `publishToMavenLocal` configures the Android library modules.

Do not modify either repo to work around a failure here. Report it and stop.

## 1) Publish to Maven local

```bash
cd "$BACKPACK_DIR"
./gradlew publishToMavenLocal -Pversion="99.9.9-SNAPSHOT"
```

Run this directly (no need to hand off to the user — it's non-interactive). If the build fails, stop and report the failure; do not proceed.

## 2) Verify the publish actually landed

A green build is **not** sufficient evidence. `publishToMavenLocal` can succeed while publishing under the wrong version — a version-less publish leaves artifacts under `unspecified/`. Check the filesystem, not the build log:

```bash
ls -d ~/.m2/repository/net/skyscanner/backpack/*/99.9.9-SNAPSHOT
```

Expect exactly three directories — `backpack-android`, `backpack-common`, `backpack-compose`. If any are missing, or if the build wrote to `unspecified/` instead, stop and report it.

Republishing overwrites these in place, so re-running is safe and produces no version proliferation.

## 3) Point skyscanner-app at the snapshot

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

Do NOT add `mavenLocal()` to the `pluginManagement` repositories block — only the `dependencyResolutionManagement` one is needed. Note that `pluginManagement` appears first in the file and has a near-identical `repositories` block, so confirm which block you're editing.

Change only these two entries. The app repo often has unrelated work in progress — do not revert, stash, or clean anything you didn't change.

## 4) Sync skyscanner-app and confirm it resolves

Don't just tell the user to sync — do it, and prove the snapshot is what the app resolves.

```bash
cd "$APP_DIR/android/src"
./gradlew --refresh-dependencies :Go.Android.App:dependencies --configuration debugRuntimeClasspath 2>&1 | grep "net.skyscanner.backpack"
```

`--refresh-dependencies` matters: Gradle caches changing modules for 24 hours by default, so without it a freshly republished snapshot may not be picked up.

Every `net.skyscanner.backpack:*` line should read `99.9.9-SNAPSHOT`. If any still shows the old release version, the wiring didn't take — report it rather than retrying blindly.

If the app module path has changed and `:Go.Android.App` no longer exists, discover the current one with `./gradlew projects` rather than guessing.

## 5) Report

State plainly, with the evidence gathered above:

- Whether `publishToMavenLocal` succeeded, and which three artifacts were confirmed on disk at `99.9.9-SNAPSHOT` (from step 2, not from the build log).
- Which files in skyscanner-app were changed, and whether `mavenLocal()` was added or was already present and skipped.
- Whether the app sync succeeded and resolved backpack to `99.9.9-SNAPSHOT`.

If any step failed, report the actual error output and where it stopped. Don't summarise a failure as "didn't work" — the point is to give the user enough to debug it.

To undo the app-side wiring afterwards:

```bash
cd "$APP_DIR" && git checkout android/src/settings.gradle.kts android/src/gradle/dependencies-backpack/gradle/libs.versions.toml
```
