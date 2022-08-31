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
import android.os.Build
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.widget.TextView
import androidx.annotation.StyleRes
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.res.ResourcesCompat
import androidx.core.widget.TextViewCompat
import net.skyscanner.backpack.R
import net.skyscanner.backpack.util.BpkTheme
import net.skyscanner.backpack.util.ResourcesUtil
import net.skyscanner.backpack.util.isInEditMode
import net.skyscanner.backpack.util.use

open class BpkText @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0,
) : AppCompatTextView(context, attrs, defStyleAttr) {

  enum class TextStyle(internal val id: Int) {
    Hero1(0),
    Hero2(1),
    Hero3(2),
    Hero4(3),
    Hero5(4),
    Heading1(5),
    Heading2(6),
    Heading3(7),
    Heading4(8),
    Heading5(9),
    Subheading(10),
    BodyLongform(11),
    BodyDefault(12),
    Label1(13),
    Label2(14),
    Label3(15),
    Footnote(16),
    Caption(17),
    ;

    fun toStyle() =
      when (this) {
        Hero1 -> R.attr.bpkTextHero1Appearance
        Hero2 -> R.attr.bpkTextHero2Appearance
        Hero3 -> R.attr.bpkTextHero3Appearance
        Hero4 -> R.attr.bpkTextHero4Appearance
        Hero5 -> R.attr.bpkTextHero5Appearance
        Heading1 -> R.attr.bpkTextHeading1Appearance
        Heading2 -> R.attr.bpkTextHeading2Appearance
        Heading3 -> R.attr.bpkTextHeading3Appearance
        Heading4 -> R.attr.bpkTextHeading4Appearance
        Heading5 -> R.attr.bpkTextHeading5Appearance
        Subheading -> R.attr.bpkTextSubheadingAppearance
        BodyLongform -> R.attr.bpkTextBodyLongformAppearance
        BodyDefault -> R.attr.bpkTextBodyDefaultAppearance
        Label1 -> R.attr.bpkTextLabel1Appearance
        Label2 -> R.attr.bpkTextLabel2Appearance
        Label3 -> R.attr.bpkTextLabel3Appearance
        Footnote -> R.attr.bpkTextFootnoteAppearance
        Caption -> R.attr.bpkTextCaptionAppearance
      }
  }

  companion object {

    @JvmStatic
    fun getFont(
      context: Context,
      textStyle: TextStyle,
    ) =
      internalGetFont(context, textStyle)
  }

  private var _textStyle: TextStyle = TextStyle.BodyDefault
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
      _textStyle = it.getInt(R.styleable.BpkText_textStyle, TextStyle.BodyDefault.id)
        .let { textStyleArg -> TextStyle.values().first { it.id == textStyleArg } }

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
    if (context.isInEditMode() && Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      // Preview is broken when using the compat version for font loading as of AS 4.2 (see https://issuetracker.google.com/issues/150587499)
      context.resources.getFont(typefaceResId)
    } else {
      ResourcesCompat.getFont(context, typefaceResId)
    }
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
