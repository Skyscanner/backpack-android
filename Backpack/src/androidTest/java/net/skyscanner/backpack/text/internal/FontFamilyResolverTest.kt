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

package net.skyscanner.backpack.text.internal

import android.content.Context
import android.graphics.Typeface
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import net.skyscanner.backpack.test.R
import net.skyscanner.backpack.text.BpkText
import net.skyscanner.backpack.text.FontCache
import net.skyscanner.backpack.util.TestActivity
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FontFamilyResolverTest {
  private lateinit var context: Context

  @get:Rule
  internal var activityRule: ActivityTestRule<TestActivity> =
    ActivityTestRule(TestActivity::class.java)

  @Before
  fun setUp() {
    context = activityRule.activity
  }

  @Test
  fun test_default_font() {
    val serif = Typeface.create("sans-serif", Typeface.NORMAL)
    val serifMedium = Typeface.create("sans-serif-medium", Typeface.NORMAL)
    val serifBlack = Typeface.create("sans-serif-black", Typeface.NORMAL)

    Assert.assertEquals("normal", serif, FontFamilyResolver(context, BpkText.Weight.NORMAL))
    Assert.assertEquals("emphasized", serifMedium, FontFamilyResolver(context, BpkText.Weight.EMPHASIZED))
    Assert.assertEquals("heavy", serifBlack, FontFamilyResolver(context, BpkText.Weight.HEAVY))
  }

  @Test
  fun test_custom_font() {
    val shadows = FontCache[R.font.shadows_into_light, context]
    val cursive = Typeface.create("cursive", Typeface.NORMAL)
    val casual = Typeface.create("casual", Typeface.NORMAL)

    Assert.assertEquals("normal", shadows, FontFamilyResolver(context, BpkText.Weight.NORMAL))
    Assert.assertEquals("emphasized", cursive, FontFamilyResolver(context, BpkText.Weight.EMPHASIZED))
    Assert.assertEquals("heavy", casual, FontFamilyResolver(context, BpkText.Weight.HEAVY))
  }

  @Test
  fun test_with_font_not_defined() {
    Assert.assertNotNull("normal", FontFamilyResolver(context, BpkText.Weight.NORMAL))
    Assert.assertNotNull("emphasized", FontFamilyResolver(context, BpkText.Weight.EMPHASIZED))
    Assert.assertNotNull("heavy", FontFamilyResolver(context, BpkText.Weight.HEAVY))
  }
}
