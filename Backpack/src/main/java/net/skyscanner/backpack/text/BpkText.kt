package net.skyscanner.backpack.text

import android.content.Context
import android.graphics.Paint
import android.graphics.Color
import android.graphics.PorterDuffColorFilter
import android.graphics.PorterDuff
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
import net.skyscanner.backpack.util.ResourcesUtil
import net.skyscanner.backpack.util.use

private val styleMapping = mapOf(
  BpkText.XS to arrayOf(R.attr.bpkTextXsAppearance, R.attr.bpkTextXsEmphasizedAppearance, null),
  BpkText.SM to arrayOf(R.attr.bpkTextSmAppearance, R.attr.bpkTextSmEmphasizedAppearance, null),
  BpkText.CAPS to arrayOf(R.attr.bpkTextCapsAppearance, R.attr.bpkTextCapsEmphasizedAppearance, null),
  BpkText.BASE to arrayOf(R.attr.bpkTextBaseAppearance, R.attr.bpkTextBaseEmphasizedAppearance, null),
  BpkText.LG to arrayOf(R.attr.bpkTextLgAppearance, R.attr.bpkTextLgEmphasizedAppearance, null),
  BpkText.XL to arrayOf(R.attr.bpkTextXlAppearance, R.attr.bpkTextXlEmphasizedAppearance, R.attr.bpkTextXlHeavyAppearance),
  BpkText.XXL to arrayOf(R.attr.bpkTextXxlAppearance, R.attr.bpkTextXxlEmphasizedAppearance, R.attr.bpkTextXxlHeavyAppearance),
  BpkText.XXXL to arrayOf(R.attr.bpkTextXxxlAppearance, R.attr.bpkTextXxxlEmphasizedAppearance, R.attr.bpkTextXxxlHeavyAppearance)
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

  @IntDef(XS, SM, BASE, LG, XL, XXL, XXXL, CAPS)
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

    @JvmStatic
    fun getFont(context: Context, textStyle: Int = BpkText.BASE, weight: BpkText.Weight = BpkText.Weight.NORMAL) =
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

  private val fontResolver = FontFamilyResolver(this.context)

  /**
   * Sets the text style to emphasized.
   *
   * @Deprecated use [BpkText.weight] instead.
   */
  @Deprecated("Use weight instead")
  var emphasize: Boolean = false
    get() = weight == Weight.EMPHASIZED || field
    set(value) {
      field = value
      if (value) {
        weight = Weight.EMPHASIZED
      }
    }

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
      if (weightArg == -1) {
        // if weight has not been set we still read the emphasize property
        emphasize = it.getBoolean(R.styleable.BpkText_emphasize, false)
      } else {
        _weight = Weight.values()[weightArg]
      }

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

      val drawableTint = it.getColorStateList(R.styleable.BpkText_drawableTint)
      if (drawableTint != null) {
        setDrawableTint(drawableTint.getColorForState(EMPTY_STATE_SET, Color.WHITE))
      }
    }
  }

  fun setDrawableTint(color: Int) {
    for (drawable in compoundDrawablesRelative) {
      drawable?.colorFilter = PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN)
    }
  }

  private fun setup() {
    val textAppearance = getStyleId(context, textStyle, weight)
    if (textStyle == CAPS) {
      isAllCaps = true
    }

    TextViewCompat.setTextAppearance(this, textAppearance)
    fontResolver.getForWeight(weight)?.let { this.typeface = it }
  }

  data class FontDefinition(
    val typeface: Typeface,
    val fontSize: Int,
    val letterSpacing: Float?
  ) {

    fun applyTo(text: TextView) {
      text.typeface = typeface
      text.setTextSize(TypedValue.COMPLEX_UNIT_PX, fontSize.toFloat())
      letterSpacing?.let { text.letterSpacing = it }
    }

    fun applyTo(paint: Paint) {
      paint.typeface = typeface
      paint.textSize = fontSize.toFloat()
      letterSpacing?.let { paint.letterSpacing = it }
    }
  }
}

private fun internalGetFont(context: Context, textStyle: Int = BpkText.BASE, weight: BpkText.Weight = BpkText.Weight.NORMAL): BpkText.FontDefinition {
  val styleRes = getStyleId(context, textStyle, weight)

  val textStyleAttributes = context.obtainStyledAttributes(styleRes, R.styleable.BpkTextStyle)
  val fontSize = textStyleAttributes.getDimensionPixelSize(R.styleable.BpkTextStyle_android_textSize, ResourcesUtil.dpToPx(16, context))
  val letterSpacing = textStyleAttributes.getFloat(R.styleable.BpkTextStyle_android_letterSpacing, -1f)
    .let { if (it == -1f) null else it }

  textStyleAttributes.recycle()

  return FontFamilyResolver(context).getForWeight(weight)?.let {
    BpkText.FontDefinition(it, fontSize, letterSpacing)
  } ?: throw IllegalStateException("Bpk font not configured correctly")
}

@StyleRes
private fun getStyleId(context: Context, textStyle: Int, weight: BpkText.Weight): Int {
  val styleProps = styleMapping[textStyle]
  styleProps ?: throw IllegalStateException("Invalid textStyle")
  val textAppearanceAttr = styleProps[weight.ordinal]

  textAppearanceAttr
    ?: throw IllegalStateException("Weight $weight is not supported for the current size")

  val outValue = TypedValue()
  if (context.theme.resolveAttribute(textAppearanceAttr, outValue, true)) {
    return outValue.resourceId
  }

  return 0
}
