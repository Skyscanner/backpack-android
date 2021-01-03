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

package net.skyscanner.backpack.badge

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.TypedArray
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.util.AttributeSet
import android.view.Gravity
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import net.skyscanner.backpack.R
import net.skyscanner.backpack.text.BpkText
import net.skyscanner.backpack.util.createContextThemeWrapper

open class BpkBadge @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : BpkText(createContextThemeWrapper(context, attrs, R.attr.bpkBadgeStyle), attrs, defStyleAttr) {

  private var initialized = false

  init {
    initialize(attrs, defStyleAttr)
    initialized = true
  }

  enum class Type(
    internal var id: Int,
    @ColorRes internal var bgColor: Int,
    @ColorRes internal var textColor: Int
  ) {
    /**
     * Style for badges with positive messages
     */
    Success(1, R.color.bpkGlencoe, R.color.bpkSkyGrayTint01),
    /**
     *  Style for badges with warning messages
     */
    Warning(2, R.color.bpkErfoud, R.color.bpkSkyGrayTint01),
    /**
     * Style for badges with error messages
     */
    Destructive(3, R.color.bpkPanjin, R.color.bpkWhite),
    /**
     *  Light themed style for badges
     */
    Light(4, R.color.bpkSkyGrayTint07, R.color.bpkSkyGrayTint01),
    /**
     *  Style for badges on dark themes
     */
    Inverse(5, R.color.bpkWhite, R.color.bpkSkyGrayTint01),
    /**
     * Style for badges with a thin white outline
     */
    Outline(6, R.color.bpkWhite, R.color.bpkWhite),

    /**
     * Style for badges with a dark background
     */
    Dark(7, R.color.bpkSkyGray, R.color.bpkWhite);

    internal companion object {

      internal fun fromId(id: Int): Type {
        for (f in values()) {
          if (f.id == id) return f
        }
        throw IllegalArgumentException()
      }
    }
  }

  /**
   * @property type
   * Type of badge. Default Type.Success
   */
  var type: Type = Type.Success
    set(value) {
      field = value
      if (initialized) setup()
    }
  /**
   * @property message
   * message on the badge
   */
  var message: String? = null
    set(value) {
      field = value
      this.text = message
    }

  private fun initialize(attrs: AttributeSet?, defStyleAttr: Int) {

    val a: TypedArray = context.theme.obtainStyledAttributes(
      attrs,
      R.styleable.BpkBadge,
      defStyleAttr,
      0
    )

    type = Type.fromId(a.getInt(R.styleable.BpkBadge_badgeType, 1))
    message = a.getString(R.styleable.BpkBadge_message)
    includeFontPadding = a.getBoolean(R.styleable.BpkBadge_android_includeFontPadding, false)

    a.recycle()

    setup()
  }

  private fun setup() {
    this.text = message

    // set padding
    val paddingMd = resources.getDimension(R.dimen.bpkSpacingMd).toInt()
    val paddingSm = resources.getDimension(R.dimen.bpkSpacingSm).toInt()
    this.setPadding(paddingMd, paddingSm, paddingMd, paddingSm)

    // set Text color
    this.setTextColor(ContextCompat.getColor(context, type.textColor))

    // Set background
    val bgColor = ContextCompat.getColorStateList(context, type.bgColor)!!
    if (type == Type.Outline) {
      setBackground(ColorStateList.valueOf(bgColor.defaultColor and 0x32ffffff), bgColor)
    } else {
      setBackground(bgColor)
    }
    this.gravity = Gravity.CENTER
  }

  internal fun setBackground(
    solid: ColorStateList,
    stroke: ColorStateList = solid
  ) {
    val drawable = GradientDrawable()
    drawable.color = solid
    drawable.setStroke(resources.getDimension(R.dimen.badge_border_size).toInt(), stroke)

    val cornerRadius = resources.getDimension(R.dimen.bpkBorderRadiusXs)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
      drawable.cornerRadius = cornerRadius
    } else {
      drawable.cornerRadii = FloatArray(8) { cornerRadius }
    }
    this.background = drawable
  }
}
