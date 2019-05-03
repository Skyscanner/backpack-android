package net.skyscanner.backpack.calendar

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import net.skyscanner.backpack.R
import net.skyscanner.backpack.badge.BpkBadge
import net.skyscanner.backpack.calendar.presenter.BpkCalendarController
import net.skyscanner.backpack.calendar.view.CalendarView
import net.skyscanner.backpack.calendar.view.OnYearChangedListener
import net.skyscanner.backpack.calendar.view.WeekdayHeaderView
import org.threeten.bp.YearMonth

open class BpkCalendar @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyle: Int = 0
) : ConstraintLayout(context, attrs, defStyle), OnYearChangedListener {

  init {
    inflate(this.context, R.layout.view_bpk_calendar, this)
  }

  fun setController(controller: BpkCalendarController) {
    val calendarView = findViewById<CalendarView>(R.id.calendar_view)
    findViewById<WeekdayHeaderView>(R.id.weekday_header_view).initializeWithLocale(controller.locale)
    calendarView.controller = controller
    controller.updateContentCallback = calendarView
    calendarView.listener = this

    updateYearPill(controller.startDate.year)
  }

  override fun onYearChanged(year: Int) {
    updateYearPill(year)
  }

  private fun updateYearPill(year: Int) {
    val yearPillView = findViewById<BpkBadge>(R.id.year_pill_view)
    yearPillView.message = year.toString()
    yearPillView.visibility = if (YearMonth.now().year == year) View.GONE else View.VISIBLE
  }
}
