# Select

[![Maven Central](https://img.shields.io/maven-central/v/net.skyscanner.backpack/backpack-compose)](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-compose)
[![Class reference](https://img.shields.io/badge/Class%20reference-Android-blue)](https://backpack.github.io/android/backpack-compose/net.skyscanner.backpack.compose.select)
[![Source code](https://img.shields.io/badge/Source%20code-GitHub-lightgrey)](https://github.com/Skyscanner/backpack-android/tree/main/backpack-compose/src/main/kotlin/net/skyscanner/backpack/compose/select)

## Default

| Day                                                                                                                                                              | Night                                                                                                                                                                                       |
|------------------------------------------------------------------------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/Select/screenshots/default.png" alt="Select component" width="375" />  | <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/Select/screenshots/default_dm.png" alt="Select component - dark mode" width="375" />              |

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
    placeHolder = "Select",
    state = BpkSelectState.Default,
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
    placeHolder = "Select",
    state = BpkFieldStatus.Disabled,
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
    placeHolder = "Select",
    state = BpkFieldStatus.Error("Error text"),
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
    placeHolder = "Select",
    state = BpkFieldStatus.Default,
    onClick = {}
)
```
