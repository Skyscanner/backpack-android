# NavigationTabGroup

[![Maven Central](https://img.shields.io/maven-central/v/net.skyscanner.backpack/backpack-compose)](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-compose)
[![Class reference](https://img.shields.io/badge/Class%20reference-Android-blue)](https://backpack.github.io/android/backpack-compose/net.skyscanner.backpack.compose.navigationtabgroup)
[![Source code](https://img.shields.io/badge/Source%20code-GitHub-lightgrey)](https://github.com/Skyscanner/backpack-android/tree/main/backpack-compose/src/main/kotlin/net/skyscanner/backpack/compose/navigationtabgroup)

## Default

| Day | Night |
| --- | --- |
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/NavigationTabGroup/screenshots/default.png" alt="NavigationTabGroup component" width="375" /> | <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/NavigationTabGroup/screenshots/default_dm.png" alt="NavigationTabGroup component - dark mode" width="375" /> |

## Installation

Backpack Compose is available through [Maven Central](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-compose). Check the main [Readme](https://github.com/skyscanner/backpack-android#installation) for a complete installation guide.

## Usage

Example of a NavigationTabGroup:

```Kotlin
import net.skyscanner.backpack.compose.navigationtabgroup.BpkNavigationTabGroup
import net.skyscanner.backpack.compose.navigationtabgroup.BpkNavigationTabItem

val tabs = listOf(
    BpkNavigationTabItem("Flights", BpkIcon.Flight),
    BpkNavigationTabItem("Hotels", BpkIcon.Hotels),
    BpkNavigationTabItem("Cars", BpkIcon.Cars),
)
var selectedIndex by remember { mutableStateOf(0) }

BpkNavigationTabGroup(
    tabs = tabs,
    selectedIndex = selectedIndex,
    onItemClicked = { selectedIndex = tabs.indexOf(it) },
    style = BpkNavigationTabGroupStyle.CanvasDefault,
)
```
