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

package net.skyscanner.backpack.panel

import androidx.test.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import net.skyscanner.backpack.R
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BpkPanelTest {

  @Test
  fun test_with_padding() {
    val context = InstrumentationRegistry.getInstrumentation().targetContext
    val panel = BpkPanel(context).apply {
      padding = true
    }
    val expected = context.resources.getDimensionPixelOffset(R.dimen.bpkSpacingBase)
    Assert.assertEquals(expected, panel.paddingBottom)
    Assert.assertEquals(expected, panel.paddingLeft)
    Assert.assertEquals(expected, panel.paddingTop)
    Assert.assertEquals(expected, panel.paddingRight)
  }

  @Test
  fun test_without_padding() {
    val context = InstrumentationRegistry.getInstrumentation().targetContext
    val panel = BpkPanel(context)
    panel.padding = false
    Assert.assertEquals(0, panel.paddingBottom)
    Assert.assertEquals(0, panel.paddingLeft)
    Assert.assertEquals(0, panel.paddingTop)
    Assert.assertEquals(0, panel.paddingRight)
  }
}
