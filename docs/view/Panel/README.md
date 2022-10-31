# Panel

[![Maven Central](https://img.shields.io/maven-central/v/net.skyscanner.backpack/backpack-android)](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-android)
[![Class reference](https://img.shields.io/badge/Class%20reference-Android-blue)](https://backpack.github.io/android/Backpack/net.skyscanner.backpack.panel)
[![Source code](https://img.shields.io/badge/Source%20code-GitHub-lightgrey)](https://github.com/Skyscanner/backpack-android/tree/main/Backpack/src/main/java/net/skyscanner/backpack/panel)

## All

| Day | Night |
| --- | --- |
| ![Panel component](https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/view/Panel/screenshots/all.png) |![Panel component - dark mode](https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/view/Panel/screenshots/all_dm.png) |

## Installation

Backpack Android is available through [Maven Central](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-android). Check the main [Readme](https://github.com/skyscanner/backpack-android#installation) for a complete installation guide.

## Usage

The Panel component can be used in both XML and Kotlin

Example of a padded panel in XML

```xml
<net.skyscanner.backpack.panel.BpkPanel
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    app:padding="true">

    <TextView
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:text="panel message" />
</net.skyscanner.backpack.panel.BpkPanel>
```

Example of a padded panel in Kotlin

```Kotlin
import net.skyscanner.backpack.panel.BpkPanel

BpkPanel(context).apply {
   padding = true
}
```
