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

BpkButton(context).apply {
   icon = ContextCompat.getDrawable(context, R.drawable.bpk_weather)
   iconPosition = BpkButton.END
   type = BpkButton.Type.Primary
   text = "Button"
}
```
