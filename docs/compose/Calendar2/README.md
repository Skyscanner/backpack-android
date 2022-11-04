# Calendar

[![Maven Central](https://img.shields.io/maven-central/v/net.skyscanner.backpack/backpack-compose)](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-compose)
[![Class reference](https://img.shields.io/badge/Class%20reference-Android-blue)](https://backpack.github.io/android/backpack-compose/net.skyscanner.backpack.compose.calendar)
[![Source code](https://img.shields.io/badge/Source%20code-GitHub-lightgrey)](https://github.com/Skyscanner/backpack-android/tree/main/backpack-compose/src/main/kotlin/net/skyscanner/backpack/compose/calendar)

## Coloured

| Day                                                                                                                                                                           | Night |
|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------| --- |
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/Calendar2/screenshots/colored.png" alt="Colored Calendar2 component" width="375" /> |<img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/Calendar2/screenshots/colored_dm.png" alt="Colored Calendar2 component - dark mode" width="375" /> |

## Labeled

| Day | Night |
| --- | --- |
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/Calendar2/screenshots/labeled.png" alt="Labeled Calendar2 component" width="375" /> |<img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/Calendar2/screenshots/labeled_dm.png" alt="Labeled Calendar2 component - dark mode" width="375" /> |

## Month

| Day | Night |
| --- | --- |
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/Calendar2/screenshots/month.png" alt="Month Calendar2 component" width="375" /> |<img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/Calendar2/screenshots/month_dm.png" alt="Month Calendar2 component - dark mode" width="375" /> |

## Range

| Day | Night |
| --- | --- |
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/Calendar2/screenshots/range.png" alt="Range Calendar2 component" width="375" /> |<img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/Calendar2/screenshots/range_dm.png" alt="Range Calendar2 component - dark mode" width="375" /> |

## Installation

Backpack Android is available through [Maven Central](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-compose). Check the main [Readme](https://github.com/skyscanner/backpack-android#installation) for a complete installation guide.

The calendar implementation relies on the Android adaptation of the `JSR-310` backport for dealing with dates: [`ThreeTenABP`](https://github.com/JakeWharton/ThreeTenABP)

Add the following line in your base module's Gradle script:

```groovy
implementation 'com.jakewharton.threetenabp:threetenabp:$latestVersion'
```

## Usage

Be sure to initialize the `ThreeTenABP` library according to their [usage guidelines](https://github.com/JakeWharton/ThreeTenABP/blob/master/README.md) in your `Application`'s context:

```Kotlin
override fun onCreate() {
  super.onCreate()
  AndroidThreeTen.init(this)
}
```

BpkCalendar uses coroutines under the hood.
Since it designed with coroutines, the API is designed primarily for Kotlin language and may not be interoperable with Java.
To use the API, make sure [kotlinx-coroutines](https://github.com/Kotlin/kotlinx.coroutines) is included to your project.

BpkCalendar is designed as a state machine.
All the information it contains is available as a state in the [StateFlow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-state-flow/).
Each method of the public API and some of the user interactions will change its state and emit it.

Example of a declaration in Compose

```Kotlin
import net.skyscanner.backpack.calendar2.CalendarParams
import net.skyscanner.backpack.compose.calendar.BpkCalendar
import net.skyscanner.backpack.compose.calendar.BpkCalendarController
import net.skyscanner.backpack.compose.calendar.rememberCalendarController

val controller = rememberCalendarController(
  initialParams = CalendarParams(
    range = LocalDate.of(2019, 1, 2)..LocalDate.of(2019, 12, 31), // start and end dates in the range
    selectionMode = CalendarParams.SelectionMode.Single, // selection mode - can be Single, Dates, Months or Disabled
  )
)

BpkCalendar(controller)
```

Now the component is ready. You can listen for the selection change using its state:

```Kotlin
controller
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
controller.setParams(
  CalendarParams(
    range = range,
    selectionMode = selectionMode,
    cellsInfo = mapOf(
      LocalDate.of(2019, 1, 2) to CellInfo(
        disabled = true, // marks date as disabled
        status = CellStatus.Positive, // adds green colour to cell, you can use Neutral, Negative, Empty and null as well
        label = "£30", // adds label below the date
        style = CellStatusStyle.Background, // specifies application of the colour: Background or Label
      ),
    )
  )
)
```
