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

package net.skyscanner.backpack.dialog

import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ScrollView
import androidx.appcompat.app.AppCompatActivity
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.RootMatchers.isDialog
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.squareup.picasso.Picasso
import net.skyscanner.backpack.BpkSnapshotTest
import net.skyscanner.backpack.button.BpkButton
import net.skyscanner.backpack.demo.R
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BpkDialogTest : BpkSnapshotTest() {

  @Before
  fun setUp() {
    setDimensions(600, 420)
  }

  @Test
  fun default() {
    val asyncScreenshot = prepareForAsyncTest()

    val scenario = launchActivity<AppCompatActivity>()
    var dialog: BpkDialog? = null
    scenario.onActivity { activity ->
      dialog = BpkDialog(activity, BpkDialog.Type.Success).apply {
        title = "You are going to Tokyo!"
        description = "Your flight is all booked. Why not check out some hotels now?"
        icon = BpkDialog.Icon(R.drawable.bpk_tick)

        addActionButton(
          BpkDialog.Button("Continue") { }
        )

        addActionButton(
          BpkDialog.Button("Skip") { }
        )
      }
    }

    record(scenario, dialog!!, asyncScreenshot)
  }

  @Test
  fun fullscreen() {
    val asyncScreenshot = prepareForAsyncTest()

    val scenario = launchActivity<AppCompatActivity>()
    var dialog: BpkDialog? = null
    scenario.onActivity { activity ->
      dialog = BpkDialog(activity, BpkDialog.Type.Success).apply {
        title = "You are going to Tokyo!"
        description = Array(30) {
          "Your flight is all booked. Why not check out some hotels now?"
        }.joinToString(separator = "\n")

        icon = BpkDialog.Icon(R.drawable.bpk_tick)

        addActionButton(
          BpkDialog.Button("Continue") { }
        )

        addActionButton(
          BpkDialog.Button("Skip") { }
        )
      }
    }

    record(scenario, dialog!!, asyncScreenshot)
  }

  @Test
  fun destructive() {
    val asyncScreenshot = prepareForAsyncTest()

    val scenario = launchActivity<AppCompatActivity>()
    var dialog: BpkDialog? = null
    scenario.onActivity { activity ->
      dialog = BpkDialog(activity, BpkDialog.Type.Destructive).apply {
        title = "Delete?"
        description = "Delete your profile?"
        icon = BpkDialog.Icon(R.drawable.bpk_trash)

        addActionButton(
          BpkDialog.Button("Delete") { }
        )

        addActionButton(
          BpkDialog.Button("Cancel") { }
        )
      }
    }

    record(scenario, dialog!!, asyncScreenshot)
  }

  @Suppress("DEPRECATION")
  @Test
  fun deprecated() {
    val asyncScreenshot = prepareForAsyncTest()

    val scenario = launchActivity<AppCompatActivity>()
    var dialog: BpkDialog? = null
    scenario.onActivity { activity ->
      dialog = BpkDialog(activity, BpkDialog.Style.ALERT).apply {
        title = "Delete?"
        description = "Delete your profile?"
        icon = BpkDialog.Icon(R.drawable.bpk_trash, activity.getColor(R.color.bpkValensole))

        addActionButton(
          BpkButton(activity).apply {
            type = BpkButton.Type.Secondary
            text = "Secondary"
          }
        )
        addActionButton(
          BpkButton(activity).apply {
            type = BpkButton.Type.Destructive
            text = "Destructive"
          }
        )
        addActionButton(
          BpkButton(activity).apply {
            type = BpkButton.Type.Featured
            text = "Featured"
          }
        )
      }
    }

    record(scenario, dialog!!, asyncScreenshot)
  }

  @Test
  fun warning() {
    val asyncScreenshot = prepareForAsyncTest()

    val scenario = launchActivity<AppCompatActivity>()
    var dialog: BpkDialog? = null
    scenario.onActivity { activity ->
      dialog = BpkDialog(activity, BpkDialog.Type.Warning)
      dialog!!.apply {
        title = "Want to know when prices change?"
        description = "Create a price alert and we'll let you know changes for this route"
        icon = BpkDialog.Icon(R.drawable.bpk_trash)

        addActionButton(
          BpkDialog.Button("Create") { }
        )

        addActionButton(
          BpkDialog.Button("No, Thanks!") { }
        )
      }
    }

    record(scenario, dialog!!, asyncScreenshot)
  }

  @Test
  fun flare() {
    val bitmap = Picasso.get().load("file:///android_asset/dialog_sample.jpg").get()
    val asyncScreenshot = prepareForAsyncTest()

    val scenario = launchActivity<AppCompatActivity>()
    var dialog: BpkDialog? = null
    scenario.onActivity { activity ->
      dialog = BpkDialog(activity, BpkDialog.Type.Flare).apply {
        title = "You are going to Tokyo!"
        description = "Your flight is all booked."
        icon = BpkDialog.Icon(R.drawable.bpk_tick)

        image!!.setImageBitmap(bitmap)

        addActionButton(
          BpkDialog.Button("Continue") { }
        )

        addActionButton(
          BpkDialog.Button("Skip") { }
        )
      }
    }

    record(scenario, dialog!!, asyncScreenshot)
  }

  private fun record(scenario: ActivityScenario<AppCompatActivity>, dialog: BpkDialog, asyncScreenshot: AsyncSnapshot) {
    // not ideal, but the scrollbar disappears too early when running on CI causing test failures if visible
    dialog.window?.decorView?.findScrollView()?.scrollBarDefaultDelayBeforeFade = 5000

    scenario.onActivity {
      dialog.show()
    }

    onView(withId(R.id.dialog_buttons_root))
      .inRoot(isDialog())
      .check { _, _ ->
        // This is not ideal but I couldn't find a way to snapshot the whole window and we need contrast to
        // see the rounded corners

        scenario.onActivity { activity ->
          val rootView = dialog.window!!.decorView
          activity.windowManager.removeView(rootView)

          val wrapper = FrameLayout(activity)
          wrapper.layoutParams = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.WRAP_CONTENT,
            FrameLayout.LayoutParams.WRAP_CONTENT
          )
          wrapper.setPadding(20, 20, 20, 20)
          wrapper.setBackgroundColor(activity.getColor(R.color.bpkTextSecondary))
          wrapper.addView(rootView)

          setupView(wrapper)
          asyncScreenshot.record(wrapper)
        }
      }
  }

  private fun View.findScrollView(): ScrollView? {
    if (this !is ViewGroup) return null
    for (i in 0..childCount) {
      val child = getChildAt(i)
      if (child is ScrollView) {
        return child
      } else if (child is ViewGroup) {
        val view = child.findScrollView()
        if (view != null) {
          return view
        }
      }
    }
    return null
  }
}
