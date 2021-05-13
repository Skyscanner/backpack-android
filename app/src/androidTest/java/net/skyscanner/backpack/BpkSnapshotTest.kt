/**
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2018-2021 Skyscanner Ltd
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

import android.content.Context
import android.graphics.Canvas
import android.os.Looper
import android.view.View
import androidx.annotation.ColorRes
import androidx.annotation.Dimension
import androidx.annotation.Dimension.DP
import androidx.test.platform.app.InstrumentationRegistry
import com.facebook.testing.screenshot.Screenshot
import com.facebook.testing.screenshot.ViewHelpers
import com.facebook.testing.screenshot.internal.TestNameDetector

open class BpkSnapshotTest {

  private var height = 100
  private var width = 100
  private var background = R.color.bpkBackground

  private val variant = BpkTestVariant.current
  var testContext = variant.newContext(InstrumentationRegistry.getInstrumentation().targetContext)

  protected fun setupView(view: View) {
    view.layoutDirection = testContext.resources.configuration.layoutDirection
    ViewHelpers.setupView(view)
      .setExactHeightDp(height)
      .setExactWidthDp(width)
      .layout()
  }

  protected fun snap(view: View) {
    setupView(view)
    Screenshot.snap(wrapMeasuredViewWithBackground(view))
      .setName(getScreenshotName())
      .record()
  }

  protected fun setDimensions(@Dimension(unit = DP) height: Int, @Dimension(unit = DP) width: Int) {
    this.height = height
    this.width = width
  }

  protected fun setBackground(@ColorRes background: Int) {
    this.background = background
  }

  protected fun prepareForAsyncTest(): AsyncSnapshot {
    if (Looper.myLooper() == null) {
      Looper.prepare()
    }

    val testClass = TestNameDetector.getTestClass()
    val testName = TestNameDetector.getTestName()
    return AsyncSnapshot(testClass, testName)
  }

  inner class AsyncSnapshot(private val testClass: String, private val testName: String) {
    fun record(view: View) {
      Screenshot.snap(wrapMeasuredViewWithBackground(view))
        .setName(getScreenshotName(testClass, testName))
        .record()
    }
  }

  private fun getScreenshotName(
    testClass: String = TestNameDetector.getTestClass(),
    testName: String = TestNameDetector.getTestName(),
  ): String =
    "${testClass.removePrefix("net.skyscanner.backpack.")}_$testName"

  private fun wrapMeasuredViewWithBackground(view: View): View {
    val result = ViewMirror(view.context, view)
    result.setBackgroundResource(background)

    ViewHelpers.setupView(result)
      .setExactHeightDp(height)
      .setExactWidthDp(width)
      .layout()

    return result
  }

  // adding view to FrameLayout or creating custom ViewGroup breaks many tests for some reason.
  // instead we use custom drawing here via proxy view
  private class ViewMirror constructor(
    context: Context,
    private val view: View,
  ) : View(context) {

    init {
      view.jumpDrawablesToCurrentState() // this is for views with custom drawable state, such as checkboxes, radios, etc
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
      setMeasuredDimension(view.measuredWidth, view.measuredHeight) // we want the canvas to have the exact same size
    }

    override fun dispatchDraw(canvas: Canvas) {
      super.dispatchDraw(canvas)
      view.draw(canvas)
    }
  }
}
