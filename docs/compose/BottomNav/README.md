# Bottom Nav

[![Maven Central](https://img.shields.io/maven-central/v/net.skyscanner.backpack/backpack-compose)](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-compose)
[![Class reference](https://img.shields.io/badge/Class%20reference-Android-blue)](https://backpack.github.io/android/backpack-compose/net.skyscanner.backpack.compose.bottomnav)
[![Source code](https://img.shields.io/badge/Source%20code-GitHub-lightgrey)](https://github.com/Skyscanner/backpack-android/tree/main/backpack-compose/src/main/kotlin/net/skyscanner/backpack/compose/bottomnav)

## Default

| Day                                                                                                                                                                   | Night                                                                                                                                                                                |
|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/BottomNav/screenshots/default.png" alt="BottomNav component" width="375" /> | <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/BottomNav/screenshots/default_dm.png" alt="BottomNav component - dark mode" width="375" /> |

## Installation

Backpack Compose is available through [Maven Central](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-compose). Check the main [Readme](https://github.com/skyscanner/backpack-android#installation) for a complete installation guide.

## Usage

Example of a `BpkBottomNav`:

```Kotlin
import net.skyscanner.backpack.compose.bottomnav.BpkBottomNav

BpkBottomNav(
  items = listOf(
    BpkBottomNavItem(
      painter = painterResource(id = R.drawable.sample_icon),
      title = "Explore",
      id = 1,
    ),
    BpkBottomNavItem(
      icon = BpkIcon.Trips,
      title = "Trips",
      id = 2,
    ),
    BpkBottomNavItem(
      icon = BpkIcon.AccountCircle,
      title = "Account",
      id = 3,
      showBadge = true,
    ),
  ),
  selectedItemId = 1,
  onTabClicked = {}, // Handle update
)
```
