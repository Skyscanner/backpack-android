package net.skyscanner.backpack.util

import kotlin.coroutines.coroutineContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.job
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class StateMachineTest {

  private val scope = TestCoroutineScope()

  @Test
  fun `SM holds its initial state when created`() {
    val sm = MutableStateMachine<Int, Unit>(scope, 1)
    assertEquals(1, sm.state.value)
  }

  @Test
  fun `SM holds its new state when updated`() {
    val sm = MutableStateMachine<Int, Unit>(scope, 1)
    sm.commit { 2 }
    assertEquals(2, sm.state.value)
  }

  @Test
  fun `SM emits its new state when updated`() {
    val sm = MutableStateMachine<Int, Unit>(scope, 1)
    val result = mutableListOf<Int>()
    sm.state.onEach { result += it }.launchIn(scope)
    sm.commit { 2 }
    assertEquals(2, result.last())
  }

  @Test
  fun `SM emits effect`() {
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
  fun `SM emits effect sequentially`() {
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
  fun `SM cancels its job when scope cancelled`() {
    val scope = CoroutineScope(TestCoroutineDispatcher())
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
