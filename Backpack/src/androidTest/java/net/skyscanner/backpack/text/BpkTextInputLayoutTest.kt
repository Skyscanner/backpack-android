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

package net.skyscanner.backpack.text

import android.view.View
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.Assert.assertEquals
import net.skyscanner.backpack.R
import net.skyscanner.backpack.util.TestActivity
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BpkTextInputLayoutTest {

  private lateinit var subject: BpkTextInputLayout

  private val indicatorView: View
    get() = subject.findViewById(R.id.bpk_input_indicator)

  @get:Rule
  internal var activityRule: ActivityScenarioRule<TestActivity> =
    ActivityScenarioRule(TestActivity::class.java)

  @Before
  fun setUp() {
    activityRule.scenario.onActivity {
      val textField = BpkTextField(it).apply {
        hint = "Hint"
        setText("Text")
      }
      subject = BpkTextInputLayout(it).apply {
        label = "Label"
        addView(textField)
      }
    }
  }

  @Test
  fun test_errorFieldInvisibleWhenErrorEnabled() {
    subject.errorEnabled = true

    assertEquals(indicatorView.visibility, View.INVISIBLE)
  }

  @Test
  fun test_errorFieldGoneWithoutError() {
    assertEquals(indicatorView.visibility, View.GONE)
  }

  @Test
  fun test_errorFieldVisibleWithError() {
    subject.error = "Error"

    assertEquals(indicatorView.visibility, View.VISIBLE)
  }

  @Test
  fun test_errorFieldGoneAfterErrorDisabled() {
    subject.error = "Error"
    subject.errorEnabled = false

    assertEquals(indicatorView.visibility, View.GONE)
  }
}
