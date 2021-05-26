package net.skyscanner.backpack.calendar2.data

import kotlinx.coroutines.CoroutineScope
import net.skyscanner.backpack.calendar2.CalendarComponent
import net.skyscanner.backpack.calendar2.CalendarParams
import net.skyscanner.backpack.calendar2.CalendarState
import net.skyscanner.backpack.util.MutableStateMachine
import net.skyscanner.backpack.util.StateMachine

internal interface CalendarStateMachine : CalendarComponent, StateMachine<CalendarState, Nothing> {

  fun onClick(day: Int, month: Int, year: Int)
}

internal fun CalendarStateMachine(
  scope: CoroutineScope,
  params: CalendarParams,
): CalendarStateMachine {

  val fsm = MutableStateMachine<CalendarState, Nothing>(scope, CalendarState(params).dispatchParamsUpdate(params))

  return object : CalendarStateMachine, StateMachine<CalendarState, Nothing> by fsm {

    override fun setParams(value: CalendarParams) {
      fsm.commit {
        it.dispatchParamsUpdate(value)
      }
    }

    override fun onClick(day: Int, month: Int, year: Int) {
      fsm.commit {
        it.dispatchClick(day, month, year)
      }
    }
  }
}
