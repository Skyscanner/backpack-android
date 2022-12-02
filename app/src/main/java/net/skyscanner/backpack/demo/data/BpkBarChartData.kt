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

import android.content.Context
import android.icu.text.NumberFormat
import net.skyscanner.backpack.calendar2.extension.toIterable
import net.skyscanner.backpack.compose.barchart.BpkBarChartModel
import net.skyscanner.backpack.demo.R
import org.threeten.bp.LocalDate
import org.threeten.bp.Month
import org.threeten.bp.YearMonth
import org.threeten.bp.format.TextStyle
import java.util.Locale
import java.util.Random

object BpkBarChartData {

  private val random = Random(18735)
  private val year = 2018

  fun generateModel(
    context: Context,
  ): BpkBarChartModel {

    val locale = context.resources.configuration.locales.get(0)
    val formatter = NumberFormat.getCurrencyInstance(locale)
    formatter.maximumFractionDigits = 0

    return BpkBarChartModel(
      caption = context.getString(R.string.generic_departures),
      items = Month.values().flatMap { month ->
        createMonth(month) { date ->
          val values = random.nextFloat().let {
            BpkBarChartModel.Values(
              text = formatter.format(it * 100),
              percent = it,
              accessibilityLabel = context.getString(R.string.generic_price_placeholder, formatter.format(it * 100)),
            )
          }
          createBar(
            date = date,
            locale = locale,
            values = values.takeIf { random.nextInt(5) != 0 },
          )
        }
      },
      legend = BpkBarChartModel.Legend(
        selectedTitle = context.getString(R.string.generic_selected),
        inactiveTitle = context.getString(R.string.generic_no_price),
        activeTitle = context.getString(R.string.generic_price),
      )
    )
  }

  fun createMonth(
    month: Month,
    create: (LocalDate) -> BpkBarChartModel.Item = { createBar(it) },
  ): List<BpkBarChartModel.Item> =
    YearMonth.of(year, month)
      .let { LocalDate.of(it.year, it.month, 1)..LocalDate.of(it.year, it.month, it.lengthOfMonth()) }
      .toIterable()
      .map(create)

  fun createBar(
    date: LocalDate,
    locale: Locale = Locale.getDefault(),
    values: BpkBarChartModel.Values? = null,
  ): BpkBarChartModel.Item =
    BpkBarChartModel.Item(
      key = date,
      title = date.dayOfWeek.getDisplayName(TextStyle.SHORT, locale),
      subtitle = date.dayOfMonth.toString(),
      group = date.month.getDisplayName(TextStyle.FULL, locale),
      values = values,
    )
}
