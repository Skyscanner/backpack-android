# Slider

## Installation

Backpack Android is available through [Jitpack](https://jitpack.io/#Skyscanner/backpack-android). Check the main [Readme](https://github.com/skyscanner/backpack-android#installation) for a complete installation guide.

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

