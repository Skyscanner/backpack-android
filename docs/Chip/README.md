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
  text = "Message"
  isSelected = true
  disabled = false
  chipBackgroundColor = ContextCompat.getColor(context, R.color.bpkGray50)
  chipSelectedBackgroundColor = ContextCompat.getColor(context, R.color.bpkBlue500)
}
```

## Toggling the chip's state

By default the chip does not add any click listeners, so clicking it will not toggle its state.
To do so add a click listener:

```Kotlin
chip.setOnClickListener { chip.toggle() }
```

## Theme Props

- `chipSelectedBackgroundColor`
- `chipBackgroundColor`

Styles can be changed globally through `bpkChipStyle`. Check [theming](https://github.com/Skyscanner/backpack-android/blob/master/docs/THEMING.md) for more information.
