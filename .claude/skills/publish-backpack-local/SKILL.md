---
name: publish-backpack-local
description: Publish backpack-android to Maven local as a SNAPSHOT and wire skyscanner-app to consume it. Use when the user wants to test unreleased backpack-android changes inside the skyscanner-app before a real release.
allowed-tools: Bash(git tag*), Bash(./gradlew *), Bash(find *), Read, Edit
---

# Publish Backpack to Maven Local

Round-trips local backpack-android changes into the skyscanner-app for manual testing, without cutting a real release.

## 1) Derive the SNAPSHOT version

```bash
git -C /Users/henrik.sym/StudioProjects/backpack-android tag --sort=-v:refname
```

Filter to tags matching `Backpack@X.Y.Z` (ignore anything that doesn't match this exact pattern, e.g. malformed/test tags). Take the first match — that's the latest release. Bump the patch: `X.Y.Z` → `X.Y.(Z+1)`, then append `.SNAPSHOT`.

Example: latest tag `Backpack@83.1.0` → derived version `83.1.1.SNAPSHOT`.

State the derived version to the user and confirm before proceeding — this is a cheap sanity check in case the tag list looks unexpected.

## 2) Publish to Maven local

```bash
cd /Users/henrik.sym/StudioProjects/backpack-android
./gradlew publishToMavenLocal -Pversion="<derived-version>"
```

Run this directly (no need to hand off to the user — it's non-interactive). If the build fails, stop and report the failure; do not proceed to step 3.

## 3) Point skyscanner-app at the snapshot

Two files to update, both under `/Users/henrik.sym/StudioProjects/skyscanner-app/android/src/`:

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
