# Calendar

## Installation

Backpack Android is available through [Jitpack](https://jitpack.io/#Skyscanner/backpack-android). Check the main [Readme](https://github.com/skyscanner/backpack-android#installation) for a complete installation guide.

## Usage

BpkCalendar is building on a fork of `CalendarView` and `MonthView` from the Android Open Source Project.

The Calendar component can be used in both XML and Kotlin, but it currently requires a `BpkCalendarController` to be sub-classed and set.
Both single day and data range are supported. 

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
class ExampleBpkCalendarController(private val context: Context) : BpkCalendarController() {
  override fun onRangeSelected(range: CalendarRange) {
    // Do something with the selected range
  }

  override fun onSingleDaySelected(day: CalendarDay) {
    // Do something with the selected day
  }

  override val isRtl: Boolean = getLayoutDirectionFromLocale(Locale.getDefault()) == LAYOUT_DIRECTION_RTL
  override val locale: Locale = Locale.getDefault()
}
```

Setting the controller on the calendar:

```Kotlin
override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
  super.onViewCreated(view, savedInstanceState)

  val controller = ExampleBpkCalendarController(requireContext())
  
  controller.selectionType = SelectionType.RANGE
  
  view.findViewById<BpkCalendar>(R.id.bpkCalendar).setController(controller)
}
```

Notice the usage of `selectionType`. 
When `selectionType` is `SelectionType.RANGE` the calendar will allow a date range and `onRangeSelected()` will be used in the controller.
When `selectionType` is `SelectionType.SINGLE_DAY` the calendar will allow a date rang and `onSingleDaySelected()` will be used in the controller.
