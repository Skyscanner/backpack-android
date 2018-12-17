package net.skyscanner.backpack.demo.data

import android.content.Context
import android.widget.Toast
import net.skyscanner.backpack.calendar.model.CalendarDay
import net.skyscanner.backpack.calendar.model.CalendarRange
import net.skyscanner.backpack.calendar.presenter.BpkCalendarController
import java.util.*

class ExampleBpkCalendarController(private val context: Context) : BpkCalendarController() {
  override fun onRangeSelected(range: CalendarRange) {
    Toast.makeText(context, String.format("%s - %s", range.start.toString(), range.end.toString()), Toast.LENGTH_SHORT)
      .show()
  }

  override val selectedDay: CalendarDay = CalendarDay()
  override val range: CalendarRange = CalendarRange()
  override val minDate: Calendar = Calendar.getInstance()
  override val maxDate: Calendar = Calendar.getInstance().apply { add(Calendar.YEAR, 1) }
  override val isRtl: Boolean = false
  override val locale: Locale = Locale.UK
}
