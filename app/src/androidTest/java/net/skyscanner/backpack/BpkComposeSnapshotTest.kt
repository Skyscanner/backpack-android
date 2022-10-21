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

package net.skyscanner.backpack

import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidedValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.test.junit4.createEmptyComposeRule
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.test.core.app.launchActivity
import androidx.test.platform.app.InstrumentationRegistry
import com.karumi.shot.ScreenshotTest
import net.skyscanner.backpack.SnapshotUtil.screenshotName
import net.skyscanner.backpack.demo.compose.BackpackPreview
import org.junit.Assume
import org.junit.Rule

open class BpkComposeSnapshotTest : ScreenshotTest {

  var snapshotSize = IntSize(100, 100)

  private val variant = BpkTestVariant.current
  var testContext = variant.newContext(InstrumentationRegistry.getInstrumentation().targetContext)

  @get:Rule
  val composeTestRule = createEmptyComposeRule()

  protected fun composed(
    size: IntSize = this.snapshotSize,
    background: Color = Color.Unspecified,
    tags: List<Any> = emptyList(),
    vararg providers: ProvidedValue<*>,
    content: @Composable () -> Unit,
  ) {

    // we don't run Compose tests in Themed variant – Compose uses it own theming engine
    Assume.assumeFalse(BpkTestVariant.current == BpkTestVariant.Themed)

    val scenario = launchActivity<AppCompatActivity>()
    scenario.onActivity { activity ->
      activity.setContent {
        BackpackPreview(
          modifier = Modifier.size(size.width.dp, size.height.dp),
          background = background,
          providers = providers,
          content = content,
        )
      }
    }

    compareScreenshot(composeTestRule, screenshotName(tags))
  }
}
