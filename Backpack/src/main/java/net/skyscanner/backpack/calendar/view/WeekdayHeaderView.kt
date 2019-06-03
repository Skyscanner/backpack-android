package net.skyscanner.backpack.calendar.view

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import net.skyscanner.backpack.R
import net.skyscanner.backpack.text.BpkText
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.temporal.WeekFields
import java.util.Locale

internal class WeekdayHeaderView @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyle: Int = 0
) : LinearLayout(context, attrs, defStyle) {

  init {
    addView(inflate(context, R.layout.view_bpk_calendar_weekday_header, null))
    orientation = VERTICAL
  }

  private val firstWeekdayView: BpkText = findViewById(R.id.first_weekday_label)
  private val secondWeekdayView: BpkText = findViewById(R.id.second_weekday_label)
  private val thirdWeekdayView: BpkText = findViewById(R.id.third_weekday_label)
  private val fourthWeekdayView: BpkText = findViewById(R.id.fourth_weekday_label)
  private val fifthWeekdayView: BpkText = findViewById(R.id.fifth_weekday_label)
  private val sixthWeekdayView: BpkText = findViewById(R.id.sixth_weekday_label)
  private val seventhWeekdayView: BpkText = findViewById(R.id.seventh_weekday_label)

  internal fun initializeWithLocale(locale: Locale) {
    val formatter = DateTimeFormatter.ofPattern("ccc", locale)

    firstWeekdayView.text = dayOfWeek(locale, formatter, 1)
    secondWeekdayView.text = dayOfWeek(locale, formatter, 2)
    thirdWeekdayView.text = dayOfWeek(locale, formatter, 3)
    fourthWeekdayView.text = dayOfWeek(locale, formatter, 4)
    fifthWeekdayView.text = dayOfWeek(locale, formatter, 5)
    sixthWeekdayView.text = dayOfWeek(locale, formatter, 6)
    seventhWeekdayView.text = dayOfWeek(locale, formatter, 7)
  }

  private fun dayOfWeek(locale: Locale, formatter: DateTimeFormatter, dayOfWeekIndex: Long): String {
    val fieldISO = WeekFields.of(locale).dayOfWeek()
    val dayOfWeek = LocalDate.now().with(fieldISO, dayOfWeekIndex)
    return formatter.format(dayOfWeek)
  }
}
