package net.skyscanner.backpack.util

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.plus
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope

interface TestStateMachineResult

class TestStateMachineScope<SM : StateMachine<State, *>, State>(
  val stateMachine: SM
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

fun <SM : StateMachine<State, *>, State> testStateMachine(
  creator: CoroutineScope.(CoroutineDispatcher) -> SM,
  block: suspend TestStateMachineScope<SM, State>.() -> TestStateMachineResult
) {
  val coroutineDispatcher = TestCoroutineDispatcher()
  val coroutineScope = TestCoroutineScope() + coroutineDispatcher

  runBlocking(coroutineDispatcher) {
    val stateMachine = coroutineScope.creator(coroutineDispatcher)
    val testScope = TestStateMachineScope(stateMachine)
    testScope.block()
  }
}
