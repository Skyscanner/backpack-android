package net.skyscanner.backpack.dialog

import androidx.appcompat.app.AppCompatActivity
import android.os.Handler
import android.os.Looper
import android.view.Gravity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.FlakyTest
import androidx.test.rule.ActivityTestRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import net.skyscanner.backpack.R
import net.skyscanner.backpack.button.BpkButton
import net.skyscanner.backpack.util.TestActivity
import org.junit.Test
import org.junit.Rule
import org.junit.Before
import org.junit.Assert
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
      addActionButton(BpkButton(mActivity).apply {
        text = "Confirm"
      })
    }

    handler.post {
      dialog.show()
    }

    onView(withText("Confirm"))
      .check(matches(isDisplayed()))
  }

  @Test
  @FlakyTest
  fun test_alert_style() {
    val dialog = BpkDialog(mActivity)

    handler.post {
      dialog.show()
    }

    onView(withId(R.id.dialog_buttons_root))
      .check { _, _ ->
        val gravity = dialog.window.attributes.gravity
        val windowAnimation = dialog.window.attributes.windowAnimations
        Assert.assertEquals(Gravity.CENTER, gravity)
        Assert.assertNotEquals(R.style.Bpk_dialog_animation, windowAnimation)
      }
  }

  @Test
  @FlakyTest
  fun test_bottom_sheet_style() {
    val dialog = BpkDialog(mActivity, BpkDialog.Style.BOTTOM_SHEET)

    handler.post {
      dialog.show()
    }

    onView(withId(R.id.dialog_buttons_root))
      .check { _, _ ->
        val gravity = dialog.window.attributes.gravity
        val windowAnimation = dialog.window.attributes.windowAnimations
        Assert.assertEquals(Gravity.BOTTOM, gravity)
        Assert.assertEquals(R.style.Bpk_dialog_animation, windowAnimation)
      }
  }
}
