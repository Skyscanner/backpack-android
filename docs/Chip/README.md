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
      app:selected="true"
      app:chipIcon="@drawable/bpk_close" />
```

Example of a chip in Kotlin

```Kotlin
import net.skyscanner.backpack.chip.BpkChip

BpkChip(context).apply {
  text = "Message"
  isSelected = true
  disabled = false
  chipBackgroundColor = ContextCompat.getColor(context, R.color.bpkSkyGrayTint07)
  chipSelectedBackgroundColor = ContextCompat.getColor(context, R.color.bpkSkyBlue)
  chipIcon = ContextCompat.getDrawable(testContext, R.drawable.bpk_account)
}
```

## Toggling the chip's state

By default the chip does not add any click listeners, so clicking it will not toggle its state.
To do so add a click listener:

```Kotlin
chip.setOnClickListener { chip.toggle() }
```

## BpkOutlineChip

Chip with outline style, supports the same properties as a normal chip except changing the background colour.

Example of a outline chip in XML

```xml
    <net.skyscanner.backpack.chip.BpkOutlineChip
      android:layout_width="wrap_content"
      android:layout_height="wrap_content"
      android:layout_margin="@dimen/bpkSpacingSm"
      android:text="Message"
      app:disabled="true"
      app:selected="true" />
```

Example of a outline chip in Kotlin

```Kotlin
import net.skyscanner.backpack.chip.BpkOutlineChip

BpkOutlineChip(context).apply {
  text = "Message"
  isSelected = true
  disabled = false
  chipSelectedBackgroundColor = ContextCompat.getColor(context, R.color.bpkSkyBlue)
  chipIcon = ContextCompat.getDrawable(testContext, R.drawable.bpk_account)
}
```

## Theme Props

- `chipSelectedBackgroundColor`
- `chipBackgroundColor`


Styles can be changed globally through `bpkChipStyle` or `bpkOutlineChipStyle`. Check [theming](https://github.com/Skyscanner/backpack-android/blob/master/docs/THEMING.md) for more information.
