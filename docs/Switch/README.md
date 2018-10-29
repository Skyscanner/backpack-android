# Switch

## Installation

Backpack Android is available through [Jitpack](https://jitpack.io/#Skyscanner/backpack-android). Check the main [Readme](../../README.md#installation) for a complete installation guide.

## Usage

The Switch component can be used in both XML and Kotlin/Java

Example of a switch in XML

```xml
<net.skyscanner.backpack.toggle.BpkSwitch
  android:layout_width="wrap_content"
  android:layout_height="wrap_content"
  android:checked="true" />
```

Example of a switch in Kotlin

```Kotlin
import net.skyscanner.backpack.toggle.BpkSwitch

BpkSwitch(context).apply {
  isChecked = true
}
```
