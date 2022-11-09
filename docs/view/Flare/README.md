# Flare

[![Maven Central](https://img.shields.io/maven-central/v/net.skyscanner.backpack/backpack-android)](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-android)
[![Class reference](https://img.shields.io/badge/Class%20reference-Android-blue)](https://backpack.github.io/android/Backpack/net.skyscanner.backpack.flare)
[![Source code](https://img.shields.io/badge/Source%20code-GitHub-lightgrey)](https://github.com/Skyscanner/backpack-android/tree/main/Backpack/src/main/java/net/skyscanner/backpack/flare)

| Day | Night |
| --- | --- |
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/view/Flare/screenshots/default.png" alt="Flare component" width="375" /> |<img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/view/Flare/screenshots/default_dm.png" alt="Flare component - dark mode" width="375" /> |

## Inset padding

| Day | Night |
| --- | --- |
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/view/Flare/screenshots/inset-padding.png" alt="Inset padding Flare component" width="375" /> |<img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/view/Flare/screenshots/inset-padding_dm.png" alt="Inset padding Flare component - dark mode" width="375" /> |

## Pointer offset

| Day | Night |
| --- | --- |
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/view/Flare/screenshots/pointer-offset.png" alt="Pointer offset Flare component" width="375" /> |<img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/view/Flare/screenshots/pointer-offset_dm.png" alt="Pointer offset Flare component - dark mode" width="375" /> |

## Pointing up

| Day | Night |
| --- | --- |
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/view/Flare/screenshots/pointing-up.png" alt="Pointing up Flare component" width="375" /> |<img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/view/Flare/screenshots/pointing-up_dm.png" alt="Pointing up Flare component - dark mode" width="375" /> |

## Rounded

| Day | Night |
| --- | --- |
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/view/Flare/screenshots/rounded.png" alt="Rounded Flare component" width="375" /> |<img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/view/Flare/screenshots/rounded_dm.png" alt="Rounded Flare component - dark mode" width="375" /> |

## Installation

Backpack Android is available through [Maven Central](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-android). Check the main [Readme](https://github.com/skyscanner/backpack-android#installation) for a complete installation guide.

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

    <ImageView
      android:layout_width="wrap_content"
      android:layout_height="wrap_content"
      android:scaleType="centerCrop"
      app:srcCompat="@drawable/canadian_rockies_canada"/>
</net.skyscanner.backpack.flare.BpkFlare>
```

Example of a Flare in Kotlin

```Kotlin
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
    setImageDrawable(AppCompatResources.getDrawable(context, R.drawable.canadian_rockies_canada))
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
