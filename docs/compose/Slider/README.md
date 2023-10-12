# Slider

[![Maven Central](https://img.shields.io/maven-central/v/net.skyscanner.backpack/backpack-compose)](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-compose)
[![Class reference](https://img.shields.io/badge/Class%20reference-Android-blue)](https://backpack.github.io/android/backpack-compose/net.skyscanner.backpack.compose.slider)
[![Source code](https://img.shields.io/badge/Source%20code-GitHub-lightgrey)](https://github.com/Skyscanner/backpack-android/tree/main/backpack-compose/src/main/kotlin/net/skyscanner/backpack/compose/slider)

## Default

| Day | Night |
| --- | --- |
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/Slider/screenshots/default.png" alt="Slider component" width="375" /> | <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/Slider/screenshots/default_dm.png" alt="Slider component - dark mode" width="375" /> |

## Installation

Backpack Compose is available through [Maven Central](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-compose). Check the main [Readme](https://github.com/skyscanner/backpack-android#installation) for a complete installation guide.

## Usage

Example of a `BpkSlider`:

```Kotlin
import net.skyscanner.backpack.compose.slider.BpkSlider

BpkSlider(
  value = 0.5f,
  onValueChange = { newValue -> } // Handle update
)
```

Example of a `BpkRangeSlider`:

```Kotlin
import net.skyscanner.backpack.compose.slider.BpkSlider

BpkRangeSlider(
  value = 0.2f..0.8f,
  onValueChange = { newValue -> } // Handle update
)
```

Example of a `BpkRangeSlider` with Label:

```Kotlin
import net.skyscanner.backpack.compose.slider.BpkSlider

var rangeSliderValue by remember { mutableStateOf(0.2f..0.8f) }
BpkRangeSlider(
    value = rangeSliderValue,
    lowerThumbLabel = stringResource(id = R.string.gbp_formatter, rangeSliderValue.start),
    upperThumbLabel = stringResource(id = R.string.gbp_formatter, rangeSliderValue.endInclusive),
    onValueChange = { newValue -> } // Handle update
)
```
