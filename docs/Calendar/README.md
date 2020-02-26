# Calendar

## Installation

Backpack Android is available through [Jitpack](https://jitpack.io/#Skyscanner/backpack-android). Check the main [Readme](https://github.com/skyscanner/backpack-android#installation) for a complete installation guide.

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

```Java
@Override public void onCreate() {
  super.onCreate();
  AndroidThreeTen.init(this);
}
```


BpkCalendar is based on `CalendarView` and `MonthView` from the Android Open Source Project.

The Calendar component can be used in both XML and Kotlin, but it currently requires a `BpkCalendarController` to be sub-classed and set.
Both single and range selection are supported.

Example of a calendar defined in XML

```xml
<net.skyscanner.backpack.calendar.BpkCalendar
    android:id="@+id/bpkCalendar"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:nestedScrollingEnabled="true"/>
```

Example of a padded and focused card in Kotlin

```Kotlin
import net.skyscanner.backpack.calendar.BpkCalendar

BpkCalendar(context)
```

Example of a calendar controller:

```Kotlin
class ExampleBpkCalendarController(
  private val context: Context,
  override val selectionType: SelectionType
 ) : BpkCalendarController(selectionType) {
  override fun onRangeSelected(range: CalendarSelection) {
      if (selection is CalendarRange) {
         // handles range
      } else if (selection is SingleDay) {
         // handles single selection
      }
  }

  override val isRtl: Boolean = getLayoutDirectionFromLocale(Locale.getDefault()) == LAYOUT_DIRECTION_RTL
  override val locale: Locale = Locale.getDefault()
}
```

Setting the controller on the calendar:

```Kotlin
override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
  super.onViewCreated(view, savedInstanceState)

  val controller = ExampleBpkCalendarController(requireContext(), SelectionType.SINGLE)
  
  view.findViewById<BpkCalendar>(R.id.bpkCalendar).setController(controller)
}
```

Notice the selection type is set in the `BpkCalendarController` constructor through the `selectionType` argument.
 
When `selectionType` is `SelectionType.RANGE` the calendar will allow a date range. 
When `selectionType` is `SelectionType.SINGLE_DAY` the calendar will allow a single date.
In both cases, the handling of the selection
 will be done in `onRangeSelected()` as in the example above.

Example of controller with disabled dates support:

```kotlin
class ExampleBpkCalendarController(
  override val selectionType: SelectionType
 ) : BpkCalendarController(selectionType) {
  //...
  override fun isDateDisabled(date: LocalDate): Boolean {
    return date.dayOfWeek == DayOfWeek.WEDNESDAY
  }
  //...
}
```

### Coloured buckets

Coloured buckets are used to change the style of calendar cells.

To define coloured buckets set the `calendarColoring` attribute in the calendar controller.

```kotlin
class ExampleBpkCalendarController(
  override val selectionType: SelectionType
 ) : BpkCalendarController(selectionType) {
  override val calendarColoring = CalendarColoring(
    setOf(
      ColoredBucket(CalendarCellStyle.negative, redSet),
      ColoredBucket(CalendarCellStyle.neutral, yellowSet),
      ColoredBucket(CalendarCellStyle.positive, greenSet),
      ColoredBucket(
        CalendarCellStyle.custom(ContextCompat.getColor(context, R.color.bpkBackgroundSecondary)),
        greySet
      )
    )
}
```

For more examples see:

- [ExampleBpkCalendarController](https://github.com/Skyscanner/backpack-android/blob/master/app/src/main/java/net/skyscanner/backpack/demo/data/ExampleBpkCalendarController.kt)
- [CalendarColoring](https://github.com/Skyscanner/backpack-android/blob/master/Backpack/src/main/java/net/skyscanner/backpack/calendar/model/CalendarColoring.kt)

## Theme Props

- `calendarDateSelectedBackgroundColor`
- `calendarDateSelectedRangeBackgroundColor`
- `calendarDateSelectedTextColor`
- `calendarDateSelectedSameDayBackgroundColor`

Styles can be changed globally through `bpkCalendarStyle`. Check [theming](https://github.com/Skyscanner/backpack-android/blob/master/docs/THEMING.md) for more information.
