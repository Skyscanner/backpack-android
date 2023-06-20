# ChipGroup

[![Maven Central](https://img.shields.io/maven-central/v/net.skyscanner.backpack/backpack-compose)](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-compose)
[![Class reference](https://img.shields.io/badge/Class%20reference-Android-blue)](https://backpack.github.io/android/backpack-compose/net.skyscanner.backpack.compose.chipgroup)
[![Source code](https://img.shields.io/badge/Source%20code-GitHub-lightgrey)](https://github.com/Skyscanner/backpack-android/tree/main/backpack-compose/src/main/kotlin/net/skyscanner/backpack/compose/chipgroup)

## Single Select Rail

| Day | Night |
| --- | --- |
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/ChipGroup/screenshots/single-select-rail.png" alt="SingleSelect Rail Chip Group component" width="375" /> | <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/ChipGroup/screenshots/single-select-rail_dm.png" alt="SingleSelect Rail Chip Group component - dark mode" width="375" /> |

## Single Select Wrap

| Day | Night |
| --- | --- |
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/ChipGroup/screenshots/single-select-wrap.png" alt="SingleSelect Wrap Chip Group component" width="375" /> | <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/ChipGroup/screenshots/single-select-wrap_dm.png" alt="SingleSelect Wrap Chip Group component - dark mode" width="375" /> |


## Installation

Backpack Compose is available through [Maven Central](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-compose). Check the main [Readme](https://github.com/skyscanner/backpack-android#installation) for a complete installation guide.

## Usage

Example of a Rail SingleSelectChipGroup:

```Kotlin
import net.skyscanner.backpack.compose.chipgroup.single.BpkSingleSelectChipGroup
import net.skyscanner.backpack.compose.chipgroup.single.internal.BpkSingleChipGroupType
import net.skyscanner.backpack.compose.chipgroup.single.internal.BpkSingleChipItem

var selectedIndex by remember { mutableStateOf(0) }

BpkSingleSelectChipGroup(
    chips = listOf(BpkSingleChipItem("City", BpkIcon.Deals)),
    selectedIndex = selectedIndex,
    onItemClicked = {/*on click */},
    type = BpkSingleChipGroupType.Rail,
)
```

Example of a Wrap SingleSelectChipGroup:

```Kotlin
import net.skyscanner.backpack.compose.chipgroup.single.BpkSingleSelectChipGroup
import net.skyscanner.backpack.compose.chipgroup.single.internal.BpkSingleChipGroupType
import net.skyscanner.backpack.compose.chipgroup.single.internal.BpkSingleChipItem

var selectedIndex by remember { mutableStateOf(0) }

BpkSingleSelectChipGroup(
    chips = listOf(BpkSingleChipItem("City", BpkIcon.Deals)),
    selectedIndex = selectedIndex,
    onItemClicked = {/*on click */},
    type = BpkSingleChipGroupType.Wrap,
)
```
