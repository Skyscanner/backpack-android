package net.skyscanner.backpack

import android.view.ViewGroup
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ProvidedValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.takeOrElse
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.test.core.app.ActivityScenario
import com.facebook.testing.screenshot.Screenshot
import com.facebook.testing.screenshot.ViewHelpers
import com.facebook.testing.screenshot.internal.TestNameDetector.getTestClass
import com.facebook.testing.screenshot.internal.TestNameDetector.getTestName
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.demo.MainActivity

interface BpkComposeTest {

  fun composed(
    size: IntSize = IntSize(100, 100),
    background: Color = Color.Unspecified,
    vararg providers: ProvidedValue<*>,
    content: @Composable () -> Unit,
  ) {

    val screenshotName = "${getTestClass().removePrefix("net.skyscanner.backpack.")}_${getTestName()}"

    ActivityScenario.launch(MainActivity::class.java).use { scenario ->
      scenario.onActivity { activity ->
        with(activity) {
          setContent {
            BpkTheme {
              CompositionLocalProvider(*providers) {
                Box(
                  modifier = Modifier
                    .size(size.width.dp, size.height.dp)
                    .background(background.takeOrElse { BpkTheme.colors.background }),
                  content = { content() },
                )
              }
            }
          }

          val view = (window.decorView as ViewGroup).getChildAt(0)

          ViewHelpers.setupView(view)
            .setExactWidthDp(size.width)
            .setExactHeightDp(size.height)
            .layout()

          Screenshot.snap(view)
            .setName(screenshotName)
            .record()
        }
      }
    }
  }
}
