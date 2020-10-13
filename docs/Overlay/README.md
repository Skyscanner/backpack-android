# Overlay

## Installation

Backpack Android is available through [Jitpack](https://jitpack.io/#Skyscanner/backpack-android). Check the main [Readme](https://github.com/skyscanner/backpack-android#installation) for a complete installation guide.

## Usage

The Overlay component can be used in both XML and Kotlin/Java

Example of a Overlay in XML

```xml
  <net.skyscanner.backpack.overlay.BpkOverlay
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    app:overlayCornerType="rounded"
    app:overlayType="tint">

    <ImageView
      android:layout_width="match_parent"
      android:layout_height="wrap_content"
      android:adjustViewBounds="true"
      android:scaleType="centerInside"
      android:src="@drawable/canadian_rockies_canada" />

    <net.skyscanner.backpack.text.BpkText
      android:layout_width="wrap_content"
      android:layout_height="wrap_content"
      android:layout_gravity="center"
      android:text="Rounded corners"
      android:textColor="@color/bpkWhite" />

  </net.skyscanner.backpack.overlay.BpkOverlay>
```

Example of a Overlay in Kotlin

```Kotlin
import androidx.core.content.ContextCompat
import net.skyscanner.backpack.overlay.BpkOverlay

BpkOverlay(context).apply {
  cornerType = BpkOverlay.CornerType.Rounded
  overlayType = BpkOverlay.OverlayType.Tint
  addView(backgroundView)
  addView(foregroundView)
}
```

Overlay view uses its first child as a background layer and draws
overlay on top if it. All the other children are considered to be
foreground layers and drawn on the top of the overlay. Overlay view
extends `FrameLayout` and inherits its behaviour.
