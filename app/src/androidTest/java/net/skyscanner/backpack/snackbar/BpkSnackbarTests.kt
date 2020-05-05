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

package net.skyscanner.backpack.snackbar

import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import net.skyscanner.backpack.BpkSnapshotTest
import net.skyscanner.backpack.createThemedContext
import net.skyscanner.backpack.demo.MainActivity
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.util.unsafeLazy
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BpkSnackbarTests : BpkSnapshotTest() {

  private lateinit var activity: AppCompatActivity

  @get:Rule
  var activityRule: ActivityTestRule<MainActivity> =
    ActivityTestRule(MainActivity::class.java)

  @Before
  fun setUp() {
    setDimensions(400, 350)
    activity = activityRule.activity
  }

  private val root by unsafeLazy {
    FrameLayout(testContext).apply {
      activity.setContentView(this)
    }
  }

  private val themedRoot by unsafeLazy {
    FrameLayout(createThemedContext(testContext)).apply {
      activity.setContentView(this)
    }
  }

  @Test
  fun screenshotTestSnackbar_Default() {
    capture {
      BpkSnackbar.make(root, "Test", BpkSnackbar.LENGTH_INDEFINITE)
    }
  }

  @Test
  fun screenshotTestSnackbar_DefaultWithAction() {
    capture {
      BpkSnackbar.make(root, "Test", BpkSnackbar.LENGTH_INDEFINITE)
        .setAction("Action") {}
    }
  }

  @Test
  fun screenshotTestSnackbar_DefaultThemed() {
    capture {
      BpkSnackbar.make(themedRoot, "Test", BpkSnackbar.LENGTH_INDEFINITE)
    }
  }

  @Test
  fun screenshotTestSnackbar_DefaultWithActionThemed() {
    capture {
      BpkSnackbar.make(themedRoot, "Test", BpkSnackbar.LENGTH_INDEFINITE)
        .setAction("Action") {}
    }
  }

  @Test
  fun screenshotTestSnackbar_withTitle() {
    capture {
      BpkSnackbar.make(root, "Test", BpkSnackbar.LENGTH_INDEFINITE)
        .setTitle("Title")
        .setAction("Action") {}
    }
  }

  @Test
  fun screenshotTestSnackbar_withIcon() {
    capture {
      BpkSnackbar.make(root, "Test", BpkSnackbar.LENGTH_INDEFINITE)
        .setIcon(R.drawable.bpk_tick_circle)
        .setAction("Action") {}
    }
  }

  @Test
  fun screenshotTestSnackbar_withTitleAndIcon() {
    capture {
      BpkSnackbar.make(root, "Test", BpkSnackbar.LENGTH_INDEFINITE)
        .setTitle("Title")
        .setIcon(R.drawable.bpk_tick_circle)
        .setAction("Action") {}
    }
  }

  @Test
  fun screenshotTestSnackbar_iconOnly() {
    capture {
      BpkSnackbar.make(root, "Test", BpkSnackbar.LENGTH_INDEFINITE)
        .setAction(R.drawable.bpk_close) {}
    }
  }

  @Test
  fun screenshotTestSnackbar_iconOnly_withTitle() {
    capture {
      BpkSnackbar.make(root, "Test", BpkSnackbar.LENGTH_INDEFINITE)
        .setTitle("Title")
        .setAction(R.drawable.bpk_close) {}
    }
  }

  @Test
  fun screenshotTestSnackbar_iconOnly_withIcon() {
    capture {
      BpkSnackbar.make(root, "Test", BpkSnackbar.LENGTH_INDEFINITE)
        .setIcon(R.drawable.bpk_tick_circle)
        .setAction(R.drawable.bpk_close) {}
    }
  }

  @Test
  fun screenshotTestSnackbar_iconOnly_withTitleAndIcon() {
    capture {
      BpkSnackbar.make(root, "Test", BpkSnackbar.LENGTH_INDEFINITE)
        .setTitle("Title")
        .setIcon(R.drawable.bpk_tick_circle)
        .setAction(R.drawable.bpk_close) {}
    }
  }

  private inline fun capture(crossinline what: AsyncSnapshot.() -> BpkSnackbar) {
    val asyncScreenshot = prepareForAsyncTest()
    activity.runOnUiThread {
      what(asyncScreenshot)
        .addCallback(object : BpkSnackbar.Callback() {
          override fun onShown(sb: BpkSnackbar) {
            asyncScreenshot.record(sb.rawSnackbar.view)
          }
        })
        .show()
    }
  }
}
