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

package net.skyscanner.backpack.calendar.presenter

import android.content.Context
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.jakewharton.threetenabp.AndroidThreeTen
import net.skyscanner.backpack.R
import net.skyscanner.backpack.calendar.presenter.HighlightedDaysAdapter.HighlightedDay
import net.skyscanner.backpack.calendar.view.HighlightedDaysMonthFooter
import net.skyscanner.backpack.text.BpkText
import net.skyscanner.backpack.util.TestActivity
import org.hamcrest.Matchers.instanceOf
import org.hamcrest.Matchers.`is`
import org.junit.Assert.assertArrayEquals
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertThat
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.threeten.bp.LocalDate
import java.util.Locale

@RunWith(AndroidJUnit4::class)
class HighlightedDaysAdapterTest {

  @get:Rule
  internal var activityRule: ActivityTestRule<TestActivity> =
    ActivityTestRule(TestActivity::class.java)

  private lateinit var context: Context

  private lateinit var subject: HighlightedDaysAdapter
  private lateinit var holidays: Map<String, Set<HighlightedDay>>

  @Before
  fun setup() {
    context = activityRule.activity
    AndroidThreeTen.init(context)

    holidays = mapOf(
      "2020-1" to setOf(
        HighlightedDay(
          LocalDate.of(2020, 1, 1), "New Year's Day"
        )
      ),
      "2020-12" to setOf(
        HighlightedDay(
          LocalDate.of(2020, 12, 25), "Christmas Day"
        ),
        HighlightedDay(
          LocalDate.of(2020, 12, 31), "New Year's Eve"
        )
      )
    )

    subject = HighlightedDaysAdapter(
      context,
      Locale.UK,
      holidays.values.flatten().toSet()
    )
  }

  @Test
  fun test_hasFooterForMonth() {
    assertTrue(subject.hasFooterForMonth(1, 2020))
    assertTrue(subject.hasFooterForMonth(12, 2020))

    assertFalse(subject.hasFooterForMonth(2, 2020))
    assertFalse(subject.hasFooterForMonth(11, 2020))
  }

  @Test
  fun test_onCreateView() {
    assertThat(
      subject.onCreateView(1, 2020),
      `is`(instanceOf(HighlightedDaysMonthFooter::class.java))
    )

    assertThat(
      subject.onCreateView(12, 2020),
      `is`(instanceOf(HighlightedDaysMonthFooter::class.java))
    )
  }

  @Test
  fun test_onBindView() {
    val view = HighlightedDaysMonthFooter(context) { it.toString() }

    subject.onBindView(view, 1, 2020)
    assertNotNull(view.holidays)
    assertArrayEquals(
      holidays["2020-1"]?.toTypedArray(),
      view.holidays?.toTypedArray()
    )

    subject.onBindView(view, 12, 2020)
    assertNotNull(view.holidays)
    assertArrayEquals(
      holidays["2020-12"]?.toTypedArray(),
      view.holidays?.toTypedArray()
    )
  }

  @Test
  fun test_date_formatter() {
    val view = subject.onCreateView(1, 2020) as HighlightedDaysMonthFooter
    subject.onBindView(view, 1, 2020)

    val dateView = view.getChildAt(0).findViewById<BpkText>(R.id.date)
    assertEquals("01 Jan", dateView.text)
  }

  @Test
  fun test_date_formatter_custom_locale() {
    val subject = HighlightedDaysAdapter(
      context,
      Locale.forLanguageTag("pt-BR"),
      holidays.values.flatten().toSet()
    )
    val view = subject.onCreateView(12, 2020) as HighlightedDaysMonthFooter
    subject.onBindView(view, 12, 2020)

    val dateView = view.getChildAt(0).findViewById<BpkText>(R.id.date)
    assertEquals("25 dez", dateView.text)
  }
}
