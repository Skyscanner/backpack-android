# Image Gallery

[![Maven Central](https://img.shields.io/maven-central/v/net.skyscanner.backpack/backpack-compose)](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-compose)
[![Class reference](https://img.shields.io/badge/Class%20reference-Android-blue)](https://backpack.github.io/android/backpack-compose/net.skyscanner.backpack.compose.imagegallery)
[![Source code](https://img.shields.io/badge/Source%20code-GitHub-lightgrey)](https://github.com/Skyscanner/backpack-android/tree/main/backpack-compose/src/main/kotlin/net/skyscanner/backpack/compose/imagegallery)

## Gallery Preview Default

| Day                                                                                                                                                                                                          | Night                                                                                                                                                                                                                       |
|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/ImageGallery/screenshots/gallery-preview-default.png" alt="Image Gallery Preview Default component" width="375" /> | <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/ImageGallery/screenshots/gallery-preview-default_dm.png" alt="Image Gallery Preview Default component - dark mode" width="375" /> |

## Gallery Preview Hero

| Day                                                                                                                                                                                                | Night                                                                                                                                                                                                                 |
|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/ImageGallery/screenshots/gallery-preview-hero.png" alt="Image Gallery Preview Hero component" width="375" /> | <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/ImageGallery/screenshots/gallery-preview-hero_dm.png" alt="Image Gallery Preview Hero component - dark mode" width="375" /> |

## Slideshow

| Day                                                                                                                                                                                     | Night                                                                                                                                                                                                   |
|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/ImageGallery/screenshots/slideshow.png" alt="Image Gallery Slideshow component" width="375" /> | <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/ImageGallery/screenshots/slideshow_dm.png" alt="Image Gallery Slideshow component - dark mode" width="375" /> |

## Image Grid

| Day                                                                                                                                                                                        | Night                                                                                                                                                                                                     |
|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/ImageGallery/screenshots/image-grid.png" alt="Image Gallery Image Grid component" width="375" /> | <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/ImageGallery/screenshots/image-grid_dm.png" alt="Image Gallery Image Grid component - dark mode" width="375" /> |

## Chip Grid

| Day                                                                                                                                                                                      | Night                                                                                                                                                                                                   |
|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/ImageGallery/screenshots/chip-grid.png" alt="Image Gallery Chip Grid component" width="375" /> | <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/ImageGallery/screenshots/chip-grid_dm.png" alt="Image Gallery Chip Grid component - dark mode" width="375" /> |

## Installation

Backpack Compose is available through [Maven Central](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-compose). Check the main [Readme](https://github.com/skyscanner/backpack-android#installation) for a complete installation guide.

## Usage

Example of an image gallery preview default:

```Kotlin
import net.skyscanner.backpack.compose.imagegallery.BpkImageGalleryPreviewDefault

BpkImageGalleryPreviewDefault(
    image = Image(painter = painterResource(id = imageResId), contentDescription = ""),
    buttonText = stringResource(stringResId),
    onButtonClicked = { /* Do something */ },
)
```

Example of an image gallery preview hero:

```Kotlin
import androidx.compose.foundation.layout.height
import androidx.compose.ui.unit.dp
import net.skyscanner.backpack.compose.carousel.rememberBpkCarouselState
import net.skyscanner.backpack.compose.imagegallery.BpkImageGalleryPreviewHero

val pagerState = rememberBpkCarouselState(
    totalImages = 4,
)

BpkImageGalleryPreviewHero(
    modifier = modifier.height(345.dp),
    state = pagerState,
) {
    Image(
        modifier = Modifier.fillMaxSize(),
        painter = painterResource(id = imageResId),
        contentDescription = "",
        contentScale = ContentScale.Crop,
    )
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
            content = { contentDescription: String, contentScale: ContentScale ->
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
                    content = { contentDescription: String, contentScale: ContentScale ->
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
                    content = { contentDescription: String, contentScale: ContentScale ->
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
