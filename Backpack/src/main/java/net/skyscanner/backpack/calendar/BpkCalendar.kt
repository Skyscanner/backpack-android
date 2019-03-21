package net.skyscanner.backpack.calendar

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.android.synthetic.main.view_bpk_calendar.view.calendar_view
import kotlinx.android.synthetic.main.view_bpk_calendar.view.weekday_header_view
import kotlinx.android.synthetic.main.view_bpk_calendar.view.year_pill_view
import net.skyscanner.backpack.R
import net.skyscanner.backpack.calendar.presenter.BpkCalendarController
import net.skyscanner.backpack.calendar.view.OnYearChangedListener
import java.util.Calendar

open class BpkCalendar @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyle: Int = 0
) : ConstraintLayout(context, attrs, defStyle), OnYearChangedListener {

  init {
    inflate(context, R.layout.view_bpk_calendar, this)
  }

  fun setController(controller: BpkCalendarController) {
    weekday_header_view.initializeWithLocale(controller.locale)
    calendar_view.controller = controller
    controller.updateContentCallback = calendar_view
    calendar_view.listener = this

    updateYearPill(controller.startDate.year)
  }

  override fun onYearChanged(year: Int) {
    this.post { updateYearPill(year) }
  }

  private fun updateYearPill(year: Int) {
    val currentYear = Calendar.getInstance().get(Calendar.YEAR)
    year_pill_view.visibility = if (currentYear == year) View.GONE else View.VISIBLE
    year_pill_view.message = year.toString()
  }
}
