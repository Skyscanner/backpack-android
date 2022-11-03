# Button

[![Maven Central](https://img.shields.io/maven-central/v/net.skyscanner.backpack/backpack-android)](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-android)
[![Class reference](https://img.shields.io/badge/Class%20reference-Android-blue)](https://backpack.github.io/android/Backpack/net.skyscanner.backpack.button)
[![Source code](https://img.shields.io/badge/Source%20code-GitHub-lightgrey)](https://github.com/Skyscanner/backpack-android/tree/main/Backpack/src/main/java/net/skyscanner/backpack/button)

## Standard

| Day | Night |
| --- | --- |
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/view/Button/screenshots/standard.png" alt="Standard Button component" width="375" /> |<img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/view/Button/screenshots/standard_dm.png" alt="Standard Button component - dark mode" width="375" /> |

## Large

| Day | Night |
| --- | --- |
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/view/Button/screenshots/large.png" alt="Large Button component" width="375" /> |<img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/view/Button/screenshots/large_dm.png" alt="Large Button component - dark mode" width="375" /> |

## Link

| Day | Night |
| --- | --- |
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/view/Button/screenshots/link.png" alt="Link Button component" width="375" /> |<img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/view/Button/screenshots/link_dm.png" alt="Link Button component - dark mode" width="375" /> |

## Installation

Backpack Android is available through [Maven Central](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-android). Check the main [Readme](https://github.com/skyscanner/backpack-android#installation) for a complete installation guide.

## Usage

The Button component can be used in both XML and Kotlin/Java

Example of a primary button in XML

```xml
<net.skyscanner.backpack.button.BpkButton
  android:layout_width="wrap_content"
  android:layout_height="wrap_content"
  android:text="Button"
  app:buttonIconPosition="end"
  app:buttonIcon="@drawable/bpk_long_arrow_right"
  app:buttonType="primary"
  app:buttonLoading="false" />
```

Example of a primary button in Kotlin

```Kotlin
import net.skyscanner.backpack.button.BpkButton

BpkButton(context,BpkButton.Type.Primary).apply {
   icon = AppCompatResources.getDrawable(context, R.drawable.bpk_weather)
   iconPosition = BpkButton.END
   text = "Button"
   loading = false
}
```

### Large

Example of a large secondary button in XML

```xml
<net.skyscanner.backpack.button.BpkButton
  android:layout_width="wrap_content"
  android:layout_height="wrap_content"
  android:text="Button"
  app:buttonIconPosition="end"
  app:buttonIcon="@drawable/bpk_long_arrow_right"
  app:buttonType="primary"
  app:bpkButtonSize="large"
  app:buttonLoading="false" />
```

Example of a large secondary button in Kotlin

```Kotlin
import net.skyscanner.backpack.button.BpkButton

BpkButton(context, BpkButton.Type.Secondary, size = BpkButton.Size.Large).apply {
   icon = AppCompatResources.getDrawable(context, R.drawable.bpk_weather)
   iconPosition = BpkButton.END
   text = "Button"
   loading = false
}
```

### Icon

The icon prop only supports `VectorDrawables` or `BitmapDrawables`. If you provide a different `drawable` make sure
its size is `16dp`.

## Theme Props

- `buttonBackground`
- `buttonTextColor`

BpkButton accepts the above themeable attributes for the following styles

- `bpkButtonPrimary`
- `bpkButtonSecondary`
- `bpkButtonDestructive`
- `bpkButtonPrimaryOnDark`
- `bpkButtonPrimaryOnLight`
- `bpkButtonFeatured`

Example

```xml
  <style name="BlueTheme" parent="AppTheme">
    <item name="bpkButtonPrimary">@style/BlueButton</item>
  </style>

  <style name="BlueButton">
    <item name="buttonBackground">@color/bpkSkyBlueShade03</item>
    <item name="buttonTextColor">@color/bpkSkyGrayTint05</item>
  </style>

```

Check [theming](https://github.com/Skyscanner/backpack-android/blob/main/docs/view/THEMING.md) for more information.
