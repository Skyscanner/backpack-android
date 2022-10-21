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

package net.skyscanner.backpack.navbar

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.karumi.shot.ActivityScenarioUtils.waitForActivity
import net.skyscanner.backpack.BpkSnapshotTest
import net.skyscanner.backpack.demo.R
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BpkNavBarTest : BpkSnapshotTest() {

  @get:Rule
  val rule = activityScenarioRule<AppCompatActivity>()

  @Before
  fun setup() {
    setDimensions(400, 400)
  }

  @Test
  fun screenshotNavBar_default() {
    init()
    var view: View? = null
    onView(ViewMatchers.withId(R.id.appBar))
      .check { v, _ ->
        view = v
      }
    snap(view!!)
  }

  @Test
  fun screenshotNavBar_collapsed() {
    init()
    var view: View? = null
    onView(ViewMatchers.withId(R.id.appBar))
      .perform(ViewActions.swipeUp())
      .check { v, _ ->
        view = v
      }
    snap(view!!)
  }

  @Test
  fun screenshotNavBar_expanded() {
    init()
    var view: View? = null
    onView(ViewMatchers.withId(R.id.appBar))
      .perform(ViewActions.swipeDown())
      .check { v, _ ->
        view = v
      }
    snap(view!!)
  }

  @Test
  fun screenshotNavBar_collapsed_icon() {
    init(icon = true)
    var view: View? = null
    onView(ViewMatchers.withId(R.id.appBar))
      .perform(ViewActions.swipeUp())
      .check { v, _ ->
        view = v
      }
    snap(view!!)
  }

  @Test
  fun screenshotNavBar_expanded_icon() {
    init(icon = true)
    var view: View? = null
    onView(ViewMatchers.withId(R.id.appBar))
      .perform(ViewActions.swipeDown())
      .check { v, _ ->
        view = v
      }
    snap(view!!)
  }

  @Test
  fun screenshotNavBar_collapsed_menu() {
    init(menu = true)
    var view: View? = null
    onView(ViewMatchers.withId(R.id.appBar))
      .perform(ViewActions.swipeUp())
      .check { v, _ ->
        view = v
      }
    snap(view!!)
  }

  @Test
  fun screenshotNavBar_expanded_menu() {
    init(menu = true)
    var view: View? = null
    onView(ViewMatchers.withId(R.id.appBar))
      .perform(ViewActions.swipeDown())
      .check { v, _ ->
        view = v
      }
    snap(view!!)
  }

  @Test
  fun screenshotNavBar_collapsed_iconAndMenu() {
    init(icon = true, menu = true)
    var view: View? = null
    onView(ViewMatchers.withId(R.id.appBar))
      .perform(ViewActions.swipeUp())
      .check { v, _ ->
        view = v
      }
    snap(view!!)
  }

  @Test
  fun screenshotNavBar_expanded_iconAndMenu() {
    init(icon = true, menu = true)
    var view: View? = null
    onView(ViewMatchers.withId(R.id.appBar))
      .perform(ViewActions.swipeDown())
      .check { v, _ ->
        view = v
      }
    snap(view!!)
  }

  private fun init(
    icon: Boolean = false,
    menu: Boolean = false,
  ) {
    rule.scenario.waitForActivity().also {
      with(it) {
        runOnUi {
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
  }
}
