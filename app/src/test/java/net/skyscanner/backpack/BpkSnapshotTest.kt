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

import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.activity.ComponentActivity
import androidx.annotation.ColorRes
import androidx.annotation.Dimension
import androidx.annotation.Dimension.Companion.DP
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.core.view.setPadding
import androidx.test.platform.app.InstrumentationRegistry
import com.github.takahirom.roborazzi.RobolectricDeviceQualifiers
import com.github.takahirom.roborazzi.RoborazziRule
import com.github.takahirom.roborazzi.captureRoboImage
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
    val roborazziRule = RoborazziRule(
        options = SnapshotUtil.roborazziOptions(variant.id, tags),
    )

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Before
    fun setQualifiers() {
        composeTestRule.activity.setTheme(variant.themeId)
        if (variant.qualifier != null) {
            RuntimeEnvironment.setQualifiers(variant.qualifier)
        }
    }

    protected fun snap(
        view: View,
        @ColorRes background: Int = R.color.bpkCanvas,
        @Dimension(unit = DP) width: Int? = null,
        @Dimension(unit = DP) height: Int? = null,
        @Dimension(unit = DP) padding: Int = testContext.resources.getDimensionPixelSize(R.dimen.bpkSpacingMd),
        captureFullScreen: Boolean = false,
    ) {
        if (tags.isNotEmpty()) {
            SnapshotUtil.filterTest(variant)
        }
        view.layoutDirection = testContext.resources.configuration.layoutDirection
        val wrappedView = wrapMeasuredViewWithBackground(
            view = view,
            background = background,
            padding = padding,
        )
        wrappedView.layoutParams = ViewGroup.LayoutParams(measureSpec(width), measureSpec(height))
        if (captureFullScreen) {
            composeTestRule.activity.setContentView(wrappedView)
            wrappedView.captureRoboImage()
        } else {
            val container = FrameLayout(view.context)
            container.addView(wrappedView)
            composeTestRule.activity.setContentView(container)
            wrappedView.captureRoboImage()
        }
    }

    private fun measureSpec(size: Int?): Int {
        return if (size == null) ViewGroup.LayoutParams.WRAP_CONTENT else dpToPx(size)
    }

    private fun wrapMeasuredViewWithBackground(view: View, background: Int, padding: Int): View {
        val result = FrameLayout(view.context)
        if (view.parent != null) {
            (view.parent as ViewGroup).removeView(view)
            view.visibility = View.VISIBLE
        }
        result.addView(view)
        result.setBackgroundResource(background)
        result.setPadding(padding)

        return result
    }

    fun dpToPx(@Dimension dp: Int): Int {
        return (dp * testContext.resources.displayMetrics.density).toInt()
    }
}
