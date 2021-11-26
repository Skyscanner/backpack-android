package net.skyscanner.backpack

import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.takeOrElse
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.facebook.testing.screenshot.Screenshot
import com.facebook.testing.screenshot.internal.TestNameDetector
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.demo.MainActivity
import org.junit.Rule

abstract class BpkComposeTest {

  private lateinit var activity: AppCompatActivity

  @get:Rule
  var activityRule: ActivityTestRule<MainActivity> =
    ActivityTestRule(MainActivity::class.java)

  fun composable(
    width: Dp = 100.dp,
    height: Dp = 100.dp,
    variant: BpkTestVariant = BpkTestVariant.current,
    background: Color = Color.Unspecified,
    content: @Composable () -> Unit,
  ) {

    if (Looper.myLooper() == null) {
      Looper.prepare()
    }

    val testClass = TestNameDetector.getTestClass()
    val testName = TestNameDetector.getTestName()

    activity.runOnUiThread {
      val context = variant.newContext(InstrumentationRegistry.getInstrumentation().targetContext)
      val view = ComposeView(context)
      activity.setContentView(view)

      view.setContent {
        BpkTheme {
          Box(
            Modifier
              .size(width, height)
              .background(background.takeOrElse { BpkTheme.colors.background })
          ) {
            content()
          }
        }
      }

      Screenshot.snap(view)
        .setName(getScreenshotName(testClass, testName))
        .record()
    }
  }

  private fun getScreenshotName(
    testClass: String = TestNameDetector.getTestClass(),
    testName: String = TestNameDetector.getTestName(),
  ): String =
    "${testClass.removePrefix("net.skyscanner.backpack.")}_$testName"
}
