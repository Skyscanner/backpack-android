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

package net.skyscanner.backpack.card

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import androidx.annotation.DimenRes
import androidx.annotation.Dimension
import androidx.cardview.widget.CardView
import net.skyscanner.backpack.R
import net.skyscanner.backpack.util.use

/**
 * Cards are used to group related items.
 * They allow complex datasets to be broken down into individual, distinct areas for easy consumption.
 *
 * @see [CardView]
 */
open class BpkCardView @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = R.style.Bpk_card,
) : CardView(context, attrs, defStyleAttr) {

  /**
   * List of possible border radius for the [BpkCardView].
   * Those map directly to the following tokens:
   *
   * [CornerStyle.SMALL] = [R.dimen.bpkBorderRadiusSm]
   * [CornerStyle.LARGE] = [R.dimen.bpkBorderRadiusLg]
   */
  enum class CornerStyle(@DimenRes val tokenRes: Int) {
    SMALL(R.dimen.bpkBorderRadiusMd),
    LARGE(R.dimen.bpkBorderRadiusLg)
  }

  enum class ElevationLevel {
    NONE,
    DEFAULT,
    FOCUSED,
    ;

    fun toDimension(context: Context): Float =
      when (this) {
        NONE -> 0f
        DEFAULT -> context.resources.getDimension(R.dimen.bpkElevationSm)
        FOCUSED -> context.resources.getDimension(R.dimen.bpkElevationLg)
      }

    fun toBackgroundColor(context: Context): ColorStateList {
      val background = when (this) {
        NONE, DEFAULT -> context.getColor(R.color.bpkSurfaceDefault)
        FOCUSED -> context.getColor(R.color.bpkSurfaceElevated)
      }
      return ColorStateList.valueOf(background)
    }

    companion object {
      fun fromAttr(attr: Int) = when (attr) {
        0 -> NONE
        1 -> DEFAULT
        2 -> FOCUSED
        else -> throw IllegalStateException("Wrong elevation value")
      }
    }
  }

  init {
    initialize(attrs, defStyleAttr)
  }

  @Dimension
  private var paddingSize: Int = 0

  private var customBackgroundColor: ColorStateList? = null

  /**
   * Sets the card to padded or not
   * @property padded
   */
  var padded: Boolean = true
    set(value) {
      field = value
      val padding = if (padded) paddingSize else 0
      this.setContentPadding(padding, padding, padding, padding)
    }

  /**
   * Sets the card to focused or not
   * @property focused
   */
  @Deprecated("Use elevation instead")
  var focused: Boolean = false
    set(value) {
      field = value
      elevationLevel = if (value) ElevationLevel.FOCUSED else ElevationLevel.DEFAULT
    }

  /**
   * Sets the border radius of the card.
   *
   * @see [BpkCardView.CornerStyle]
   * @property cornerStyle
   */
  var cornerStyle: CornerStyle = CornerStyle.SMALL
    set(value) {
      field = value
      radius = context.resources.getDimension(value.tokenRes)
    }

  var elevationLevel: ElevationLevel = ElevationLevel.DEFAULT
    set(value) {
      field = value
      cardElevation = value.toDimension(context)
      val background = customBackgroundColor ?: elevationLevel.toBackgroundColor(context)
      setCardBackgroundColor(background)
    }

  private fun initialize(attrs: AttributeSet?, defStyleAttr: Int) {
    paddingSize = context.resources.getDimension(R.dimen.bpkSpacingBase).toInt()
    maxCardElevation = context.resources.getDimension(R.dimen.bpkElevationLg)

    context.obtainStyledAttributes(attrs, R.styleable.BpkCardView, defStyleAttr, 0).use {
      padded = it.getBoolean(R.styleable.BpkCardView_padded, true)
      customBackgroundColor = it.getColorStateList(R.styleable.BpkCardView_cardBackgroundColor)
      if (it.hasValue(R.styleable.BpkCardView_elevationLevel)) {
        elevationLevel = ElevationLevel.fromAttr(it.getInt(R.styleable.BpkCardView_elevationLevel, 1))
      } else {
        @Suppress("DEPRECATION")
        focused = it.getBoolean(R.styleable.BpkCardView_focused, false)
      }
      cornerStyle = CornerStyle.values()[it.getInt(R.styleable.BpkCardView_cornerStyle, 0)]
    }
  }
}
