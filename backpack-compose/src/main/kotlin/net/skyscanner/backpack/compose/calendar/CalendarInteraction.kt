package net.skyscanner.backpack.compose.calendar

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import net.skyscanner.backpack.compose.calendar.internal.data.CalendarCell

@Stable
sealed interface CalendarInteraction {

    @Immutable
    data class DateClicked(val day: CalendarCell.Day) : CalendarInteraction
}
