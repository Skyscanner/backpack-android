# RadioButton

## Installation

Backpack Android is available through [Jitpack](https://jitpack.io/#Skyscanner/backpack-android). Check the main [Readme](https://github.com/skyscanner/backpack-android#installation) for a complete installation guide.

## Usage

The RadioButton component can be used in both XML and Kotlin/Java

Example of a RadioButton in XML

```xml
<RadioGroup
android:layout_width="wrap_content"
android:layout_height="wrap_content"
android:checkedButton="@+id/radio_button_checked">

<net.skyscanner.backpack.radiobutton.BpkRadioButton
  android:layout_width="wrap_content"
  android:layout_height="wrap_content"
  android:text="Unchecked" />

<net.skyscanner.backpack.radiobutton.BpkRadioButton
  android:id="@+id/radio_button_checked"
  android:layout_width="wrap_content"
  android:layout_height="wrap_content"
  android:text="Checked" />

</RadioGroup>

```

Example of a RadioButton in Kotlin

```Kotlin
import net.skyscanner.backpack.radiobutton.BpkRadioButton

BpkRadioButton(context).apply {
  isChecked = true
}
```

## Theme Props

- `radioButtonColorChecked`
- `radioButtonColorDisabled`
- `radioButtonColor`

Styles can be changed globally through `bpkRadioButtonStyle`. Check [theming](https://github.com/Skyscanner/backpack-android/blob/main/docs/THEMING.md) for more information.
