package net.skyscanner.backpack.compose.calendar

import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import net.skyscanner.backpack.calendar2.CalendarParams
import net.skyscanner.backpack.calendar2.CalendarSelection
import net.skyscanner.backpack.calendar2.CalendarState
import net.skyscanner.backpack.calendar2.data.CalendarInteraction
import net.skyscanner.backpack.calendar2.data.dispatchClick
import net.skyscanner.backpack.calendar2.data.dispatchSetSelection
import java.io.Serializable
import java.time.LocalDate

class BpkCalendarComposeController(
    initialParams: CalendarParams,
    internal val lazyGridState: LazyGridState = LazyGridState(),
    internal val onSelectionChanged: (CalendarSelection) -> Unit,
) {
    var state by mutableStateOf(CalendarState(initialParams))
        private set

    fun setSelection(selection: CalendarSelection) {
        updateState(state.dispatchSetSelection(selection))
    }

    suspend fun scrollToDate(date: LocalDate) {
        val index = state.cells.indexOf(date)
        if (index >= 0) lazyGridState.scrollToItem(index)
    }

    suspend fun smoothScrollToDate(date: LocalDate) {
        val index = state.cells.indexOf(date)
        if (index >= 0) lazyGridState.animateScrollToItem(index)
    }

    fun setParams(value: CalendarParams) {
        state = state.copy(params = value)
    }

    private fun updateState(newState: CalendarState) {
        if (newState != state) {
            state = newState
            onSelectionChanged(state.selection)
        }
    }

    internal val firstVisibleItemYear: Int
        get() = state.cells[lazyGridState.firstVisibleItemIndex].yearMonth.year

    internal fun onClick(calendarInteraction: CalendarInteraction) {
        when (calendarInteraction) {
            is CalendarInteraction.DateClicked -> updateState(state.dispatchClick(calendarInteraction.day))
            is CalendarInteraction.SelectMonthClicked -> updateState(state.dispatchClick(calendarInteraction.header))
        }
    }
}

@Composable
fun rememberCalendarComposeController(
    initialParams: CalendarParams,
    lazyGridState: LazyGridState = rememberLazyGridState(),
    onSelectionChanged: (CalendarSelection) -> Unit,
): BpkCalendarComposeController =
    rememberSaveable(
        inputs = arrayOf(initialParams),
        saver = Saver<BpkCalendarComposeController, Serializable>(
            save = { CalendarSavableData(it.state.selection, it.state.params) },
            restore = { savedData ->
                val (savedSelection, savedParams) = savedData as CalendarSavableData
                BpkCalendarComposeController(
                    savedParams,
                    lazyGridState,
                    onSelectionChanged,
                ).apply {
                    setSelection(savedSelection)
                }
            },
        ),
        init = {
            BpkCalendarComposeController(initialParams, lazyGridState, onSelectionChanged)
        },
    )

internal data class CalendarSavableData(
    val selection: CalendarSelection,
    val params: CalendarParams,
) : Serializable
