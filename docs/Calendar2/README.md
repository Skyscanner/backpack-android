# Calendar

## Installation

Backpack Android is available through [Maven Central](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-android). Check the main [Readme](https://github.com/skyscanner/backpack-android#installation) for a complete installation guide.

The calendar implementation relies on the Android adaptation of the `JSR-310` backport for dealing with dates: [`ThreeTenABP`](https://github.com/JakeWharton/ThreeTenABP)

Add the following line in your base module's Gradle script:

```groovy
implementation 'com.jakewharton.threetenabp:threetenabp:$latestVersion'
```

## Usage

> **_NOTE:_**  The docs for the previous version of Calendar is available [here](https://github.com/Skyscanner/backpack-android/blob/main/docs/Calendar/README.md).

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
    selectionMode = CalendarParams.SelectionMode.Single, // selection mode - can be Single, Range or Disabled
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
      is CalendarSelection.Range -> showToast("${selection.start} to {${selection.end} is selected")
      is CalendarSelection.Single -> showToast("${selection.date} is selected")
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
        label = "£30", // adds label below the date
        style = CellStatusStyle.Background, // specifies application of the colour: Background or Label
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

Styles can be changed globally through `bpkCalendarStyle`. Check [theming](https://github.com/Skyscanner/backpack-android/blob/main/docs/THEMING.md) for more information.
