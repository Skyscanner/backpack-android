package net.skyscanner.backpack.dialog

import androidx.appcompat.app.AppCompatActivity
import android.widget.FrameLayout
import androidx.core.content.res.ResourcesCompat
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.RootMatchers.isDialog
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.FlakyTest
import androidx.test.rule.ActivityTestRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import net.skyscanner.backpack.BpkSnapshotTest
import net.skyscanner.backpack.button.BpkButton
import net.skyscanner.backpack.demo.MainActivity
import net.skyscanner.backpack.demo.R
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BpkDialogTest : BpkSnapshotTest() {

  private lateinit var activity: AppCompatActivity

  @get:Rule
  var activityRule: ActivityTestRule<MainActivity> =
    ActivityTestRule(MainActivity::class.java)

  @Before
  fun setUp() {
    setDimensions(400, 350)
    activity = activityRule.activity
  }

  @Test
  @FlakyTest
  fun screenshotTestDialog() {
    val asyncScreenshot = prepareForAsyncTest()

    val dialog = BpkDialog(activity).apply {
      title = "You are going to Tokyo!"
      description = "Your flight is all booked. Why not check out some hotels now?"
      icon = BpkDialog.Icon(
        R.drawable.bpk_tick,
        R.color.bpkGreen500
      )

      addActionButton(BpkButton(context).apply {
        text = "Continue"
      })

      addActionButton(BpkButton(context, BpkButton.Type.Secondary).apply {
        text = "Skip"
      })
    }

    record(dialog, asyncScreenshot)
  }

  @Test
  @FlakyTest
  fun screenshotTestDialogBottomSheet() {
    val asyncScreenshot = prepareForAsyncTest()

    val dialog = BpkDialog(activity, BpkDialog.Style.BOTTOM_SHEET).apply {
      title = "Delete?"
      description = "Delete your profile?"
      icon = BpkDialog.Icon(
        R.drawable.bpk_trash,
        R.color.bpkRed500
      )

      addActionButton(BpkButton(context, BpkButton.Type.Destructive).apply {
        text = "Delete"
      })

      addActionButton(BpkButton(context, BpkButton.Type.Secondary).apply {
        text = "Cancel"
      })
    }

    record(dialog, asyncScreenshot)
  }

  private fun record(dialog: BpkDialog, asyncScreenshot: AsyncSnapshot) {
    activity.runOnUiThread {
      dialog.show()
    }

    onView(withId(R.id.dialog_content_layout))
      .inRoot(isDialog())
      .check { _, _ ->
        // This is not ideal but I couldn't find a way to snapshot the whole window and we need contrast to
        // see the rounded corners

        val rootView = dialog.window!!.decorView
        activity.windowManager.removeView(rootView)

        val wrapper = FrameLayout(activity)
        wrapper.layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT)
        wrapper.setPadding(20, 20, 20, 20)
        wrapper.setBackgroundColor(ResourcesCompat.getColor(activity.resources, R.color.bpkGray500, activity.theme))
        wrapper.addView(rootView)

        setupView(wrapper)
        asyncScreenshot.record(wrapper)
      }
  }
}
