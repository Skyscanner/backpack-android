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

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.annotation.ColorInt
import androidx.appcompat.widget.AppCompatImageView
import androidx.constraintlayout.widget.ConstraintLayout
import net.skyscanner.backpack.R
import net.skyscanner.backpack.calendar.presenter.DateFormatter
import net.skyscanner.backpack.calendar.presenter.HighlightedDaysAdapter
import net.skyscanner.backpack.text.BpkText
import net.skyscanner.backpack.util.unsafeLazy

@Deprecated("Use Calendar2 instead")
@SuppressLint("ViewConstructor") // this view is only instantiated in code
open class HighlightedDaysMonthFooter(
  context: Context,
  val formatDate: DateFormatter,
) : LinearLayout(context) {

  var holidays: Set<HighlightedDaysAdapter.HighlightedDay>? = null
    set(value) {
      field = value
      update()
    }

  private val spacingBase = context.resources.getDimension(R.dimen.bpkSpacingBase).toInt()
  private val spacingLg = context.resources.getDimension(R.dimen.bpkSpacingXxl).toInt()

  init {
    orientation = VERTICAL
    setPadding(spacingBase, 0, spacingBase, spacingLg)
  }

  private fun update() {
    removeAllViews()
    holidays?.forEach { holiday ->
      addView(
        HolidayView(context).apply {
          descriptionOnly = holiday.descriptionOnly
          description = holiday.description
          date = formatDate(holiday.date)
          color = holiday.color
        },
        LayoutParams(
          LayoutParams.WRAP_CONTENT,
          LayoutParams.WRAP_CONTENT
        )
      )
    }
  }

  private class HolidayView(context: Context) : ConstraintLayout(context) {
    private val dateView by unsafeLazy { findViewById<BpkText>(R.id.date) }
    private val descriptionView by unsafeLazy { findViewById<BpkText>(R.id.description) }
    private val dotView by unsafeLazy { findViewById<AppCompatImageView>(R.id.dot) }
    private val spacingMd = context.resources.getDimensionPixelSize(R.dimen.bpkSpacingMd)
    private val spacingSm = context.resources.getDimensionPixelSize(R.dimen.bpkSpacingSm)

    init {
      LayoutInflater
        .from(this.context)
        .inflate(R.layout.view_bpk_calendar_holiday, this, true)
    }

    var date: String? = null
      set(value) {
        field = value
        dateView.text = value
      }

    var description: String? = null
      set(value) {
        field = value
        descriptionView.text = value
      }

    @ColorInt
    var color: Int? = null
      set(value) {
        field = value
        if (value != null) {
          dotView.backgroundTintList = ColorStateList.valueOf(value)
        }
      }

    var descriptionOnly = false
      set(value) {
        field = value
        if (value) {
          dateView.visibility = View.GONE
          descriptionView.setPaddingRelative(spacingSm, 0, 0, 0)
        } else {
          dateView.visibility = View.VISIBLE
          descriptionView.setPaddingRelative(spacingMd, 0, 0, 0)
        }
      }
  }
}
