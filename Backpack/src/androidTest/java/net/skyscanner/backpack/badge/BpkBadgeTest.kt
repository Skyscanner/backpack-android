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

package net.skyscanner.backpack.badge

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import net.skyscanner.backpack.R
import net.skyscanner.backpack.util.TestActivity
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BpkBadgeTest {

  @get:Rule
  internal var activityRule: ActivityTestRule<TestActivity> =
    ActivityTestRule(TestActivity::class.java)

  private lateinit var context: Context

  @Before
  fun setup() {
    context = activityRule.activity
  }

  @Test
  fun test_message() {
    val badge = BpkBadge(context)
    badge.message = "error"
    badge.type = BpkBadge.Type.Destructive
    Assert.assertEquals("error", badge.text.toString())
  }

  @Test
  fun test_alpha_default() {
    val badge = BpkBadge(context)
    badge.type = BpkBadge.Type.Success
    Assert.assertEquals(context.getColorStateList(R.color.bpkGlencoe), (badge.background as GradientDrawable).color)
  }

  @Test
  fun test_alpha_outline() {
    val badge = BpkBadge(context)
    badge.type = BpkBadge.Type.Outline
    Assert.assertEquals(ColorStateList.valueOf(Color.TRANSPARENT), (badge.background as GradientDrawable).color)
  }
}
