/**
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2018 Skyscanner Ltd
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.skyscanner.backpack.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

/**
 * StateMachine apis are used for some complex components with some state management.
 *
 * Each time the state changes, a new version of it is available for reading in [state].
 *
 * Each state change may also emmit a temporarily existing [effects] (for instance, to show Toast in case of error).
 *
 * The widget decided itself when the state needs to be changed. It may also provide some public methods
 * to have limited control over the state changes (e.g. settings the params).
 */
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
