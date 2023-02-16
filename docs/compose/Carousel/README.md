# Carousel

[![Maven Central](https://img.shields.io/maven-central/v/net.skyscanner.backpack/backpack-compose)](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-compose)
[![Class reference](https://img.shields.io/badge/Class%20reference-Android-blue)](https://backpack.github.io/android/backpack-compose/net.skyscanner.backpack.compose.carousel)
[![Source code](https://img.shields.io/badge/Source%20code-GitHub-lightgrey)](https://github.com/Skyscanner/backpack-android/tree/main/backpack-compose/src/main/kotlin/net/skyscanner/backpack/compose/carousel)

## Default

| Day | Night |
| --- | --- |
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/Carousel/screenshots/default.png" alt="Carousel component" width="375" /> | <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/Carousel/screenshots/default_dm.png" alt="Carousel component - dark mode" width="375" /> |

## Installation

Backpack Compose is available through [Maven Central](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-compose). Check the main [Readme](https://github.com/skyscanner/backpack-android#installation) for a complete installation guide.

## Usage

Example of a Carousel:

```Kotlin
import net.skyscanner.backpack.compose.carousel.BpkCarousel

BpkCarousel(count = 3){ index ->
  Image(painter = painterResource(id = imageResAtIndex(index)), contentDescription = "")
}
```

Example of a image changed callback:

```Kotlin
import net.skyscanner.backpack.compose.carousel.BpkCarousel

BpkCarousel(
  count = 3,
  onImageChanged = {  index ->
    // do something
  }
) { index ->
  Image(painter = painterResource(id = imageResAtIndex(index)), contentDescription = "")
}
```

Example of starting in a different image:

```Kotlin
import net.skyscanner.backpack.compose.carousel.BpkCarousel
import net.skyscanner.backpack.compose.carousel.rememberCarouselPagerState

val pagerState = rememberCarouselPagerState(currentImage = 1) // show carousel with image index 1

BpkCarousel(
  count = 3,
  pagerState = pagerState,
) { index ->
  Image(painter = painterResource(id = imageResAtIndex(index)), contentDescription = "")
}
```