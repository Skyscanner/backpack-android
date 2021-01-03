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

package net.skyscanner.backpack.tokens

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import net.skyscanner.backpack.R
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class BpkResources {

  @Test
  fun bpkColours() {
    val appContext = InstrumentationRegistry.getInstrumentation().targetContext
    assertEquals("#ff0770e3", appContext.getString(R.color.bpkSkyBlue))
    assertEquals("#ffd1435b", appContext.getString(R.color.bpkPanjin))
    assertEquals("#ffffb54d", appContext.getString(R.color.bpkErfoud))
    assertEquals("#ff00a698", appContext.getString(R.color.bpkMonteverde))
    assertEquals("#ff5a489b", appContext.getString(R.color.bpkAbisko))
    assertEquals("#ffffebd0", appContext.getString(R.color.bpkBagan))
    assertEquals("#ffff7b59", appContext.getString(R.color.bpkBunol))
    assertEquals("#ff73cec6", appContext.getString(R.color.bpkGlencoe))
    assertEquals("#ffe18b96", appContext.getString(R.color.bpkHillier))
    assertEquals("#ffff9400", appContext.getString(R.color.bpkKolkata))
    assertEquals("#ffffe7e0", appContext.getString(R.color.bpkNara))
    assertEquals("#ffffab95", appContext.getString(R.color.bpkPetra))
    assertEquals("#ffd0eeec", appContext.getString(R.color.bpkSagano))
    assertEquals("#ffe1ddec", appContext.getString(R.color.bpkTochigi))
    assertEquals("#ffa59bc8", appContext.getString(R.color.bpkValensole))
    assertEquals("#ff68697f", appContext.getString(R.color.bpkSkyGrayTint02))
    assertEquals("#ffffffff", appContext.getString(R.color.bpkWhite))
  }

  @Test
  fun bpkSpacing() {
    val appContext = InstrumentationRegistry.getInstrumentation().targetContext
    assertEquals("4.0dip", appContext.getString(R.dimen.bpkSpacingSm))
    assertEquals("8.0dip", appContext.getString(R.dimen.bpkSpacingMd))
    assertEquals("16.0dip", appContext.getString(R.dimen.bpkSpacingBase))
    assertEquals("24.0dip", appContext.getString(R.dimen.bpkSpacingLg))
    assertEquals("32.0dip", appContext.getString(R.dimen.bpkSpacingXl))
    assertEquals("40.0dip", appContext.getString(R.dimen.bpkSpacingXxl))
  }

  @Test
  fun bpkRadii() {
    val appContext = InstrumentationRegistry.getInstrumentation().targetContext
    assertEquals("4.0dip", appContext.getString(R.dimen.bpkBorderRadiusXs))
    assertEquals("8.0dip", appContext.getString(R.dimen.bpkBorderRadiusSm))
    assertEquals("12.0dip", appContext.getString(R.dimen.bpkBorderRadiusMd))
    assertEquals("24.0dip", appContext.getString(R.dimen.bpkBorderRadiusLg))
    assertEquals("40.0dip", appContext.getString(R.dimen.bpkBorderRadiusPill))
  }

  @Test
  fun bpkBorders() {
    val appContext = InstrumentationRegistry.getInstrumentation().targetContext
    assertEquals("1.0dip", appContext.getString(R.dimen.bpkBorderSizeSm))
    assertEquals("2.0dip", appContext.getString(R.dimen.bpkBorderSizeLg))
    assertEquals("3.0dip", appContext.getString(R.dimen.bpkBorderSizeXl))
  }

  @Test
  fun bpkElevation() {
    val appContext = InstrumentationRegistry.getInstrumentation().targetContext
    assertEquals("1.0dip", appContext.getString(R.dimen.bpkElevationXs))
    assertEquals("2.0dip", appContext.getString(R.dimen.bpkElevationSm))
    assertEquals("4.0dip", appContext.getString(R.dimen.bpkElevationBase))
    assertEquals("8.0dip", appContext.getString(R.dimen.bpkElevationLg))
    assertEquals("16.0dip", appContext.getString(R.dimen.bpkElevationXl))
    assertEquals("24.0dip", appContext.getString(R.dimen.bpkElevationXxl))
  }

  @Test
  fun bpkTextStyles() {
    val appContext = InstrumentationRegistry.getInstrumentation().targetContext
    val toTest = arrayOf(R.style.bpkTextSm, R.style.bpkTextSmEmphasized)

    for (style in toTest) {
      val result = appContext.obtainStyledAttributes(R.style.bpkTextLg, R.styleable.BpkTextStyle)

      assertNotNull(result.getString(R.styleable.BpkTextStyle_android_textSize))

      result.recycle()
    }
  }
}
