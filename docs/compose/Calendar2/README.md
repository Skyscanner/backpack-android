# Calendar

[![Maven Central](https://img.shields.io/maven-central/v/net.skyscanner.backpack/backpack-compose)](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-compose)
[![Class reference](https://img.shields.io/badge/Class%20reference-Android-blue)](https://backpack.github.io/android/backpack-compose/net.skyscanner.backpack.compose.calendar)
[![Source code](https://img.shields.io/badge/Source%20code-GitHub-lightgrey)](https://github.com/Skyscanner/backpack-android/tree/main/backpack-compose/src/main/kotlin/net/skyscanner/backpack/compose/calendar)

## Labeled

| Day | Night |
| --- | --- |
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/Calendar2/screenshots/day-labels.png" alt="Labeled Calendar2 component" width="375" /> | <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/Calendar2/screenshots/day-labels_dm.png" alt="Labeled Calendar2 component - dark mode" width="375" /> |

## Highlighted Dates

| Day                                                                                                                                                                                                | Night                                                                                                                                                                                                             |
|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/Calendar2/screenshots/highlighted-dates.png" alt="Highlighted dates Calendar2 component" width="375" /> | <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/Calendar2/screenshots/highlighted-dates_dm.png" alt="Highlighted dates Calendar2 component - dark mode" width="375" /> |

## Icon as label

| Day                                                                                                                                                                                                  | Night                                                                                                                                                                                                            |
|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/Calendar2/screenshots/day-icon-as-labels.png" alt="Day icon as labeled Calendar2 component" width="375" /> | <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/Calendar2/screenshots/day-icon-as-labels_dm.png" alt="Day icon labeled Calendar2 component - dark mode" width="375" /> |


## Month

| Day | Night |
| --- | --- |
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/Calendar2/screenshots/selection-whole-month.png" alt="Month Calendar2 component" width="375" /> |<img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/Calendar2/screenshots/selection-whole-month_dm.png" alt="Month Calendar2 component - dark mode" width="375" /> |

## Range

| Day | Night |
| --- | --- |
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/Calendar2/screenshots/pre-selected-range.png" alt="Range Calendar2 component" width="375" /> | <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/Calendar2/screenshots/pre-selected-range_dm.png" alt="Range Calendar2 component - dark mode" width="375" /> |

## Year in Month Label, no floating year

| Day                                                                                                                                                                                                                            | Night                                                                                                                                                                                                                                         |
|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/Calendar2/screenshots/year-in-month-label-no-floating-year.png" alt="Calendar2 component showing year in month label" width="375" /> | <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/Calendar2/screenshots/year-in-month-label-no-floating-year_dm.png" alt="Calendar2 component showing year in month label - dark mode" width="375" /> |

## Loading

| Day                                                                                                                                                                                         | Night                                                                                                                                                                                                       |
|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/Calendar2/screenshots/loading.png" alt="Calendar2 component showing loading label" width="375" /> | <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/Calendar2/screenshots/loading_dm.png" alt="Calendar2 component showing loading labels - dark mode" width="375" /> |

## Installation

Backpack Android is available through [Maven Central](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-compose). Check the main [Readme](https://github.com/skyscanner/backpack-android#installation) for a complete installation guide.

## Usage

`BpkCalendar` is designed for **Jetpack Compose** and manages its state internally without requiring external reactive dependencies.
The API is optimized for **Kotlin** and may not be fully interoperable with Java.

### **State Management**
- `BpkCalendarController` maintains the **calendar parameters** (`params`) and **selected dates** (`selection`).
- State updates happen **synchronously** without background processing.
- Selection changes trigger a **callback function** to notify updates.

### **Coroutines Requirement**
`BpkCalendarController` provides scrolling functions that are `suspend` functions.
To use them, ensure that [kotlinx-coroutines](https://github.com/Kotlin/kotlinx.coroutines) is included in your project.

Example of a declaration in Compose

```Kotlin
import net.skyscanner.backpack.calendar2.CalendarParams
import net.skyscanner.backpack.compose.calendar.BpkCalendar
import net.skyscanner.backpack.compose.calendar.BpkCalendarController
import net.skyscanner.backpack.compose.calendar.rememberCalendarController

val controller = rememberCalendarController(
  initialParams = CalendarParams(
    range = LocalDate.of(2019, 1, 2)..LocalDate.of(2019, 12, 31), // start and end dates in the range
    selectionMode = CalendarParams.SelectionMode.Single, // selection mode - can be Single, Dates, Months or Disabled,
    onSelectionChanged = { selection -> // callback for selection change, you can react to the selection here
        when (selection) {
            is CalendarSelection.None -> {}
            is CalendarSelection.Single -> {}
            is CalendarSelection.Dates -> {}
            is CalendarSelection.Month -> {}
        }

    }
  )
)

BpkCalendar(controller)
```


### (Optional) Manual Selection Handling

To achieve selection behaviour that differs from the default selection, you can provide the optional
`customDateHandling` property.
This property is a lambda that will be called when a date is selected by the user.
You can use it to handle the selection manually.
You can then set the selection in the controller using the `setSelection` method.

### (Optional) Handling Calendar Scroll Updates

To receive notifications when the calendar is scrolled, you can utilize the `onScrollToMonth` parameter.
This callback is triggered whenever the `lazyGridState.firstVisibleItemIndex` property of the `BpkCalendarController` is
updated.

For example you can show the furthest scrolled month's number (i.e. January = 1, February = 2, etc.) in a `BpkText` label:

```Kotlin
Column {
    var month: YearMonth by remember { mutableStateOf(YearMonth.now()) }
    BpkText("Month: ${month.monthValue}")
    BpkCalendar(
        controller = controller,
        onScrollToMonth = {
            month = it
        }
    )
}
```

### Advanced Dates Customisation

You can attach some information to each date displayed in the calendar.
This information will update its appearance and behaviour.

In order to do this, specify `cellInfo` parameters like shown here:

```Kotlin
controller.setParams(
  CalendarParams(
    range = range,
    selectionMode = selectionMode,
    cellsInfo = mapOf(
      LocalDate.of(2019, 1, 2) to CellInfo(
        disabled = true, // marks date as disabled
        status = CellStatus.Positive, // adds green colour to cell, you can use Neutral, Negative, Empty and null as well
        label = CellLabel.Text("£30"), // adds label below the date
        highlighted = true, // marks date as highlighted
      ),
    )
  )
)
```

You can also use icons as the cell info

```Kotlin
controller.setParams(
  CalendarParams(
    range = range,
    selectionMode = selectionMode,
    cellsInfo = mapOf(
      LocalDate.of(2019, 1, 2) to CellInfo(
        disabled = true, // marks date as disabled
        status = CellStatus.Positive, // adds green colour to cell, you can use Neutral, Negative, Empty and null as well
        label = CellLabel.Icon(resId = R.drawable.bpk_search_sm, tint = R.color.bpkCoreAccent), // adds icon below the date
      ),
    )
  )
)
```

You can also show a loading state

```Kotlin
controller.setParams(
    CalendarParams(
        range = range,
        selectionMode = selectionMode,
        cellsInfo = mapOf(
            LocalDate.of(2019, 1, 2) to CellInfo(
                label = CellLabel.Loading(contentDescription = "Loading"), // adds loading indicator below the date
            ),
        )
    )
)
```
