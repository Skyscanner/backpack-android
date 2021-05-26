package net.skyscanner.backpack.calendar2.widget

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import androidx.appcompat.widget.LinearLayoutCompat
import java.util.Locale
import net.skyscanner.backpack.R
import net.skyscanner.backpack.calendar2.CalendarState
import net.skyscanner.backpack.text.BpkText
import net.skyscanner.backpack.util.Consumer
import org.threeten.bp.DayOfWeek
import org.threeten.bp.format.TextStyle

internal class CalendarHeaderView @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0,
) : LinearLayoutCompat(context, attrs, defStyleAttr), Consumer<CalendarState> {

  init {
    orientation = HORIZONTAL
  }

  private var lastState: CalendarState? = null

  override fun invoke(state: CalendarState) {
    if (lastState?.weekFields == state.weekFields && lastState?.params == state.params) return

    lastState = state
    removeAllViews()

    var current = state.weekFields.firstDayOfWeek
    do {
      addField(current, state.params.dayOfWeekTextStyle, state.params.locale)
      current += 1
    } while (current != state.weekFields.firstDayOfWeek)
  }

  private fun addField(field: DayOfWeek, textStyle: TextStyle, locale: Locale) {
    val text = BpkText(context)
    text.textStyle = BpkText.SM
    text.weight = BpkText.Weight.EMPHASIZED
    text.setTextColor(context.getColorStateList(R.color.bpkTextSecondary))
    text.gravity = Gravity.CENTER
    text.maxLines = 1
    text.isSingleLine = true
    text.isAllCaps = true
    text.text = field.getDisplayName(textStyle, locale)
    addView(text, LayoutParams(0, LayoutParams.MATCH_PARENT, 1f))
  }
}
