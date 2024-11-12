# SegmentedControl

[![Maven Central](https://img.shields.io/maven-central/v/net.skyscanner.backpack/backpack-compose)](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-compose)
[![Class reference](https://img.shields.io/badge/Class%20reference-Android-blue)](https://backpack.github.io/android/backpack-compose/net.skyscanner.backpack.compose.segmentedcontrol)
[![Source code](https://img.shields.io/badge/Source%20code-GitHub-lightgrey)](https://github.com/Skyscanner/backpack-android/tree/main/backpack-compose/src/main/kotlin/net/skyscanner/backpack/compose/segmentedcontrol)

## Canvas Default

| Day                                                                                                                                                                                                                               | Night                                                                                                                                                                                                      |
|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/SegmentedControl/screenshots/canvas-default.png" alt="SegmentedControl BpkSegmentedControlStyle.CanvasDefault component" width="375" /> | <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/SegmentedControl/screenshots/canvas-default_dm.png" alt="SegmentedControl BpkSegmentedControlStyle.CanvasDefault component - dark mode" width="375" /> |

## Canvas Contrast
| Day                                                                                                                                                                                                                               | Night                                                                                                                                                                                                      |
|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/SegmentedControl/screenshots/canvas-contrast.png" alt="SegmentedControl BpkSegmentedControlStyle.CanvasContrast component" width="375" /> | <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/SegmentedControl/screenshots/canvas-contrast_dm.png" alt="SegmentedControl BpkSegmentedControlStyle.CanvasContrast component - dark mode" width="375" /> |

## Surface Default
| Day                                                                                                                                                                                                                               | Night                                                                                                                                                                                                      |
|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/SegmentedControl/screenshots/surface-default.png" alt="SegmentedControl BpkSegmentedControlStyle.SurfaceDefault component" width="375" /> | <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/SegmentedControl/screenshots/surface-default_dm.png" alt="SegmentedControl BpkSegmentedControlStyle.SurfaceDefault component - dark mode" width="375" /> |

## Surface Contrast
| Day                                                                                                                                                                                                                               | Night                                                                                                                                                                                                      |
|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/SegmentedControl/screenshots/surface-contrast.png" alt="SegmentedControl BpkSegmentedControlStyle.SurfaceContrast component" width="375" /> | <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/SegmentedControl/screenshots/surface-contrast_dm.png" alt="SegmentedControl BpkSegmentedControlStyle.SurfaceContrast component - dark mode" width="375" /> |

## Installation

Backpack Compose is available through [Maven Central](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-compose). Check the main [Readme](https://github.com/skyscanner/backpack-android#installation) for a complete installation guide.

## Usage

Example of a SegmentedControl:

```Kotlin
import net.skyscanner.backpack.compose.segmentedcontrol.BpkSegmentedControl

var selectedIndex by remember { mutableIntStateOf(selectedInt) }
val buttonContents = List(numberOfButtons) { content }

BpkSegmentedControl(
    modifier = modifier,
    type = type,
    shadow = shadow,
    buttonContents = buttonContents,
    onItemClick = { selectedIndex = it },
    selectedInt = selectedIndex
)
```
