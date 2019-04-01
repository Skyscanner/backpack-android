package net.skyscanner.backpack.calendar

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.android.synthetic.main.view_bpk_calendar.view.*
import net.skyscanner.backpack.R
import net.skyscanner.backpack.calendar.presenter.BpkCalendarController
import net.skyscanner.backpack.calendar.view.OnYearChangedListener
import net.skyscanner.backpack.util.createContextThemeOverlayWrapper
import org.threeten.bp.YearMonth

open class BpkCalendar @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyle: Int = 0
) : ConstraintLayout(createContextThemeOverlayWrapper(context, attrs), attrs, defStyle), OnYearChangedListener {

  init {
    inflate(this.context, R.layout.view_bpk_calendar, this)
  }

  fun setController(controller: BpkCalendarController) {
    weekday_header_view.initializeWithLocale(controller.locale)
    calendar_view.controller = controller
    controller.updateContentCallback = calendar_view
    calendar_view.listener = this

    updateYearPill(controller.startDate.year)
  }

  override fun onYearChanged(year: Int) {
    updateYearPill(year)
  }

  private fun updateYearPill(year: Int) {
    year_pill_view.message = year.toString()
    year_pill_view.visibility = if (YearMonth.now().year == year) View.GONE else View.VISIBLE
  }
}
