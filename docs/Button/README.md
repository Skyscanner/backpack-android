# Button

## Installation

Backpack Android is available through [Jitpack](https://jitpack.io/#Skyscanner/backpack-android). Check the main [Readme](../../README.md#installation) for a complete installation guide.

## Usage

The Button component can be used in both XML and Kotlin/Java

Example of a primary button in XML

```xml
<net.skyscanner.backpack.button.BpkButton
  android:layout_width="wrap_content"
  android:layout_height="wrap_content"
  android:text="With icon"
  app:buttonIconStart="@drawable/bpk_weather"
  app:buttonType="primary"/>
```

Example of a primary button in Kotlin

```Kotlin
import net.skyscanner.backpack.button.BpkButton

BpkButton(context).apply {
   iconEnd = ContextCompat.getDrawable(context, R.drawable.bpk_weather)
   iconStart = ContextCompat.getDrawable(context, R.drawable.bpk_weather)
   type = BpkButton.Type.Primary
   text = "Message"
}
```
