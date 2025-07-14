# Map

[![Maven Central](https://img.shields.io/maven-central/v/net.skyscanner.backpack/backpack-compose)](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-compose)
[![Class reference](https://img.shields.io/badge/Class%20reference-Android-blue)](https://backpack.github.io/android/backpack-compose/net.skyscanner.backpack.compose.map)
[![Source code](https://img.shields.io/badge/Source%20code-GitHub-lightgrey)](https://github.com/Skyscanner/backpack-android/tree/main/backpack-compose/src/main/kotlin/net/skyscanner/backpack/compose/map)

## Price Marker

| Day                                                                                                                                                       | Night                                                                                                                                                                    |
|-----------------------------------------------------------------------------------------------------------------------------------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/Maps/screenshots/price.png" alt="Maps component" width="375" /> | <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/Maps/screenshots/price_dm.png" alt="Maps component - dark mode" width="375" /> |

## Location Marker

| Day                                                                                                                                                          | Night                                                                                                                                                                       |
|--------------------------------------------------------------------------------------------------------------------------------------------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/Maps/screenshots/location.png" alt="Maps component" width="375" /> | <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/Maps/screenshots/location_dm.png" alt="Maps component - dark mode" width="375" /> |

## Poi Marker

| Day                                                                                                                                                     | Night                                                                                                                                                                  |
|---------------------------------------------------------------------------------------------------------------------------------------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/Maps/screenshots/poi.png" alt="Maps component" width="375" /> | <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/Maps/screenshots/poi_dm.png" alt="Maps component - dark mode" width="375" /> |

## Installation

Backpack Compose is available through [Maven Central](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-compose). Check the main [Readme](https://github.com/skyscanner/backpack-android#installation) for a complete installation guide.

## Usage

Example of an price marker:

```Kotlin
import net.skyscanner.backpack.compose.map.BpkPriceMapMarker
import net.skyscanner.backpack.compose.map.BpkPriceMarkerStatus

BpkPriceMapMarker(
    title = "£198",
    status = BpkPriceMarkerStatus.Selected
)
```

Example of an price marker with prefix icon:

```Kotlin
import net.skyscanner.backpack.compose.map.BpkPriceMapMarker
import net.skyscanner.backpack.compose.map.BpkPriceMarkerStatus

BpkPriceMapMarker(
    title = "£198",
    status = BpkPriceMarkerStatus.Selected,
    prefixIcon = BpkIcon.Heart,
)
```

Example of a location marker:

```Kotlin
import net.skyscanner.backpack.compose.map.BpkLocationMapMarker

BpkLocationMapMarker(
    title = "Location",
)
```

Example of a poi marker:

```Kotlin
import net.skyscanner.backpack.compose.map.BpkPoiMapMarker

BpkPoiMapMarker(
    title = "Poi",
)
```
