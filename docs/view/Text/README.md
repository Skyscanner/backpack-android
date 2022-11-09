# Text

[![Maven Central](https://img.shields.io/maven-central/v/net.skyscanner.backpack/backpack-android)](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-android)
[![Class reference](https://img.shields.io/badge/Class%20reference-Android-blue)](https://backpack.github.io/android/Backpack/net.skyscanner.backpack.text)
[![Source code](https://img.shields.io/badge/Source%20code-GitHub-lightgrey)](https://github.com/Skyscanner/backpack-android/tree/main/Backpack/src/main/java/net/skyscanner/backpack/text)

## Body

| Day | Night |
| --- | --- |
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/view/Text/screenshots/body.png" alt="Body Text component" width="375" /> |<img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/view/Text/screenshots/body_dm.png" alt="Body Text component - dark mode" width="375" /> |

## Heading

| Day | Night |
| --- | --- |
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/view/Text/screenshots/heading.png" alt="Heading Text component" width="375" /> |<img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/view/Text/screenshots/heading_dm.png" alt="Heading Text component - dark mode" width="375" /> |

## Hero

| Day | Night |
| --- | --- |
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/view/Text/screenshots/hero.png" alt="Hero Text component" width="375" /> |<img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/view/Text/screenshots/hero_dm.png" alt="Hero Text component - dark mode" width="375" /> |

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


