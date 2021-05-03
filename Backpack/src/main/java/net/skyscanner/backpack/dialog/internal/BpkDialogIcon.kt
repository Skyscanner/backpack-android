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

package net.skyscanner.backpack.dialog.internal

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import androidx.annotation.ColorInt
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.AppCompatImageView
import net.skyscanner.backpack.R
import net.skyscanner.backpack.dialog.BpkDialog

internal class BpkDialogIcon @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr) {

  private val size = resources.getDimensionPixelSize(R.dimen.bpk_dialog_icon_size)

  init {
    minimumWidth = size
    minimumHeight = size
    maxWidth = size
    maxHeight = size
    scaleType = ScaleType.CENTER
    imageTintList = context.getColorStateList(R.color.bpkWhite)
  }

  var icon: BpkDialog.Icon? = null
    set(value) {
      field = value
      if (value != null) {
        setImageDrawable(AppCompatResources.getDrawable(context, value.drawableRes))
        background = createBackground(value.color)
      }
    }

  private fun createBackground(@ColorInt backgroundColor: Int) = GradientDrawable().apply {
    cornerRadius = size / 2f
    color = ColorStateList.valueOf(backgroundColor)
    setStroke(
      resources.getDimensionPixelSize(R.dimen.bpk_dialog_icon_stroke),
      context.getColorStateList(R.color.__dialogBackground)
    )
  }
}
