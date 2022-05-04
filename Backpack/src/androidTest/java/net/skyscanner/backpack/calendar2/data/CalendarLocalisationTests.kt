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

package net.skyscanner.backpack.calendar2.data

import android.text.Spanned
import android.text.style.TtsSpan
import androidx.core.text.getSpans
import net.skyscanner.backpack.calendar2.CalendarSettings
import net.skyscanner.backpack.calendar2.testCalendarWith
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.threeten.bp.DayOfWeek
import java.util.Locale

class CalendarLocalisationTests {

  private val russianLocale = CalendarSettings.Default.copy(
    locale = Locale.forLanguageTag("ru-RU"),
  )

  @Test
  fun month_titles_depend_on_locale() {
    testCalendarWith(russianLocale) {
      verify {
        assertEquals("Январь", (state.cells[0] as CalendarCell.Header).title)
      }
    }
  }

  @Test
  fun week_fields_order_depends_on_locale() {
    testCalendarWith(CalendarSettings.Default) {
      verify {
        assertEquals(DayOfWeek.SUNDAY, state.params.weekFields.firstDayOfWeek)
      }
    }
  }

  @Test
  fun when_locale_changes_week_fields_order_is_updated() {
    testCalendarWith(CalendarSettings.Default) {
      stateMachine.onLocaleChanged(russianLocale.locale)
      verify {
        assertEquals(DayOfWeek.MONDAY, state.params.weekFields.firstDayOfWeek)
      }
    }
  }

  @Test
  fun days_content_description_is_a_correct_Tts_span() {
    testCalendarWith(CalendarSettings.Default) {
      verify {
        val cell = state.cells[6] as CalendarCell.Day
        val text = cell.text as Spanned
        val spans = text.getSpans<TtsSpan>()
        assertTrue(spans.size == 1)

        val ttsSpan = spans.first()

        assertEquals(1, ttsSpan.args.get(TtsSpan.ARG_DAY)) // 1st
        assertEquals(0, ttsSpan.args.get(TtsSpan.ARG_MONTH)) // of Jan
        assertEquals(7, ttsSpan.args.get(TtsSpan.ARG_WEEKDAY)) // Saturday
      }
    }
  }
}
