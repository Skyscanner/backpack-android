# Calendar

[![Maven Central](https://img.shields.io/maven-central/v/net.skyscanner.backpack/backpack-android)](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-android)
[![Class reference](https://img.shields.io/badge/Class%20reference-Android-blue)](https://backpack.github.io/android/Backpack/net.skyscanner.backpack.calendar2)
[![Source code](https://img.shields.io/badge/Source%20code-GitHub-lightgrey)](https://github.com/Skyscanner/backpack-android/tree/main/Backpack/src/main/java/net/skyscanner/backpack/calendar2)

## Labeled

| Day | Night |
| --- | --- |
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/view/Calendar2/screenshots/day-labels.png" alt="Labeled Calendar2 component" width="375" /> | <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/view/Calendar2/screenshots/day-labels_dm.png" alt="Labeled Calendar2 component - dark mode" width="375" /> |

## Icon as labeled

| Day                                                                                                                                                                                                  | Night                                                                                                                                                                                                            |
|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/Calendar2/screenshots/day-icon-as-labels.png" alt="Day icon as labeled Calendar2 component" width="375" /> | <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/Calendar2/screenshots/day-icon-as-labels_dm.png" alt="Day icon labeled Calendar2 component - dark mode" width="375" /> |


## Month

| Day | Night |
| --- | --- |
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/view/Calendar2/screenshots/selection-whole-month.png" alt="Month Calendar2 component" width="375" /> | <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/view/Calendar2/screenshots/selection-whole-month_dm.png" alt="Month Calendar2 component - dark mode" width="375" /> |

## Range

| Day | Night |
| --- | --- |
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/view/Calendar2/screenshots/pre-selected-range.png" alt="Range Calendar2 component" width="375" /> | <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/view/Calendar2/screenshots/pre-selected-range_dm.png" alt="Range Calendar2 component - dark mode" width="375" /> |

## Installation

Backpack Android is available through [Maven Central](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-android). Check the main [Readme](https://github.com/skyscanner/backpack-android#installation) for a complete installation guide.

## Usage

BpkCalendar uses coroutines under the hood.
Since it designed with coroutines, the API is designed primarily for Kotlin language and may not be interoperable with Java.
To use the API, make sure [kotlinx-coroutines](https://github.com/Kotlin/kotlinx.coroutines) is included to your project.

BpkCalendar is designed as a state machine.
All the information it contains is available as a state in the [StateFlow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-state-flow/).
Each method of the public API and some of the user interactions will change its state and emit it.

The Calendar component can be used in both XML and Kotlin, but requires the basic setup to be done in Kotlin.
Both single and range selection are supported.

Example of a calendar declaration in XML

> **_NOTE:_**  Note that the package name of new Calendar has been changed to `net.skyscanner.backpack.calendar2`.

```xml
<net.skyscanner.backpack.calendar2.BpkCalendar
    android:id="@+id/bpkCalendar"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:nestedScrollingEnabled="true"/>
```

Example of a declaration in Kotlin

```Kotlin
import net.skyscanner.backpack.calendar2.BpkCalendar

BpkCalendar(context)
```

Once you've declared the calendar, you need to setup it like following:

```Kotlin
calendar.setParams(
  CalendarParams(
    range = LocalDate.of(2019, 1, 2)..LocalDate.of(2019, 12, 31), // start and end dates in the range
    selectionMode = CalendarParams.SelectionMode.Single, // selection mode - can be Single, Dates, Months or Disabled
  )
)
```

Now the component is ready. You can listen for the selection change using its state:

```Kotlin
calendar
  .state
  .map { it.selection }
  .onEach { selection ->
    when (selection) {
      is CalendarSelection.None -> showToast("No date is selected")
      is CalendarSelection.Single -> showToast("${selection.date} is selected")
      is CalendarSelection.Dates -> showToast("${selection.start} to {${selection.end} is selected")
      is CalendarSelection.Month -> showToast("${selection.month} is selected")
    }
  }
  .launchIn(myCoroutineScope)
```

### Advanced dates customisation

You can attach some of the information to each date displayed in calendar.
This information will update its appearance and behaviour.

In order to do this, specify `cellInfo` parameters like shown here:

```Kotlin
calendar.setParams(
  CalendarParams(
    range = range,
    selectionMode = selectionMode,
    cellsInfo = mapOf(
      LocalDate.of(2019, 1, 2) to CellInfo(
        disabled = true, // marks date as disabled
        status = CellStatus.Positive, // adds green colour to cell, you can use Neutral, Negative, Empty and null as well
        label = CellLabel.Text("£30"), // adds label below the date
      ),
    )
  )
)
```

You can also use icons as the cell info:

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

## Theme Props

- `calendarDateSelectedBackgroundColor`
- `calendarDateSelectedRangeBackgroundColor`
- `calendarDateSelectedTextColor`
- `calendarDateSelectedSameDayBackgroundColor`

Styles can be changed globally through `bpkCalendarStyle`. Check [theming](https://github.com/Skyscanner/backpack-android/blob/main/docs/view/THEMING.md) for more information.
