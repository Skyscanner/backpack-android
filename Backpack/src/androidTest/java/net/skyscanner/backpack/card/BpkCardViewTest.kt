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

package net.skyscanner.backpack.card

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import net.skyscanner.backpack.R
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BpkCardViewTest {

  @Test
  fun test_with_padding() {
    val context = InstrumentationRegistry.getInstrumentation().targetContext
    val card = BpkCardView(context)
    card.padded = true
    Assert.assertEquals(context.resources.getDimensionPixelOffset(R.dimen.bpkSpacingBase), card.paddingBottom)
    Assert.assertEquals(context.resources.getDimensionPixelOffset(R.dimen.bpkSpacingBase), card.paddingLeft)
    Assert.assertEquals(context.resources.getDimensionPixelOffset(R.dimen.bpkSpacingBase), card.paddingTop)
    Assert.assertEquals(context.resources.getDimensionPixelOffset(R.dimen.bpkSpacingBase), card.paddingRight)
  }

  @Test
  fun test_without_padding() {
    val context = InstrumentationRegistry.getInstrumentation().targetContext
    val card = BpkCardView(context)
    card.padded = false
    Assert.assertEquals(0, card.paddingBottom)
    Assert.assertEquals(0, card.paddingLeft)
    Assert.assertEquals(0, card.paddingTop)
    Assert.assertEquals(0, card.paddingRight)
  }

  @Test
  fun test_without_focus() {
    val context = InstrumentationRegistry.getInstrumentation().targetContext
    val card = BpkCardView(context)
    card.focused = false
    Assert.assertEquals(context.resources.getDimension(R.dimen.bpkElevationSm), card.cardElevation)
  }

  @Test
  fun test_with_focus() {
    val context = InstrumentationRegistry.getInstrumentation().targetContext
    val card = BpkCardView(context)
    card.focused = true
    Assert.assertEquals(context.resources.getDimension(R.dimen.bpkElevationLg), card.cardElevation)
  }

  @Test
  fun test_with_corner_style_large() {
    val context = InstrumentationRegistry.getInstrumentation().targetContext
    val card = BpkCardView(context)
    card.cornerStyle = BpkCardView.CornerStyle.LARGE
    Assert.assertEquals(context.resources.getDimension(R.dimen.bpkBorderRadiusLg), card.radius)
  }
}
