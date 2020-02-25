package net.skyscanner.backpack.docs

import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import android.content.Intent
import androidx.appcompat.app.AppCompatDelegate
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import net.skyscanner.backpack.demo.*
import org.junit.Rule

@RunWith(Parameterized::class)
open class GenerateScreenshots(
  private val componentPath: String,
  private val screenshotName: String,
  private val setup: (() -> Unit)?
) {

  companion object {
    @JvmStatic
    @Parameterized.Parameters(name = "{0} Screenshot")
    fun data() = DocsRegistry.screenshots
  }

  @get:Rule
  var activityRule = ActivityTestRule(ComponentDetailActivity::class.java, true, false)

  private val screenshotFullName: String
    get() {
      val componentName = componentPath.split(" - ").first()
      return "${componentName.replace(" ", "")}_$screenshotName"
    }

  private val screenGrab by lazy {
    val serverIp = InstrumentationRegistry.getArguments().getString("screenshotServer")
      ?: throw IllegalStateException("screenshotServer argument not provided or null")

    RemoteScreenGrab(serverIp)
  }

  @Test
  fun testTakeScreenshot() {
    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    runActivityAndTakeScreenshot()
  }

  @Test
  fun testTakeScreenshotDm() {
    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
    runActivityAndTakeScreenshot("dm")
  }

  private fun runActivityAndTakeScreenshot(suffix: String? = null) {
    val intent = Intent()
    intent.putExtra(ComponentDetailFragment.ARG_ITEM_ID, componentPath)
    intent.putExtra(ComponentDetailFragment.AUTOMATION_MODE, true)
    activityRule.launchActivity(intent)
    setup?.invoke()
    takeScreenshot(suffix)
    activityRule.finishActivity()
  }

  private fun takeScreenshot(suffix: String? = null) {
    val name = if (suffix != null) {
      "${screenshotFullName}_$suffix"
    } else {
      screenshotFullName
    }
    screenGrab.takeScreenshot(name)
  }
}
