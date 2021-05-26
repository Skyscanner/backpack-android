package net.skyscanner.backpack.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

interface StateMachine<State, Effect> {

  val state: StateFlow<State>

  val effects: SharedFlow<Effect>
}

internal interface MutableStateMachine<State, Effect> : StateMachine<State, Effect> {

  interface CommitScope<Effect> {

    fun emmit(effect: Effect)
  }

  fun commit(block: suspend CommitScope<Effect>.(State) -> State)
}

@Suppress("FunctionName")
internal fun <State, Effect> MutableStateMachine(
  scope: CoroutineScope,
  initial: State,
): MutableStateMachine<State, Effect> {

  val state = MutableStateFlow(initial)
  val effects = MutableSharedFlow<Effect>()
  val mutex = Mutex()

  return object : MutableStateMachine<State, Effect> {

    override val effects: SharedFlow<Effect> = effects

    override val state: StateFlow<State> = state

    override fun commit(block: suspend MutableStateMachine.CommitScope<Effect>.(State) -> State) {
      scope.launch {
        val accumulator = EffectsAccumulator<Effect>()
        mutex.withLock {
          state.value = accumulator.block(state.value)
          accumulator.sinkTo(effects)
        }
      }
    }
  }
}

private class EffectsAccumulator<Effect> : MutableStateMachine.CommitScope<Effect> {

  private var effects: MutableList<Effect>? = null

  override fun emmit(effect: Effect) {
    (effects ?: mutableListOf<Effect>().also { effects = it }).add(effect)
  }

  suspend fun sinkTo(sink: MutableSharedFlow<Effect>) {
    effects?.forEach { sink.emit(it) }
  }
}
