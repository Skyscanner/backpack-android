# BpkSearchInputSummary

[![Maven Central](https://img.shields.io/maven-central/v/net.skyscanner.backpack/backpack-compose)](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-compose)
[![Class reference](https://img.shields.io/badge/Class%20reference-Android-blue)](https://backpack.github.io/android/backpack-compose/net.skyscanner.backpack.compose.searchinputsummary)
[![Source code](https://img.shields.io/badge/Source%20code-GitHub-lightgrey)](https://github.com/Skyscanner/backpack-android/tree/main/backpack-compose/src/main/kotlin/net/skyscanner/backpack/compose/searchinputsummary)

## Default

| Day                                                                                                                                                                                                                                                                 | Night                                                                                                                                                                                                                                                                              |
|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/SearchInputSummary/screenshots/default.png" alt="BpkSearchInputSummary component showing states including icon, text prefix with and without text entered" width="375" /> | <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/SearchInputSummary/screenshots/default_dm.png" alt="BpkSearchInputSummary component showing states including icon, text prefix with and without text entered - dark mode" width="375" /> |

## Installation

Backpack Compose is available
through [Maven Central](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-compose). Check the
main [Readme](https://github.com/skyscanner/backpack-android#installation) for a complete installation guide.

## Usage

Example of a BpkSearchInputSummary showing a text prefix before the search query:

```Kotlin
import net.skyscanner.backpack.compose.searchinputsummary.BpkSearchInputSummary
import net.skyscanner.backpack.compose.textfield.BpkClearAction

BpkSearchInputSummary(
    inputText = inputText,
    inputHint = inputHint,
    prefix = Prefix.Text("Some text"),
    onInputChanged = {/* update input*/ },
    clearAction = BpkClearAction(stringResource(id = R.string.text_field_clear_action_description)) {/* clear input*/ },
)
```

Example of a BpkSearchInputSummary showing an icon before the search query:

```Kotlin
import net.skyscanner.backpack.compose.searchinputsummary.BpkSearchInputSummary
import net.skyscanner.backpack.compose.textfield.BpkClearAction

BpkSearchInputSummary(
    inputText = inputText,
    inputHint = inputHint,
    prefix = Prefix.Icon(BpkIcon.Hotels),
    onInputChanged = {/* update input*/ },
    clearAction = BpkClearAction(stringResource(id = R.string.text_field_clear_action_description)) {/* clear input*/ },
)
```
