# Badge

[![Maven Central](https://img.shields.io/maven-central/v/net.skyscanner.backpack/backpack-compose)](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-compose)
[![Class reference](https://img.shields.io/badge/Class%20reference-Android-blue)](https://backpack.github.io/android/backpack-compose/net.skyscanner.backpack.compose.badge)
[![Source code](https://img.shields.io/badge/Source%20code-GitHub-lightgrey)](https://github.com/Skyscanner/backpack-android/tree/main/backpack-compose/src/main/kotlin/net/skyscanner/backpack/compose/badge)

## Default

| Day | Night |
| --- | --- |
| ![Badge component](https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/Badge/screenshots/default.png) |![Badge component - dark mode](https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/Badge/screenshots/default_dm.png) |

## Installation

Backpack Compose is available through [Maven Central](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-compose). Check the main [Readme](https://github.com/skyscanner/backpack-android#installation) for a complete installation guide.

## Usage

Example of a Badge:

```Kotlin
import net.skyscanner.backpack.compose.badge.BpkBadge
import net.skyscanner.backpack.compose.badge.BpkBadgeType

BpkBadge(
  text = "Badge text",
  type = BpkBadgeType.Normal,
)
```

Example of a Badge with icon:

```Kotlin
import net.skyscanner.backpack.compose.badge.BpkBadge
import net.skyscanner.backpack.compose.badge.BpkBadgeType
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.tokens.TickCircle

BpkBadge(
  text = "Badge text",
  type = BpkBadgeType.Normal,
  icon = BpkIcon.TickCircle,
)
```
