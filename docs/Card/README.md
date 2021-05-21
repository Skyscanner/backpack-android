# Card

## Installation

Backpack Android is available through [Jitpack](https://jitpack.io/#Skyscanner/backpack-android). Check the main [Readme](https://github.com/skyscanner/backpack-android#installation) for a complete installation guide.

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
    app:focused="true"
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
   focused = true
   cornerStyle = BpkCardView.CornerStyle.SMALL
}
```

## Accessibility

Consider if your card should be atomic or non-atomic. If the copy inside your card is lengthy, consider making it non-atomic and providing a CTA inside. The entire card will still be clickable/tappable, but will be ignored by TalkBack.

A clickable & focusable card is atomic by default, whereas a non-clickable/focusable card is non-atomic. To change that set the `importantForAccessibility` property to `yes` for atomic cards and `no` for non-atomic cards.
