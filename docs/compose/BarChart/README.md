# BarChart

[![Maven Central](https://img.shields.io/maven-central/v/net.skyscanner.backpack/backpack-compose)](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-compose)
[![Class reference](https://img.shields.io/badge/Class%20reference-Android-blue)](https://backpack.github.io/android/backpack-compose/net.skyscanner.backpack.compose.barchart)
[![Source code](https://img.shields.io/badge/Source%20code-GitHub-lightgrey)](https://github.com/Skyscanner/backpack-android/tree/main/backpack-compose/src/main/kotlin/net/skyscanner/backpack/compose/barchart)

## Default

| Day                                                                                                                                                          | Night |
|--------------------------------------------------------------------------------------------------------------------------------------------------------------| --- |
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/BarChart/screenshots/all.png" alt="BarChart component" width="375" /> |<img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/BarChart/screenshots/all_dm.png" alt="BarChart component - dark mode" width="375" /> |

## Installation

Backpack Compose is available
through [Maven Central](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-compose). Check the
main [Readme](https://github.com/skyscanner/backpack-android#installation) for a complete installation guide.

## Usage

Example of a BarChart:

```Kotlin
import net.skyscanner.backpack.compose.barchart.BpkBarChart
import net.skyscanner.backpack.compose.barchart.BpkBarChartModel

val model = createBarChartModel() // see docs below

var selectedItem by remember { mutableStateOf<BpkBarChartModel.Item?>(null) }

BpkBarChart(
  model = model,
  selected = selectedItem,
  onSelectionChange = { selectedItem = it },
)
```

To render data you need to create a model for `BarChart` in code.
Detailed documentation on each property of the model is
available [here](https://backpack.github.io/android/Backpack/net.skyscanner.backpack.compose.barchart).

Here's an example which generates a model for one single month in Kotlin:

```kotlin
import net.skyscanner.backpack.compose.barchart.BpkBarChartModel
import org.threeten.bp.LocalDate
import org.threeten.bp.YearMonth
import org.threeten.bp.format.TextStyle
import java.util.Locale

fun createBarChartModel(locale: Locale, yearMonth: YearMonth) =
  BpkBarChartModel(
    caption = "Bar chart caption",
    legend = BpkBarChartModel.Legend(
      selectedTitle = "Selected",
      inactiveTitle = "No price",
      activeTitle = "Price",
    ),
    items = List(yearMonth.lengthOfMonth()) { day ->
      val date = LocalDate.of(yearMonth.year, yearMonth.month, day + 1)
      BpkBarChartModel.Item(
        key = date, // unique id of a specific bar – date in this case
        title = date.dayOfWeek.getDisplayName(TextStyle.SHORT, locale),
        subtitle = date.dayOfMonth.toString(),
        group = date.month.getDisplayName(TextStyle.FULL, locale), // title of the current list section
        values = BpkBarChartModel.Values(
          // null if not selectable
          text = "£42", // text to be displayed when selected,
          percent = 0.5f, // fill bar by 50%
        ),
        // provides accessibility information here to be used with screen readers
        // make sure it's formatted properly and includes all the relevant information needed (title, subtitle, text value, group)
        // the example below is just for general purposes
        contentDescription = "Monday the 7th, price is £142",
      )
    }
  )

```
