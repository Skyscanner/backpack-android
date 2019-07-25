# Rating

## Installation

Backpack Android is available through [Jitpack](https://jitpack.io/#Skyscanner/backpack-android). Check the main [Readme](https://github.com/skyscanner/backpack-android#installation) for a complete installation guide.

## Usage

The Rating component can be used in both XML and Kotlin/Java

Example of a Rating in XML:

```xml
<net.skyscanner.backpack.rating.BpkRating
  android:layout_width="wrap_content"
  android:layout_height="wrap_content"
  app:ratingIcon="@drawable/rating_sample_icon"
  app:ratingTitle="@array/rating_sample_titles"
  app:ratingSubtitle="@array/rating_sample_subtitles"
  app:ratingOrientation="vertical"
  app:ratingSize="large"
  app:ratingValue="3.0"/>
```

In this example:

- `ratingIcon` – a reference to [`LayerDrawable`](https://developer.android.com/reference/android/graphics/drawable/LayerDrawable) or [`Drawable`](https://developer.android.com/reference/android/graphics/drawable/Drawable).
The layer index will be chosen based on the corresponding score value (0 - low, 1 - medium, 2 - high).
If drawable is not layered, the same drawable will be used for all values.
When the provided drawable is not a layered drawable, the same icon will be used for all scores.
*Please note that on some sizes icons disabled*.
*Note that when size is equal to `Icon` only one icon is supported and thus layered drawables are ignored*  

- `ratingTitle` – a reference to [`StringArray`](https://developer.android.com/guide/topics/resources/string-resource#StringArray) or [`String`](https://developer.android.com/guide/topics/resources/string-resource#String).
The item index will be chosen based on the corresponding score value (0 - low, 1 - medium, 2 - high).
If the value is a string resource, the same value will be used for all scores.

- `ratingSubtitle` – a reference to [`StringArray`](https://developer.android.com/guide/topics/resources/string-resource#StringArray) or [`String`](https://developer.android.com/guide/topics/resources/string-resource#String).
The item index will be chosen based on the corresponding score value (0 - low, 1 - medium, 2 - high).
If the value is a string resource, the same value will be used for all scores.
*Note that when size is equal to `Icon` the subtitle is disabled*.

- `ratingOrientation` - the layout orientation.
- `ratingSize` - the dimensions of the layout
- `ratingValue` - A float value in `[0.0-10.0]` range. If the value exceeds the boundaries, it'll be clamped.


Example of a Rating in Kotlin

```Kotlin
import net.skyscanner.backpack.rating.BpkRating

BpkRating(context, BpkRating.Orientation.Horizontal, BpkRating.Size.Base).apply {
    icon = {
      when (it) {
        BpkRating.Score.Low -> context.getDrawable(R.drawable.bpk_star_outline)
        BpkRating.Score.Medium -> context.getDrawable(R.drawable.bpk_star_half)
        BpkRating.Score.High -> context.getDrawable(R.drawable.bpk_star)
      }
    }
    title = {
      when (it) {
        BpkRating.Score.Low -> "Low"
        BpkRating.Score.Medium -> "Medium"
        BpkRating.Score.High -> "High"
      }
    }
    subtitle = {
      when (it) {
        BpkRating.Score.Low -> "Sub Low"
        BpkRating.Score.Medium -> "Sub Medium"
        BpkRating.Score.High -> "Sub High"
      }
    }
    rating = 3f
}
```
`title`, `subtitle` and `icon` are optional functions that return the value for the current score.

## Theme Props

- `ratingColor`
  Refers to a [`LayerDrawable`](https://developer.android.com/reference/android/graphics/drawable/LayerDrawable) containing [`ColorDrawables`](https://developer.android.com/reference/android/graphics/drawable/ColorDrawable).
  The layer index will be chosen based on corresponding score value (0 - low, 1 - medium, 2 - high).

Styles can be changed globally through `bpkRatingStyle`. Check [theming](https://github.com/Skyscanner/backpack-android/blob/master/docs/THEMING.md) for more information.
