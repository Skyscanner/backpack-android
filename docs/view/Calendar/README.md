# Calendar

## Installation

Backpack Android is available through [Maven Central](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-android). Check the main [Readme](https://github.com/skyscanner/backpack-android#installation) for a complete installation guide.

The calendar implementation relies on the Android adaptation of the `JSR-310` backport for dealing with dates: [`ThreeTenABP`](https://github.com/JakeWharton/ThreeTenABP)

Add the following line in your base module's Gradle script:

```groovy
implementation 'com.jakewharton.threetenabp:threetenabp:$latestVersion'
```

## Usage

> **_NOTE:_**  This component is deprecated and will be removed in the future.

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
      if (range is CalendarRange) {
         // handles range
      } else if (range is SingleDay) {
         // handles single selection
      }
  }

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
      ColoredBucket(CalendarCellStyle.Negative, redSet),
      ColoredBucket(CalendarCellStyle.Neutral, yellowSet),
      ColoredBucket(CalendarCellStyle.Positive, greenSet),
      ColoredBucket(
        CalendarCellStyle.Custom(context.getColor(R.color.bpkBackgroundSecondary)),
        greySet
      )
    )
}
```

For more examples see:

- [ExampleBpkCalendarController](https://github.com/Skyscanner/backpack-android/blob/main/app/src/main/java/net/skyscanner/backpack/demo/data/ExampleBpkCalendarController.kt)
- [CalendarColoring](https://github.com/Skyscanner/backpack-android/blob/main/Backpack/src/main/java/net/skyscanner/backpack/calendar/model/CalendarColoring.kt)


### Labeled buckets

Labeled buckets are used to add some extra text to each calendar cell.

To define labels set the `calendarLabels` attribute in the calendar controller.

```kotlin
class ExampleBpkCalendarController(
  override val selectionType: SelectionType
 ) : BpkCalendarController(selectionType) {
  override val calendarLabels = mapOf(
    LocalDate.of(2020, 11, 7) to CalendarLabel(text = "£10", style = CalendarLabel.Style.PriceHigh),
    LocalDate.of(2020, 11, 8) to CalendarLabel(text = "£11", style = CalendarLabel.Style.PriceMedium),
    LocalDate.of(2020, 11, 9) to CalendarLabel(text = "£12", style = CalendarLabel.Style.PriceLow),
  )
}
```

For more examples see:

- [ExampleBpkCalendarController](https://github.com/Skyscanner/backpack-android/blob/main/app/src/main/java/net/skyscanner/backpack/demo/data/ExampleBpkCalendarController.kt)
- [CalendarLabel](https://github.com/Skyscanner/backpack-android/blob/main/Backpack/src/main/java/net/skyscanner/backpack/calendar/model/CalendarLabel.kt)

### Month footer views

`BpkCalendar` supports adding a footer to each month to display additional information through the `BpkCalendarController.monthFooterAdapter` attribute.
We provide `HighlightedDaysAdapter` to add a footer listing days you want to highlight for each month.

Example of controller with holidays support:

```kotlin
class ExampleBpkCalendarController(
  override val selectionType: SelectionType
 ) : BpkCalendarController(selectionType) {
  override val monthFooterAdapter =
    HighlightedDaysAdapter(
      context,
      locale,
      setOf(
        HighlightedDay(LocalDate.of(2020, 12, 25), "Christmas Day")
      ))
}
```

To highlight the day in the calendar itself, use the coloured buckets mechanism shown above and the `CalendarCellStyle.Highlight` style.

Example of controller with holidays highlighted:

```kotlin
class ExampleBpkCalendarController(
  override val selectionType: SelectionType
 ) : BpkCalendarController(selectionType) {
  override val calendarColoring = CalendarColoring(
    setOf(
      ColoredBucket(CalendarCellStyle.Highlight, setOf(LocalDate.of(2020, 12, 25))),
    )
}
```

For more examples see:

- [FooterViewCalendarStory](https://github.com/Skyscanner/backpack-android/blob/main/app/src/main/java/net/skyscanner/backpack/demo/stories/FooterViewCalendarStory.kt)

## Theme Props

- `calendarDateSelectedBackgroundColor`
- `calendarDateSelectedRangeBackgroundColor`
- `calendarDateSelectedTextColor`
- `calendarDateSelectedSameDayBackgroundColor`

Styles can be changed globally through `bpkCalendarStyle`. Check [theming](https://github.com/Skyscanner/backpack-android/blob/main/docs/view/THEMING.md) for more information.
