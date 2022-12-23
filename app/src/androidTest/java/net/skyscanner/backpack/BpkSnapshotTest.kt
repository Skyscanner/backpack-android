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

import android.os.Looper
import android.view.View
import android.view.View.MeasureSpec.makeMeasureSpec
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.ColorRes
import androidx.annotation.Dimension
import androidx.annotation.Dimension.Companion.DP
import androidx.core.view.setPadding
import androidx.test.platform.app.InstrumentationRegistry
import com.karumi.shot.ScreenshotTest
import net.skyscanner.backpack.SnapshotUtil.screenshotName
import org.junit.Before

open class BpkSnapshotTest(private val tags: List<Any> = emptyList()) : ScreenshotTest {

  private val variant = BpkTestVariant.current
  var testContext = variant.newContext(InstrumentationRegistry.getInstrumentation().targetContext)

  @Before
  fun initLooper() {
    if (Looper.myLooper() == null) {
      Looper.prepare()
    }
  }

  protected fun snap(
    view: View,
    @ColorRes background: Int = R.color.bpkCanvas,
    @Dimension(unit = DP) width: Int? = null,
    @Dimension(unit = DP) height: Int? = null,
    @Dimension(unit = DP) padding: Int = testContext.resources.getDimensionPixelSize(R.dimen.bpkSpacingMd),
    // wrapView should almost always be true - in some rare cases (like Snackbar) wrapping the view may break.
    // padding + background won't be applied if used
    wrapView: Boolean = true,
  ) {
    runOnUi {
      view.layoutDirection = testContext.resources.configuration.layoutDirection
    }
    val wrappedView = wrapMeasuredViewWithBackground(
      view = view,
      background = background,
      padding = padding,
      wrapView = wrapView,
    )
    wrappedView.measure(measureSpec(width), measureSpec(height))
    compareScreenshot(
      view = wrappedView,
      widthInPx = wrappedView.measuredWidth,
      heightInPx = wrappedView.measuredHeight,
      name = screenshotName(tags),
    )
  }

  private fun measureSpec(size: Int?): Int {
    val dimenSize = if (size == null) {
      0
    } else {
      (size * testContext.resources.displayMetrics.density).toInt()
    }
    return makeMeasureSpec(dimenSize, if (size == null) View.MeasureSpec.UNSPECIFIED else View.MeasureSpec.EXACTLY)
  }

  private fun wrapMeasuredViewWithBackground(view: View, background: Int, padding: Int, wrapView: Boolean): View {
    if (!wrapView) {
      return view
    }
    val result = FrameLayout(view.context)
    if (view.parent != null) {
      runOnUi {
        (view.parent as ViewGroup).removeView(view)
      }
    }
    result.addView(view)
    result.setBackgroundResource(background)
    result.setPadding(padding)

    return result
  }
}
