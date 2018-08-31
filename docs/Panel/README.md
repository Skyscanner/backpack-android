# Panel

## Installation

Backpack Android is available through [Jitpack](https://jitpack.io/#Skyscanner/backpack-android). Check the main [Readme](../../README.md#installation) for a complete installation guide.

## Usage

The Panel component can be used in both XML and Kotlin

Example of a padded panel in XML

```xml
<net.skyscanner.backpack.panel.bpkpanel
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    app:bpk_padding="true">

    <textview
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:text="panel message" />
</net.skyscanner.backpack.panel.bpkpanel>
```

Example of a padded panel in Kotlin

```Kotlin
import net.skyscanner.backpack.panel

BpkPanel(context).apply {
   padding = true
}
```
