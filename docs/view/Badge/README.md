# Badge

[![Maven Central](https://img.shields.io/maven-central/v/net.skyscanner.backpack/backpack-android)](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-android)
[![Class reference](https://img.shields.io/badge/Class%20reference-Android-blue)](https://backpack.github.io/android/Backpack/net.skyscanner.backpack.badge)
[![Source code](https://img.shields.io/badge/Source%20code-GitHub-lightgrey)](https://github.com/Skyscanner/backpack-android/tree/main/Backpack/src/main/java/net/skyscanner/backpack/badge)

## All

| Day | Night |
| --- | --- |
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/view/Badge/screenshots/all.png" alt="Badge component" width="375" /> |<img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/view/Badge/screenshots/all_dm.png" alt="Badge component - dark mode" width="375" /> |

## Installation

Backpack Android is available through [Maven Central](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-android). Check the main [Readme](https://github.com/skyscanner/backpack-android#installation) for a complete installation guide.

## Usage

The Badge component can be used in both XML and Kotlin

Example of a success type badge in XML

```xml
<net.skyscanner.backpack.badge.BpkBadge
  android:layout_width="wrap_content"
  android:layout_height="wrap_content"
  android:layout_margin="@dimen/bpkSpacingBase"
  app:message="Message"
  app:type="success"/>
```

Example of a success type badge in Kotlin

```Kotlin
import net.skyscanner.backpack.badge.BpkBadge

BpkBadge(context).apply {
   message = 'Message'
   type = 'success'
}
```

Styles can be changed globally through `bpkBadgeStyle`. Check [theming](https://github.com/Skyscanner/backpack-android/blob/main/docs/view/THEMING.md) for more information.
