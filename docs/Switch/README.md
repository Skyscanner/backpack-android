# Switch

## Installation

Backpack Android is available through [Jitpack](https://jitpack.io/#Skyscanner/backpack-android). Check the main [Readme](https://github.com/skyscanner/backpack-android#installation) for a complete installation guide.

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

## Theme Props

- `switchPrimaryColor`

Styles can be changed globally through `bpkSwitchStyle`. Check [theming](https://github.com/Skyscanner/backpack-android/blob/master/docs/THEMING.md) for more information.

