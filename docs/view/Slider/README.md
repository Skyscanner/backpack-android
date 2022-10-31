# Slider

[![Maven Central](https://img.shields.io/maven-central/v/net.skyscanner.backpack/backpack-android)](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-android)
[![Class reference](https://img.shields.io/badge/Class%20reference-Android-blue)](https://backpack.github.io/android/Backpack/net.skyscanner.backpack.slider)
[![Source code](https://img.shields.io/badge/Source%20code-GitHub-lightgrey)](https://github.com/Skyscanner/backpack-android/tree/main/Backpack/src/main/java/net/skyscanner/backpack/slider)

## All

| Day | Night |
| --- | --- |
| ![Slider component](https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/view/Slider/screenshots/all.png) |![Slider component - dark mode](https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/view/Slider/screenshots/all_dm.png) |

## Installation

Backpack Android is available through [Maven Central](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-android). Check the main [Readme](https://github.com/skyscanner/backpack-android#installation) for a complete installation guide.

## Usage

The Slider component can be used in both XML and Kotlin

Example of a slider in XML

```xml
<net.skyscanner.backpack.slider.BpkSlider
  android:layout_width="wrap_content"
  android:layout_height="wrap_content"
  android:valueTo="10"
  android:valueFrom="0"
  android:value="5"
  android:stepSize="1" />
```

Example of a slider in Kotlin

```Kotlin
import net.skyscanner.backpack.slider.BpkSlider

BpkSlider(context).apply {
   valueFrom = 0f
   valueTo = 10f
   value = 5f
   stepSize = 1f
}
```

Example of a range slider in XML

```xml
<net.skyscanner.backpack.slider.BpkSlider
  android:layout_width="wrap_content"
  android:layout_height="wrap_content"
  android:valueTo="100"
  android:valueFrom="0"
  app:values="@array/initial_slider_values"
  android:stepSize="5" />
```

```xml
<resources>
  <array name="initial_slider_values">
    <item>25</item>
    <item>75</item>
  </array>
</resources>
```

Example of a range slider in Kotlin

```Kotlin
import net.skyscanner.backpack.slider.BpkSlider

BpkSlider(context).apply {
   valueFrom = 0f
   valueTo = 100f
   setValues(25f, 75f)
   stepSize = 5f
}
```

Example of a slider with formatted label in Kotlin

```Kotlin
import net.skyscanner.backpack.slider.BpkSlider
import java.text.NumberFormat
import java.util.*

BpkSlider(context).apply {
   valueFrom = 1000f
   valueTo = 5000f
   value = 3000f
   stepSize = 10f
   setLabelFormatter { value: Float ->
     val format = NumberFormat.getCurrencyInstance()
     format.maximumFractionDigits = 0
     format.currency = Currency.getInstance("GBP")
     format.format(value.toDouble())
   }
}
```
