# Carousel

[![GitHub Packages](https://img.shields.io/badge/GitHub%20Packages-backpack--compose-blue)](https://github.com/Skyscanner/backpack-android/packages)
[![Class reference](https://img.shields.io/badge/Class%20reference-Android-blue)](https://backpack.github.io/android/backpack-compose/net.skyscanner.backpack.compose.carousel)
[![Source code](https://img.shields.io/badge/Source%20code-GitHub-lightgrey)](https://github.com/Skyscanner/backpack-android/tree/main/backpack-compose/src/main/kotlin/net/skyscanner/backpack/compose/carousel)

## Default

| Day | Night |
| --- | --- |
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/Carousel/screenshots/default.png" alt="Carousel component" width="375" /> |<img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/Carousel/screenshots/default_dm.png" alt="Carousel component - dark mode" width="375" /> |

## Installation

Backpack Compose is available through [GitHub Packages](https://github.com/Skyscanner/backpack-android/packages). Check the main [Readme](https://github.com/skyscanner/backpack-android#installation) for a complete installation guide.

## Usage

Example of a Carousel:

```kotlin
import net.skyscanner.backpack.compose.carousel.BpkCarousel
import net.skyscanner.backpack.compose.carousel.rememberBpkCarouselState

val carouselState = rememberBpkCarouselState(totalImages = 4)

BpkCarousel(
    state = carouselState,
) {
    Image(
        modifier = Modifier.fillMaxSize(),
        painter = painterResource(id = R.drawable.image),
        contentDescription = "Image $it",
        contentScale = ContentScale.Crop,
    )
}
```

Example of starting at a specific image:

```kotlin
import net.skyscanner.backpack.compose.carousel.BpkCarousel
import net.skyscanner.backpack.compose.carousel.rememberBpkCarouselState

val carouselState = rememberBpkCarouselState(
    totalImages = 4,
    initialImage = 2,
)

BpkCarousel(
    state = carouselState,
) {
    Image(
        modifier = Modifier.fillMaxSize(),
        painter = painterResource(id = R.drawable.image),
        contentDescription = "Image $it",
        contentScale = ContentScale.Crop,
    )
}
```

Example of listening to page changes:

```kotlin
import net.skyscanner.backpack.compose.carousel.BpkCarousel
import net.skyscanner.backpack.compose.carousel.rememberBpkCarouselState

val carouselState = rememberBpkCarouselState(totalImages = 4)

LaunchedEffect(carouselState.currentPage) {
    println("Current page: ${carouselState.currentPage}")
}

BpkCarousel(
    state = carouselState,
) {
    Image(
        modifier = Modifier.fillMaxSize(),
        painter = painterResource(id = R.drawable.image),
        contentDescription = "Image $it",
        contentScale = ContentScale.Crop,
    )
}
```

Example of programmatically scrolling to a page:

```kotlin
import net.skyscanner.backpack.compose.carousel.BpkCarousel
import net.skyscanner.backpack.compose.carousel.rememberBpkCarouselState

val carouselState = rememberBpkCarouselState(totalImages = 4)
val coroutineScope = rememberCoroutineScope()

BpkButton(
    text = "Go to page 2",
    onClick = {
        coroutineScope.launch {
            carouselState.animateScrollToPage(2)
        }
    },
)

BpkCarousel(
    state = carouselState,
) {
    Image(
        modifier = Modifier.fillMaxSize(),
        painter = painterResource(id = R.drawable.image),
        contentDescription = "Image $it",
        contentScale = ContentScale.Crop,
    )
}
```
