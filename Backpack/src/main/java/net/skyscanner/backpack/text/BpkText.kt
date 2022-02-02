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

package net.skyscanner.backpack.text

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Paint
import android.graphics.Typeface
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.widget.TextView
import androidx.annotation.IntDef
import androidx.annotation.StyleRes
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.widget.TextViewCompat
import net.skyscanner.backpack.R
import net.skyscanner.backpack.text.internal.FontFamilyResolver
import net.skyscanner.backpack.util.BpkTheme
import net.skyscanner.backpack.util.ResourcesUtil
import net.skyscanner.backpack.util.use

private val styleMapping = mapOf(
  BpkText.XS to arrayOf(R.attr.bpkTextXsAppearance, R.attr.bpkTextXsEmphasizedAppearance, null),
  BpkText.SM to arrayOf(R.attr.bpkTextSmAppearance, R.attr.bpkTextSmEmphasizedAppearance, null),
  BpkText.CAPS to arrayOf(R.attr.bpkTextCapsAppearance, R.attr.bpkTextCapsEmphasizedAppearance, null),
  BpkText.BASE to arrayOf(R.attr.bpkTextBaseAppearance, R.attr.bpkTextBaseEmphasizedAppearance, null),
  BpkText.LG to arrayOf(R.attr.bpkTextLgAppearance, R.attr.bpkTextLgEmphasizedAppearance, null),
  BpkText.XL to arrayOf(R.attr.bpkTextXlAppearance, R.attr.bpkTextXlEmphasizedAppearance, R.attr.bpkTextXlHeavyAppearance),
  BpkText.XXL to arrayOf(
    R.attr.bpkTextXxlAppearance,
    R.attr.bpkTextXxlEmphasizedAppearance,
    R.attr.bpkTextXxlHeavyAppearance
  ),
  BpkText.XXXL to arrayOf(
    R.attr.bpkTextXxxlAppearance,
    R.attr.bpkTextXxxlEmphasizedAppearance,
    R.attr.bpkTextXxxlHeavyAppearance
  ),
  BpkText.HERO1 to arrayOf(R.attr.bpkTextHero1Appearance, R.attr.bpkTextHero1Appearance, null),
  BpkText.HERO2 to arrayOf(R.attr.bpkTextHero2Appearance, R.attr.bpkTextHero2Appearance, null),
  BpkText.HERO3 to arrayOf(R.attr.bpkTextHero3Appearance, R.attr.bpkTextHero3Appearance, null),
  BpkText.HERO4 to arrayOf(R.attr.bpkTextHero4Appearance, R.attr.bpkTextHero4Appearance, null),
  BpkText.HERO5 to arrayOf(R.attr.bpkTextHero5Appearance, R.attr.bpkTextHero5Appearance, null),
)

open class BpkText @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

  enum class Weight {
    NORMAL,
    EMPHASIZED,
    HEAVY
  }

  @IntDef(XS, SM, BASE, LG, XL, XXL, XXXL, CAPS, HERO1, HERO2, HERO3, HERO4, HERO5)
  annotation class Styles

  companion object {
    const val XS = 0
    const val SM = 1
    const val BASE = 2
    const val LG = 3
    const val XL = 4
    const val XXL = 5
    const val XXXL = 6
    const val CAPS = 7
    const val HERO1 = 8
    const val HERO2 = 9
    const val HERO3 = 10
    const val HERO4 = 11
    const val HERO5 = 12

    @JvmStatic
    fun getFont(
      context: Context,
      textStyle: Int = BpkText.BASE,
      weight: BpkText.Weight = BpkText.Weight.NORMAL
    ) =
      internalGetFont(context, textStyle, weight)
  }

  @Styles
  private var _textStyle: Int = BASE
  var textStyle
    get() = _textStyle
    set(value) {
      _textStyle = value
      this.setup()
    }

  private var _weight: Weight = Weight.NORMAL
  var weight
    get() = _weight
    set(value) {
      _weight = value
      setup()
    }

  private var _textColour: ColorStateList? = null
  private var _textColorLink: ColorStateList? = null

  init {
    initialize(attrs, defStyleAttr)
    setup()
  }

  private fun initialize(attrs: AttributeSet?, defStyleAttr: Int) {

    context.theme.obtainStyledAttributes(
      attrs,
      R.styleable.BpkText,
      defStyleAttr, 0
    ).use {
      _textStyle = it.getInt(R.styleable.BpkText_textStyle, BASE)
      val weightArg = it.getInt(R.styleable.BpkText_weight, -1)
      if (weightArg != -1) {
        _weight = Weight.values()[weightArg]
      }

      if (it.hasValue(R.styleable.BpkText_android_textColor)) {
        _textColour = it.getColorStateList(R.styleable.BpkText_android_textColor)
      }
      _textColorLink = ColorStateList.valueOf(BpkTheme.getPrimaryColor(context))

      // Adding tint and compoundDrawables does not work. Converting compoundDrawables to compoundDrawablesRelative
      var start = compoundDrawablesRelative[0] ?: compoundDrawables[0]
      val top = compoundDrawablesRelative[1] ?: compoundDrawables[1]
      var end = compoundDrawablesRelative[2] ?: compoundDrawables[2]
      val bottom = compoundDrawablesRelative[3] ?: compoundDrawables[3]

      // Swapping drawables in case of RTL.
      // compoundDrawablesRelative order is `start`,`top`,`end`,`bottom`
      // compoundDrawables order is  `left`,`top`,`right`,`bottom`

      if (this.layoutDirection == View.LAYOUT_DIRECTION_RTL) {
        start = compoundDrawables[2]
        end = compoundDrawables[0]
      }
      setCompoundDrawablesRelative(start, top, end, bottom)
    }
  }

  fun setDrawableTint(color: Int) {
    compoundDrawableTintList = ColorStateList.valueOf(color)
  }

  private fun setup() {
    val textAppearance = getStyleId(context, textStyle, weight)
    if (textStyle == CAPS) {
      isAllCaps = true
    }

    setTextAppearance(textAppearance)
    applyLineHeight(textAppearance)
    _textColour?.let(::setTextColor)
    setLinkTextColor(_textColorLink)
  }

  @SuppressLint("CustomViewStyleable")
  private fun applyLineHeight(textAppearance: Int) {
    val textStyleAttributes = context.obtainStyledAttributes(textAppearance, R.styleable.BpkTextStyle)
    val lineHeight = textStyleAttributes.getDimensionPixelSize(R.styleable.BpkTextStyle_lineHeight, -1)
      .let { if (it == -1) null else it }
    lineHeight?.let { TextViewCompat.setLineHeight(this, lineHeight) }

    textStyleAttributes.recycle()
  }

  data class FontDefinition(
    val typeface: Typeface,
    val fontSize: Int,
    val letterSpacing: Float?,
    val lineHeight: Int?,
  ) {

    fun applyTo(text: TextView) {
      text.typeface = typeface
      text.setTextSize(TypedValue.COMPLEX_UNIT_PX, fontSize.toFloat())
      letterSpacing?.let { text.letterSpacing = it }
      lineHeight?.let { TextViewCompat.setLineHeight(text, lineHeight) }
    }

    fun applyTo(paint: Paint) {
      paint.typeface = typeface
      paint.textSize = fontSize.toFloat()
      letterSpacing?.let { paint.letterSpacing = it }
    }
  }
}

private fun internalGetFont(
  context: Context,
  textStyle: Int = BpkText.BASE,
  weight: BpkText.Weight = BpkText.Weight.NORMAL
): BpkText.FontDefinition {
  val styleRes = getStyleId(context, textStyle, weight)

  val textStyleAttributes = context.obtainStyledAttributes(styleRes, R.styleable.BpkTextStyle)
  val fontSize = textStyleAttributes.getDimensionPixelSize(
    R.styleable.BpkTextStyle_android_textSize,
    ResourcesUtil.dpToPx(16, context)
  )
  val letterSpacing = textStyleAttributes.getFloat(R.styleable.BpkTextStyle_android_letterSpacing, -1f)
    .let { if (it == -1f) null else it }

  val lineHeight = textStyleAttributes.getDimensionPixelSize(R.styleable.BpkTextStyle_lineHeight, -1)
    .let { if (it == -1) null else it }

  textStyleAttributes.recycle()

  return FontFamilyResolver(context, weight)?.let {
    BpkText.FontDefinition(it, fontSize, letterSpacing, lineHeight)
  } ?: throw IllegalStateException("Bpk font not configured correctly")
}

@StyleRes
private fun getStyleId(context: Context, textStyle: Int, weight: BpkText.Weight): Int {
  val styleProps = styleMapping[textStyle]
    ?: throw IllegalStateException("Invalid textStyle")

  val textAppearanceAttr = styleProps[weight.ordinal]
    ?: throw IllegalStateException("Weight $weight is not supported for the current size")

  val outValue = TypedValue()
  if (context.theme.resolveAttribute(textAppearanceAttr, outValue, true)) {
    return outValue.resourceId
  }

  return 0
}
