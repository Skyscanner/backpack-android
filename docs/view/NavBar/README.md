# Nav Bar

[![Maven Central](https://img.shields.io/maven-central/v/net.skyscanner.backpack/backpack-android)](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-android)
[![Class reference](https://img.shields.io/badge/Class%20reference-Android-blue)](https://backpack.github.io/android/Backpack/net.skyscanner.backpack.navbar)
[![Source code](https://img.shields.io/badge/Source%20code-GitHub-lightgrey)](https://github.com/Skyscanner/backpack-android/tree/main/Backpack/src/main/java/net/skyscanner/backpack/navbar)

## Collapsed

| Day | Night |
| --- | --- |
| ![Collapsed NavBar component](https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/view/NavBar/screenshots/collapsed.png) |![Collapsed NavBar component - dark mode](https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/view/NavBar/screenshots/collapsed_dm.png) |

## Expanded

| Day | Night |
| --- | --- |
| ![Expanded NavBar component](https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/view/NavBar/screenshots/expanded.png) |![Expanded NavBar component - dark mode](https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/view/NavBar/screenshots/expanded_dm.png) |

## Navigation

| Day | Night |
| --- | --- |
| ![Navigation NavBar component](https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/view/NavBar/screenshots/navigation.png) |![Navigation NavBar component - dark mode](https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/view/NavBar/screenshots/navigation_dm.png) |

## Installation

Backpack Android is available through [Maven Central](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-android). Check the main [Readme](https://github.com/skyscanner/backpack-android#installation) for a complete installation guide.

## Usage

The `NavBar` component can be used in both XML and Kotlin/Java

Example of a `NavBar` in XML

```xml
<net.skyscanner.backpack.navbar.BpkNavBar
  android:layout_width="match_parent"
  android:layout_height="wrap_content"
  app:navBarTitle="Nav Bar"
  app:navBarIcon="@drawable/bpk_native_android__back"
  app:navBarMenu="@menu/settings"
  app:navBarExpandedTextColor="@color/bpkSkyGray"
  app:navBarCollapsedTextColor="@color/bpkSkyGray"/>
```

Example of a `NavBar` in Kotlin

```Kotlin
import net.skyscanner.backpack.navbar.BpkNavBar

BpkNavBar(context).apply {
  title = "Nav Bar"
  menu = R.menu.settings
  icon = AppCompatResources.getDrawable(context, R.drawable.bpk_native_android__back)
  expandedTitleColor = context.getColor(R.color.bpkSkyGray)
  collapsedTitleColor = context.getColor(R.color.bpkSkyGray)
}
```

The `NavBar` component uses the same logic as [`AppBarLayout`](https://developer.android.com/reference/android/support/design/widget/AppBarLayout),
and needs to be used as a direct child of [`CoordinatorLayout`](https://developer.android.com/reference/android/support/design/widget/CoordinatorLayout).

The scrolling child of [`CoordinatorLayout`](https://developer.android.com/reference/android/support/design/widget/CoordinatorLayout) should have
`app:layout_behavior="@string/appbar_scrolling_view_behavior"` attribute.

## Theme Props

- `navBarCollapsedTextColor` - `bpkTextPrimary` by default.

Styles can be changed globally through `bpkNavBarStyle`. Check [theming](https://github.com/Skyscanner/backpack-android/blob/main/docs/view/THEMING.md) for more information.
