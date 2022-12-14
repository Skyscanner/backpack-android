# Page Indicator

[![Maven Central](https://img.shields.io/maven-central/v/net.skyscanner.backpack/backpack-compose)](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-compose)
[![Class reference](https://img.shields.io/badge/Class%20reference-Android-blue)](https://backpack.github.io/android/backpack-compose/net.skyscanner.backpack.compose.pageindicator)
[![Source code](https://img.shields.io/badge/Source%20code-GitHub-lightgrey)](https://github.com/Skyscanner/backpack-android/tree/main/backpack-compose/src/main/kotlin/net/skyscanner/backpack/compose/pageindicator)

## Default

| Day                                                                                                                                                                       | Night                                                                                                                                                                                    |
|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/PageIndicator/screenshots/all.png" alt="PageIndicator component" width="375" /> | <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/PageIndicator/screenshots/all_dm.png" alt="PageIndicator component - dark mode" width="375" /> |

## Installation

Backpack Compose is available through [Maven Central](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-compose). Check the main [Readme](https://github.com/skyscanner/backpack-android#installation) for a complete installation guide.

## Usage

Example of a Page Indicator:

```Kotlin
import androidx.compose.runtime.rememberCoroutineScope
import net.skyscanner.backpack.compose.button.BpkButton
import net.skyscanner.backpack.compose.pageindicator.BpkPageIndicator
import net.skyscanner.backpack.compose.pageindicator.BpkPageIndicatorStyle
import net.skyscanner.backpack.compose.pageindicator.rememberBpkPageIndicatorState

val coroutineScope = rememberCoroutineScope()
val state = rememberBpkPageIndicatorState()
BpkButton(text = "Prev") {
  coroutineScope.launch {
    state.scrollToPage(state.currentPage - 1)
  }
}
BpkPageIndicator(
  state = state,
  totalIndicators = 7,
  style = BpkPageIndicatorStyle.Default,
  indicatorLabel = "content description",
)
BpkButton(text = "Next") {
  coroutineScope.launch {
    state.scrollToPage(state.currentPage + 1)
  }
}
```
