# Flare

## Installation

Backpack Android is available through [Jitpack](https://jitpack.io/#Skyscanner/backpack-android). Check the main [Readme](https://github.com/skyscanner/backpack-android#installation) for a complete installation guide.

## Usage

The Flare component can be used in both XML and Kotlin/Java

Example of a Flare in XML

```xml
<net.skyscanner.backpack.contentbubble.BpkFlare
  android:layout_width="wrap_content"
  android:layout_height="200dp"
  android:layout_marginBottom="@dimen/bpkSpacingMd"
  app:flareFitContent="false"
  app:flarePointerPosition="middle"
  app:flareRound="false">

    <androidx.appcompat.widget.AppCompatImageView
      android:layout_width="wrap_content"
      android:layout_height="wrap_content"
      android:scaleType="centerCrop"
      app:srcCompat="@drawable/canadian_rockies_canada"/>
</net.skyscanner.backpack.contentbubble.BpkFlare>
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
  fitContent = false
  round = false
  addView(ImageView(contenxt).apply {
    setImageDrawable(ContextCompat.getDrawable(context, R.drawable.canadian_rockies_canada))
    layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    scaleType = ImageView.ScaleType.CENTER_CROP
  })
}
```
