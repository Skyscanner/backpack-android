/**
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2018-2020 Skyscanner Ltd
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

package net.skyscanner.backpack.gradient

import android.graphics.drawable.GradientDrawable
import android.view.View
import androidx.core.content.ContextCompat
import androidx.test.ext.junit.runners.AndroidJUnit4
import net.skyscanner.backpack.BpkSnapshotTest
import net.skyscanner.backpack.R
import net.skyscanner.backpack.createThemedContext
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)

class BpkGradientsTest : BpkSnapshotTest() {

  @Before
  fun setup() {
    setDimensions(100, 100)
  }

  @Test
  fun screenshotTestGradientDefault() {
    val gradient = BpkGradients(testContext)
    val view = View(testContext)
    view.background = gradient
    snap(view)
  }

  @Test
  fun screenshotTestGradient_withCustomColor() {
    val gradient = BpkGradients(
      testContext,
      GradientDrawable.Orientation.TL_BR,
      intArrayOf(
        ContextCompat.getColor(testContext, R.color.bpkMonteverde),
        ContextCompat.getColor(testContext, R.color.bpkSagano)))

    val view = View(testContext)
    view.background = gradient
    snap(view)
  }

  @Test
  fun screenshotTestGradient_getPrimary() {
    val gradient = BpkGradients.getPrimary(testContext)
    val view = View(testContext)
    view.background = gradient
    snap(view)
  }

  @Test
  fun screenshotTestGradient_getPrimary_withTheme() {
    val gradient = BpkGradients.getPrimary(createThemedContext(testContext))
    val view = View(testContext)
    view.background = gradient
    snap(view)
  }

  @Test
  fun screenshotTestGradient_getPrimary_withCustomOrientation() {
    val gradient = BpkGradients.getPrimary(testContext, GradientDrawable.Orientation.BL_TR)
    val view = View(testContext)
    view.background = gradient
    snap(view)
  }

  @Test
  fun screenshotTestGradient() {
    val gradient = BpkGradients(testContext, GradientDrawable.Orientation.LEFT_RIGHT)
    val view = View(testContext)
    view.background = gradient
    snap(view)
  }
}
