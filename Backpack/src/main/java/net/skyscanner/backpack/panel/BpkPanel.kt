/**
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2018-2020 Skyscanner Ltd
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

package net.skyscanner.backpack.panel

import android.content.Context
import android.util.AttributeSet
import androidx.annotation.Dimension
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.LinearLayoutCompat
import net.skyscanner.backpack.R
import net.skyscanner.backpack.util.wrapContextWithDefaults

open class BpkPanel @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = R.style.Bpk_panel
) : LinearLayoutCompat(wrapContextWithDefaults(context), attrs, defStyleAttr) {

  @Dimension
  private var paddingSize = resources.getDimensionPixelOffset(R.dimen.bpkSpacingBase)

  init {
    initialize(attrs, defStyleAttr)
  }

  /**
   * @property padding
   * padding for panel
   */
  var padding: Boolean = false
    set(value) {
      field = value
      if (this.padding) {
        this.setPadding(paddingSize, paddingSize, paddingSize, paddingSize)
      } else {
        this.setPadding(0, 0, 0, 0)
      }
    }

  private fun initialize(attrs: AttributeSet?, defStyleAttr: Int) {
    val a = context.obtainStyledAttributes(attrs, R.styleable.BpkPanel, R.attr.padding, defStyleAttr)
    padding = a.getBoolean(R.styleable.BpkPanel_padding, true)
    this.background = AppCompatResources.getDrawable(context, R.drawable.border)
    a.recycle()
  }
}
