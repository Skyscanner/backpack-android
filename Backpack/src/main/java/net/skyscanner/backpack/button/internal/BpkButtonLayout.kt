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

package net.skyscanner.backpack.button.internal

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.TextUtils
import android.util.AttributeSet
import android.view.Gravity
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.AppCompatButton
import kotlin.math.max
import kotlin.math.min
import net.skyscanner.backpack.R

// responsible for correct compound icon positioning
abstract class BpkButtonLayout internal constructor(
  context: Context,
  attrs: AttributeSet?,
  defStyleAttr: Int
) : AppCompatButton(context, attrs, defStyleAttr) {

  private var minPaddingStart = resources.getDimensionPixelSize(R.dimen.bpkSpacingBase) -
    resources.getDimensionPixelSize(R.dimen.bpkSpacingSm)

  private var minPaddingEnd = minPaddingStart
  private var maxIconSpacing = resources.getDimensionPixelSize(R.dimen.bpkSpacingSm)

  init {
    maxLines = 1
    gravity = Gravity.CENTER
    ellipsize = TextUtils.TruncateAt.END
    super.setCompoundDrawablePadding(maxIconSpacing)
    super.setPaddingRelative(minPaddingStart, paddingTop, minPaddingEnd, paddingBottom)
  }

  override fun setCompoundDrawablesWithIntrinsicBounds(
    @DrawableRes left: Int,
    @DrawableRes top: Int,
    @DrawableRes right: Int,
    @DrawableRes bottom: Int
  ) {
    super.setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom)
    layoutComponents()
  }

  override fun setCompoundDrawablesWithIntrinsicBounds(
    left: Drawable?,
    top: Drawable?,
    right: Drawable?,
    bottom: Drawable?
  ) {
    super.setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom)
    layoutComponents()
  }

  override fun setCompoundDrawables(left: Drawable?, top: Drawable?, right: Drawable?, bottom: Drawable?) {
    super.setCompoundDrawables(left, top, right, bottom)
    layoutComponents()
  }

  override fun setCompoundDrawablesRelative(start: Drawable?, top: Drawable?, end: Drawable?, bottom: Drawable?) {
    super.setCompoundDrawablesRelative(start, top, end, bottom)
    layoutComponents()
  }

  override fun setText(text: CharSequence, type: BufferType) {
    super.setText(text, type)
    layoutComponents()
  }

  override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
    super.onSizeChanged(w, h, oldw, oldh)
    layoutComponents()
  }

  override fun setPadding(left: Int, top: Int, right: Int, bottom: Int) {
    super.setPadding(left, top, right, bottom)
    minPaddingStart = left
    minPaddingEnd = right
    layoutComponents()
  }

  override fun setPaddingRelative(start: Int, top: Int, end: Int, bottom: Int) {
    super.setPaddingRelative(start, top, end, bottom)
    minPaddingStart = start
    minPaddingEnd = end
    layoutComponents()
  }

  override fun setCompoundDrawablePadding(pad: Int) {
    maxIconSpacing = pad
    layoutComponents()
  }

  private fun layoutComponents() {
    var width = width
    if (width == 0) {
      width = measuredWidth
    }
    if (width == 0) {
      return
    }

    val icon = compoundDrawables[0] ?: compoundDrawables[2]
    val iconWidth = icon?.intrinsicWidth ?: 0
    val iconSpacing = if (icon != null && text.isNotEmpty()) maxIconSpacing else 0
    val maxLineWidth = max(0, width - minPaddingStart - minPaddingEnd - iconWidth - iconSpacing)
    val textToMeasure = transformationMethod?.getTransformation(text, this) ?: text
    val totalTextWidth = paint.measureText(textToMeasure, 0, length()).toInt()
    val singleLineTextWidth = min(maxLineWidth, totalTextWidth)
    val padding = (width - singleLineTextWidth - iconWidth - iconSpacing) / 2

    super.setCompoundDrawablePadding(iconSpacing)
    super.setPaddingRelative(padding, paddingTop, padding, paddingBottom)
  }
}
