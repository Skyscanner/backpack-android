# Text

## Installation

Backpack Android is available through [Jitpack](https://jitpack.io/#Skyscanner/backpack-android). Check the main [Readme](https://github.com/skyscanner/backpack-android#installation) for a complete installation guide.

## Usage

The Text component can be used in both XML and Kotlin

Example of a text view with large and emphasized font in XML

```xml
<net.skyscanner.backpack.text.BpkText
  android:layout_width="wrap_content"
  android:layout_height="wrap_content"
  android:text="Flights to Edinburgh"
  app:weight="emphasized"
  app:textStyle="xxl" />
```

Example of a text view with large and emphasized font in Kotlin

```Kotlin
import net.skyscanner.backpack.text.BpkText

BpkText(context).apply {
   text = 'Flights to Edinburgh'
   textStyle = 'xxl'
   weight = BpkText.Weight.EMPHASIZED
}
```


## Theme Props

- `fontFamilyBase`
- `fontFamilyEmphasized`
- `fontFamilyHeavy`

BpkText accepts the above themeable attributes for the following styles

- `bpkTextFont`

Example

```xml
  <style name="BlueTheme" parent="AppTheme">
      <item name="bpkTextFont">@style/CursiveText</item>
  </style>

  <style name="CursiveText" >
    <item name="fontFamilyBase">@font/shadows_into_light</item>
    <item name="fontFamilyEmphasized">@font/shadows_into_dark</item>
    <item name="fontFamilyHeavy">@font/shadows_into_light_heavy</item>
  </style>

```

Check [theming](https://github.com/Skyscanner/backpack-android/blob/master/docs/THEMING.md) for more information.

