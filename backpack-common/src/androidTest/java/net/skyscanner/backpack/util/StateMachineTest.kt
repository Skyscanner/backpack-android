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
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.job
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import kotlin.coroutines.coroutineContext

@OptIn(ExperimentalCoroutinesApi::class)
class StateMachineTest {

  private val scope = TestScope(UnconfinedTestDispatcher())

  @Test
  fun sm_holds_its_initial_state_when_created() {
    val sm = MutableStateMachine<Int, Unit>(scope, 1)
    assertEquals(1, sm.state.value)
  }

  @Test
  fun sm_holds_its_new_state_when_updated() {
    val sm = MutableStateMachine<Int, Unit>(scope, 1)
    sm.commit { 2 }
    assertEquals(2, sm.state.value)
  }

  @Test
  fun sm_emits_its_new_state_when_updated() {
    val sm = MutableStateMachine<Int, Unit>(scope, 1)
    val result = mutableListOf<Int>()
    sm.state.onEach { result += it }.launchIn(scope)
    sm.commit { 2 }
    assertEquals(2, result.last())
  }

  @Test
  fun sm_emits_effect() {
    val sm = MutableStateMachine<Int, Int>(scope, 1)
    val result = mutableListOf<Int>()
    sm.effects.onEach { result += it }.launchIn(scope)
    sm.commit {
      emmit(3)
      it
    }
    assertEquals(3, result.last())
  }

  @Test
  fun sm_emits_effect_sequentially() {
    val sm = MutableStateMachine<Int, Int>(scope, 1)
    val result = mutableListOf<Int>()
    sm.effects.onEach { result += it }.launchIn(scope)
    sm.commit {
      emmit(3)
      emmit(4)
      it
    }
    assertEquals(3, result.first())
    assertEquals(4, result.last())
  }

  @Test
  fun sm_cancels_its_job_when_scope_cancelled() {
    val scope = CoroutineScope(UnconfinedTestDispatcher())
    val sm = MutableStateMachine<Int, Unit>(scope, 1)

    var job: Job = Job()
    sm.commit {
      job = coroutineContext.job
      delay(Long.MAX_VALUE)
      it
    }
    assertFalse(job.isCancelled)
    scope.cancel()
    assertTrue(job.isCancelled)
  }
}
