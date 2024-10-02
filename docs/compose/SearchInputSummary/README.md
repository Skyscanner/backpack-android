# BpkSearchInputSummary

[![Maven Central](https://img.shields.io/maven-central/v/net.skyscanner.backpack/backpack-compose)](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-compose)
[![Class reference](https://img.shields.io/badge/Class%20reference-Android-blue)](https://backpack.github.io/android/backpack-compose/net.skyscanner.backpack.compose.searchinputsummary)
[![Source code](https://img.shields.io/badge/Source%20code-GitHub-lightgrey)](https://github.com/Skyscanner/backpack-android/tree/main/backpack-compose/src/main/kotlin/net/skyscanner/backpack/compose/searchinputsummary)

## Default

| Day                                                                                                                                                                                                                                     | Night                                                                                                                                                                                                                                                   |
|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/SearchInputSummary/screenshots/default-prefix.png" alt="BpkSearchInputSummary component showing default magnifying glass icon" width="375" /> | <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/SearchInputSummary/screenshots/default-prefix_dm.png" alt="BpkSearchInputSummary component showing default magnifiying glass icon - dark mode" width="375" /> |

## Text prefix

| Day                                                                                                                                                                                                                     | Night                                                                                                                                                                                                                                  |
|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/SearchInputSummary/screenshots/text-prefix.png" alt="BpkSearchInputSummary component showing text prefix From" width="375" /> | <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/SearchInputSummary/screenshots/text-prefix_dm.png" alt="BpkSearchInputSummary component showing text prefix From - dark mode" width="375" /> |

## Icon prefix

| Day                                                                                                                                                                                                                       | Night                                                                                                                                                                                                                                    |
|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/SearchInputSummary/screenshots/icon-prefix.png" alt="BpkSearchInputSummary component showing custom icon prefix" width="375" /> | <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/SearchInputSummary/screenshots/icon-prefix_dm.png" alt="BpkSearchInputSummary component showing custom icon prefix - dark mode" width="375" /> |

## No prefix

| Day                                                                                                                                                                                                            | Night                                                                                                                                                                                                                          |
|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/SearchInputSummary/screenshots/no-prefix.png" alt="BpkSearchInputSummary component showing no prefix" width="375" /> | <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/SearchInputSummary/screenshots/no-prefix_dm.png" alt="BpkSearchInputSummary component showing  no prefix - dark mode" width="375" /> |

## Read only

| Day                                                                                                                                                                                                            | Night                                                                                                                                                                                                                         |
|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/SearchInputSummary/screenshots/read-only.png" alt="BpkSearchInputSummary component showing no prefix" width="375" /> | <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/SearchInputSummary/screenshots/read-only_dm.png" alt="BpkSearchInputSummary component showing no prefix - dark mode" width="375" /> |

## Installation

Backpack Compose is available
through [Maven Central](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-compose). Check the
main [Readme](https://github.com/skyscanner/backpack-android#installation) for a complete installation guide.

## Usage

BpkSearchInputSummary is a specialised text field with larger corners and support for a Prefix that can be None/Text/Icon.

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

Example of a BpkSearchInputSummary showing no icon

```Kotlin
import net.skyscanner.backpack.compose.searchinputsummary.BpkSearchInputSummary
import net.skyscanner.backpack.compose.textfield.BpkClearAction

BpkSearchInputSummary(
    inputText = inputText,
    inputHint = inputHint,
    prefix = Prefix.None,
    onInputChanged = {/* update input*/ },
    clearAction = BpkClearAction(stringResource(id = R.string.text_field_clear_action_description)) {/* clear input*/ },
    type = BpkSearchInputSummaryType.TextInput
)
```

Example of a BpkSearchInputSummary showing no icon, not allowing input and being focused

```Kotlin
import net.skyscanner.backpack.compose.searchinputsummary.BpkSearchInputSummary
import net.skyscanner.backpack.compose.textfield.BpkClearAction

BpkSearchInputSummary(
    inputText = inputText,
    inputHint = inputHint,
    prefix = Prefix.None,
    onInputChanged = {/* update input*/ },
    clearAction = BpkClearAction(stringResource(id = R.string.text_field_clear_action_description)) {/* clear input*/ },
    type = BpkSearchInputSummaryType.ReadOnly(isFocused = true)
)
```
