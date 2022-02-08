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
import androidx.core.content.res.ResourcesCompat
import androidx.core.widget.TextViewCompat
import net.skyscanner.backpack.R
import net.skyscanner.backpack.util.BpkTheme
import net.skyscanner.backpack.util.ResourcesUtil
import net.skyscanner.backpack.util.use

private val legacyStyleMapping = mapOf(
  BpkText.XS to arrayOf(BpkText.TextStyle.CAPTION, BpkText.TextStyle.CAPTION, null),
  BpkText.SM to arrayOf(BpkText.TextStyle.FOOTNOTE, BpkText.TextStyle.LABEL2, null),
  BpkText.CAPS to arrayOf(BpkText.TextStyle.CAPTION, BpkText.TextStyle.CAPTION, null),
  BpkText.BASE to arrayOf(BpkText.TextStyle.BODY_DEFAULT, BpkText.TextStyle.LABEL1, null),
  BpkText.LG to arrayOf(BpkText.TextStyle.BODY_LONGFORM, BpkText.TextStyle.HEADING4, null),
  BpkText.XL to arrayOf(BpkText.TextStyle.SUBHEADING, BpkText.TextStyle.HEADING3, BpkText.TextStyle.HEADING3),
  BpkText.XXL to arrayOf(BpkText.TextStyle.HEADING2, BpkText.TextStyle.HEADING2, BpkText.TextStyle.HEADING2),
  BpkText.XXXL to arrayOf(BpkText.TextStyle.HEADING1, BpkText.TextStyle.HEADING1, BpkText.TextStyle.HEADING1),
)

open class BpkText @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0,
) : AppCompatTextView(context, attrs, defStyleAttr) {

  @Deprecated("Use new text styles instead")
  enum class Weight {
    NORMAL,
    EMPHASIZED,
    HEAVY
  }

  enum class TextStyle(internal val id: Int) {
    HERO1(8),
    HERO2(9),
    HERO3(10),
    HERO4(11),
    HERO5(12),
    HEADING1(13),
    HEADING2(14),
    HEADING3(15),
    HEADING4(16),
    HEADING5(17),
    SUBHEADING(18),
    BODY_LONGFORM(19),
    BODY_DEFAULT(20),
    LABEL1(21),
    LABEL2(22),
    FOOTNOTE(23),
    CAPTION(24),
    ;

    fun toStyle() =
      when (this) {
        HERO1 -> R.attr.bpkTextHero1Appearance
        HERO2 -> R.attr.bpkTextHero2Appearance
        HERO3 -> R.attr.bpkTextHero3Appearance
        HERO4 -> R.attr.bpkTextHero4Appearance
        HERO5 -> R.attr.bpkTextHero5Appearance
        HEADING1 -> R.attr.bpkTextHeading1Appearance
        HEADING2 -> R.attr.bpkTextHeading2Appearance
        HEADING3 -> R.attr.bpkTextHeading3Appearance
        HEADING4 -> R.attr.bpkTextHeading4Appearance
        HEADING5 -> R.attr.bpkTextHeading5Appearance
        SUBHEADING -> R.attr.bpkTextSubheadingAppearance
        BODY_LONGFORM -> R.attr.bpkTextBodyLongformAppearance
        BODY_DEFAULT -> R.attr.bpkTextBodyDefaultAppearance
        LABEL1 -> R.attr.bpkTextLabel1Appearance
        LABEL2 -> R.attr.bpkTextLabel2Appearance
        FOOTNOTE -> R.attr.bpkTextFootnoteAppearance
        CAPTION -> R.attr.bpkTextCaptionAppearance
      }
  }

  @Deprecated("Use TextStyle instead")
  @IntDef(XS, SM, BASE, LG, XL, XXL, XXXL, CAPS)
  annotation class Styles

  companion object {
    @Deprecated("Use CAPTION")
    const val XS = 0

    @Deprecated("Use FOOTNOTE or LABEL2 (emphasized)")
    const val SM = 1

    @Deprecated("Use BODY_DEFAULT or HEADING5 (emphasized)")
    const val BASE = 2

    @Deprecated("Use BODY_LONGFORM or HEADING4 (emphasized)")
    const val LG = 3

    @Deprecated("Use SUBHEADING or HEADING3 (emphasized)")
    const val XL = 4

    @Deprecated("Use HEADING2")
    const val XXL = 5

    @Deprecated("Use HEADING1")
    const val XXXL = 6

    @Deprecated("Use CAPTION")
    const val CAPS = 7

    @JvmStatic
    @Deprecated("Use new TextStyle instead")
    fun getFont(
      context: Context,
      textStyle: Int = BASE,
      weight: Weight = Weight.NORMAL,
    ) =
      internalGetFont(context, mapLegacyStyle(textStyle, weight))

    @JvmStatic
    fun getFont(
      context: Context,
      textStyle: TextStyle,
    ) =
      internalGetFont(context, textStyle)

    private fun mapLegacyStyle(textStyle: Int, weight: Weight): TextStyle =
      legacyStyleMapping[textStyle]
        .let { if (it == null) throw IllegalStateException("Unsupported text style") else it[weight.ordinal] }
        .let { it ?: throw IllegalStateException("Unsupported text style") }
  }

  private var _textStyle: TextStyle = TextStyle.BODY_DEFAULT
  var textStyle
    get() = _textStyle
    set(value) {
      _textStyle = value
      this.setup()
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
      // this needs to be base, rather than body default to ensure the legacy weight property is still working as expected
      val textStyleArg = it.getInt(R.styleable.BpkText_textStyle, BASE)
      val weight = it.getInt(R.styleable.BpkText_weight, -1)
        .let { arg -> if (arg == -1) Weight.NORMAL else Weight.values()[arg] }
      _textStyle = TextStyle.values().firstOrNull { it.id == textStyleArg } ?: mapLegacyStyle(textStyleArg, weight)

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
    val textAppearance = getStyleId(context, textStyle)

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

private fun internalGetFont(context: Context, textStyle: BpkText.TextStyle): BpkText.FontDefinition {
  val styleRes = getStyleId(context, textStyle)

  val textStyleAttributes = context.obtainStyledAttributes(styleRes, R.styleable.BpkTextStyle)
  val fontSize = textStyleAttributes.getDimensionPixelSize(
    R.styleable.BpkTextStyle_android_textSize,
    ResourcesUtil.dpToPx(16, context)
  )
  val letterSpacing = textStyleAttributes.getFloat(R.styleable.BpkTextStyle_android_letterSpacing, -1f)
    .let { if (it == -1f) null else it }

  val lineHeight = textStyleAttributes.getDimensionPixelSize(R.styleable.BpkTextStyle_lineHeight, -1)
    .let { if (it == -1) null else it }

  val typefaceResId = textStyleAttributes.getResourceId(R.styleable.BpkTextStyle_android_fontFamily, -1)
  val typeface = if (typefaceResId == -1) {
    textStyleAttributes.getString(R.styleable.BpkTextStyle_android_fontFamily)?.let { Typeface.create(it, Typeface.NORMAL) }
  } else {
    ResourcesCompat.getFont(context, typefaceResId)
  } ?: throw IllegalStateException("Bpk font not configured correctly")

  textStyleAttributes.recycle()

  return BpkText.FontDefinition(typeface, fontSize, letterSpacing, lineHeight)
}

@StyleRes
private fun getStyleId(context: Context, textStyle: BpkText.TextStyle): Int {
  val styleProps = textStyle.toStyle()

  val outValue = TypedValue()
  if (context.theme.resolveAttribute(styleProps, outValue, true)) {
    return outValue.resourceId
  }

  return 0
}
