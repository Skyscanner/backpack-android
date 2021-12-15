
# Horizontal Nav

## Installation

Backpack Android is available through [Maven Central](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-android). Check the main [Readme](https://github.com/skyscanner/backpack-android#installation) for a complete installation guide.

## Usage

The `HorizontalNav` component can be used in both XML and Kotlin/Java

Example of a `HorizontalNav` in XML

```xml
<net.skyscanner.backpack.horisontalnav.BpkHorizontalNav
  android:layout_width="wrap_content"
  android:layout_height="wrap_content"/>
```

Example of a `HorizontalNav` in Kotlin

```Kotlin
import net.skyscanner.backpack.horisontalnav.BpkHorizontalNav

BpkHorizontalNav(context).apply {
  addTab(newTab().setText("1"))
  addTab(newTab().setText("2"))
  setNotificationDot(0, true)
  setBadge(1, "Beta")
}
```

## Theme Props

- `horizontalNavTextColor`
- `horizontalNavSelectedTextColor`
- `horizontalNavIndicatorColor`

Styles can be changed globally through `bpkHorizontalNavStyle` and `bpkHorizontalNavStyleAlternate`. Check [theming](https://github.com/Skyscanner/backpack-android/blob/main/docs/view/THEMING.md) for more information.
