# Nudger

[![Maven Central](https://img.shields.io/maven-central/v/net.skyscanner.backpack/backpack-compose)](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-compose)
[![Class reference](https://img.shields.io/badge/Class%20reference-Android-blue)](https://backpack.github.io/android/backpack-compose/net.skyscanner.backpack.compose.nudger)
[![Source code](https://img.shields.io/badge/Source%20code-GitHub-lightgrey)](https://github.com/Skyscanner/backpack-android/tree/main/backpack-compose/src/main/kotlin/net/skyscanner/backpack/compose/nudger)

## Default

| Day                                                                                                                             | Night                                                                                                                                          |
|---------------------------------------------------------------------------------------------------------------------------------|------------------------------------------------------------------------------------------------------------------------------------------------|
| ![Nudger component](https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/Nudger/screenshots/all.png) | ![Nudger component - dark mode](https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/Nudger/screenshots/all_dm.png) |

## Installation

Backpack Compose is available through [Maven Central](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-compose). Check the main [Readme](https://github.com/skyscanner/backpack-android#installation) for a complete installation guide.

## Usage

Example of a Nudger:

```Kotlin
import net.skyscanner.backpack.compose.nudger.BpkNudger

BpkNudger(
  value = currentValue,
  onValueChange = { /* update the value */ },
  range = minValue..maxValue,
)
```
