package net.skyscanner.backpack.compose

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
import com.facebook.testing.screenshot.Screenshot
import com.facebook.testing.screenshot.ViewHelpers
import com.facebook.testing.screenshot.internal.TestNameDetector
import net.skyscanner.backpack.BpkTestVariant
import net.skyscanner.backpack.compose.theme.BpkTheme

interface BpkComposeTest {

  fun composable(
    width: Dp = 100.dp,
    height: Dp = 100.dp,
    variant: BpkTestVariant = BpkTestVariant.current,
    background: Color = Color.Unspecified,
    content: @Composable () -> Unit,
  ) {

    val context = variant.newContext(InstrumentationRegistry.getInstrumentation().targetContext)
    val view = ComposeView(context)

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

    ViewHelpers.setupView(view)
      .layout()

    Screenshot.snap(view)
      .setName(getScreenshotName())
      .record()

  }

  private fun getScreenshotName(
    testClass: String = TestNameDetector.getTestClass(),
    testName: String = TestNameDetector.getTestName(),
  ): String =
    "${testClass.removePrefix("net.skyscanner.backpack.")}_$testName"

}
