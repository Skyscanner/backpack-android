# Text Field

## Installation

Backpack Android is available through [Maven Central](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-android). Check the main [Readme](https://github.com/skyscanner/backpack-android#installation) for a complete installation guide.

## Usage

The Text Field component can be used in both XML and Kotlin

Example of a text field with icon on start in XML:

```xml
<net.skyscanner.backpack.text.BpkTextField
  android:layout_width="match_parent"
  android:layout_height="wrap_content"
  app:textFieldIconStart="@drawable/bpk_search" />
```

Example of a text field with icon on start in Kotlin:

```Kotlin
import net.skyscanner.backpack.text.BpkTextField

BpkTextField(context).apply {
   setText('Flights to Edinburgh')
   iconStart = AppCompatResources.getDrawable(context, R.drawable.bpk_search)
}
```

To add a label and/or a helper or error message you can use the `BpkTextInputLayout`:

Example of a text layout with label, error and helper text:

```xml
<net.skyscanner.backpack.text.BpkTextInputLayout
  android:layout_width="match_parent"
  android:layout_height="wrap_content"
  android:label="Label"
  app:textInputHelperText="Helper text"
  app:textInputError="Error message">

  <net.skyscanner.backpack.text.BpkTextField
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:hint="Placeholder"
    android:text="Content"
    app:textFieldIconStart="@drawable/bpk_search" />
</net.skyscanner.backpack.text.BpkTextInputLayout>
```

Example of a text layout with label, error and helper text:

```Kotlin
import net.skyscanner.backpack.text.BpkTextField
import net.skyscanner.backpack.text.BpkTextInputLayout

BpkTextInputLayout(testContext).apply {
  label = "Label"
  error = "Error"
  helperText = "Helper text"

  val textField = BpkTextField(testContext).apply {
    hint = "Hint"
    setText("Text")
  }

  addView(textField)
}
```

**Note:** If both error message and helper text are supplied the error message takes precedence.

By default the error view has a visibility of `GONE` until an error is set. To avoid the layout jumping set the `textFieldErrorEnabled` (XML) or `errorEnabled` (Java/Kotlin) flag to true, which will set visibility to `INVISIBLE`.

## Theme Props

### `BpkTextField`
- `textFieldColor`
- `textFieldColorHintNormal`
- `textFieldColorHintFocused`
- `textFieldColorIcon`
- `textFieldBackground`
- [Font theming](https://github.com/Skyscanner/backpack-android/blob/main/docs/view/Text/README.md)

### `BpkTextInputLayout`
- `textInputErrorTextColor`
- `textInputHelperTextColor`
- `textInputErrorIcon`

Styles can be changed globally through `bpkTextFieldStyle` and `bpkTextInputLayoutStyle`. Check [theming](https://github.com/Skyscanner/backpack-android/blob/main/docs/view/THEMING.md) for more information.

Example

```xml
  <style name="BlueTheme" parent="AppTheme">
    <item name="bpkTextFieldStyle">@style/TextFieldStyle</item>
    <item name="bpkTextInputLayoutStyle">@style/TextInputLayoutStyle</item>
  </style>

  <style name="TextFieldStyle" >
    <item name="textFieldColor">@color/bpkSkyBlueShade02</item>
    <item name="textFieldColorHintNormal">@color/bpkSkyBlueTint01</item>
    <item name="textFieldColorHintFocused">@color/bpkSkyBlue</item>
  </style>

  <style name="TextInputLayoutStyle">
    <item name="textInputErrorTextColor">@color/bpkSkyBlueShade02</item>
    <item name="textInputHelperTextColor">@color/bpkSkyBlueTint01</item>
    <item name="textInputErrorIcon">@drawable/bpk_information</item>
  </style>
```
