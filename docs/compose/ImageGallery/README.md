# Image Gallery

[![Maven Central](https://img.shields.io/maven-central/v/net.skyscanner.backpack/backpack-compose)](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-compose)
[![Class reference](https://img.shields.io/badge/Class%20reference-Android-blue)](https://backpack.github.io/android/backpack-compose/net.skyscanner.backpack.compose.imagegallery)
[![Source code](https://img.shields.io/badge/Source%20code-GitHub-lightgrey)](https://github.com/Skyscanner/backpack-android/tree/main/backpack-compose/src/main/kotlin/net/skyscanner/backpack/compose/imagegallery)

## Carousel

| Day                                                                                                                                                                                    | Night                                                                                                                                                                                                 |
|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/ImageGallery/screenshots/carousel.png" alt="Image Gallery Carousel component" width="375" /> | <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/ImageGallery/screenshots/carousel_dm.png" alt="Image Gallery Carousel component - dark mode" width="375" /> |

## Installation

Backpack Compose is available through [Maven Central](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-compose). Check the main [Readme](https://github.com/skyscanner/backpack-android#installation) for a complete installation guide.

## Usage

Example of an image gallery carousel:

```Kotlin
import net.skyscanner.backpack.compose.carousel.rememberBpkCarouselState
import net.skyscanner.backpack.compose.imagegallery.BpkImageGalleryCarousel

val state = rememberBpkCarouselState(totalImages = totalImages)

BpkImageGalleryCarousel(
    state = state,
    modifier = Modifier.height(100.dp),
    onImageClicked = { index -> /* handle on click */ },
) { index ->
    Image(painter = painterResource(id = imageResAtIndex(index)), contentDescription = "")
}
```

Example of an image gallery without categories:

```Kotlin
import net.skyscanner.backpack.compose.carousel.rememberBpkCarouselState
import net.skyscanner.backpack.compose.imagegallery.BpkImageGallerySlideshow
import net.skyscanner.backpack.compose.imagegallery.BpkImageGalleryImage

val modalState = rememberBpkModalState()
val coroutineScope = rememberCoroutineScope()

BpkImageGallerySlideshow(
    modifier = modifier,
    state = modalState,
    closeContentDescription = "",
    initialImage = 0,
    onCloseClicked = { coroutineScope.launch { modalState.hide() } },
    onDismiss = { /* handle dismiss */ },
    onImageChanged = { /* Handle on image change if needed (ie logging) */ },
    images = listOf(
        BpkImageGalleryImage(
            title = "",
            description = "",
            credit =  "",
            content = { contentDescription, contentScale ->
                // Image content
            },
        ),
    )
)
```

Example of an image gallery with categories and chips:


```kotlin
import net.skyscanner.backpack.compose.carousel.rememberBpkCarouselState
import net.skyscanner.backpack.compose.imagegallery.BpkImageGalleryChipCategory
import net.skyscanner.backpack.compose.imagegallery.BpkImageGalleryChipGrid
import net.skyscanner.backpack.compose.imagegallery.BpkImageGalleryImage

val modalState = rememberBpkModalState()
val coroutineScope = rememberCoroutineScope()

BpkImageGalleryChipGrid(
    modifier = modifier,
    state = modalState,
    initialCategory = 0,
    closeContentDescription = "",
    onCloseClicked = { coroutineScope.launch { modalState.hide() } },
    onDismiss = { /* handle dismiss */ },
    onImageChanged = { /* Handle on image change if needed (ie logging) */ },
    onCategoryChanged = { /* Handle on category change if needed (ie logging) */ },
    onImageClicked = { /* Handle on image clicked if needed (ie logging) */ },
    categories =  listOf(
        BpkImageGalleryChipCategory(
            title = title,
            images = listOf(
                BpkImageGalleryImage(
                    title = "",
                    description = "",
                    credit =  "",
                    content = { contentDescription, contentScale ->
                        // Image content
                    },
                ),
                // more images
            ),
        ),
        // more categories
    ),
)
```

Example of an image gallery with categories and image chips:


```kotlin
import net.skyscanner.backpack.compose.carousel.rememberBpkCarouselState
import net.skyscanner.backpack.compose.imagegallery.BpkImageGalleryImageCategory
import net.skyscanner.backpack.compose.imagegallery.BpkImageGalleryImageGrid
import net.skyscanner.backpack.compose.imagegallery.BpkImageGalleryImage

val modalState = rememberBpkModalState()
val coroutineScope = rememberCoroutineScope()

BpkImageGalleryImageGrid(
    modifier = modifier,
    state = modalState,
    initialCategory = 0,
    closeContentDescription = "",
    onCloseClicked = { coroutineScope.launch { modalState.hide() } },
    onDismiss = { /* handle dismiss */ },
    onImageChanged = { /* Handle on image change if needed (ie logging) */ },
    onCategoryChanged = { /* Handle on category change if needed (ie logging) */ },
    onImageClicked = { /* Handle on image clicked if needed (ie logging) */ },
    categories =  listOf(
        BpkImageGalleryImageCategory(
            title = title,
            images = listOf(
                BpkImageGalleryImage(
                    title = "",
                    description = "",
                    credit =  "",
                    content = { contentDescription, contentScale ->
                        // Image content
                    },
                ),
                // more images
            ),
            content = {  /* Category image content */ },
        ),
        // more categories
    ),
)
```
