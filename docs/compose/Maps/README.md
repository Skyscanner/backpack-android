# Map

[![Maven Central](https://img.shields.io/maven-central/v/net.skyscanner.backpack/backpack-compose)](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-compose)
[![Class reference](https://img.shields.io/badge/Class%20reference-Android-blue)](https://backpack.github.io/android/backpack-compose/net.skyscanner.backpack.compose.map)
[![Source code](https://img.shields.io/badge/Source%20code-GitHub-lightgrey)](https://github.com/Skyscanner/backpack-android/tree/main/backpack-compose/src/main/kotlin/net/skyscanner/backpack/compose/map)

## Price Marker

| Day                                                                                                                                                       | Night                                                                                                                                                                      |
|-----------------------------------------------------------------------------------------------------------------------------------------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/Maps/screenshots/price.png" alt="Maps component" width="375" /> | <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/Maps/screenshots/price_dm.png" alt="Maps component - dark mode" width="375" /> |

## Icon Marker

| Day                                                                                                                                                         | Night                                                                                                                                                                   |
|-------------------------------------------------------------------------------------------------------------------------------------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/Maps/screenshots/icon.png" alt="Maps component" width="375" /> | <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/Maps/screenshots/icon_dm.png" alt="Maps component - dark mode" width="375" /> |

## Pointer Marker

| Day                                                                                                                                                         | Night                                                                                                                                                                      |
|-------------------------------------------------------------------------------------------------------------------------------------------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/Maps/screenshots/pointer.png" alt="Maps component" width="375" /> | <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/Maps/screenshots/pointer_dm.png" alt="Maps component - dark mode" width="375" /> |

## Installation

Backpack Compose is available through [Maven Central](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-compose). Check the main [Readme](https://github.com/skyscanner/backpack-android#installation) for a complete installation guide.

## Usage

Example of an price marker:

```Kotlin
import net.skyscanner.backpack.compose.map.BpkPriceMapMarker
import net.skyscanner.backpack.compose.map.BpkPriceMarkerStatus

BpkPriceMapMarker(
    title = "£198",
    status = BpkPriceMarkerStatus.Focused,
)
```

Example of an icon marker:

```Kotlin
import net.skyscanner.backpack.compose.map.BpkIconMapMarker
import net.skyscanner.backpack.compose.map.BpkIconMarkerStatus

BpkIconMapMarker(
    contentDescription = "Landmark",
    icon = BpkIcon.Landmark,
    status = BpkIconMarkerStatus.Disabled
)
```

Example of a pointer marker:

```Kotlin
import net.skyscanner.backpack.compose.map.BpkPointerMapMarker

BpkPointerMapMarker(
    title = "Pointer",
)
```
