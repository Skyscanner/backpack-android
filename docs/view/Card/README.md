# Card

[![Maven Central](https://img.shields.io/maven-central/v/net.skyscanner.backpack/backpack-android)](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-android)
[![Class reference](https://img.shields.io/badge/Class%20reference-Android-blue)](https://backpack.github.io/android/Backpack/net.skyscanner.backpack.card)
[![Source code](https://img.shields.io/badge/Source%20code-GitHub-lightgrey)](https://github.com/Skyscanner/backpack-android/tree/main/Backpack/src/main/java/net/skyscanner/backpack/card)

## Default

| Day | Night |
| --- | --- |
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/view/Card/screenshots/default.png" alt="Card component" width="375" /> |<img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/view/Card/screenshots/default_dm.png" alt="Card component - dark mode" width="375" /> |

## Corner style large

| Day | Night |
| --- | --- |
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/view/Card/screenshots/corner-style-large.png" alt="Corner style large Card component" width="375" /> |<img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/view/Card/screenshots/corner-style-large_dm.png" alt="Corner style large Card component - dark mode" width="375" /> |

## Selected

| Day | Night |
| --- | --- |
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/view/Card/screenshots/selected.png" alt="Selected Card component" width="375" /> |<img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/view/Card/screenshots/selected_dm.png" alt="Selected Card component - dark mode" width="375" /> |

## With divider and corner style large

| Day | Night |
| --- | --- |
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/view/Card/screenshots/with-divider-and-corner-style-large.png" alt="With divider and corner style large Card component" width="375" /> |<img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/view/Card/screenshots/with-divider-and-corner-style-large_dm.png" alt="With divider and corner style large Card component - dark mode" width="375" /> |

## With divider arranged vertically

| Day | Night |
| --- | --- |
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/view/Card/screenshots/with-divider-arranged-vertically.png" alt="With divider arranged verticy Card component" width="375" /> |<img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/view/Card/screenshots/with-divider-arranged-vertically_dm.png" alt="With divider arranged verticy Card component - dark mode" width="375" /> |

## With divider without padding

| Day | Night |
| --- | --- |
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/view/Card/screenshots/with-divider-without-padding.png" alt="With divider without padding Card component" width="375" /> |<img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/view/Card/screenshots/with-divider-without-padding_dm.png" alt="With divider without padding Card component - dark mode" width="375" /> |

## With divider

| Day | Night |
| --- | --- |
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/view/Card/screenshots/with-divider.png" alt="With divider Card component" width="375" /> |<img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/view/Card/screenshots/with-divider_dm.png" alt="With divider Card component - dark mode" width="375" /> |

## Without padding

| Day | Night |
| --- | --- |
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/view/Card/screenshots/without-padding.png" alt="Without padding Card component" width="375" /> |<img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/view/Card/screenshots/without-padding_dm.png" alt="Without padding Card component - dark mode" width="375" /> |

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
