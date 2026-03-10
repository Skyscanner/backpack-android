# BpkSearchInputControl

[![Maven Central](https://img.shields.io/maven-central/v/net.skyscanner.backpack/backpack-compose)](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-compose)
[![Class reference](https://img.shields.io/badge/Class%20reference-Android-blue)](https://backpack.github.io/android/backpack-compose/net.skyscanner.backpack.compose.searchinputcontrol)
[![Source code](https://img.shields.io/badge/Source%20code-GitHub-lightgrey)](https://github.com/Skyscanner/backpack-android/tree/main/backpack-compose/src/main/kotlin/net/skyscanner/backpack/compose/searchinputcontrol)

## Default

| Day | Night |
|-----|-------|
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/SearchInputControl/screenshots/default-prefix.png" alt="BpkSearchInputControl component showing default magnifying glass icon" width="375" /> | <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/SearchInputControl/screenshots/default-prefix_dm.png" alt="BpkSearchInputControl component showing default magnifying glass icon - dark mode" width="375" /> |

## Text prefix

| Day | Night |
|-----|-------|
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/SearchInputControl/screenshots/text-prefix.png" alt="BpkSearchInputControl component showing text prefix From" width="375" /> | <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/SearchInputControl/screenshots/text-prefix_dm.png" alt="BpkSearchInputControl component showing text prefix From - dark mode" width="375" /> |

## Icon prefix

| Day | Night |
|-----|-------|
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/SearchInputControl/screenshots/icon-prefix.png" alt="BpkSearchInputControl component showing custom icon prefix" width="375" /> | <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/SearchInputControl/screenshots/icon-prefix_dm.png" alt="BpkSearchInputControl component showing custom icon prefix - dark mode" width="375" /> |

## No prefix

| Day | Night |
|-----|-------|
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/SearchInputControl/screenshots/no-prefix.png" alt="BpkSearchInputControl component showing no prefix" width="375" /> | <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/SearchInputControl/screenshots/no-prefix_dm.png" alt="BpkSearchInputControl component showing no prefix - dark mode" width="375" /> |

## Read only

| Day | Night |
|-----|-------|
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/SearchInputControl/screenshots/read-only.png" alt="BpkSearchInputControl component in read only mode" width="375" /> | <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/SearchInputControl/screenshots/read-only_dm.png" alt="BpkSearchInputControl component in read only mode - dark mode" width="375" /> |

## Corner (Docking)

| Day | Night |
|-----|-------|
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/SearchInputControl/screenshots/corner.png" alt="BpkSearchInputControl components docked together as a stack" width="375" /> | <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/SearchInputControl/screenshots/corner_dm.png" alt="BpkSearchInputControl components docked together as a stack - dark mode" width="375" /> |

## Installation

Backpack Compose is available
through [Maven Central](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-compose). Check the
main [Readme](https://github.com/skyscanner/backpack-android#installation) for a complete installation guide.

## Usage

`BpkSearchInputControl` is a specialised search text field with larger corners and support for a prefix (`None`, `Text`, or `Icon`). It supports read-only mode, optional clear action, and docking — allowing multiple controls to be stacked together with shared corner rounding.

Example of a `BpkSearchInputControl` showing a text prefix before the search query:

```Kotlin
import net.skyscanner.backpack.compose.searchinputcontrol.BpkSearchInputControl
import net.skyscanner.backpack.compose.searchinputsummary.Prefix

BpkSearchInputControl(
    inputText = inputText,
    inputHint = inputHint,
    prefix = Prefix.Text("From"),
    onInputChanged = { /* update input */ },
)
```

Example of a `BpkSearchInputControl` showing an icon prefix with a clear action:

```Kotlin
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.searchinputcontrol.BpkSearchInputControl
import net.skyscanner.backpack.compose.searchinputsummary.Prefix
import net.skyscanner.backpack.compose.textfield.BpkClearAction

BpkSearchInputControl(
    inputText = inputText,
    inputHint = inputHint,
    prefix = Prefix.Icon(BpkIcon.Hotels),
    onInputChanged = { /* update input */ },
    clearAction = BpkClearAction(stringResource(id = R.string.text_field_clear_action_description)) { /* clear input */ },
)
```

Example of a `BpkSearchInputControl` in read-only focused mode:

```Kotlin
import net.skyscanner.backpack.compose.searchinputcontrol.BpkSearchInputControl
import net.skyscanner.backpack.compose.searchinputcontrol.BpkSearchInputControlType
import net.skyscanner.backpack.compose.searchinputsummary.Prefix

BpkSearchInputControl(
    inputText = inputText,
    inputHint = inputHint,
    prefix = Prefix.None,
    onInputChanged = { /* update input */ },
    type = BpkSearchInputControlType.ReadOnly(isFocused = true),
)
```

Example of multiple `BpkSearchInputControl` components docked together:

```Kotlin
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.searchinputcontrol.BpkSearchInputControl
import net.skyscanner.backpack.compose.searchinputcontrol.Docking
import net.skyscanner.backpack.compose.searchinputsummary.Prefix

BpkSearchInputControl(
    inputText = origin,
    inputHint = "From",
    prefix = Prefix.Icon(BpkIcon.Search),
    onInputChanged = { origin = it },
    docking = Docking.Top,
)
BpkSearchInputControl(
    inputText = destination,
    inputHint = "To",
    prefix = Prefix.Icon(BpkIcon.Search),
    onInputChanged = { destination = it },
    docking = Docking.Bottom,
)
```
