# Nudger

## Installation

Backpack Android is available through [Maven Central](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-android). Check the main [Readme](https://github.com/skyscanner/backpack-android#installation) for a complete installation guide.

## Usage

The Nudger component can be used in both XML and Kotlin

Example of a nudger in XML

```xml
<net.skyscanner.backpack.nudger.BpkNudger
  android:layout_width="wrap_content"
  android:layout_height="wrap_content"
  app:nudgerMaxValue="10"
  app:nudgerMinValue="0"
  app:nudgerValue="5" />
```

Example of a nudger in Kotlin

```Kotlin
import net.skyscanner.backpack.nudger.BpkNudger

BpkNudger(context).apply {
   maxValue = 10
   minValue = 0
   value = 5
}
```
