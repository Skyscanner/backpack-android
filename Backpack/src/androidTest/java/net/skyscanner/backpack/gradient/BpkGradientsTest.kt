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

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.os.Build
import androidx.core.content.ContextCompat
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import net.skyscanner.backpack.R
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)

class BpkGradientsTest {
  private lateinit var testContext: Context

  @Before
  fun setup() {
    testContext = InstrumentationRegistry.getInstrumentation().targetContext
  }

  @Test
  fun getPrimary() {

    val expectedGradient = GradientDrawable(
      GradientDrawable.Orientation.TL_BR,
      intArrayOf(
        ContextCompat.getColor(testContext, R.color.bpkSkyBlue),
        ContextCompat.getColor(testContext, R.color.bpkSkyBlue)
      )
    )

    val gradient = BpkGradients(testContext)

    assertEquals(expectedGradient.orientation, gradient.orientation)
    assertEquals(expectedGradient.alpha, gradient.alpha)
    if (Build.VERSION.SDK_INT >= 24) {
      assertEquals(ContextCompat.getColor(testContext, R.color.bpkSkyBlue), gradient.colors?.get(0))
      assertEquals(ContextCompat.getColor(testContext, R.color.bpkSkyBlue), gradient.colors?.get(1))
    }
  }

  @Test
  fun getPrimaryDefault() {

    val expectedGradient = GradientDrawable(
      GradientDrawable.Orientation.BOTTOM_TOP,
      intArrayOf(
        ContextCompat.getColor(testContext, R.color.bpkSkyBlue),
        ContextCompat.getColor(testContext, R.color.bpkSkyBlue)
      )
    )

    val gradient = BpkGradients(testContext, GradientDrawable.Orientation.BOTTOM_TOP)

    assertEquals(expectedGradient.orientation, gradient.orientation)
    assertEquals(expectedGradient.alpha, gradient.alpha)
    if (Build.VERSION.SDK_INT >= 24) {
      assertEquals(ContextCompat.getColor(testContext, R.color.bpkSkyBlue), gradient.colors?.get(0))
      assertEquals(ContextCompat.getColor(testContext, R.color.bpkSkyBlue), gradient.colors?.get(1))
    }
  }
}
