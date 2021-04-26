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

package net.skyscanner.backpack.nudger

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.LinearLayoutCompat
import kotlin.math.max
import kotlin.math.min
import net.skyscanner.backpack.R
import net.skyscanner.backpack.util.unsafeLazy
import net.skyscanner.backpack.util.use
import net.skyscanner.backpack.util.wrapContextWithDefaults

open class BpkNudger @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : LinearLayoutCompat(wrapContextWithDefaults(context), attrs, defStyleAttr) {

  private val decrementButton by unsafeLazy<View> {
    findViewById(R.id.bpk_nudger_decrement)
  }

  private val incrementButton by unsafeLazy<View> {
    findViewById(R.id.bpk_nudger_increment)
  }

  private val label by unsafeLazy<TextView> {
    findViewById(R.id.bpk_nudger_label)
  }

  var value: Int = 0
    set(value) {
      if (field != value) {
        if (value < minValue || value > maxValue) {
          return
        }
        field = value
        update()
      }
    }

  var minValue: Int = 0
    set(minValue) {
      if (minValue > maxValue) {
        throw IllegalArgumentException("Cannot set minValue $minValue when maxValue is $maxValue")
      }
      field = minValue
      value = max(value, minValue)
      update()
    }

  var maxValue: Int = 100
    set(maxValue) {
      if (maxValue < minValue) {
        throw IllegalArgumentException("Cannot set maxValue $maxValue when minValue is $minValue")
      }
      field = maxValue
      value = min(value, maxValue)
      update()
    }

  var onChangeListener: ((Int) -> Unit)? = null

  init {
    LayoutInflater.from(context).inflate(R.layout.view_bpk_nudger, this, true)
    context.theme.obtainStyledAttributes(
      attrs,
      R.styleable.BpkNudger,
      defStyleAttr, 0
    ).use {
      minValue = it.getInt(R.styleable.BpkNudger_nudgerMinValue, minValue)
      maxValue = it.getInt(R.styleable.BpkNudger_nudgerMaxValue, maxValue)
      value = it.getInt(R.styleable.BpkNudger_nudgerValue, value)
    }

    decrementButton.setOnClickListener {
      value -= 1
      onChangeListener?.invoke(value)
    }
    incrementButton.setOnClickListener {
      value += 1
      onChangeListener?.invoke(value)
    }
  }

  private fun update() {
    decrementButton.isEnabled = value > minValue
    incrementButton.isEnabled = value < maxValue
    label.text = value.toString()
  }
}
