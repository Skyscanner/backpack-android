package net.skyscanner.backpack.nudger

import android.content.Context
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import net.skyscanner.backpack.R
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.verifyNoInteractions

@RunWith(AndroidJUnit4::class)
class BpkNudgerTest {

  private lateinit var subject: BpkNudger
  private lateinit var context: Context

  @Before
  fun setUp() {
    context = InstrumentationRegistry.getInstrumentation().targetContext
    subject = BpkNudger(context)
  }

  @Test
  fun test_listenerIsNotInvoked_onSet() {
    val listener = { _: Int -> }
    subject.onChangeListener = listener
    verifyNoInteractions(listener)
  }

  @Test
  fun test_listenerIsNotInvoked_whenValueSet() {
    val listener = { _: Int -> }
    subject.onChangeListener = listener
    subject.value = 5
    verifyNoInteractions(listener)
  }

  @Test
  fun test_listenerIsInvoked_whenValueIncreased() {
    var lastCurrent = 0
    var invocationsCount = 0
    val listener = { current: Int ->
      invocationsCount++
      lastCurrent = current
    }
    subject.onChangeListener = listener
    onView(withId(R.id.bpk_nudger_increment)).perform(click())
    assertEquals(1, lastCurrent)
    assertEquals(1, invocationsCount)
  }

  @Test
  fun test_listenerIsInvoked_whenValueDecreased() {
    var lastCurrent = 0
    var invocationsCount = 0
    val listener = { current: Int ->
      invocationsCount++
      lastCurrent = current
    }
    subject.value = 2
    subject.onChangeListener = listener
    onView(withId(R.id.bpk_nudger_decrement)).perform(click())
    assertEquals(1, lastCurrent)
    assertEquals(1, invocationsCount)
  }
}
