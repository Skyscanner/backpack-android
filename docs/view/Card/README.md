# Card

[![Maven Central](https://img.shields.io/maven-central/v/net.skyscanner.backpack/backpack-android)](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-android)
[![Class reference](https://img.shields.io/badge/Class%20reference-Android-blue)](https://backpack.github.io/android/Backpack/net.skyscanner.backpack.card)
[![Source code](https://img.shields.io/badge/Source%20code-GitHub-lightgrey)](https://github.com/Skyscanner/backpack-android/tree/main/Backpack/src/main/java/net/skyscanner/backpack/card)

## Default

| Day | Night |
| --- | --- |
| ![Card component](https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/view/Card/screenshots/default.png) |![Card component - dark mode](https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/view/Card/screenshots/default_dm.png) |

## Corner style large

| Day | Night |
| --- | --- |
| ![Corner style large Card component](https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/view/Card/screenshots/corner-style-large.png) |![Corner style large Card component - dark mode](https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/view/Card/screenshots/corner-style-large_dm.png) |

## Selected

| Day | Night |
| --- | --- |
| ![Selected Card component](https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/view/Card/screenshots/selected.png) |![Selected Card component - dark mode](https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/view/Card/screenshots/selected_dm.png) |

## With divider and corner style large

| Day | Night |
| --- | --- |
| ![With divider and corner style large Card component](https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/view/Card/screenshots/with-divider-and-corner-style-large.png) |![With divider and corner style large Card component - dark mode](https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/view/Card/screenshots/with-divider-and-corner-style-large_dm.png) |

## With divider arranged vertically

| Day | Night |
| --- | --- |
| ![With divider arranged verticy Card component](https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/view/Card/screenshots/with-divider-arranged-vertically.png) |![With divider arranged verticy Card component - dark mode](https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/view/Card/screenshots/with-divider-arranged-vertically_dm.png) |

## With divider without padding

| Day | Night |
| --- | --- |
| ![With divider without padding Card component](https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/view/Card/screenshots/with-divider-without-padding.png) |![With divider without padding Card component - dark mode](https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/view/Card/screenshots/with-divider-without-padding_dm.png) |

## With divider

| Day | Night |
| --- | --- |
| ![With divider Card component](https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/view/Card/screenshots/with-divider.png) |![With divider Card component - dark mode](https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/view/Card/screenshots/with-divider_dm.png) |

## Without padding

| Day | Night |
| --- | --- |
| ![Without padding Card component](https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/view/Card/screenshots/without-padding.png) |![Without padding Card component - dark mode](https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/view/Card/screenshots/without-padding_dm.png) |

## Installation

Backpack Android is available through [Maven Central](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-android). Check the main [Readme](https://github.com/skyscanner/backpack-android#installation) for a complete installation guide.

## Usage

`BpkCardView` is an extension of `CardView` in `com.android.support:cardview-v7`

The Card component can be used in both XML and Kotlin

Example of a padded and focused card in XML

```xml
 <net.skyscanner.backpack.card.BpkCardView
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:clickable="true"
    android:focusable="true"
    android:foreground="?android:attr/selectableItemBackground"
    app:elevationLevel="focused"
    app:cornerStyle="small">

    <net.skyscanner.backpack.text.BpkText
      android:layout_width="match_parent"
      android:layout_height="match_parent"
      android:text="@string/stub_md" />

  </net.skyscanner.backpack.card.BpkCardView>
```

Example of a padded and focused card in Kotlin

```Kotlin
import net.skyscanner.backpack.card.BpkCardView

BpkCardView(context).apply {
   padded = true
   elevationLevel = BpkCardView.ElevationLevel.Focused
   cornerStyle = BpkCardView.CornerStyle.SMALL
}
```

## Properties

| Property         | PropType                                 | Required | Default Value |
| ---------------- | ---------------------------------------- | -------- | ------------- |
| `padded`         | Boolean                                  | false    | true          |
| `cornerStyle`    | `CornerStyle`: Small, Large              | false    | Small         |
| `elevationLevel` | `ElevationLevel`: None, Default, Focused | false    | Default       |

## Accessibility

Consider if your card should be atomic or non-atomic. If the copy inside your card is lengthy, consider making it non-atomic and providing a CTA inside. The entire card will still be clickable/tappable, but will be ignored by TalkBack.

A clickable & focusable card is atomic by default, whereas a non-clickable/focusable card is non-atomic. To change that set the `importantForAccessibility` property to `yes` for atomic cards and `no` for non-atomic cards.
