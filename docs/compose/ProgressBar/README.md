# ProgressBar

[![Maven Central](https://img.shields.io/maven-central/v/net.skyscanner.backpack/backpack-compose)](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-compose)
[![Class reference](https://img.shields.io/badge/Class%20reference-Android-blue)](https://backpack.github.io/android/backpack-compose/net.skyscanner.backpack.compose.progressbar)
[![Source code](https://img.shields.io/badge/Source%20code-GitHub-lightgrey)](https://github.com/Skyscanner/backpack-android/tree/main/backpack-compose/src/main/kotlin/net/skyscanner/backpack/compose/progressbar)

## Default

| Day | Night |
| --- | --- |
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/ProgressBar/screenshots/default.png" alt="ProgressBar component" width="375" /> | <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/ProgressBar/screenshots/default_dm.png" alt="ProgressBar component - dark mode" width="375" /> |

## Installation

Backpack Compose is available through [Maven Central](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-compose). Check the main [Readme](https://github.com/skyscanner/backpack-android#installation) for a complete installation guide.

## Usage

Example of a ProgressBar:

```Kotlin
import net.skyscanner.backpack.compose.progressbar.BpkProgressBar

BpkProgressBar(
    value = 0.5F
)
```

Example of a stepped ProgressBar:

If `stepped == true` the number of steps will be equal to `max`, therefore the number of dividers will be equal to `max - 1`.

```Kotlin
import net.skyscanner.backpack.compose.progressbar.BpkProgressBar

BpkProgressBar(
    value = 3F,
    max = 5F,
    stepped = true
)
```