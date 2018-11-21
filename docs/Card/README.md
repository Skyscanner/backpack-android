# Card

## Installation

Backpack Android is available through [Jitpack](https://jitpack.io/#Skyscanner/backpack-android). Check the main [Readme](https://github.com/skyscanner/backpack-android#installation) for a complete installation guide.

## Usage

BpkCardView is an extension of `CardView` in `com.android.support:cardview-v7`

The Card component can be used in both XML and Kotlin

Example of a padded and focused card in XML

```xml
 <net.skyscanner.backpack.card.BpkCardView
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:clickable="true"
    android:focusable="true"
    android:foreground="?android:attr/selectableItemBackground"
    app:focused="true">

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
}
```
