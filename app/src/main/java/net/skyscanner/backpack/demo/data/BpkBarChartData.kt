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
import android.content.res.Resources
import android.icu.text.NumberFormat
import androidx.compose.ui.text.buildAnnotatedString
import net.skyscanner.backpack.calendar2.extension.toIterable
import net.skyscanner.backpack.compose.barchart.BpkBarChartModel
import net.skyscanner.backpack.demo.R
import org.threeten.bp.LocalDate
import org.threeten.bp.Month
import org.threeten.bp.YearMonth
import org.threeten.bp.format.TextStyle
import java.util.Random

object BpkBarChartData {

  private val random = Random(42)
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
        createMonth(month, context.resources) { date ->
          val values = random.nextFloat().let {
            BpkBarChartModel.Values(
              text = formatter.format(it * 100),
              percent = it,
            )
          }
          createBar(
            date = date,
            resources = context.resources,
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
    resources: Resources,
    create: (LocalDate) -> BpkBarChartModel.Item = { createBar(it, resources) },
  ): List<BpkBarChartModel.Item> =
    YearMonth.of(year, month)
      .let { LocalDate.of(it.year, it.month, 1)..LocalDate.of(it.year, it.month, it.lengthOfMonth()) }
      .toIterable()
      .map(create)

  fun createBar(
    date: LocalDate,
    resources: Resources,
    values: BpkBarChartModel.Values? = null,
  ): BpkBarChartModel.Item {
    val locale = resources.configuration.locales.get(0)
    val dayOfMonth = date.dayOfMonth.toString()
    val dayOfWeek = date.dayOfWeek.getDisplayName(TextStyle.SHORT, locale)
    val fullDayOfWeek = date.dayOfWeek.getDisplayName(TextStyle.FULL, locale)
    return BpkBarChartModel.Item(
      key = date,
      title = dayOfWeek,
      subtitle = dayOfMonth,
      group = date.month.getDisplayName(TextStyle.FULL, locale),
      values = values,
      accessibilityLabel = buildAnnotatedString {
        if (values != null) {
          resources.getString(R.string.bar_chart_accessibility_label, fullDayOfWeek, dayOfMonth, values.text)
        } else {
          resources.getString(R.string.bar_chart_accessibility_label_price_is_unknown, fullDayOfWeek, dayOfMonth)
        }.let(::append)
      },
    )
  }
}
