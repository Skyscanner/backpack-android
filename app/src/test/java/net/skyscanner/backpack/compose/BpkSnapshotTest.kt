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

import androidx.activity.ComponentActivity
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
import androidx.compose.ui.test.isRoot
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.unit.Dp
import androidx.test.platform.app.InstrumentationRegistry
import com.github.takahirom.roborazzi.RobolectricDeviceQualifiers
import com.github.takahirom.roborazzi.RoborazziRule
import com.github.takahirom.roborazzi.captureRoboImage
import com.github.takahirom.roborazzi.captureScreenRoboImage
import net.skyscanner.backpack.BpkTestRunner
import net.skyscanner.backpack.BpkTestVariant
import net.skyscanner.backpack.SnapshotUtil
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.demo.BackpackDemoTheme
import org.junit.Assume.assumeFalse
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode

@RunWith(BpkTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(qualifiers = RobolectricDeviceQualifiers.SmallPhone, sdk = [33])
abstract class BpkSnapshotTest(private val tags: List<Any> = emptyList()) {

    private val variant = BpkTestVariant.current
    var testContext = variant.newContext(InstrumentationRegistry.getInstrumentation().targetContext)

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @get:Rule
    val roborazziRule = RoborazziRule(
        composeRule = composeTestRule,
        captureRoot = composeTestRule.onRoot(),
        options = SnapshotUtil.roborazziOptions(variant.id, tags),
    )

    @Before
    fun setQualifiers() {
        if (variant.qualifier != null) {
            RuntimeEnvironment.setQualifiers(variant.qualifier)
        }
    }

    protected fun snap(
        background: @Composable () -> Color = { Color.Unspecified },
        width: Dp = Dp.Unspecified,
        height: Dp = Dp.Unspecified,
        padding: Dp = BpkSpacing.Md,
        vararg providers: ProvidedValue<*>,
        assertion: ComposeTestRule.() -> Unit = {},
        captureFullScreen: Boolean = false,
        content: @Composable () -> Unit,
    ) {
        if (variant == BpkTestVariant.Themed) {
            assumeFalse("Not running tests for compose themed variants", true)
        } else if (tags.isNotEmpty()) {
            SnapshotUtil.filterTest(variant)
        }
        composeTestRule.setContent {
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
        composeTestRule.waitForIdle()
        assertion(composeTestRule)
        if (captureFullScreen) {
            captureScreenRoboImage()
        } else {
            composeTestRule.onNode(isRoot()).captureRoboImage()
        }
    }
}
