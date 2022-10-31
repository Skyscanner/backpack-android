# Spinner

[![Maven Central](https://img.shields.io/maven-central/v/net.skyscanner.backpack/backpack-compose)](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-compose)
[![Class reference](https://img.shields.io/badge/Class%20reference-Android-blue)](https://backpack.github.io/android/backpack-compose/net.skyscanner.backpack.compose.spinner)
[![Source code](https://img.shields.io/badge/Source%20code-GitHub-lightgrey)](https://github.com/Skyscanner/backpack-android/tree/main/backpack-compose/src/main/kotlin/net/skyscanner/backpack/compose/spinner)

## Default

| Day | Night |
| --- | --- |
| ![Spinner component](https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/Spinner/screenshots/default.png) |![Spinner component - dark mode](https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/Spinner/screenshots/default_dm.png) |

## Installation

Backpack Compose is available through [Maven Central](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-compose). Check the main [Readme](https://github.com/skyscanner/backpack-android#installation) for a complete installation guide.

## Usage

Example of a Spinner:

```Kotlin
import net.skyscanner.backpack.compose.spinner.BpkSpinner
import net.skyscanner.backpack.compose.spinner.BpkSpinnerSize

BpkSpinner(
  size = BpkSpinnerSize.Large,
)
```

Example of a Spinner on dark surface:

```Kotlin
import net.skyscanner.backpack.compose.spinner.BpkSpinner
import net.skyscanner.backpack.compose.spinner.BpkSpinnerSize
import net.skyscanner.backpack.compose.spinner.BpkSpinnerStype

BpkSpinner(
  size = BpkSpinnerSize.Large,
  style = BpkSpinnerStyle.OnDarkSurface,
)
```

When style is not specified, the Spinner sets it based on `LocalContentColor`. When color is more close to white, and it's
in light mode `BpkSpinnerStyle.OnDarkSurface` will be used – otherwise it falls back to `BpkSpinnerStyle.TextPrimary`.
