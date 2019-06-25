package net.skyscanner.backpack.button

import android.app.Activity
import androidx.appcompat.content.res.AppCompatResources
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import net.skyscanner.backpack.R
import net.skyscanner.backpack.util.TestActivity
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BpkButtonTest {

  private lateinit var activity: Activity

  @get:Rule
  internal var activityRule: ActivityTestRule<TestActivity> =
    ActivityTestRule(TestActivity::class.java)

  @Before
  fun setUp() {
    activity = activityRule.activity
  }

  @Test
  fun test_message() {
    val button = BpkButton(activity).apply {
      text = "Message"
    }
    Assert.assertEquals("Message", button.text.toString())
  }

  // The drawables are set as start,top,end,bottom and are accessible in the compoundDrawables array
  @Test
  fun test_icon_end() {
    val trainIcon = AppCompatResources.getDrawable(activity, R.drawable.bpk_train)
    val button = BpkButton(activity).apply {
      icon = trainIcon
      iconPosition = BpkButton.END
    }

    Assert.assertNotNull(button.compoundDrawablesRelative[2])
  }

  @Test
  fun test_icon_start() {
    val trainIcon = AppCompatResources.getDrawable(activity, R.drawable.bpk_train)
    val button = BpkButton(activity).apply {
      icon = trainIcon
      iconPosition = BpkButton.START
    }
    Assert.assertNotNull(button.compoundDrawablesRelative[0])
  }

  @Test
  fun test_enabled_state() {
    // android.util.AndroidRuntimeException: Animators may only be run on Looper threads
    activity.runOnUiThread {
      val button = BpkButton(activity, BpkButton.Type.Primary).apply {
        isEnabled = true
      }
      val newState = false
      val expectedBackgroundState = button.disabledBackground()
      button.isEnabled = newState

      Assert.assertEquals(newState, button.isEnabled)
      Assert.assertEquals(expectedBackgroundState.bounds, button.background.bounds)
      Assert.assertEquals(expectedBackgroundState.alpha, button.background.alpha)
      Assert.assertEquals(expectedBackgroundState.colorFilter, button.background.colorFilter)
    }
  }
}
