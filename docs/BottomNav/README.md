# Bottom Nav

## Installation

Backpack Android is available through [Jitpack](https://jitpack.io/#Skyscanner/backpack-android). Check the main [Readme](https://github.com/skyscanner/backpack-android#installation) for a complete installation guide.

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
