# Bottom Sheet

[![Maven Central](https://img.shields.io/maven-central/v/net.skyscanner.backpack/backpack-compose)](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-compose)
[![Class reference](https://img.shields.io/badge/Class%20reference-Android-blue)](https://backpack.github.io/android/backpack-compose/net.skyscanner.backpack.compose.bottomsheet)
[![Source code](https://img.shields.io/badge/Source%20code-GitHub-lightgrey)](https://github.com/Skyscanner/backpack-android/tree/main/backpack-compose/src/main/kotlin/net/skyscanner/backpack/compose/bottomsheet)

## Default

| Day                                                                                                                                                                | Night |
|--------------------------------------------------------------------------------------------------------------------------------------------------------------------| --- |
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/BottomSheet/screenshots/default.png" alt="BottomSheet component" width="375" /> |<img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/BottomSheet/screenshots/default_dm.png" alt="BottomSheet component - dark mode" width="375" /> |


## Installation

Backpack Compose is available through [Maven Central](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-compose). Check the main [Readme](https://github.com/skyscanner/backpack-android#installation) for a complete installation guide.

## Usage

Example of a Bottom Sheet:

```Kotlin
import net.skyscanner.backpack.compose.bottomsheet.BpkBottomSheet
import net.skyscanner.backpack.compose.bottomsheet.BpkBottomSheetState
import net.skyscanner.backpack.compose.bottomsheet.rememberBpkBottomSheetState

val state = rememberBpkBottomSheetState()

BpkBottomSheet(
  state = state,
  peekHeight = HeightOfCollapsedBottomSheet,
  sheetContent = {
    // content of the bottom sheet
  },
  content = { contentPadding ->
    // content displayed behind bottom sheet
    // you should apply content padding to avoid displaying content behind collapsed bottom sheet
    // here's an example:
    Box(modifier = Modifier
      .fillMaxSize()
      .background(myBackground)
      .padding(contentPadding)
    ) {
        // this content will respect the paddings
    }
  }
)
```
