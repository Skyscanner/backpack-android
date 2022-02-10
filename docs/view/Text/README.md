# Text

## Installation

Backpack Android is available through [Maven Central](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-android). Check the main [Readme](https://github.com/skyscanner/backpack-android#installation) for a complete installation guide.

## Usage

The Text component can be used in both XML and Kotlin

Example of a text view with heading 4 style in XML

```xml
<net.skyscanner.backpack.text.BpkText
  android:layout_width="wrap_content"
  android:layout_height="wrap_content"
  android:text="Flights to Edinburgh"
  app:textStyle="heading4" />
```

Example of a text view with large and emphasized font in Kotlin

```Kotlin
import net.skyscanner.backpack.text.BpkText

BpkText(context).apply {
   text = 'Flights to Edinburgh'
   textStyle = BpkText.TextStyle.Heading4
}
```


## Theme Props

- `bpkFontFamilyBase`
- `bpkFontFamilyEmphasized`
- `bpkFontFamilyHeavy`

Example

```xml
  <style name="BlueTheme" parent="AppTheme">
    <item name="bpkFontFamilyBase">@font/shadows_into_light</item>
    <item name="bpkFontFamilyEmphasized">@font/shadows_into_dark</item>
    <item name="bpkFontFamilyHeavy">@font/shadows_into_light_heavy</item>
  </style>
```


