package net.skyscanner.backpack.calendar

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import net.skyscanner.backpack.calendar.presenter.BpkCalendarController
import net.skyscanner.backpack.calendar.view.CalendarView
import net.skyscanner.backpack.calendar.view.WeekdayHeaderView

class BpkCalendar @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyle: Int = 0
) : LinearLayout(context, attrs, defStyle) {

  private val calendarView: CalendarView by lazy {
    CalendarView(context, attrs).also { calendarView ->
      LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        .also { calendarView.layoutParams = it }
    }
  }

  private val weekdayHeaderView: WeekdayHeaderView by lazy {
    WeekdayHeaderView(context, attrs)
  }

  init {
    orientation = LinearLayout.VERTICAL
    layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)

    addView(weekdayHeaderView)
    addView(LinearLayout(context).also {
      it.addView(calendarView)
    })
  }

  fun setController(controller: BpkCalendarController) {
    weekdayHeaderView.initializeWithLocale(controller.locale)
    calendarView.controller = controller
  }
}
