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

package net.skyscanner.backpack.nudger

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.nhaarman.mockitokotlin2.mock
import net.skyscanner.backpack.R
import net.skyscanner.backpack.util.TestActivity
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.verifyNoInteractions

@RunWith(AndroidJUnit4::class)
class BpkNudgerTest {

  private lateinit var subject: BpkNudger

  @get:Rule
  internal var activityRule: ActivityScenarioRule<TestActivity> =
    ActivityScenarioRule(TestActivity::class.java)

  @Before
  fun setUp() {
    activityRule.scenario.onActivity {
      subject = BpkNudger(it)
    }
  }

  @Test
  fun test_listenerIsNotInvoked_onSet() {
    val listener = mock<(Int) -> Unit>()
    subject.onChangeListener = listener
    verifyNoInteractions(listener)
  }

  @Test
  fun test_listenerIsNotInvoked_whenValueSet() {
    val listener = mock<(Int) -> Unit>()
    subject.onChangeListener = listener
    subject.value = 5
    verifyNoInteractions(listener)
  }

  @Test
  fun test_listenerIsInvoked_whenValueIncreased() {
    activityRule.scenario.onActivity { it.setContentView(subject) }
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
    activityRule.scenario.onActivity { it.setContentView(subject) }
    var lastCurrent = 0
    var invocationsCount = 0
    val listener = { current: Int ->
      invocationsCount++
      lastCurrent = current
    }
    activityRule.scenario.onActivity {
      subject.value = 2
      subject.onChangeListener = listener
    }
    onView(withId(R.id.bpk_nudger_decrement)).perform(click())
    assertEquals(1, lastCurrent)
    assertEquals(1, invocationsCount)
  }
}
