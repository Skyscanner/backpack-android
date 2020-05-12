# Chip

## Installation

Backpack Android is available through [Jitpack](https://jitpack.io/#Skyscanner/backpack-android). Check the main [Readme](../../README.md#installation) for a complete installation guide.

## Usage

The Chip component can be used in both XML and Kotlin/Java

Example of a chip in XML

```xml
    <net.skyscanner.backpack.chip.BpkOutlineChip
      android:layout_width="wrap_content"
      android:layout_height="wrap_content"
      android:layout_margin="@dimen/bpkSpacingSm"
      android:text="Message"/>
```

Example of a chip in Kotlin

```Kotlin
import net.skyscanner.backpack.chip.BpkOutlineChip

BpkOutlineChip(context)
```

## Toggling the chip's state

By default the chip does not add any click listeners, so clicking it will not toggle its state.
To do so add a click listener:

```Kotlin
chip.setOnClickListener {  }
```

Styles can be changed globally through `bpkChipStyle`. Check [theming](https://github.com/Skyscanner/backpack-android/blob/master/docs/THEMING.md) for more information.
