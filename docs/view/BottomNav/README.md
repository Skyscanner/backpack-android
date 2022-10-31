# Bottom Nav

[![Maven Central](https://img.shields.io/maven-central/v/net.skyscanner.backpack/backpack-android)](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-android)
[![Class reference](https://img.shields.io/badge/Class%20reference-Android-blue)](https://backpack.github.io/android/Backpack/net.skyscanner.backpack.bottomnav)
[![Source code](https://img.shields.io/badge/Source%20code-GitHub-lightgrey)](https://github.com/Skyscanner/backpack-android/tree/main/Backpack/src/main/java/net/skyscanner/backpack/bottomnav)

## Default

| Day | Night |
| --- | --- |
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/view/BottomNav/screenshots/default.png" alt="BottomNav component" width="375" /> |<img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/view/BottomNav/screenshots/default_dm.png" alt="BottomNav component - dark mode" width="375" /> |

## Installation

Backpack Android is available through [Maven Central](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-android). Check the main [Readme](https://github.com/skyscanner/backpack-android#installation) for a complete installation guide.

## Usage

The Bottom Nav component can be used in both XML and Kotlin/Java

Example of a Bottom Nav in XML

```xml
<net.skyscanner.backpack.bottomnav.BpkBottomNav
  android:id="@+id/bottom_nav"
  android:layout_width="match_parent"
  android:layout_height="wrap_content"
  android:layout_gravity="bottom"
  app:menu="@menu/bottom_nav"/>
```

Example of a Bottom Nav in Kotlin

```Kotlin
import net.skyscanner.backpack.bottomnav.BpkBottomNav

BpkBottomNav(context).apply {
      addItem(ID_HOME, R.string.bottom_nav_home, R.drawable.bpk_hotels)
      addItem(ID_EXPLORE, R.string.bottom_nav_explore, R.drawable.bpk_navigation)
      addItem(ID_TRIPS, R.string.bottom_nav_trips, R.drawable.bpk_trips)
      addItem(ID_PROFILE, R.string.bottom_nav_profile, R.drawable.bpk_account_circle)
}
```
