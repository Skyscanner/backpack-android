# Button

## Installation

Backpack Android is available through [Jitpack](https://jitpack.io/#Skyscanner/backpack-android). Check the main [Readme](https://github.com/skyscanner/backpack-android#installation) for a complete installation guide.

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
  app:buttonType="primary"/>
```

Example of a primary button in Kotlin

```Kotlin
import net.skyscanner.backpack.button.BpkButton

BpkButton(context,BpkButton.Type.Primary).apply {
   icon = ContextCompat.getDrawable(context, R.drawable.bpk_weather)
   iconPosition = BpkButton.END
   text = "Button"
}
```

### Icon

The icon prop only supports `VectorDrawables` or `BitmapDrawables`. If you provide a different `drawable` make sure
its size is `16dp`.

## Theme Props

- `buttonBackground`
- `buttonTextColor`
- `buttonStrokeColor`
- `buttonCornerRadius`
- `buttonAddElevation`

BpkButton accepts the above themeable attributes for the following styles

- `bpkButtonPrimary`
- `bpkButtonSecondary`
- `bpkButtonDestructive`
- `bpkButtonOutline`
- `bpkButtonFeatured`

Example

```xml
  <style name="BlueTheme" parent="AppTheme">
    <item name="bpkButtonPrimary">@style/BlueButton</item>
  </style>

  <style name="BlueButton">
    <item name="buttonBackground">@color/bpkBlue900</item>
    <item name="buttonTextColor">@color/bpkGray200</item>
    <item name="buttonStrokeColor">@color/bpkYellow500</item>
  </style>

```

Check [theming](https://github.com/Skyscanner/backpack-android/blob/master/docs/THEMING.md) for more information.


