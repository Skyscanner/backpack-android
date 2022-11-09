# RadioButton

[![Maven Central](https://img.shields.io/maven-central/v/net.skyscanner.backpack/backpack-android)](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-android)
[![Class reference](https://img.shields.io/badge/Class%20reference-Android-blue)](https://backpack.github.io/android/Backpack/net.skyscanner.backpack.radiobutton)
[![Source code](https://img.shields.io/badge/Source%20code-GitHub-lightgrey)](https://github.com/Skyscanner/backpack-android/tree/main/Backpack/src/main/java/net/skyscanner/backpack/radiobutton)

## Default

| Day | Night |
| --- | --- |
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/view/RadioButton/screenshots/default.png" alt="RadioButton component" width="375" /> |<img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/view/RadioButton/screenshots/default_dm.png" alt="RadioButton component - dark mode" width="375" /> |

## Installation

Backpack Android is available through [Maven Central](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-android). Check the main [Readme](https://github.com/skyscanner/backpack-android#installation) for a complete installation guide.

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

val radioButton = BpkRadioButton(context).apply {
  isChecked = true
}
radioGroup.addView(radioButton)
```

## Theme Props

- `radioButtonColorChecked`
- `radioButtonColorDisabled`
- `radioButtonColor`

Styles can be changed globally through `bpkRadioButtonStyle`. Check [theming](https://github.com/Skyscanner/backpack-android/blob/main/docs/view/THEMING.md) for more information.
