# PriceRange

[![Maven Central](https://img.shields.io/maven-central/v/net.skyscanner.backpack/backpack-compose)](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-compose)
[![Class reference](https://img.shields.io/badge/Class%20reference-Android-blue)](https://backpack.github.io/android/backpack-compose/net.skyscanner.backpack.compose.pricerange)
[![Source code](https://img.shields.io/badge/Source%20code-GitHub-lightgrey)](https://github.com/Skyscanner/backpack-android/tree/main/backpack-compose/src/main/kotlin/net/skyscanner/backpack/compose/pricerange)

## Default

| Day                                                                                                                                                                 | Night                                                                                                                                                                              |
|---------------------------------------------------------------------------------------------------------------------------------------------------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/PriceRange/screenshots/all.png" alt="PriceRange component" width="375" /> | <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/PriceRange/screenshots/all_dm.png" alt="PriceRange component - dark mode" width="375" /> |

## Installation

Backpack Compose is available through [Maven Central](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-compose). Check the main [Readme](https://github.com/skyscanner/backpack-android#installation) for a complete installation guide.

## Usage

Example of a PriceRange:

```Kotlin
import net.skyscanner.backpack.compose.pricerange.BpkPriceRange
import net.skyscanner.backpack.compose.pricerange.BpkPriceRangeConfiguration
import net.skyscanner.backpack.compose.pricerange.BpkPriceRangeMarker
import net.skyscanner.backpack.compose.pricerange.BpkPriceRangeSegmentsLabeled

BpkPriceRange(
    configuration = BpkPriceRangeConfiguration.Default(
        marker = BpkPriceRangeMarker.Label(
            text = "£850",
            percentage = 85f,
        ),
        segments = BpkPriceRangeSegmentsLabeled(
            lower = BpkPriceRangeSegmentsLabeled.LabeledPoint(
                text = "£200",
                percentage = 20f,
            ),
            upper = BpkPriceRangeSegmentsLabeled.LabeledPoint(
                text = "£800",
                percentage = 80f,
            ),
        ),
    ),
)
```


Example of a PriceRange (Collapsed):

```Kotlin
import net.skyscanner.backpack.compose.pricerange.BpkPriceRange
import net.skyscanner.backpack.compose.pricerange.BpkPriceRangeConfiguration
import net.skyscanner.backpack.compose.pricerange.BpkPriceRangeSegmentsPlain

BpkPriceRange(
    configuration = BpkPriceRangeConfiguration.Collapsed(
        markerPercentage = 50f,
        segments = BpkPriceRangeSegmentsPlain(
            lower = 20f,
            upper = 80f,
        ),
    ),
)
```

Example with a fixed width:

```Kotlin
import androidx.compose.ui.unit.dp
import net.skyscanner.backpack.compose.pricerange.BpkPriceRange
import net.skyscanner.backpack.compose.pricerange.BpkPriceRangeConfiguration
import net.skyscanner.backpack.compose.pricerange.BpkPriceRangeMarker
import net.skyscanner.backpack.compose.pricerange.BpkPriceRangeSegmentsLabeled

BpkPriceRange(
    configuration = BpkPriceRangeConfiguration.Default(
        marker = BpkPriceRangeMarker.Label("£500", 50f),
        segments = BpkPriceRangeSegmentsLabeled(
            lower = BpkPriceRangeSegmentsLabeled.LabeledPoint("£200", 20f),
            upper = BpkPriceRangeSegmentsLabeled.LabeledPoint("£800", 80f),
        ),
    ),
    cardWidth = 240.dp,
)
```

