# BpkSearchInputControl

[![Maven Central](https://img.shields.io/maven-central/v/net.skyscanner.backpack/backpack-compose)](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-compose)
[![Class reference](https://img.shields.io/badge/Class%20reference-Android-blue)](https://backpack.github.io/android/backpack-compose/net.skyscanner.backpack.compose.searchinputcontrol)
[![Source code](https://img.shields.io/badge/Source%20code-GitHub-lightgrey)](https://github.com/Skyscanner/backpack-android/tree/main/backpack-compose/src/main/kotlin/net/skyscanner/backpack/compose/searchinputcontrol)

## On default

| Day | Night |
|-----|-------|
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/SearchInputControl/screenshots/on-default.png" alt="BpkSearchInputControl on default background" width="375" /> | <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/SearchInputControl/screenshots/on-default_dm.png" alt="BpkSearchInputControl on default background - dark mode" width="375" /> |

## On contrast

| Day | Night |
|-----|-------|
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/SearchInputControl/screenshots/on-contrast.png" alt="BpkSearchInputControl on contrast background" width="375" /> | <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/SearchInputControl/screenshots/on-contrast_dm.png" alt="BpkSearchInputControl on contrast background - dark mode" width="375" /> |

## Installation

Backpack Compose is available
through [Maven Central](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-compose). Check the
main [Readme](https://github.com/skyscanner/backpack-android#installation) for a complete installation guide.

## Usage

`BpkSearchInputControl` is a non-editable search field with prefix support (`None`, `Text`, or `Icon`), focus state, and docking — allowing multiple controls to be stacked together with shared corner rounding. It comes in two styles: `OnDefault` (for light backgrounds) and `OnContrast` (for dark backgrounds).

Example of a basic `BpkSearchInputControl`:

```Kotlin
import net.skyscanner.backpack.compose.searchinputcontrol.BpkSearchInputControl
import net.skyscanner.backpack.compose.searchinputsummary.Prefix

BpkSearchInputControl(
    inputText = "London",
    inputHint = "Where from?",
    prefix = Prefix.Text("From"),
)
```

Example with an icon prefix, clear action, and focus state:

```Kotlin
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.searchinputcontrol.BpkSearchInputControl
import net.skyscanner.backpack.compose.searchinputsummary.Prefix
import net.skyscanner.backpack.compose.textfield.BpkClearAction
import net.skyscanner.backpack.compose.tokens.FlightTakeoff

BpkSearchInputControl(
    inputText = "London",
    inputHint = "Where from?",
    prefix = Prefix.Icon(BpkIcon.FlightTakeoff),
    isFocused = true,
    clearAction = BpkClearAction("Clear") { /* clear input */ },
)
```

Example on a contrast background:

```Kotlin
import net.skyscanner.backpack.compose.searchinputcontrol.BpkSearchInputControl
import net.skyscanner.backpack.compose.searchinputcontrol.BpkSearchInputControlStyle
import net.skyscanner.backpack.compose.searchinputsummary.Prefix

BpkSearchInputControl(
    inputText = "London",
    inputHint = "Where from?",
    prefix = Prefix.Icon(BpkIcon.FlightTakeoff),
    style = BpkSearchInputControlStyle.OnContrast,
)
```

Example of multiple `BpkSearchInputControl` components docked together:

```Kotlin
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.searchinputcontrol.BpkSearchInputControl
import net.skyscanner.backpack.compose.searchinputcontrol.Docking
import net.skyscanner.backpack.compose.searchinputsummary.Prefix
import net.skyscanner.backpack.compose.tokens.FlightLanding
import net.skyscanner.backpack.compose.tokens.FlightTakeoff

BpkSearchInputControl(
    inputText = "London",
    inputHint = "Where from?",
    prefix = Prefix.Icon(BpkIcon.FlightTakeoff),
    docking = Docking.Top,
)
BpkSearchInputControl(
    inputText = "Edinburgh",
    inputHint = "Where to?",
    prefix = Prefix.Icon(BpkIcon.FlightLanding),
    docking = Docking.Bottom,
)
```
