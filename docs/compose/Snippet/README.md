# Snippet

[![Maven Central](https://img.shields.io/maven-central/v/net.skyscanner.backpack/backpack-compose)](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-compose)
[![Class reference](https://img.shields.io/badge/Class%20reference-Android-blue)](https://backpack.github.io/android/backpack-compose/net.skyscanner.backpack.compose.snippet)
[![Source code](https://img.shields.io/badge/Source%20code-GitHub-lightgrey)](https://github.com/Skyscanner/backpack-android/tree/main/backpack-compose/src/main/kotlin/net/skyscanner/backpack/compose/snippet)

## Landscape (default)

| Day | Night |
| --- | --- |
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/Snippet/screenshots/landscape.png" alt="Snippet component" width="375" /> | <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/Snippet/screenshots/landscape_dm.png" alt="Snippet component - dark mode" width="375" /> |

## Square

| Day | Night |
| --- | --- |
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/Snippet/screenshots/square.png" alt="Snippet component" width="375" /> | <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/Snippet/screenshots/square_dm.png" alt="Snippet component - dark mode" width="375" /> |

## Portrait

| Day | Night |
| --- | --- |
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/Snippet/screenshots/portrait.png" alt="Snippet component" width="375" /> | <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/Snippet/screenshots/portrait_dm.png" alt="Snippet component - dark mode" width="375" /> |

## Installation

Backpack Compose is available through [Maven Central](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-compose). Check the main [Readme](https://github.com/skyscanner/backpack-android#installation) for a complete installation guide.

## Usage

### Snippet with image only
All text fields are optional, which means by default `BPKSnippet` only has an image.
If you don't specify an `imageOrientation` parameter it will use the `Landscape` type

```Kotlin
import net.skyscanner.backpack.compose.snippet.BpkSnippet
import net.skyscanner.backpack.compose.snippet.ImageOrientation

    BpkSnippet {
        Image(
            contentDescription = stringResource(R.string.snippet_image_content_description),
            contentScale = ContentScale.Crop,
            painter = painterResource(R.drawable.snippet_placeholder_1),
        )
    }
```

### Snippet with all text fields

```Kotlin
import net.skyscanner.backpack.compose.snippet.BpkSnippet
import net.skyscanner.backpack.compose.snippet.ImageOrientation

    BpkSnippet(
        bodyText = bodyText,
        description = description,
        headline = headline,
        imageOrientation = imageOrientation,
    ) {
        Image(
            contentDescription = stringResource(R.string.snippet_image_content_description),
            contentScale = ContentScale.Crop,
            painter = painterResource(R.drawable.snippet_placeholder_1),
        )
    }
```

### Snippet with action to be performed on tap
Optionally, Snippet can also perform an action on tap, passing in the `onClick` closure argument:

```Kotlin
import net.skyscanner.backpack.compose.snippet.BpkSnippet
import net.skyscanner.backpack.compose.snippet.ImageOrientation

    BpkSnippet(
        bodyText = bodyText,
        description = description,
        headline = headline,
        imageOrientation = imageOrientation,
        onClick = {}
    ) {
        Image(
            contentDescription = stringResource(R.string.snippet_image_content_description),
            contentScale = ContentScale.Crop,
            painter = painterResource(R.drawable.snippet_placeholder_1),
        )
    }
```

