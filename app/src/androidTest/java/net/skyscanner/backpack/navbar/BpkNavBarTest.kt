/**
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2018-2021 Skyscanner Ltd
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

package net.skyscanner.backpack.navbar

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import net.skyscanner.backpack.BpkSnapshotTest
import net.skyscanner.backpack.demo.R
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BpkNavBarTest : BpkSnapshotTest() {

  private lateinit var activity: AppCompatActivity

  @get:Rule
  var activityRule: ActivityTestRule<AppCompatActivity> =
    ActivityTestRule(AppCompatActivity::class.java)

  @Before
  fun setup() {
    activity = activityRule.activity
    setDimensions(400, 400)
  }

  @Test
  fun screenshotNavBar_default() {
    activity.init()
    val asyncSnapshot = prepareForAsyncTest()
    onView(ViewMatchers.withId(R.id.appBar))
      .check { v, _ ->
        asyncSnapshot.record(v)
      }
  }

  @Test
  fun screenshotNavBar_collapsed() {
    activity.init()
    val asyncSnapshot = prepareForAsyncTest()
    onView(ViewMatchers.withId(R.id.appBar))
      .perform(ViewActions.swipeUp())
      .check { v, _ ->
        asyncSnapshot.record(v)
      }
  }

  @Test
  fun screenshotNavBar_expanded() {
    activity.init()
    val asyncSnapshot = prepareForAsyncTest()
    onView(ViewMatchers.withId(R.id.appBar))
      .perform(ViewActions.swipeDown())
      .check { v, _ ->
        asyncSnapshot.record(v)
      }
  }

  @Test
  fun screenshotNavBar_collapsed_icon() {
    activity.init(icon = true)
    val asyncSnapshot = prepareForAsyncTest()
    onView(ViewMatchers.withId(R.id.appBar))
      .perform(ViewActions.swipeUp())
      .check { v, _ ->
        asyncSnapshot.record(v)
      }
  }

  @Test
  fun screenshotNavBar_expanded_icon() {
    activity.init(icon = true)
    val asyncSnapshot = prepareForAsyncTest()
    onView(ViewMatchers.withId(R.id.appBar))
      .perform(ViewActions.swipeDown())
      .check { v, _ ->
        asyncSnapshot.record(v)
      }
  }

  @Test
  fun screenshotNavBar_collapsed_menu() {
    activity.init(menu = true)
    val asyncSnapshot = prepareForAsyncTest()
    onView(ViewMatchers.withId(R.id.appBar))
      .perform(ViewActions.swipeUp())
      .check { v, _ ->
        asyncSnapshot.record(v)
      }
  }

  @Test
  fun screenshotNavBar_expanded_menu() {
    activity.init(menu = true)
    val asyncSnapshot = prepareForAsyncTest()
    onView(ViewMatchers.withId(R.id.appBar))
      .perform(ViewActions.swipeDown())
      .check { v, _ ->
        asyncSnapshot.record(v)
      }
  }

  @Test
  fun screenshotNavBar_collapsed_iconAndMenu() {
    activity.init(icon = true, menu = true)
    val asyncSnapshot = prepareForAsyncTest()
    onView(ViewMatchers.withId(R.id.appBar))
      .perform(ViewActions.swipeUp())
      .check { v, _ ->
        asyncSnapshot.record(v)
      }
  }

  @Test
  fun screenshotNavBar_expanded_iconAndMenu() {
    activity.init(icon = true, menu = true)
    val asyncSnapshot = prepareForAsyncTest()
    onView(ViewMatchers.withId(R.id.appBar))
      .perform(ViewActions.swipeDown())
      .check { v, _ ->
        asyncSnapshot.record(v)
      }
  }

  private fun Activity.init(
    icon: Boolean = false,
    menu: Boolean = false,
  ) {
    runOnUiThread {
      setContentView(R.layout.fragment_nav_bar)
      val navBar = findViewById<BpkNavBar>(R.id.appBar)
      navBar.title = "Nav Bar"

      if (icon) {
        navBar.icon = getDrawable(R.drawable.bpk_native_android__back)
      } else {
        navBar.icon = null
      }
      if (menu) {
        navBar.menu = R.menu.settings
      } else {
        navBar.menu = 0
      }
    }
  }
}
