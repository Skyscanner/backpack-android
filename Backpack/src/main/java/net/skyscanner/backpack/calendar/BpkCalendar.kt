package net.skyscanner.backpack.calendar

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RadioGroup
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.android.synthetic.main.bpk_calendar_selection_type.view.range
import kotlinx.android.synthetic.main.bpk_calendar_selection_type.view.single
import kotlinx.android.synthetic.main.view_bpk_calendar.view.calendar_view
import kotlinx.android.synthetic.main.view_bpk_calendar.view.selection_type
import kotlinx.android.synthetic.main.view_bpk_calendar.view.weekday_header_view
import kotlinx.android.synthetic.main.view_bpk_calendar.view.year_pill_view
import net.skyscanner.backpack.R
import net.skyscanner.backpack.calendar.presenter.BpkCalendarController
import net.skyscanner.backpack.calendar.presenter.SelectionType
import net.skyscanner.backpack.calendar.view.OnYearChangedListener
import java.util.Calendar

open class BpkCalendar @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyle: Int = 0
) : ConstraintLayout(context, attrs, defStyle), OnYearChangedListener {

  private var calendarController: BpkCalendarController? = null

  var isSelectionTypeSwitcherVisible: Boolean = false
    set(value) {
      field = value
      selection_type.visibility = if (value) View.VISIBLE else View.GONE
      (selection_type as? RadioGroup)?.setOnCheckedChangeListener { group, checkedId ->
        when (checkedId) {
          R.id.single -> {
            calendarController?.selectionType = SelectionType.SINGLE
          }
          R.id.range -> {
            calendarController?.selectionType = SelectionType.RANGE
          }
        }
      }
    }

  init {
    inflate(context, R.layout.view_bpk_calendar, this)
    single.text = "Single"
    range.text = "Range"

    calendarController?.selectionType = SelectionType.RANGE // defaults to Range
    range.isChecked = true
  }

  fun setController(controller: BpkCalendarController) {
    calendarController = controller
    weekday_header_view.initializeWithLocale(controller.locale)
    calendar_view.controller = controller
    controller.updateContentCallback = calendar_view
    calendar_view.listener = this
  }

  override fun onYearChanged(year: Int) {
    val currentYear = Calendar.getInstance().get(Calendar.YEAR)
    year_pill_view.visibility = if (currentYear == year) View.GONE else View.VISIBLE
    year_pill_view.postDelayed({ year_pill_view.message = year.toString() }, 0)
  }
}
