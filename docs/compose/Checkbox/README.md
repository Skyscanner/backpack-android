# Checkbox

[![Maven Central](https://img.shields.io/maven-central/v/net.skyscanner.backpack/backpack-compose)](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-compose)
[![Class reference](https://img.shields.io/badge/Class%20reference-Android-blue)](https://backpack.github.io/android/backpack-compose/net.skyscanner.backpack.compose.checkbox)
[![Source code](https://img.shields.io/badge/Source%20code-GitHub-lightgrey)](https://github.com/Skyscanner/backpack-android/tree/main/backpack-compose/src/main/kotlin/net/skyscanner/backpack/compose/checkbox)

## Default

| Day | Night |
| --- | --- |
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/Checkbox/screenshots/default.png" alt="Checkbox component" width="375" /> |<img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/Checkbox/screenshots/default_dm.png" alt="Checkbox component - dark mode" width="375" /> |

## Installation

Backpack Compose is available through [Maven Central](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-compose). Check the main [Readme](https://github.com/skyscanner/backpack-android#installation) for a complete installation guide.

## Usage

Example of a Checkbox:

```Kotlin
import net.skyscanner.backpack.compose.checkbox.BpkCheckbox

BpkCheckbox(
  text = "Checkbox text",
  checked = true,
  onCheckedChange = { checked -> },
)
```

Example of a Checkbox with intermediate state:

```Kotlin
import net.skyscanner.backpack.compose.checkbox.BpkCheckbox

BpkCheckbox(
  text = "Checkbox text",
  checked = true,
  onCheckedChange = { checked -> },
)
```

Example of a Checkbox with custom content:

```Kotlin
import net.skyscanner.backpack.compose.checkbox.BpkCheckbox

BpkCheckbox(
  checked = true,
  onCheckedChange = { checked -> },
) { checked ->
    MyCustomContent()
}
```
