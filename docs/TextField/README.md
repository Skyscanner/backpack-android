# Text Field

## Installation

Backpack Android is available through [Jitpack](https://jitpack.io/#Skyscanner/backpack-android). Check the main [Readme](https://github.com/skyscanner/backpack-android#installation) for a complete installation guide.

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
import androidx.core.content.ContextCompat

BpkTextField(context).apply {
   setText('Flights to Edinburgh')
   iconStart = ContextCompat.getDrawable(context, R.drawable.bpk_search)
}
```


## Theme Props

- `textFieldColor`
- `textFieldColorHintNormal`
- `textFieldColorHintFocused`
- `textFieldColorIcon`
- `textFieldBackground`
- `fontFamilyBase`
- `fontFamilyEmphasized`
- `fontFamilyHeavy`


Styles can be changed globally through `bpkTextFieldStyle`. Check [theming](https://github.com/Skyscanner/backpack-android/blob/master/docs/THEMING.md) for more information.

Example

```xml
  <style name="BlueTheme" parent="AppTheme">
      <item name="bpkTextFieldStyle">@style/CursiveText</item>
  </style>

  <style name="CursiveText" >
    <item name="fontFamilyBase">@font/shadows_into_light</item>
    <item name="fontFamilyEmphasized">@font/shadows_into_dark</item>
    <item name="fontFamilyHeavy">@font/shadows_into_light_heavy</item>
  </style>

```


