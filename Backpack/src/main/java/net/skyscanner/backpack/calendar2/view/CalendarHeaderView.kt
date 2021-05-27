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

package net.skyscanner.backpack.calendar2.view

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import androidx.appcompat.widget.LinearLayoutCompat
import java.time.DayOfWeek
import net.skyscanner.backpack.R
import net.skyscanner.backpack.calendar2.CalendarParams
import net.skyscanner.backpack.text.BpkText
import net.skyscanner.backpack.util.Consumer

internal class CalendarHeaderView @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0,
) : LinearLayoutCompat(context, attrs, defStyleAttr), Consumer<CalendarParams> {

  init {
    orientation = HORIZONTAL
  }

  private var lastState: CalendarParams? = null

  override fun invoke(state: CalendarParams) {
    if (lastState == state) return

    lastState = state
    removeAllViews()

    var current = state.weekFields.firstDayOfWeek
    do {
      addField(current, state)
      current += 1
    } while (current != state.weekFields.firstDayOfWeek)
  }

  private fun addField(field: DayOfWeek, params: CalendarParams) {
    val text = BpkText(context)
    text.textStyle = BpkText.SM
    text.weight = BpkText.Weight.EMPHASIZED
    text.setTextColor(context.getColorStateList(R.color.bpkTextSecondary))
    text.gravity = Gravity.CENTER
    text.maxLines = 1
    text.isSingleLine = true
    text.isAllCaps = true
    text.text = field.getDisplayName(params.dayOfWeekText, params.locale)
    text.contentDescription = field.getDisplayName(params.dayOfWeekAccessibilityText, params.locale)
    addView(text, LayoutParams(0, LayoutParams.MATCH_PARENT, 1f))
  }
}
