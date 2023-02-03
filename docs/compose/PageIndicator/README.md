# Page Indicator

[![Maven Central](https://img.shields.io/maven-central/v/net.skyscanner.backpack/backpack-compose)](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-compose)
[![Class reference](https://img.shields.io/badge/Class%20reference-Android-blue)](https://backpack.github.io/android/backpack-compose/net.skyscanner.backpack.compose.pageindicator)
[![Source code](https://img.shields.io/badge/Source%20code-GitHub-lightgrey)](https://github.com/Skyscanner/backpack-android/tree/main/backpack-compose/src/main/kotlin/net/skyscanner/backpack/compose/pageindicator)

## Default

| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/PageIndicator/screenshots/default.png" alt="PageIndicator component" width="375" /> | <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/PageIndicator/screenshots/default_dm.png" alt="PageIndicator component - dark mode" width="375" /> |

## Installation

Backpack Compose is available through [Maven Central](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-compose). Check the main [Readme](https://github.com/skyscanner/backpack-android#installation) for a complete installation guide.

## Usage

Example of a Page Indicator:

```Kotlin
import net.skyscanner.backpack.compose.pageindicator.BpkPageIndicator
import net.skyscanner.backpack.compose.pageindicator.BpkPageIndicatorStyle

BpkPageIndicator(
  currentIndex = 1,
  totalIndicators = 8,
  style = BpkPageIndicatorStyle.Default,
  indicatorLabel = "screen 1 of 8",
)
```
