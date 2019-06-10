# Floating Action Button

## Installation

Backpack Android is available through [Jitpack](https://jitpack.io/#Skyscanner/backpack-android). Check the main [Readme](https://github.com/skyscanner/backpack-android#installation) for a complete installation guide.

## Usage

The Floating Action Button component can be used in both XML and Kotlin/Java

Example of a Floating Action Button in XML

```xml
<net.skyscanner.backpack.fab.BpkFab
  android:layout_width="56dp"
  android:layout_height="56dp"
  app:fabIcon="@drawable/bpk_search" />
```

Example of a Floating Action Button in Kotlin

```Kotlin
import androidx.core.content.ContextCompat
import net.skyscanner.backpack.fab.BpkFab

BpkFab(context).apply {
  icon = ContextCompat.getDrawable(context, R.drawable.bpk_search)
}
```

## Theme Props

- `fabBackgroundColor`
- `fabIconColor`

Styles can be changed globally through `bpkFabStyle`. Check [theming](https://github.com/Skyscanner/backpack-android/blob/master/docs/THEMING.md) for more information.
