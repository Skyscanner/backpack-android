# Star Rating

## Installation

Backpack Android is available through [Jitpack](https://jitpack.io/#Skyscanner/backpack-android). Check the main [Readme](https://github.com/skyscanner/backpack-android#installation) for a complete installation guide.

## Usage

The Star rating component can be used in both XML and Kotlin/Java

Example of a Star rating in XML

```xml
<net.skyscanner.backpack.starrating.BpkStarRating
  android:layout_width="wrap_content"
  android:layout_height="wrap_content"
  app:maxRating="5"
  app:rating="2.5" />
```

Example of a Star rating in Kotlin

```Kotlin
import net.skyscanner.backpack.starrating.BpkStarRating

BpkStarRating(context).apply {
  maxRating = 5
  rating = 2.5f
}
```

## Theme Props

- `starColor`
- `starFilledColor`

Styles can be changed globally through `bpkStarRatingStyle`. Check [theming](https://github.com/Skyscanner/backpack-android/blob/master/docs/THEMING.md) for more information.
