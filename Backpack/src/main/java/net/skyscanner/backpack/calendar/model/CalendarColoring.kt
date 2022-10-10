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

package net.skyscanner.backpack.calendar.model

import android.content.Context
import android.content.res.Configuration
import androidx.annotation.ColorInt
import androidx.core.graphics.ColorUtils
import net.skyscanner.backpack.R
import net.skyscanner.backpack.calendar.model.CalendarCellStyle.TextStyle.Dark
import net.skyscanner.backpack.calendar.model.CalendarCellStyle.TextStyle.Light
import net.skyscanner.backpack.calendar.presenter.HighlightedDaysAdapter
import org.threeten.bp.LocalDate

@Deprecated("Use Calendar2 instead")
data class CalendarColoring(
  val coloredBuckets: Set<ColoredBucket>,
)

@Deprecated("Use Calendar2 instead")
data class ColoredBucket(
  val calendarCellStyle: CalendarCellStyle,
  val days: Set<LocalDate>,
)

@Deprecated("Use Calendar2 instead")
sealed class CalendarCellStyle {

  /**
   * Text style of the calendar cell. Where [Light] and [Dark] refer to the background colour.
   * I.e.
   *  [Light] should be used to indicate the background is light and black text should be used.
   *  [Dark] should be used to indicate the background is dark and white text should be used.
   */
  @Deprecated("Use Calendar2 instead")
  enum class TextStyle {
    Light,
    Dark
  }

  /**
   * A positive cell style which is suitable to indicate for example
   * a date which has a comparatively low price among the dates in
   * the calendar.
   */
  @Deprecated("Use Calendar2 instead")
  object Positive : CalendarCellStyle() {
    override fun color(context: Context) =
      context.getColor(R.color.bpkStatusSuccessSpot)
  }

  /**
   * A neutral cell style which is suitable to indicate for example
   * a date which has a comparatively average price among the dates in
   * the calendar.
   */
  @Deprecated("Use Calendar2 instead")
  object Neutral : CalendarCellStyle() {
    override fun color(context: Context) =
      context.getColor(R.color.bpkStatusWarningSpot)
  }

  /**
   * A negative cell style which is suitable to indicate for example
   * a date which has a comparatively high price among the dates in
   * the calendar.
   */
  @Deprecated("Use Calendar2 instead")
  object Negative : CalendarCellStyle() {
    override fun color(context: Context) =
      context.getColor(R.color.bpkStatusDangerSpot)

    override fun textStyle(context: Context): TextStyle {
      val nightModeFlags: Int = context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK

      return if (nightModeFlags == Configuration.UI_MODE_NIGHT_YES) {
        Light
      } else {
        Dark
      }
    }
  }

  /**
   * A cell style which is suitable to indicate a holiday.
   * Use this in conjunction with [HighlightedDaysAdapter] to
   * show a footer with the list of holidays for the month.
   *
   * @see HighlightedDaysAdapter
   */
  @Deprecated("Use Calendar2 instead")
  object Hightlight : CalendarCellStyle() {
    override fun color(context: Context) =
      context.getColor(R.color.bpkLine)
  }

  /**
   * A custom cell style.
   */
  @Deprecated("Use Calendar2 instead")
  data class Custom(
    @ColorInt private val color: Int,
    private val textStyle: TextStyle? = null,
  ) : CalendarCellStyle() {
    override fun color(context: Context) = color
    override fun textStyle(context: Context): TextStyle {
      return textStyle ?: super.textStyle(context)
    }
  }

  @ColorInt
  abstract fun color(context: Context): Int

  open fun textStyle(context: Context): TextStyle =
    if (ColorUtils.calculateLuminance(color(context)) < 0.5f)
      TextStyle.Dark
    else
      TextStyle.Light
}
