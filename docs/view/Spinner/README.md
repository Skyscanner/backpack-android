# Spinner

[![Maven Central](https://img.shields.io/maven-central/v/net.skyscanner.backpack/backpack-android)](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-android)
[![Class reference](https://img.shields.io/badge/Class%20reference-Android-blue)](https://backpack.github.io/android/Backpack/net.skyscanner.backpack.spinner)
[![Source code](https://img.shields.io/badge/Source%20code-GitHub-lightgrey)](https://github.com/Skyscanner/backpack-android/tree/main/Backpack/src/main/java/net/skyscanner/backpack/spinner)

## Default

| Day | Night |
| --- | --- |
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/view/Spinner/screenshots/default.png" alt="Spinner component" width="375" /> |<img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/view/Spinner/screenshots/default_dm.png" alt="Spinner component - dark mode" width="375" /> |

## Installation

Backpack Android is available through [Maven Central](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-android). Check the main [Readme](https://github.com/skyscanner/backpack-android#installation) for a complete installation guide.

## Usage

The Spinner component can be used in both XML and Kotlin/Java

Example of a primary spinner in XML

```xml
<net.skyscanner.backpack.spinner.BpkSpinner
  android:layout_width="match_parent"
  android:layout_height="wrap_content"
  app:type="primary"
  app:small="false" />
```

Example of a primary spinner in Kotlin

```Kotlin
import net.skyscanner.backpack.spinner.BpkSpinner

BpkSpinner(context).apply {
  type = BpkSpinner.Type.PRIMARY
  small = false
}
```

## Theme Props

- `spinnerColor`

Styles for the `primary` spinner can be changed globally through `bpkSpinnerPrimaryStyle`. Check [theming](https://github.com/Skyscanner/backpack-android/blob/main/docs/view/THEMING.md) for more information.
