# Page Title

## Installation

Backpack Android is available through [Jitpack](https://jitpack.io/#Skyscanner/backpack-android). Check the main [Readme](https://github.com/skyscanner/backpack-android#installation) for a complete installation guide.

## Usage

The Page Title component can be used in both XML and Kotlin/Java

Example of a Page Title in XML

```xml
<net.skyscanner.backpack.pagetitle.BpkPageTitle
  android:layout_width="match_parent"
  android:layout_height="wrap_content"
  app:pageTitleText="Page Title"
  app:pageTitleExpandedTextColor="@color/bpkGray900"
  app:pageTitleCollapsedTextColor="@color/bpkGray900"/>
```

Example of a Page Title in Kotlin

```Kotlin
import net.skyscanner.backpack.pagetitle.BpkPageTitle

BpkPageTitle(context).apply {
  title = "Page Title"
  expandedTitleColor = ContextCompat.getColor(context, R.color.bpkGray900)
  collapsedTitleColor = ContextCompat.getColor(context, R.color.bpkGray900)
}
```

The Page Title component uses the same logic as [`AppBarLayout`](https://developer.android.com/reference/android/support/design/widget/AppBarLayout),
and needs to be used as a direct child of [`CoordinatorLayout`](https://developer.android.com/reference/android/support/design/widget/CoordinatorLayout).

The scrolling child of [`CoordinatorLayout`](https://developer.android.com/reference/android/support/design/widget/CoordinatorLayout) should have 
`app:layout_behavior="@string/appbar_scrolling_view_behavior"` attribute.

## Theme Props

- `pageTitleBackgroundColor` - `bpkWhite` by default.
- `pageTitleToolbarStyle` - `?attr/toolbarStyle` by default.

Styles can be changed globally through `bpkPageTitleStyle`. Check [theming](https://github.com/Skyscanner/backpack-android/blob/master/docs/THEMING.md) for more information.
