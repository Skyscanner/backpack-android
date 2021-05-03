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

BpkTextField(context).apply {
   setText('Flights to Edinburgh')
   iconStart = AppCompatResources.getDrawable(context, R.drawable.bpk_search)
}
```


## Theme Props

- `textFieldColor`
- `textFieldColorHintNormal`
- `textFieldColorHintFocused`
- `textFieldColorIcon`
- `textFieldBackground`
- [Font theming](https://github.com/Skyscanner/backpack-android/blob/main/docs/Text/README.md)

Styles can be changed globally through `bpkTextFieldStyle`. Check [theming](https://github.com/Skyscanner/backpack-android/blob/main/docs/THEMING.md) for more information.

Example

```xml
  <style name="BlueTheme" parent="AppTheme">
      <item name="bpkTextFieldStyle">@style/CursiveText</item>
  </style>

  <style name="CursiveText" >
    <item name="textFieldColor">@color/bpkSkyBlueShade02</item>
    <item name="textFieldColorHintNormal">@color/bpkSkyBlueTint01</item>
    <item name="textFieldColorHintFocused">@color/bpkSkyBlue</item>
  </style>
```
