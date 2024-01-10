# Rating

[![Maven Central](https://img.shields.io/maven-central/v/net.skyscanner.backpack/backpack-compose)](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-compose)
[![Class reference](https://img.shields.io/badge/Class%20reference-Android-blue)](https://backpack.github.io/android/backpack-compose/net.skyscanner.backpack.compose.ratingbar)
[![Source code](https://img.shields.io/badge/Source%20code-GitHub-lightgrey)](https://github.com/Skyscanner/backpack-android/tree/main/backpack-compose/src/main/kotlin/net/skyscanner/backpack/compose/ratingbar)

## Default

| Day                                                                                                                                                                    | Night                                                                                                                                                                                 |
|------------------------------------------------------------------------------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/RatingBar/screenshots/default.png" alt="Rating bar component" width="375" /> | <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/RatingBar/screenshots/default_dm.png" alt="Rating bar component - dark mode" width="375" /> |

## Installation

Backpack Compose is available through [Maven Central](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-compose). Check the main [Readme](https://github.com/skyscanner/backpack-android#installation) for a complete installation guide.

## Usage

Example of a rating bar:

```Kotlin
import net.skyscanner.backpack.compose.ratingbar.BpkRatingBar

BpkRatingBar(
    label = "Label",
    rating = 4.5f,
)
```

Example of a rating bar on contrast:

```Kotlin
import net.skyscanner.backpack.compose.ratingbar.BpkRatingBar
import net.skyscanner.backpack.compose.ratingbar.BpkRatingBarStyle

BpkRatingBar(
    label = "Label",
    rating = 4.5f,
    style = BpkRatingBarStyle.OnContrast,
)
```

Example of a rating bar with zero to 10 scale:

```Kotlin
import net.skyscanner.backpack.compose.ratingbar.BpkRatingBar
import net.skyscanner.backpack.compose.rating.BpkRatingScale

BpkRatingBar(
    label = "Label",
    rating = 4.5f,
    scale = BpkRatingScale.ZeroToTen,
)
```
