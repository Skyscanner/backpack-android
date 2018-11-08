# Text

## Installation

Backpack Android is available through [Jitpack](https://jitpack.io/#Skyscanner/backpack-android). Check the main [Readme](../../README.md#installation) for a complete installation guide.

## Usage

The Text component can be used in both XML and Kotlin

Example of a text view with large and emphasized font in XML

```xml
<net.skyscanner.backpack.text.BpkText
  android:layout_width="wrap_content"
  android:layout_height="wrap_content"
  android:text="Flights to Edinburgh"
  app:weight="emphasized"
  app:textStyle="xxl" />
```

Example of a text view with large and emphasized font in Kotlin

```Kotlin
import net.skyscanner.backpack.text.BpkText

BpkText(context).apply {
   text = 'Flights to Edinburgh'
   textStyle = 'xxl'
   weight = BpkText.Weight.EMPHASIZED
}
```
