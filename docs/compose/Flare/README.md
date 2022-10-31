# Flare

[![Maven Central](https://img.shields.io/maven-central/v/net.skyscanner.backpack/backpack-compose)](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-compose)
[![Class reference](https://img.shields.io/badge/Class%20reference-Android-blue)](https://backpack.github.io/android/backpack-compose/net.skyscanner.backpack.compose.flare)
[![Source code](https://img.shields.io/badge/Source%20code-GitHub-lightgrey)](https://github.com/Skyscanner/backpack-android/tree/main/backpack-compose/src/main/kotlin/net/skyscanner/backpack/compose/flare)

## Default

| Day | Night |
| --- | --- |
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/Flare/screenshots/default.png" alt="Flare component" width="375" /> |<img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/Flare/screenshots/default_dm.png" alt="Flare component - dark mode" width="375" /> |

## Installation

Backpack Compose is available through [Maven Central](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-compose). Check the main [Readme](https://github.com/skyscanner/backpack-android#installation) for a complete installation guide.

## Usage

Example of a Flare:

```Kotlin
import net.skyscanner.backpack.compose.flare.BpkFlare

BpkFlare {
  // content
}
```

Example of a Flare pointing up:

```Kotlin
import net.skyscanner.backpack.compose.flare.BpkFlare

BpkFlare(pointerDirection = BpkFlarePointerDirection.Up) {
  // content
}
```

Example of a Flare with rounded corners:

```Kotlin
import net.skyscanner.backpack.compose.flare.BpkFlare

BpkFlare(radius = BpkFlareRadius.Medium) {
  // content
}
```

Example of a Flare with content inset to take the flare into account when arranging the content:

```Kotlin
import net.skyscanner.backpack.compose.checkbox.BpkChecbox

BpkFlare(background = BpkTheme.colors.primary, insetContent = true) {
  // content
}
```
