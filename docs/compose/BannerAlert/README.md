# BannerAlert

[![Maven Central](https://img.shields.io/maven-central/v/net.skyscanner.backpack/backpack-compose)](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-compose)
[![Class reference](https://img.shields.io/badge/Class%20reference-Android-blue)](https://backpack.github.io/android/backpack-compose/net.skyscanner.backpack.compose.banneralert)
[![Source code](https://img.shields.io/badge/Source%20code-GitHub-lightgrey)](https://github.com/Skyscanner/backpack-android/tree/main/backpack-compose/src/main/kotlin/net/skyscanner/backpack/compose/banneralert)

## Default

| Day                                                                                                                                                                        | Night                                                                                                                                                                         |
|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/BannerAlert/screenshots/default.png" alt="ComponentName component" width="375"/> | <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/BannerAlert/screenshots/default_dm.png" alt="ComponentName component" width="375"/> |


## OnContrast

| Day                                                                                                                                                                           | Night                                                                                                                                                                            |
|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/BannerAlert/screenshots/oncontrast.png" alt="ComponentName component" width="375"/> | <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/BannerAlert/screenshots/oncontrast_dm.png" alt="ComponentName component" width="375"/> |

## Installation

Backpack Compose is available
through [Maven Central](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-compose). Check the
main [Readme](https://github.com/skyscanner/backpack-android#installation) for a complete installation guide.

## Usage

Example of a success BannerAlert:

```Kotlin
import net.skyscanner.backpack.compose.banneralert.BPKBannerAlert

BPKBannerAlert(
    type = BPKBannerAlertType.Success,
    message = "Hello world!"
)


```

Available types are

```kotlin
enum class BpkBannerAlertType {
    Info,
    Success,
    Warning,
    Error,
}
```

Example of a BannerAlert with style OnContrast:

```Kotlin
import net.skyscanner.backpack.compose.banneralert.BPKBannerAlert
import net.skyscanner.backpack.compose.banneralert.BPKBannerAlertStyle

BPKBannerAlert(
    type = BPKBannerAlertType.Success,
    message = "Hello world!",
    style = BPKBannerAlertStyle.OnContrast
)
```
