# AppSearchModal

[![Maven Central](https://img.shields.io/maven-central/v/net.skyscanner.backpack/backpack-compose)](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-compose)
[![Class reference](https://img.shields.io/badge/Class%20reference-Android-blue)](https://backpack.github.io/android/backpack-compose/net.skyscanner.backpack.compose.appsearchmodal)
[![Source code](https://img.shields.io/badge/Source%20code-GitHub-lightgrey)](https://github.com/Skyscanner/backpack-android/tree/main/backpack-compose/src/main/kotlin/net/skyscanner/backpack/compose/appsearchmodal)

## Content

| Day | Night |
| --- | --- |
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/AppSearchModal/screenshots/content.png" alt="AppSearchModal component in Content state" width="375" /> | <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/AppSearchModal/screenshots/content_dm.png" alt="AppSearchModal component in Content state - dark mode" width="375" /> |

## Loading

| Day | Night |
| --- | --- |
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/AppSearchModal/screenshots/loading.png" alt="AppSearchModal component in Loading state" width="375" /> | <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/AppSearchModal/screenshots/loading_dm.png" alt="AppSearchModal component in Loading state - dark mode" width="375" /> |

## Error

| Day | Night |
| --- | --- |
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/AppSearchModal/screenshots/error.png" alt="AppSearchModal component in Error state" width="375" /> | <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/AppSearchModal/screenshots/error_dm.png" alt="AppSearchModal component in Error state - dark mode" width="375" /> |

## Installation

Backpack Compose is available through [Maven Central](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-compose). Check the main [Readme](https://github.com/skyscanner/backpack-android#installation) for a complete installation guide.

## Usage

Example of a AppSearchModal in Content state:

```Kotlin
import net.skyscanner.backpack.compose.appsearchmodal.BpkAppSearchModal
import net.skyscanner.backpack.compose.textfield.BpkClearAction

val destination = remember { mutableStateOf(inputText) }
BpkAppSearchModal(
    title = stringResource(id = R.string.destination),
    inputText = destination.value,
    inputHint = stringResource(id = R.string.text_field_hint),
    results = BpkAppSearchModalResult.Content(
        sections = listOf(BpkSection(...)),
        shortcuts = listOf(BpkShortcut(...)),
    ),
    closeAccessibilityLabel = stringResource(id = R.string.navigation_close),
    onClose = {/* close modal*/ },
    onInputChanged = {/* update input*/ },
    clearAction = BpkClearAction("Clear"){ destination.value = "" },
)
```

Example of a AppSearchModal in Loading state:

```Kotlin
import net.skyscanner.backpack.compose.appsearchmodal.BpkAppSearchModal
import net.skyscanner.backpack.compose.textfield.BpkClearAction

val destination = remember { mutableStateOf(inputText) }
BpkAppSearchModal(
    title = stringResource(id = R.string.destination),
    inputText = destination.value,
    inputHint = stringResource(id = R.string.text_field_hint),
    results = BpkAppSearchModalResult.Loading(accessibilityLabel = stringResource(id = R.string.content_is_loading)),
    closeAccessibilityLabel = stringResource(id = R.string.navigation_close),
    onClose = {/* close modal*/ },
    onInputChanged = {/* update input*/ },
    clearAction = BpkClearAction("Clear"){ destination.value = "" },
)
```

Example of a AppSearchModal in Error state:

```Kotlin
import net.skyscanner.backpack.compose.appsearchmodal.BpkAppSearchModal
import net.skyscanner.backpack.compose.textfield.BpkClearAction

val destination = remember { mutableStateOf(inputText) }
BpkAppSearchModal(
    title = stringResource(id = R.string.destination),
    inputText = destination.value,
    inputHint = stringResource(id = R.string.text_field_hint),
    results = BpkAppSearchModalResult.Error(...),
    closeAccessibilityLabel = stringResource(id = R.string.navigation_close),
    onClose = {/* close modal*/ },
    onInputChanged = {/* update input*/ },
    clearAction = BpkClearAction("Clear"){ destination.value = "" },
)
```
