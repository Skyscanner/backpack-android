# Star Rating

[![Maven Central](https://img.shields.io/maven-central/v/net.skyscanner.backpack/backpack-compose)](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-compose)
[![Class reference](https://img.shields.io/badge/Class%20reference-Android-blue)](https://backpack.github.io/android/backpack-compose/net.skyscanner.backpack.compose.starrating)
[![Source code](https://img.shields.io/badge/Source%20code-GitHub-lightgrey)](https://github.com/Skyscanner/backpack-android/tree/main/backpack-compose/src/main/kotlin/net/skyscanner/backpack/compose/starrating)

## Default

| Day                                                                                                                                                                      | Night                                                                                                                                                                                   |
|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/StarRating/screenshots/default.png" alt="Star Rating component" width="375" /> | <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/StarRating/screenshots/default_dm.png" alt="Star Rating component - dark mode" width="375" /> |

## Interactive

| Day                                                                                                                                                                                             | Night                                                                                                                                                                                                          |
|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/StarRatingInteractive/screenshots/default.png" alt="Interactive Star Rating component" width="375" /> | <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/StarRatingInteractive/screenshots/default_dm.png" alt="Interactive Star Rating component - dark mode" width="375" /> |

## Installation

Backpack Compose is available
through [Maven Central](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-compose). Check the
main [Readme](https://github.com/skyscanner/backpack-android#installation) for a complete installation guide.

## Usage

Example of a `BpkStarRating` with full star rating:

```Kotlin
import net.skyscanner.backpack.compose.starrating.BpkStarRating
import net.skyscanner.backpack.compose.starrating.BpkStarRatingSize
import net.skyscanner.backpack.compose.starrating.BpkRatingRounding


BpkStarRating(
  rating = 2f,
  contentDescription = { value, max ->
    stringResource(R.string.star_rating_accessibility_status, value, max)
  },
  rounding = BpkRatingRounding.Up,
  size = BpkStarRatingSize.Small,
)
```

Example of a `BpkStarRating` with half star rating:

```Kotlin
import net.skyscanner.backpack.compose.starrating.BpkStarRating
import net.skyscanner.backpack.compose.starrating.BpkStarRatingSize
import net.skyscanner.backpack.compose.starrating.BpkRatingRounding


BpkStarRating(
  rating = 3.5f,
  contentDescription = { value, max ->
    stringResource(R.string.star_rating_accessibility_status, value, max)
  },
  rounding = BpkRatingRounding.Down,
  size = BpkStarRatingSize.Large,
)
```

Example of a `BpkHotelRating`:

```Kotlin
import net.skyscanner.backpack.compose.starrating.BpkStarRating
import net.skyscanner.backpack.compose.starrating.BpkStarRatingSize


BpkHotelRating(
  rating = 3,
  contentDescription = { value, max ->
    stringResource(R.string.star_rating_accessibility_status, value, max)
  },
  size = BpkStarRatingSize.Small,
)
```

Example of a `BpkHotelRating` with gray color:

```Kotlin
import net.skyscanner.backpack.compose.starrating.BpkRatingColor
import net.skyscanner.backpack.compose.starrating.BpkStarRating
import net.skyscanner.backpack.compose.starrating.BpkStarRatingSize


BpkHotelRating(
  rating = 3,
  contentDescription = { value, max ->
    stringResource(R.string.star_rating_accessibility_status, value, max)
  },
  size = BpkStarRatingSize.Small,
  color = BpkRatingColor.Gray,
)
```

Example of a `BpkInteractiveStarRating`:

```Kotlin
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import net.skyscanner.backpack.compose.starrating.BpkInteractiveStarRating
import net.skyscanner.backpack.compose.starrating.BpkStarRatingSize

var rating by remember { mutableStateOf(0) }

BpkInteractiveStarRating(
  rating = rating,
  onRatingChanged = { rating = it },
  contentDescription = { value, max ->
    stringResource(R.string.star_rating_accessibility_status, value, max)
  },
  size = BpkStarRatingSize.Small,
)
```
