# Floating Action Button

[![Maven Central](https://img.shields.io/maven-central/v/net.skyscanner.backpack/backpack-android)](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-android)
[![Class reference](https://img.shields.io/badge/Class%20reference-Android-blue)](https://backpack.github.io/android/Backpack/net.skyscanner.backpack.fab)
[![Source code](https://img.shields.io/badge/Source%20code-GitHub-lightgrey)](https://github.com/Skyscanner/backpack-android/tree/main/Backpack/src/main/java/net/skyscanner/backpack/fab)

## Default

| Day | Night |
| --- | --- |
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/view/FloatingActionButton/screenshots/default.png" alt="FloatingActionButton component" width="375" /> |<img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/view/FloatingActionButton/screenshots/default_dm.png" alt="FloatingActionButton component - dark mode" width="375" /> |

## Installation

Backpack Android is available through [Maven Central](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-android). Check the main [Readme](https://github.com/skyscanner/backpack-android#installation) for a complete installation guide.

## Usage

The Floating Action Button component can be used in both XML and Kotlin/Java

Example of a Floating Action Button in XML

```xml
<net.skyscanner.backpack.fab.BpkFab
  android:layout_width="56dp"
  android:layout_height="56dp"
  android:contentDescription="@string/search"
  android:src="@drawable/bpk_search" />
```

Example of a Floating Action Button in Kotlin

```Kotlin
import net.skyscanner.backpack.fab.BpkFab

BpkFab(context).apply {
  setImageResource(R.drawable.bpk_search)
  setContentDescription(resources.getString(R.string.search))
}
```

## Theme Props

- `fabBackgroundColor`
- `fabIconColor`

Styles can be changed globally through `bpkFabStyle`. Check [theming](https://github.com/Skyscanner/backpack-android/blob/main/docs/view/THEMING.md) for more information.
