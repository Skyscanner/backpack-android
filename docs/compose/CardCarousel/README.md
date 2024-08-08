# CardCarousel

[![Maven Central](https://img.shields.io/maven-central/v/net.skyscanner.backpack/backpack-compose)](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-compose)
[![Class reference](https://img.shields.io/badge/Class%20reference-Android-blue)](https://backpack.github.io/android/backpack-compose/net.skyscanner.backpack.compose.carousel)
[![Source code](https://img.shields.io/badge/Source%20code-GitHub-lightgrey)](https://github.com/Skyscanner/backpack-android/tree/main/backpack-compose/src/main/kotlin/net/skyscanner/backpack/compose/cardcarousel)

## Default

| Day                                                                                                                                                                         | Night                                                                                                                                                                                       |
|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/CardCarousel/screenshots/default.png" alt="CardCarousel component" width="375" /> | <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/CardCarousel/screenshots/default_dm.png" alt="Card Carousel component - dark mode" width="375" /> |

## Installation

Backpack Compose is available through [Maven Central](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-compose). Check the main [Readme](https://github.com/skyscanner/backpack-android#installation) for a complete installation guide.

## Usage

Example of a Card Carousel:

```Kotlin
import net.skyscanner.backpack.compose.cardcarousel.BpkCardCarousel
import net.skyscanner.backpack.compose.carousel.rememberBpkCarouselState

val cards = listOf(
    BpkCardCarouselItem(
        title = "Card title 1",
        description = "Description of card 1",
        contentDescription = "imageAccessibilityLabel"
        content = { contentDescription ->
            Image(
                contentScale = ContentScale.Crop,
                painter = painterResource(id = R.drawable.image),
                contentDescription = contentDescription,
            )
        },
    )
)

val carouselState = rememberBpkCarouselState(totalImages = cards.size, initialImage = initialImage)

BpkCardCarousel(
    state = carouselState,
    cards = cards,
)
```

Example of a card changed callback:

```Kotlin
import net.skyscanner.backpack.compose.cardcarousel.BpkCardCarousel
import net.skyscanner.backpack.compose.carousel.rememberBpkCarouselState

val carouselState = rememberBpkCarouselState(totalImages = cards.size, initialImage = initialImage)

LaunchedEffect(state.currentPage) {
    print("current page: ${state.currentPage}")
}

BpkCardCarousel(
    state = carouselState,
    cards = cards,
)
```

Example of starting in a different image:

```Kotlin
import net.skyscanner.backpack.compose.cardcarousel.BpkCardCarousel
import net.skyscanner.backpack.compose.carousel.rememberBpkCarouselState

val carouselState = rememberBpkCarouselState(totalImages = cards.size, initialImage = initialImage)

// show card carousel with card index 1
BpkCardCarousel(
    state = carouselState,
    cards = cards,
)
```
