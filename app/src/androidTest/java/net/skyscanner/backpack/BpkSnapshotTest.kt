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

import android.os.Looper
import android.view.View
import androidx.test.platform.app.InstrumentationRegistry
import com.facebook.testing.screenshot.Screenshot
import com.facebook.testing.screenshot.ViewHelpers
import com.facebook.testing.screenshot.internal.TestNameDetector

open class BpkSnapshotTest {

  private var height = 100
  private var width = 100

  var testContext = BpkTestVariant.current.newContext(InstrumentationRegistry.getInstrumentation().targetContext)

  protected fun setupView(view: View) {
    view.layoutDirection = testContext.resources.configuration.layoutDirection
    ViewHelpers.setupView(view)
      .setExactHeightDp(height)
      .setExactWidthDp(width)
      .layout()
  }

  protected fun snap(view: View) {
    setupView(view)
    Screenshot.snap(view).record()
  }

  protected fun setDimensions(height: Int, width: Int) {
    this.height = height
    this.width = width
  }

  protected fun prepareForAsyncTest(): AsyncSnapshot {
    if (Looper.myLooper() == null) {
      Looper.prepare()
    }

    val testClass = TestNameDetector.getTestClass()
    val testName = TestNameDetector.getTestName()
    return AsyncSnapshot(testClass, testName)
  }

  inner class AsyncSnapshot(private val tesClass: String, private val testName: String) {
    fun record(view: View) {
      Screenshot.snap(view)
        .setName("${tesClass}_$testName")
        .record()
    }
  }
}
