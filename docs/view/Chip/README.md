# Chip

## Installation

Backpack Android is available through [Maven Central](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-android). Check the main [Readme](../../../README.md#installation) for a complete installation guide.

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
      app:chipIcon="@drawable/bpk_close"
      app:chipStyle="onDark"
      app:chipType="select" />
```

Example of a chip in Kotlin

```Kotlin
import net.skyscanner.backpack.chip.BpkChip

BpkChip(context).apply {
  text = "Message"
  isSelected = true
  disabled = false
  chipBackgroundColor = context.getColor(R.color.bpkSkyGrayTint07)
  chipTextColor = context.getColor(R.color.bpkTextPrimary)
  chipSelectedBackgroundColor = context.getColor(R.color.bpkSkyBlue)
  disabledBackgroundColor = context.getColor(R.color.bpkSkyGrayTint07)
  chipIcon = AppCompatResources.getDrawable(context, R.drawable.bpk_account)
  style = BpkChip.Style.OnDark
  type = BpkChip.Type.Select
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
- `chipDisabledBackgroundColor`
- `chipTextColor`


Styles can be changed globally through `bpkChipStyle`. Check [theming](https://github.com/Skyscanner/backpack-android/blob/main/docs/view/THEMING.md) for more information.
