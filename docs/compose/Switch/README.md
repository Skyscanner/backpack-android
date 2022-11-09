# Switch

[![Maven Central](https://img.shields.io/maven-central/v/net.skyscanner.backpack/backpack-compose)](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-compose)
[![Class reference](https://img.shields.io/badge/Class%20reference-Android-blue)](https://backpack.github.io/android/backpack-compose/net.skyscanner.backpack.compose.switch)
[![Source code](https://img.shields.io/badge/Source%20code-GitHub-lightgrey)](https://github.com/Skyscanner/backpack-android/tree/main/backpack-compose/src/main/kotlin/net/skyscanner/backpack/compose/switch)

## Default

| Day | Night |
| --- | --- |
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/Switch/screenshots/default.png" alt="Switch component" width="375" /> |<img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/Switch/screenshots/default_dm.png" alt="Switch component - dark mode" width="375" /> |

## Installation

Backpack Compose is available through [Maven Central](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-compose). Check the main [Readme](https://github.com/skyscanner/backpack-android#installation) for a complete installation guide.

## Usage

Example of a Switch:

```Kotlin
import net.skyscanner.backpack.compose.switch.BpkSwitch

BpkSwitch(
  text = "Switch text",
  checked = true,
  onCheckedChange = { checked -> },
)
```

Example of a Switch with custom content:

```Kotlin
import net.skyscanner.backpack.compose.switch.BpkSwitch

BpkSwitch(
  checked = true,
  onCheckedChange = { checked -> },
) { checked ->
  MyCustomContent()
}
```
