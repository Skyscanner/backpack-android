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

@file:Suppress("DEPRECATION")

package net.skyscanner.backpack.calendar.view

import android.content.Context
import android.view.ViewGroup
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.jakewharton.threetenabp.AndroidThreeTen
import net.skyscanner.backpack.R
import net.skyscanner.backpack.text.BpkText
import net.skyscanner.backpack.util.TestActivity
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.Locale

@RunWith(AndroidJUnit4::class)
class WeekdayViewHeaderTest {

  @get:Rule
  internal var activityRule: ActivityTestRule<TestActivity> =
    ActivityTestRule(TestActivity::class.java)

  private lateinit var context: Context

  @Before
  fun setUp() {
    context = activityRule.activity
    AndroidThreeTen.init(context)
  }

  @Test
  fun test_initializeWithLocale() {
    listOf(
      "pt-br" to arrayOf("dom", "seg", "ter", "qua", "qui", "sex", "sáb"),
      "en" to arrayOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat")
    ).forEach {
      val locale = it.first
      val expected = it.second

      val view = WeekdayHeaderView(context).apply {
        initializeWithLocale(Locale.forLanguageTag(locale))
      }

      val allDays = getAllLabels(view)

      Assert.assertArrayEquals(
        allDays,
        expected
      )
    }
  }

  private fun getAllLabels(view: ViewGroup): Array<CharSequence> {
    return arrayOf(
      R.id.first_weekday_label,
      R.id.second_weekday_label,
      R.id.third_weekday_label,
      R.id.fourth_weekday_label,
      R.id.fifth_weekday_label,
      R.id.sixth_weekday_label,
      R.id.seventh_weekday_label
    ).map {
      view.findViewById<BpkText>(it).text
    }.toTypedArray()
  }
}
