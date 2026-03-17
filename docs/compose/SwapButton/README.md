# SwapButton

[![Maven Central](https://img.shields.io/maven-central/v/net.skyscanner.backpack/backpack-compose)](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-compose)
[![Class reference](https://img.shields.io/badge/Class%20reference-Android-blue)](https://backpack.github.io/android/backpack-compose/net.skyscanner.backpack.compose.swapbutton)
[![Source code](https://img.shields.io/badge/Source%20code-GitHub-lightgrey)](https://github.com/Skyscanner/backpack-android/tree/main/backpack-compose/src/main/kotlin/net/skyscanner/backpack/compose/swapbutton)

## Default

| Day | Night |
| --- | --- |
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/SwapButton/screenshots/default.png" alt="SwapButton component" width="375" /> | <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/SwapButton/screenshots/default_dm.png" alt="SwapButton component - dark mode" width="375" /> |

## Installation

Backpack Compose is available through [Maven Central](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-compose). Check the main [Readme](https://github.com/skyscanner/backpack-android#installation) for a complete installation guide.

## Usage

Example of a SwapButton:

```Kotlin
import net.skyscanner.backpack.compose.swapbutton.BpkSwapButton

BpkSwapButton(
    onClick = { /* handle swap */ },
    contentDescription = "Swap origin and destination",
)
```

Example of a SwapButton with CanvasContrast style:

```Kotlin
import net.skyscanner.backpack.compose.swapbutton.BpkSwapButton
import net.skyscanner.backpack.compose.swapbutton.BpkSwapButtonStyle

BpkSwapButton(
    onClick = { /* handle swap */ },
    contentDescription = "Swap origin and destination",
    style = BpkSwapButtonStyle.CanvasContrast,
)
```

Example of a SwapButton with SurfaceContrast style:

```Kotlin
import net.skyscanner.backpack.compose.swapbutton.BpkSwapButton
import net.skyscanner.backpack.compose.swapbutton.BpkSwapButtonStyle

BpkSwapButton(
    onClick = { /* handle swap */ },
    contentDescription = "Swap origin and destination",
    style = BpkSwapButtonStyle.SurfaceContrast,
)
```
