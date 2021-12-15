# Text

## Installation

Backpack Android is available through [Maven Central](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-android). Check the main [Readme](https://github.com/skyscanner/backpack-android#installation) for a complete installation guide.

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

- `bpkFontFamilyBase`
- `bpkFontFamilyEmphasized`
- `bpkFontFamilyHeavy`

- `bpkTextBaseAppearance`
- `bpkTextCapsAppearance`
- `bpkTextLgAppearance`
- `bpkTextSmAppearance`
- `bpkTextXlAppearance`
- `bpkTextXsAppearance`
- `bpkTextXxlAppearance`
- `bpkTextXxxlAppearance`
- `bpkTextBaseEmphasizedAppearance`
- `bpkTextCapsEmphasizedAppearance`
- `bpkTextLgEmphasizedAppearance`
- `bpkTextSmEmphasizedAppearance`
- `bpkTextXlEmphasizedAppearance`
- `bpkTextXsEmphasizedAppearance`
- `bpkTextXxlEmphasizedAppearance`
- `bpkTextXxxlEmphasizedAppearance`
- `bpkTextXlHeavyAppearance`
- `bpkTextXxlHeavyAppearance`
- `bpkTextXxxlHeavyAppearance`

Example

```xml
  <style name="BlueTheme" parent="AppTheme">
    <item name="bpkFontFamilyBase">@font/shadows_into_light</item>
    <item name="bpkFontFamilyEmphasized">@font/shadows_into_dark</item>
    <item name="bpkFontFamilyHeavy">@font/shadows_into_light_heavy</item>

    <item name="bpkTextBaseAppearance">@style/bpkTextBase</item>
    <item name="bpkTextCapsAppearance">@style/bpkTextCaps</item>
    <item name="bpkTextLgAppearance">@style/bpkTextLg</item>
    <item name="bpkTextSmAppearance">@style/bpkTextSm</item>
    <item name="bpkTextXlAppearance">@style/bpkTextXl</item>
    <item name="bpkTextXsAppearance">@style/bpkTextXs</item>
    <item name="bpkTextXxlAppearance">@style/bpkTextXxl</item>
    <item name="bpkTextXxxlAppearance">@style/bpkTextXxxl</item>
    <item name="bpkTextBaseEmphasizedAppearance">@style/bpkTextBaseEmphasized</item>
    <item name="bpkTextCapsEmphasizedAppearance">@style/bpkTextCapsEmphasized</item>
    <item name="bpkTextLgEmphasizedAppearance">@style/bpkTextLgEmphasized</item>
    <item name="bpkTextSmEmphasizedAppearance">@style/bpkTextSmEmphasized</item>
    <item name="bpkTextXlEmphasizedAppearance">@style/bpkTextXlEmphasized</item>
    <item name="bpkTextXsEmphasizedAppearance">@style/bpkTextXsEmphasized</item>
    <item name="bpkTextXxlEmphasizedAppearance">@style/bpkTextXxlEmphasized</item>
    <item name="bpkTextXxxlEmphasizedAppearance">@style/bpkTextXxxlEmphasized</item>
    <item name="bpkTextXlHeavyAppearance">@style/bpkTextXlHeavy</item>
    <item name="bpkTextXxlHeavyAppearance">@style/bpkTextXxlHeavy</item>
    <item name="bpkTextXxxlHeavyAppearance">@style/bpkTextXxxlHeavy</item>
  </style>
```


