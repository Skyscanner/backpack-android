# Flare

## Installation

Backpack Android is available through [Jitpack](https://jitpack.io/#Skyscanner/backpack-android). Check the main [Readme](https://github.com/skyscanner/backpack-android#installation) for a complete installation guide.

## Usage

The Flare component can be used in both XML and Kotlin/Java

Example of a Flare in XML

```xml
<net.skyscanner.backpack.flare.BpkFlare
  android:layout_width="wrap_content"
  android:layout_height="200dp"
  android:layout_marginBottom="@dimen/bpkSpacingMd"
  app:flarePointerPosition="middle"
  app:flarePointerDirection="down"
  app:flareRound="false"
  app:flareInsetPaddingMode="none">

    <androidx.appcompat.widget.AppCompatImageView
      android:layout_width="wrap_content"
      android:layout_height="wrap_content"
      android:scaleType="centerCrop"
      app:srcCompat="@drawable/canadian_rockies_canada"/>
</net.skyscanner.backpack.flare.BpkFlare>
```

Example of a Flare in Kotlin

```Kotlin
import androidx.core.content.ContextCompat
import android.widget.ImageView
import android.view.ViewGroup
import net.skyscanner.backpack.flare.BpkFlare

BpkFlare(context).apply {
  layoutParams = ViewGroup.LayoutParams(300, 100)
  pointerPosition = BpkFlare.PointerPosition.MIDDLE
  flarePointerDirection = BpkFlare.PointerDirection.DOWN
  insetPaddingMode = BpkFlare.InsetPaddingMode.NONE
  round = false
  addView(ImageView(contenxt).apply {
    setImageDrawable(ContextCompat.getDrawable(context, R.drawable.canadian_rockies_canada))
    layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    scaleType = ImageView.ScaleType.CENTER_CROP
  })
}
```

## Attributes

- `flarePointerPosition`: `middle|start|end`
  - Determines where the flare is rendered horizontally.

- `flarePointerDirection`: `down|up`
  - Determines where the pointer is rendered vertically and the direction it will be pointing to.

- `flareInsetPaddingMode`: `none|bottom|top`
  - Determines where to add extra padding to account for the pointer size.
    To render the flare view we cut out part of the canvas, if you intend to add, for example,
    a text view to the top of the flare view with a flare pointing up, you should set this prop
    to `top` or part of the text will be cut off.

- `flareRound`: `Boolean`
  - When true will render the flare with rounded corners.
