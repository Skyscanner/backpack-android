# Overlay

[![Maven Central](https://img.shields.io/maven-central/v/net.skyscanner.backpack/backpack-android)](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-android)
[![Class reference](https://img.shields.io/badge/Class%20reference-Android-blue)](https://backpack.github.io/android/Backpack/net.skyscanner.backpack.overlay)
[![Source code](https://img.shields.io/badge/Source%20code-GitHub-lightgrey)](https://github.com/Skyscanner/backpack-android/tree/main/Backpack/src/main/java/net/skyscanner/backpack/overlay)

## All

| Day | Night |
| --- | --- |
| ![Overlay component](https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/view/Overlay/screenshots/all.png) |![Overlay component - dark mode](https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/view/Overlay/screenshots/all_dm.png) |

## Installation

Backpack Android is available through [Maven Central](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-android). Check the main [Readme](https://github.com/skyscanner/backpack-android#installation) for a complete installation guide.

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
