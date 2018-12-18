# Calendar

## Installation

Backpack Android is available through [Jitpack](https://jitpack.io/#Skyscanner/backpack-android). Check the main [Readme](https://github.com/skyscanner/backpack-android#installation) for a complete installation guide.

## Usage

BpkCalendar is an building on a fork of `CalendarView` and `MonthView` from the Android Open Source Project.

The Calendar component can be used in both XML and Kotlin, but it currently requires a `BpkCalendarController` to be sub-classed and set.

Example of a padded and focused card in XML

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
