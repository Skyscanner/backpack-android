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

package net.skyscanner.backpack.compose.dialog

import android.view.ViewGroup
import android.view.ViewParent
import android.widget.FrameLayout
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.isDialog
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import net.skyscanner.backpack.BpkSnapshotTest
import net.skyscanner.backpack.BpkTestVariant
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.demo.compose.BackpackPreview
import net.skyscanner.backpack.demo.compose.DestructiveDialogExample
import net.skyscanner.backpack.demo.compose.NoIconDialogExample
import net.skyscanner.backpack.demo.compose.SuccessOneButtonDialogExample
import net.skyscanner.backpack.demo.compose.SuccessThreeButtonsDialogExample
import net.skyscanner.backpack.demo.compose.SuccessTwoButtonsDialogExample
import net.skyscanner.backpack.demo.compose.WarningDialogExample
import org.junit.Assume
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.lang.reflect.Field

@RunWith(AndroidJUnit4::class)
class BpkDialogTest : BpkSnapshotTest() {

  @get:Rule
  var activityRule = ActivityTestRule(AppCompatActivity::class.java, true, false)

  @get:Rule
  val composeTestRule = AndroidComposeTestRule(activityRule) { it.activity }

  @Before
  fun setup() {
    setDimensions(height = 600, width = 420)
  }

  @Test
  fun successOneButton() = record {
    SuccessOneButtonDialogExample()
  }

  @Test
  fun successTwoButtons() {
    assumeVariant(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    record {
      SuccessTwoButtonsDialogExample()
    }
  }

  @Test
  fun successThreeButtons() {
    assumeVariant(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    record {
      SuccessThreeButtonsDialogExample()
    }
  }

  @Test
  fun warning() {
    assumeVariant(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    record {
      WarningDialogExample()
    }
  }

  @Test
  fun destructive() {
    assumeVariant(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    record {
      DestructiveDialogExample()
    }
  }

  @Test
  fun noIcon() {
    assumeVariant(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    record {
      NoIconDialogExample()
    }
  }

  private fun record(content: @Composable () -> Unit) {
    // we don't run Compose tests in Themed variant – Compose uses it own theming engine
    Assume.assumeFalse(BpkTestVariant.current == BpkTestVariant.Themed)

    val asyncScreenshot = prepareForAsyncTest()
    with(activityRule.launchActivity(null)) {
      runOnUiThread {
        setContent {
          BackpackPreview(
            content = content,
          )
        }
      }

      composeTestRule.onNode(isDialog()).assertIsDisplayed()

      runOnUiThread {
        // This is not ideal but we need to see the background contrast as well
        val viewRoot = getViewRoots().first { it.hasWindowFocus() }
        val view = viewRoot.getChildAt(0)

        viewRoot.removeView(view)

        val wrapper = FrameLayout(this)
        wrapper.layoutParams = FrameLayout.LayoutParams(
          FrameLayout.LayoutParams.WRAP_CONTENT,
          FrameLayout.LayoutParams.WRAP_CONTENT
        )
        wrapper.setPadding(20, 20, 20, 20)
        wrapper.setBackgroundColor(getColor(R.color.bpkTextSecondary))
        wrapper.addView(view)

        setupView(wrapper)
        asyncScreenshot.record(wrapper)
      }
    }
  }

  // we need this to be able to get the dialog root, rather than the window root
  private fun getViewRoots(): List<ViewGroup> {
    val viewRoots: MutableList<ViewGroup> = ArrayList()
    try {
      val windowManager: Any = Class.forName("android.view.WindowManagerGlobal")
        .getMethod("getInstance").invoke(null) as Any
      val rootsField: Field = windowManager.javaClass.getDeclaredField("mRoots")
      rootsField.isAccessible = true
      val stoppedField: Field = Class.forName("android.view.ViewRootImpl")
        .getDeclaredField("mStopped")
      stoppedField.isAccessible = true

      val viewField: Field = Class.forName("android.view.ViewRootImpl")
        .getDeclaredField("mView")
      viewField.isAccessible = true
      val viewParents = rootsField.get(windowManager) as List<ViewParent>
      // Filter out inactive view roots
      for (viewParent in viewParents) {
        val stopped = stoppedField.get(viewParent) as Boolean
        val view = viewField.get(viewParent) as ViewGroup?
        if (!stopped && view != null) {
          viewRoots.add(view)
        }
      }
    } catch (e: Exception) {
      e.printStackTrace()
    }
    return viewRoots
  }
}
