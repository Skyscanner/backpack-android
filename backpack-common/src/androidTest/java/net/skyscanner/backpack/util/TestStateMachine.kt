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
import kotlinx.coroutines.plus
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher

interface TestStateMachineResult

class TestStateMachineScope<SM : StateMachine<State, *>, State>(
  val stateMachine: SM,
) {

  val state: State
    get() = stateMachine.state.value

  fun verify(block: TestStateMachineResultScope<State>.() -> Unit): TestStateMachineResult {
    val currentState = stateMachine.state.value
    val verificationScope = TestStateMachineResultScope(currentState)
    verificationScope.block()
    return object : TestStateMachineResult {}
  }
}

class TestStateMachineResultScope<State>(
  val state: State,
)

@OptIn(ExperimentalCoroutinesApi::class)
fun <SM : StateMachine<State, *>, State> testStateMachine(
  creator: CoroutineScope.() -> SM,
  block: suspend TestStateMachineScope<SM, State>.() -> TestStateMachineResult,
) {
  val testCoroutineScheduler = TestCoroutineScheduler()
  val coroutineScope = TestScope(testCoroutineScheduler) + UnconfinedTestDispatcher(testCoroutineScheduler)

  runBlocking(testCoroutineScheduler) {
    val stateMachine = coroutineScope.creator()
    val testScope = TestStateMachineScope(stateMachine)
    testScope.block()
  }
}
