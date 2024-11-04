# Select

[![Maven Central](https://img.shields.io/maven-central/v/net.skyscanner.backpack/backpack-compose)](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-compose)
[![Class reference](https://img.shields.io/badge/Class%20reference-Android-blue)](https://backpack.github.io/android/backpack-compose/net.skyscanner.backpack.compose.select)
[![Source code](https://img.shields.io/badge/Source%20code-GitHub-lightgrey)](https://github.com/Skyscanner/backpack-android/tree/main/backpack-compose/src/main/kotlin/net/skyscanner/backpack/compose/select)

## Default

| Day                                                                                                                                                              | Night                                                                                                                                                                                       |
|------------------------------------------------------------------------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/Select/screenshots/default.png" alt="Select component" width="375" />  | <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/Select/screenshots/default_dm.png" alt="Select component - dark mode" width="375" />              |

### Dropdown width

The width of the dropdown can be one of either `MaxWidth`, `MatchOptionWidth`, or `MatchSelectWidth`.
Max width allows the options to expand to the full width of the screen.
Match Option width allows the options to expand as far as they need without ellipsis, which can be narrower than the Select
it belongs to.
Match Select width means the options will be as wide as the parent Select's width which can lead to truncation.

| Width            | Day                                                                                                                                                                                                                                | Night                                                                                                                                                                                                                                             |
|------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| MaxWidth         | <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/Select/screenshots/maxwidth.png" alt="Select component showing dropdown extending full width of screen" width="375" />                   | <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/Select/screenshots/maxwidth_dm.png" alt="Select component showing dropdown extending full width of screen - dark mode" width="375" />                   |
| MatchOptionWidth | <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/Select/screenshots/matchoptionwidth.png" alt="Select component showing dropdown extending as wide as longest option text" width="375" /> | <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/Select/screenshots/matchoptionwidth_dm.png" alt="Select component showing dropdown extending as wide as longest option text - dark mode" width="375" /> |
| MatchSelectWidth | <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/Select/screenshots/matchselectwidth.png" alt="Select component showing dropdown matching width of parent" width="375" />                 | <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/Select/screenshots/matchselectwidth_dm.png" alt="Select component showing dropdown matching width of parent - dark mode" width="375" />                 |

## Installation

Backpack Compose is available through [Maven Central](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-compose). Check the main [Readme](https://github.com/skyscanner/backpack-android#installation) for a complete installation guide.

## Usage

Example of a Select:

```Kotlin
import net.skyscanner.backpack.compose.fieldset.BpkFieldStatus
import net.skyscanner.backpack.compose.select.BpkSelect

BpkSelect(
    modifier = modifier.widthIn(min = BpkSpacing.Xxl.times(5)),
    options = arrayListOf("Karachi", "Lahore", "Faisalabad", "Islamabad", "Quetta", "Peshawar", "Menu item", "Menu item"),
    selectedIndex = -1,
    placeholder = "Select",
    status = BpkSelectState.Default,
    onSelectionChange = {},
)
```
or

```Kotlin
import net.skyscanner.backpack.compose.fieldset.BpkFieldStatus
import net.skyscanner.backpack.compose.select.BpkSelect

BpkSelect(
    modifier = modifier.widthIn(min = BpkSpacing.Xxl.times(5)),
    options = arrayListOf("Karachi", "Lahore", "Faisalabad", "Islamabad", "Quetta", "Peshawar", "Menu item", "Menu item"),
    selectedIndex = 2,
    placeholder = "Select",
    status = BpkFieldStatus.Disabled,
    onSelectionChange = {},
)
```
or

```Kotlin
import net.skyscanner.backpack.compose.fieldset.BpkFieldStatus
import net.skyscanner.backpack.compose.select.BpkSelect

BpkSelect(
    modifier = modifier.widthIn(min = BpkSpacing.Xxl.times(5)),
    options = arrayListOf("Karachi", "Lahore", "Faisalabad", "Islamabad", "Quetta", "Peshawar", "Menu item", "Menu item"),
    selectedIndex = 0,
    placeholder = "Select",
    status = BpkFieldStatus.Error("Error text"),
    onSelectionChange = {},
)
```
if you want to use select component and a custom container instead of dropdown list for options, then you can use another api without options.

```Kotlin
import net.skyscanner.backpack.compose.fieldset.BpkFieldStatus
import net.skyscanner.backpack.compose.select.BpkSelect

BpkSelect(
    modifier = modifier.widthIn(min = BpkSpacing.Xxl.times(5)),
    text = "Menu item",
    placeholder = "Select",
    status = BpkFieldStatus.Default,
    onClick = {}
)
```
