# Star Rating

[![Maven Central](https://img.shields.io/maven-central/v/net.skyscanner.backpack/backpack-android)](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-android)
[![Class reference](https://img.shields.io/badge/Class%20reference-Android-blue)](https://backpack.github.io/android/Backpack/net.skyscanner.backpack.starrating)
[![Source code](https://img.shields.io/badge/Source%20code-GitHub-lightgrey)](https://github.com/Skyscanner/backpack-android/tree/main/Backpack/src/main/java/net/skyscanner/backpack/starrating)

## Default

| Day | Night |
| --- | --- |
| ![StarRating component](https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/view/StarRating/screenshots/default.png) |![StarRating component - dark mode](https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/view/StarRating/screenshots/default_dm.png) |

## Interactive

| Day | Night |
| --- | --- |
| ![StarRating component](https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/view/StarRatingInteractive/screenshots/default.png) |![StarRating component - dark mode](https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/view/StarRatingInteractive/screenshots/default_dm.png) |

## Installation

Backpack Android is available through [Maven Central](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-android). Check the main [Readme](https://github.com/skyscanner/backpack-android#installation) for a complete installation guide.

## Usage

The Star Rating component can be used in both XML and Kotlin/Java

Example of a Star Rating in XML

```xml
<net.skyscanner.backpack.starrating.BpkStarRating
  android:layout_width="wrap_content"
  android:layout_height="wrap_content"
  app:rounding="up"
  app:maxRating="5"
  app:rating="2.5" />
```

Example of an interactive Star Rating in XML

```xml
<net.skyscanner.backpack.starrating.BpkInteractiveStarRating
  android:layout_width="wrap_content"
  android:layout_height="wrap_content"
  app:rounding="up"
  app:maxRating="5"
  app:rating="2.5" />
```

Example of a Star Rating in Kotlin

```Kotlin
import net.skyscanner.backpack.starrating.BpkStarRating
import net.skyscanner.backpack.starrating.RoundingType

BpkStarRating(context).apply {
  maxRating = 5
  rating = 2.5f
  rounding = RoundingType.Up
}
```

Example of an interactive Star Rating in Kotlin

```Kotlin
import net.skyscanner.backpack.starrating.BpkInteractiveStarRating
import net.skyscanner.backpack.starrating.RoundingType

BpkInteractiveStarRating(context).apply {
  maxRating = 5
  rating = 2.5f
  rounding = RoundingType.Up
  onRatingChangedListener = { current, max ->
  }
}
```
# Props

| Property              | Type              | Default Value       |
| --------------------- | ----------------- | ------------------- |
| `maxRating`           | `Int`             | 5                   |
| `rating`              | `Float`           | `maxRating / 2`     |
| `rounding`            | `RoundingType`    | `RoundingType.DOWN` |
| `accessibilityStatus` | String resource   | -                   |

## Theme Props

- `starColor`
- `starFilledColor`

Styles can be changed globally through `bpkStarRatingStyle` or `bpkInterativeStarRatingStyle`. Check [theming](https://github.com/Skyscanner/backpack-android/blob/main/docs/view/THEMING.md) for more information.

## Accessibility

To ensure this component is accessible set a `contentDescription` for the component, unless there is a label indicating the rating available.
For simpler logic you can also set `accessibilityStatusRes` in both XML and Kotlin/Java to announce `x out of y`.

The string resource needs to contain a `%f` and `%d` placeholder:

```xml
<string name="star_rating_accessibility_status">%.0f out of %d</string>
```

If both `contentDescription` and `accessibilityStatusRes` are set the `contentDescription` will be appended to the accessibility status to match status description behaviour.
