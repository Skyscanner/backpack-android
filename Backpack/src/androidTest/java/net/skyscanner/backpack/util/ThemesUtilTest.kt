package net.skyscanner.backpack.util

import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import net.skyscanner.backpack.test.R
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ThemesUtilTest {
  private lateinit var context: Context
  private lateinit var activity: AppCompatActivity

  @get:Rule
  internal var activityRule: ActivityTestRule<TestActivity> =
    ActivityTestRule(TestActivity::class.java)

  @Before
  fun setUp() {
    context = InstrumentationRegistry.getInstrumentation().targetContext
    activity = activityRule.activity
  }

  @Test
  fun test_color_not_set() {
    activity.setTheme(R.style.TestThemeOne)
    Assert.assertEquals(ContextCompat.getColor(activity, R.color.bpkBlue500), ThemesUtil.getPrimaryColor(activity))
  }

  @Test
  fun test_color_set() {
    activity.setTheme(R.style.TestThemeTwo)
    Assert.assertEquals(Color.parseColor("#9B104C"), ThemesUtil.getPrimaryColor(activity))
  }

  @Test
  fun test_color_set_as_reference() {
    activity.setTheme(R.style.TestThemeThree)
    Assert.assertEquals(ContextCompat.getColor(activity, R.color.bpkGreen400), ThemesUtil.getPrimaryColor(activity))
  }
}
