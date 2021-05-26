package net.skyscanner.backpack.calendar2.view

import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import net.skyscanner.backpack.calendar2.CalendarState
import net.skyscanner.backpack.calendar2.data.CalendarDay
import net.skyscanner.backpack.calendar2.data.CalendarFooter
import net.skyscanner.backpack.calendar2.data.CalendarHeader
import net.skyscanner.backpack.calendar2.data.CalendarMonth
import net.skyscanner.backpack.calendar2.data.CalendarSpace
import net.skyscanner.backpack.calendar2.extension.getItem

internal class CalendarSpanSizeLookup(
  scope: CoroutineScope,
  input: StateFlow<CalendarState>,
) : GridLayoutManager.SpanSizeLookup() {

  private var data: List<CalendarMonth> = input.value.months

  init {
    input.onEach {
      this.data = it.months
    }.launchIn(scope)
  }

  override fun getSpanSize(position: Int): Int = when (data.getItem(position)) {
    is CalendarDay -> 1
    is CalendarFooter -> 7
    is CalendarHeader -> 7
    is CalendarSpace -> 1
  }
}
