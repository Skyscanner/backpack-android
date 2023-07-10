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

package net.skyscanner.backpack.compose

import android.view.View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
import android.view.View.SYSTEM_UI_FLAG_LAYOUT_STABLE
import android.view.WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ProvidedValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.takeOrElse
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.junit4.createEmptyComposeRule
import androidx.compose.ui.unit.Dp
import androidx.test.core.app.launchActivity
import androidx.test.platform.app.InstrumentationRegistry
import com.karumi.shot.ScreenshotTest
import net.skyscanner.backpack.BpkTestVariant
import net.skyscanner.backpack.SnapshotUtil.screenshotName
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.demo.BackpackDemoTheme
import org.junit.Rule

open class BpkSnapshotTest(private val tags: List<Any> = emptyList()) : ScreenshotTest {

    private val variant = BpkTestVariant.current
    var testContext = variant.newContext(InstrumentationRegistry.getInstrumentation().targetContext)

    @get:Rule
    val composeTestRule = createEmptyComposeRule()

    protected fun snap(
        background: @Composable () -> Color = { Color.Unspecified },
        width: Dp = Dp.Unspecified,
        height: Dp = Dp.Unspecified,
        padding: Dp = BpkSpacing.Md,
        vararg providers: ProvidedValue<*>,
        assertion: ComposeTestRule.() -> Unit = {},
        content: @Composable () -> Unit,
    ) {
        snap(
            background = background,
            width = width,
            height = height,
            padding = padding,
            providers = providers,
            comparison = { name ->
                assertion()
                waitForIdle()
                compareScreenshot(composeTestRule, name)
            },
            content = content,
        )
    }

    protected fun snap(
        background: @Composable () -> Color = { Color.Unspecified },
        width: Dp = Dp.Unspecified,
        height: Dp = Dp.Unspecified,
        padding: Dp = BpkSpacing.Md,
        vararg providers: ProvidedValue<*>,
        comparison: ComposeTestRule.(String?) -> Unit,
        content: @Composable () -> Unit,
    ) {
        val scenario = launchActivity<AppCompatActivity>()
        scenario.onActivity { activity ->
            activity.setContent {
                @Suppress("DEPRECATION")
                activity.window.clearFlags(
                    FLAG_TRANSLUCENT_STATUS or
                        SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                        SYSTEM_UI_FLAG_LAYOUT_STABLE,
                )
                BackpackDemoTheme {
                    CompositionLocalProvider(*providers) {
                        Box(
                            Modifier
                                .size(width, height)
                                .background(background().takeOrElse { BpkTheme.colors.canvas })
                                .padding(padding),
                        ) {
                            content()
                        }
                    }
                }
            }
        }
        composeTestRule.waitForIdle()
        comparison(composeTestRule, screenshotName(tags))

        scenario.close()
    }
}
