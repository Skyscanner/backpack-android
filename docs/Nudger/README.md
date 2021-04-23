# Nudger

## Installation

Backpack Android is available through [Jitpack](https://jitpack.io/#Skyscanner/backpack-android). Check the main [Readme](https://github.com/skyscanner/backpack-android#installation) for a complete installation guide.

## Usage

The Nudger component can be used in both XML and Kotlin

Example of a nudger in XML

```xml
<net.skyscanner.backpack.nudger.BpkNudger
  android:layout_width="wrap_content"
  android:layout_height="wrap_content"
  app:nudgerMaximumValue="10"
  app:nudgerMinimumValue="0"
  app:nudgerValue="5" />
```

Example of a nudger in Kotlin

```Kotlin
import net.skyscanner.backpack.nudger.BpkNudger

BpkNudger(context).apply {
   maximumValue = 10
   minimumValue = 0
   value = 5
}
```
