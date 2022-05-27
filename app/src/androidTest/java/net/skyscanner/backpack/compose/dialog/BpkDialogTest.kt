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

import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ViewRootForTest
import androidx.compose.ui.test.SemanticsNodeInteraction
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

      val view = composeTestRule.onNode(isDialog()).fetchRootView()

      runOnUiThread {
        // This is not ideal but we need to see the background contrast as well
        val viewRoot = view.parent as ViewGroup
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

  private fun SemanticsNodeInteraction.fetchRootView(): View {
    val node = fetchSemanticsNode()
    return (node.root as ViewRootForTest).view
  }
}
