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

package net.skyscanner.backpack.calendar2.data

import java.util.Locale
import net.skyscanner.backpack.calendar2.CalendarSettings
import net.skyscanner.backpack.calendar2.testCalendarWith
import org.junit.Assert.assertEquals
import org.junit.Test
import org.threeten.bp.DayOfWeek

class CalendarLocalisationTests {

  private val russianLocale = CalendarSettings.Default.copy(
    locale = Locale.forLanguageTag("ru-RU"),
  )

  @Test
  fun `month titles depend on locale`() {
    testCalendarWith(russianLocale) {
      verify {
        assertEquals("Январь", (state.cells[0] as CalendarCell.Header).title)
      }
    }
  }

  @Test
  fun `week fields order depends on locale`() {
    testCalendarWith(CalendarSettings.Default) {
      verify {
        assertEquals(DayOfWeek.SUNDAY, state.params.weekFields.firstDayOfWeek)
      }
    }
  }

  @Test
  fun `when locale changes week fields order is updated`() {
    testCalendarWith(CalendarSettings.Default) {
      stateMachine.onLocaleChanged(russianLocale.locale)
      verify {
        assertEquals(DayOfWeek.MONDAY, state.params.weekFields.firstDayOfWeek)
      }
    }
  }

  @Test
  fun `days content description depends on locale`() {
    testCalendarWith(russianLocale) {
      verify {
        assertEquals("1 января", (state.cells[6] as CalendarCell.Day).contentDescription)
      }
    }
  }
}
