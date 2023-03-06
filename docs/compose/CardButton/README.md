# Card Button

[![Maven Central](https://img.shields.io/maven-central/v/net.skyscanner.backpack/backpack-compose)](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-compose)
[![Class reference](https://img.shields.io/badge/Class%20reference-Android-blue)](https://backpack.github.io/android/backpack-compose/net.skyscanner.backpack.compose.cardbutton)
[![Source code](https://img.shields.io/badge/Source%20code-GitHub-lightgrey)](https://github.com/Skyscanner/backpack-android/tree/main/backpack-compose/src/main/kotlin/net/skyscanner/backpack/compose/cardbutton)

## Default

| Day                                                                                                                                                                      | Night                                                                                                                                                                                   |
|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/CardButton/screenshots/default.png" alt="Card Button component" width="375" /> | <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/CardButton/screenshots/default_dm.png" alt="Card Button component - dark mode" width="375" /> |

## Large

| Day                                                                                                                                                                          | Night                                                                                                                                                                                       |
|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/CardButton/screenshots/large.png" alt="Large Card Button component" width="375" /> | <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/CardButton/screenshots/large_dm.png" alt="Large Card Button component - dark mode" width="375" /> |

## Installation

Backpack Compose is available through [Maven Central](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-compose). Check the main [Readme](https://github.com/skyscanner/backpack-android#installation) for a complete installation guide.

## Usage

Example of a Save Button on light background:

```Kotlin
import net.skyscanner.backpack.compose.cardbutton.BpkSaveButton

var checked by remember { mutableStateOf(false) }
BpkSaveButton(
  checked = checked,
  contentDescription = "",
  onCheckedChange = { checked = it }
)
```

Example of a Share Button on an image:

```Kotlin
import net.skyscanner.backpack.compose.cardbutton.BpkShareButton
import net.skyscanner.backpack.compose.cardbutton.BpkCardButtonSize
import net.skyscanner.backpack.compose.cardbutton.BpkCardButtonStyle

BpkShareButton(
  size = BpkCardButtonSize.Default,
  style = BpkCardButtonStyle.Contained,
  contentDescription = "",
  onClick = {}
)
```
