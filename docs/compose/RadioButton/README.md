# RadioButton

[![Maven Central](https://img.shields.io/maven-central/v/net.skyscanner.backpack/backpack-compose)](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-compose)
[![Class reference](https://img.shields.io/badge/Class%20reference-Android-blue)](https://backpack.github.io/android/backpack-compose/net.skyscanner.backpack.compose.radiobutton)
[![Source code](https://img.shields.io/badge/Source%20code-GitHub-lightgrey)](https://github.com/Skyscanner/backpack-android/tree/main/backpack-compose/src/main/kotlin/net/skyscanner/backpack/compose/radiobutton)

## Default

| Day | Night |
| --- | --- |
| ![RadioButton component](https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/RadioButton/screenshots/default.png) |![RadioButton component - dark mode](https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/RadioButton/screenshots/default_dm.png) |

## Installation

Backpack Compose is available through [Maven Central](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-compose). Check the main [Readme](https://github.com/skyscanner/backpack-android#installation) for a complete installation guide.

## Usage

Example of a RadioButton:

```Kotlin
import net.skyscanner.backpack.compose.radiobutton.BpkRadioButton

BpkRadioButton(
  text = "Radiobutton text",
  selected = true,
  onClick = { },
)
```

Example of a RadioButton with custom content:

```Kotlin
import net.skyscanner.backpack.compose.radiobutton.BpkRadioButton

BpkRadioButton(
  selected = true,
  onClick = { },
) { selected ->
  MyCustomContent()
}
```
