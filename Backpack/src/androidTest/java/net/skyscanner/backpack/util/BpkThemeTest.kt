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

package net.skyscanner.backpack.util

import android.graphics.Color
import android.util.TypedValue
import android.view.ContextThemeWrapper
import androidx.appcompat.app.AppCompatActivity
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import net.skyscanner.backpack.test.R
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BpkThemeTest {
  private lateinit var activity: AppCompatActivity

  @get:Rule
  internal var activityRule: ActivityTestRule<TestActivity> =
    ActivityTestRule(TestActivity::class.java)

  private val skyBlue by unsafeLazy { activity.getColor(R.color.bpkSkyBlue) }
  private val customBlue = Color.parseColor("#9B104A")

  @Before
  fun setUp() {
    activity = activityRule.activity
  }

  @Test
  fun test_primary_color() {
    Assert.assertEquals("default", skyBlue, BpkTheme.getPrimaryColor(activity))

    Assert.assertEquals(
      "Themed",
      customBlue,
      BpkTheme.getPrimaryColor(ContextThemeWrapper(activity, R.style.TestThemeUtilsCustomColors))
    )
  }

  @Test
  fun test_theme_properties() {
    val t = TypedValue()

    Assert.assertTrue(activity.theme.resolveAttribute(R.attr.bpkPrimaryColor, t, true))

    activity.theme.resolveAttribute(R.attr.bpkPrimaryColor, t, true)
    Assert.assertEquals(skyBlue, t.data)
  }
}
