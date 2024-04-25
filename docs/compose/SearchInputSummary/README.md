# BpkSearchInputSummary

[![Maven Central](https://img.shields.io/maven-central/v/net.skyscanner.backpack/backpack-compose)](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-compose)
[![Class reference](https://img.shields.io/badge/Class%20reference-Android-blue)](https://backpack.github.io/android/backpack-compose/net.skyscanner.backpack.compose.searchinputsummary)
[![Source code](https://img.shields.io/badge/Source%20code-GitHub-lightgrey)](https://github.com/Skyscanner/backpack-android/tree/main/backpack-compose/src/main/kotlin/net/skyscanner/backpack/compose/searchinputsummary)

## Icon Prefix (Default)

| Day                                                                                                                                                                                                                          | Night                                                                                                                                                                                                                                       |
|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/SearchInputSummary/screenshots/prefix-icon.png" alt="BpkSearchInputSummary component with icon before search field" width="375" /> | <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/SearchInputSummary/screenshots/prefix-icon_dm.png" alt="BpkSearchInputSummary component with icon before search field - dark mode" width="375" /> |

## Text Prefix

| Day                                                                                                                                                                                                                          | Night                                                                                                                                                                                                                                    |
|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/SearchInputSummary/screenshots/prefix-text.png" alt="BpkSearchInputSummary component with text before search field" width="375" /> | <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/SearchInputSummary/screenshots/prefix-text.png" alt="BpkSearchInputSummary component with text before search field - dark mode" width="375" /> |

## Installation

Backpack Compose is available
through [Maven Central](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-compose). Check the
main [Readme](https://github.com/skyscanner/backpack-android#installation) for a complete installation guide.

## Usage

Example of a BpkSearchInputSummary showing a text prefix before the search query:

```Kotlin
import net.skyscanner.backpack.compose.searchinputsummary.BpkSearchInputSummary
import net.skyscanner.backpack.compose.textfield.BpkClearAction

val summary = SearchInputSummary(
    inputText = stringResource(id = R.string.city_rio),
    inputHint = stringResource(id = R.string.text_field_hint),
    prefix = SearchInputSummary.Prefix.Text(stringResource(id = R.string.text_field_prefix)),
)
var state by remember { mutableStateOf(summary) }
BpkSearchInputSummary(
    summary = state,
    onInputChanged = {
        state = state.copy(inputText = it)
    },
    clearAction = BpkClearAction(stringResource(id = R.string.text_field_clear_action_description)) {
        state = state.copy(inputText = "")
    },
)

```

Example of a BpkSearchInputSummary showing an icon before the search query:

```Kotlin
import net.skyscanner.backpack.compose.searchinputsummary.BpkSearchInputSummary
import net.skyscanner.backpack.compose.textfield.BpkClearAction

val summary = SearchInputSummary(
    inputText = stringResource(id = R.string.city_rome),
    inputHint = stringResource(id = R.string.text_field_hint),
    prefix = SearchInputSummary.Prefix.Icon(BpkIcon.Hotels),
)
var state by remember { mutableStateOf(summary) }
BpkSearchInputSummary(
    summary = state,
    onInputChanged = {
        state = state.copy(inputText = it)
    },
    clearAction = BpkClearAction(stringResource(id = R.string.text_field_clear_action_description)) {
        state = state.copy(inputText = "")
    },
)

```
