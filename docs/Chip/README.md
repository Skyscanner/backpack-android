# Chip

## Installation

Backpack Android is available through [Jitpack](https://jitpack.io/#Skyscanner/backpack-android). Check the main [Readme](../../README.md#installation) for a complete installation guide.

## Usage

The Chip component can be used in both XML and Kotlin/Java

Example of a chip in XML

```xml
    <net.skyscanner.backpack.chip.BpkChip
      android:layout_width="wrap_content"
      android:layout_height="wrap_content"
      android:layout_margin="@dimen/bpkSpacingSm"
      android:text="Message"
      app:disabled="true"
      app:selected="true" />
```

Example of a chip in Kotlin

```Kotlin
import net.skyscanner.backpack.chip.BpkChip

BpkChip(context).apply {
  isSelected = true
  disabled = false
}
```


