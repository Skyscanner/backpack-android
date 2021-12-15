# Button Link

## Installation

Backpack Android is available through [Maven Central](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-android). Check the main [Readme](https://github.com/skyscanner/backpack-android#installation) for a complete installation guide.

## Usage

The `ButtonLink` component can be used in both XML and Kotlin/Java

Example of a primary button in XML

```xml
<net.skyscanner.backpack.button.BpkButtonLink
  android:layout_width="wrap_content"
  android:layout_height="wrap_content"
  android:text="Button"
  app:buttonIconPosition="end"
  app:buttonIcon="@drawable/bpk_long_arrow_right"
  app:uppercase="true"/>
```

Example of a primary button in Kotlin

```Kotlin
import net.skyscanner.backpack.button.BpkButton

BpkButtonLink(context).apply {
   icon = AppCompatResources.getDrawable(context, R.drawable.bpk_weather)
   iconPosition = BpkButton.END
   text = "Button"
}
```

### Icon

The icon prop only supports `VectorDrawables` or `BitmapDrawables`. If you provide a different `drawable` make sure
its size is `16dp`.

## Theme Props

- `buttonTextColor`

Styles can be changed globally through `bpkButtonLinkStyle`. Check [theming](https://github.com/Skyscanner/backpack-android/blob/main/docs/view/THEMING.md) for more information.
