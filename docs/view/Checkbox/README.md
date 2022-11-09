# Checkbox

[![Maven Central](https://img.shields.io/maven-central/v/net.skyscanner.backpack/backpack-android)](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-android)
[![Class reference](https://img.shields.io/badge/Class%20reference-Android-blue)](https://backpack.github.io/android/Backpack/net.skyscanner.backpack.checkbox)
[![Source code](https://img.shields.io/badge/Source%20code-GitHub-lightgrey)](https://github.com/Skyscanner/backpack-android/tree/main/Backpack/src/main/java/net/skyscanner/backpack/checkbox)

## Default

| Day | Night |
| --- | --- |
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/view/Checkbox/screenshots/default.png" alt="Checkbox component" width="375" /> |<img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/view/Checkbox/screenshots/default_dm.png" alt="Checkbox component - dark mode" width="375" /> |

## Installation

Backpack Android is available through [Maven Central](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-android). Check the main [Readme](https://github.com/skyscanner/backpack-android#installation) for a complete installation guide.

## Usage

The Checkbox component can be used in both XML and Kotlin/Java

Example of a checkbox in XML

```xml
<net.skyscanner.backpack.checkbox.BpkCheckbox
  android:layout_width="wrap_content"
  android:layout_height="wrap_content"/>
```

Example of a checkbox in Kotlin

```Kotlin
import net.skyscanner.backpack.checkbox.BpkCheckbox

BpkCheckbox(context).apply {
  isChecked = true
}
```

## Theme Props

- `checkboxColorChecked`
- `checkboxColorDisabled`
- `checkboxColor`

Styles can be changed globally through `bpkCheckboxStyle`. Check [theming](https://github.com/Skyscanner/backpack-android/blob/main/docs/view/THEMING.md) for more information.
