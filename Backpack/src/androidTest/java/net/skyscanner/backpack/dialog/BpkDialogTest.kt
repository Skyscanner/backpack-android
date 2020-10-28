/**
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2018-2020 Skyscanner Ltd
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

package net.skyscanner.backpack.dialog

import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.FlakyTest
import androidx.test.rule.ActivityTestRule
import net.skyscanner.backpack.button.BpkButton
import net.skyscanner.backpack.util.TestActivity
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BpkDialogTest {

  private lateinit var handler: Handler
  private lateinit var mActivity: AppCompatActivity

  @get:Rule
  internal var activityRule: ActivityTestRule<TestActivity> =
    ActivityTestRule(TestActivity::class.java)

  @Before
  fun setUp() {
    if (Looper.myLooper() == null) {
      Looper.prepare()
    }

    mActivity = activityRule.activity
    handler = Handler(mActivity.mainLooper)
  }

  @Test
  @FlakyTest
  fun test_with_title() {
    val dialog = BpkDialog(mActivity).apply {
      title = "title"
    }

    handler.post {
      dialog.show()
    }

    onView(withText("title"))
      .check(matches(isDisplayed()))
  }

  @Test
  @FlakyTest
  fun test_with_description() {
    val dialog = BpkDialog(mActivity).apply {
      description = "Some description"
    }

    handler.post {
      dialog.show()
    }

    onView(withText("Some description"))
      .check(matches(isDisplayed()))
  }

  @Test
  @FlakyTest
  fun test_with_buttons() {
    val dialog = BpkDialog(mActivity).apply {
      addActionButton(
        BpkButton(mActivity).apply {
          text = "Confirm"
        }
      )
    }

    handler.post {
      dialog.show()
    }

    onView(withText("Confirm"))
      .check(matches(isDisplayed()))
  }
}
