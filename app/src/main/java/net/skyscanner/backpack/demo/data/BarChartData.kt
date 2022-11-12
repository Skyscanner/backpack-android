/*
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

package net.skyscanner.backpack.demo.data

import net.skyscanner.backpack.barchart.BpkBarChartModel
import net.skyscanner.backpack.calendar2.extension.toIterable
import org.threeten.bp.LocalDate
import org.threeten.bp.Month
import org.threeten.bp.YearMonth
import org.threeten.bp.format.TextStyle
import java.util.Locale
import java.util.Random

object BarChartData {

  private val random = Random(18735)
  private val year = 2018

  fun generateModel(): BpkBarChartModel =
    BpkBarChartModel(
      items = createMonth(Month.JANUARY) +
        createMonth(Month.FEBRUARY) +
        createMonth(Month.MARCH) +
        createMonth(Month.APRIL) +
        createMonth(Month.MAY),
      legend = BpkBarChartModel.Legend(
        selectedTitle = "Selected",
        inactiveTitle = "No Price",
        activeTitle = "Price",
      )
    )

  fun createMonth(
    month: Month,
    create: (LocalDate) -> BpkBarChartModel.Item = { createBar(it) },
  ) =
    YearMonth.of(year, month)
      .let { LocalDate.of(it.year, it.month, 0)..LocalDate.of(it.year, it.month, it.lengthOfMonth()) }
      .toIterable()
      .map(create)

  fun createBar(
    date: LocalDate,
    badge: String = "£" + random.nextInt(100),
    value: Float = random.nextFloat(),
    inactive: Boolean = random.nextInt(5) == 0,
  ) =
    BpkBarChartModel.Item(
      key = date,
      title = date.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault()),
      subtitle = date.dayOfMonth.toString(),
      group = date.month.getDisplayName(TextStyle.FULL, Locale.getDefault()),
      badge = badge,
      value = value,
      inactive = inactive,
    )
}
