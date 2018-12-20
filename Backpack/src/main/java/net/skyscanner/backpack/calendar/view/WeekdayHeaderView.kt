package net.skyscanner.backpack.calendar.view

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import net.skyscanner.backpack.R
import net.skyscanner.backpack.text.BpkText
import java.text.SimpleDateFormat
import java.util.*

internal class WeekdayHeaderView @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyle: Int = 0
) : LinearLayout(context, attrs, defStyle) {

  private val firstWeekdayView: BpkText by lazy { findViewById<BpkText>(R.id.first_weekday_label) }
  private val secondWeekdayView: BpkText by lazy { findViewById<BpkText>(R.id.second_weekday_label) }
  private val thirdWeekdayView: BpkText by lazy { findViewById<BpkText>(R.id.third_weekday_label) }
  private val fourthWeekdayView: BpkText by lazy { findViewById<BpkText>(R.id.fourth_weekday_label) }
  private val fifthWeekdayView: BpkText by lazy { findViewById<BpkText>(R.id.fifth_weekday_label) }
  private val sixthWeekdayView: BpkText by lazy { findViewById<BpkText>(R.id.sixth_weekday_label) }
  private val seventhWeekdayView: BpkText by lazy { findViewById<BpkText>(R.id.seventh_weekday_label) }

  init {
    addView(inflate(context, R.layout.view_bpk_calendar_weekday_header, null))

    orientation = VERTICAL
  }

  internal fun initializeWithLocale(locale: Locale) {
    val simpleDateFormat = SimpleDateFormat("ccc", locale)
    val calendar = Calendar.getInstance()
    val weekStart = calendar.firstDayOfWeek
    val numberOfDaysInWeek = 7

    for (i in 0 until numberOfDaysInWeek) {
      val calendarDay = (i + weekStart) % numberOfDaysInWeek
      calendar.set(Calendar.DAY_OF_WEEK, calendarDay)
      val calendarDayName = simpleDateFormat.format(calendar.time)


      when (i) {
        0 -> firstWeekdayView.text = calendarDayName
        1 -> secondWeekdayView.text = calendarDayName
        2 -> thirdWeekdayView.text = calendarDayName
        3 -> fourthWeekdayView.text = calendarDayName
        4 -> fifthWeekdayView.text = calendarDayName
        5 -> sixthWeekdayView.text = calendarDayName
        6 -> seventhWeekdayView.text = calendarDayName
      }
    }
  }
}
