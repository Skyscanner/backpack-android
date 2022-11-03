# Rating

[![Maven Central](https://img.shields.io/maven-central/v/net.skyscanner.backpack/backpack-compose)](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-compose)
[![Class reference](https://img.shields.io/badge/Class%20reference-Android-blue)](https://backpack.github.io/android/backpack-compose/net.skyscanner.backpack.compose.rating)
[![Source code](https://img.shields.io/badge/Source%20code-GitHub-lightgrey)](https://github.com/Skyscanner/backpack-android/tree/main/backpack-compose/src/main/kotlin/net/skyscanner/backpack/compose/rating)

## Default

| Day                                                                                                                                                             | Night                                                                                                                                                                          |
|-----------------------------------------------------------------------------------------------------------------------------------------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/Rating/screenshots/default.png" alt="Rating component" width="375" /> | <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/Rating/screenshots/default_dm.png" alt="Rating component - dark mode" width="375" /> |

## Installation

Backpack Compose is available through [Maven Central](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-compose). Check the main [Readme](https://github.com/skyscanner/backpack-android#installation) for a complete installation guide.

## Usage

Example of a Rating:

```Kotlin
import net.skyscanner.backpack.compose.rating.BpkRating

BpkRating {
  BpkRating(
    title = "Title",
    subtitle = "Subtitle",
    value = 4.5,
  )
}
```

Example of a large Rating:

```Kotlin
import net.skyscanner.backpack.compose.rating.BpkRating
import net.skyscanner.backpack.compose.rating.BpkRatingSize

BpkRating {
  BpkRating(
    title = "Title",
    subtitle = "Subtitle",
    value = 4.5,
    size = BpkRatingSize.Large,
  )
}
```

Example of a title only Rating:

```Kotlin
import net.skyscanner.backpack.compose.rating.BpkRating

BpkRating {
  BpkRating(
    title = "Title",
    value = 4.5,
  )
}
```

Example of a Rating with no scale:

```Kotlin
import net.skyscanner.backpack.compose.rating.BpkRating

BpkRating {
  BpkRating(
    title = "Title",
    subtitle = "Subtitle",
    value = 4.5,
    showScale = false,
  )
}
```

Example of a Rating with 0-10 scale:

```Kotlin
import net.skyscanner.backpack.compose.rating.BpkRating
import net.skyscanner.backpack.compose.rating.BpkRatingScale

BpkRating {
  BpkRating(
    title = "Title",
    subtitle = "Subtitle",
    value = 4.5,
    scale = BpkRatingScale.ZeroToTen,
  )
}
```

Example of a Rating with custom content:

```Kotlin
import net.skyscanner.backpack.compose.rating.BpkRating

BpkRating {
  BpkRating(
    subtitle = "Subtitle",
    value = 4.5,
  ) {
      // custom content
  }
}
```
