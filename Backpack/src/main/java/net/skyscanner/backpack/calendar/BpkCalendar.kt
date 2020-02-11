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
import net.skyscanner.backpack.util.unsafeLazy
import net.skyscanner.backpack.util.wrapContextWithDefaults

open class BpkCalendar @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyle: Int = 0
) : ConstraintLayout(wrapContextWithDefaults(context), attrs, defStyle), OnYearChangedListener {

  init {
    inflate(this.context, R.layout.view_bpk_calendar, this)
  }

  private val calendarView by unsafeLazy { findViewById<CalendarView>(R.id.calendar_view) }
  private val weekdayHeaderView by unsafeLazy { findViewById<WeekdayHeaderView>(R.id.weekday_header_view) }
  private val yearPillView by unsafeLazy { findViewById<BpkBadge>(R.id.year_pill_view) }

  val controller: BpkCalendarController?
    get() = calendarView.controller

  fun setController(controller: BpkCalendarController) {
    weekdayHeaderView.initializeWithLocale(controller.locale)
    calendarView.controller = controller
    controller.updateContentCallback = calendarView
    calendarView.listener = this

    updateYearPill(controller.startDate.year)
  }

  override fun onYearChanged(year: Int) {
    updateYearPill(year)
  }

  private fun updateYearPill(year: Int) {
    yearPillView.message = year.toString()
    val controller = calendarView.controller
    yearPillView.visibility = when {
      controller == null -> View.GONE
      controller.currentDateProvider.invoke().year != year -> View.VISIBLE
      else -> View.GONE
    }
  }
}
