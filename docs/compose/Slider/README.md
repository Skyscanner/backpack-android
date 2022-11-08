# Slider

## Installation

Backpack Compose is available through [Maven Central](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-compose). Check the main [Readme](https://github.com/skyscanner/backpack-android#installation) for a complete installation guide.

## Usage

Example of a `BpkSlider`:

```Kotlin
import net.skyscanner.backpack.compose.slider.BpkSlider

BpkSlider {
  value = 0.5f
  onValueChange = { newValue -> } // Handle update
}
```

Example of a `BpkRangeSlider`:

```Kotlin
import net.skyscanner.backpack.compose.slider.BpkSlider

BpkRangeSlider {
  values = 0.2f..0.8f
  onValueChange = { newValue -> } // Handle update
}
```
