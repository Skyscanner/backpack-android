# Interactive Star Rating

## Installation

Backpack Android is available through [Maven Central](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-android). Check the main [Readme](https://github.com/skyscanner/backpack-android#installation) for a complete installation guide.

## Usage

The Interactive Star Rating component can be used in both XML and Kotlin/Java

Example of a Interactive Star Rating in XML

```xml
<net.skyscanner.backpack.starrating.BpkInteractiveStarRating
  android:layout_width="wrap_content"
  android:layout_height="wrap_content"
  app:maxRating="5"
  app:rating="2.5" />
```

Example of a Interactive Star Rating in Kotlin

```Kotlin
import net.skyscanner.backpack.starrating.BpkInteractiveStarRating

BpkInteractiveStarRating(context).apply {
  maxRating = 5
  rating = 2.5f
  onRatingChangedListener = { current, max ->
  }
}
```

## Theme Props

- `starColor`
- `starFilledColor`

Styles can be changed globally through `bpkInteractiveStarRatingStyle`. Check [theming](https://github.com/Skyscanner/backpack-android/blob/main/docs/THEMING.md) for more information.


## Accessibility

To ensure this component is accessible set a `contentDescription` for the component.
To improve status announcements you can set `accessibilityStatusRes` in both XML and Kotlin/Java to announce `x out of y` instead of `x% out of 100%`.

The string resource needs to contain a `%f` and `%d` placeholder:

```xml
<string name="star_rating_accessibility_status">%.0f out of %d</string>
```
