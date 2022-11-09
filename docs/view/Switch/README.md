# Switch

[![Maven Central](https://img.shields.io/maven-central/v/net.skyscanner.backpack/backpack-android)](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-android)
[![Class reference](https://img.shields.io/badge/Class%20reference-Android-blue)](https://backpack.github.io/android/Backpack/net.skyscanner.backpack.switch)
[![Source code](https://img.shields.io/badge/Source%20code-GitHub-lightgrey)](https://github.com/Skyscanner/backpack-android/tree/main/Backpack/src/main/java/net/skyscanner/backpack/switch)

## Default

| Day | Night |
| --- | --- |
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/view/Switch/screenshots/default.png" alt="Switch component" width="375" /> |<img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/view/Switch/screenshots/default_dm.png" alt="Switch component - dark mode" width="375" /> |

## Installation

Backpack Android is available through [Maven Central](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-android). Check the main [Readme](https://github.com/skyscanner/backpack-android#installation) for a complete installation guide.

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

Styles can be changed globally through `bpkSwitchStyle`. Check [theming](https://github.com/Skyscanner/backpack-android/blob/main/docs/view/THEMING.md) for more information.

