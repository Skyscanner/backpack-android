package net.skyscanner.backpack.text

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.annotation.FontRes
import androidx.annotation.IntDef
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.widget.TextViewCompat
import net.skyscanner.backpack.R
import net.skyscanner.backpack.util.createContextThemeOverlayWrapper

open class BpkText @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : AppCompatTextView(createContextThemeOverlayWrapper(context, attrs), attrs, defStyleAttr) {

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
  }

  @Styles
  var textStyle: Int = BASE
    set(value) {
      field = value
      this.setup()
    }

  var weight = Weight.NORMAL
    set(value) {
      field = value
      setup()
    }

  @FontRes
  private var fontBase: Int = -1

  @FontRes
  private var fontHeavy: Int = -1

  @FontRes
  private var fontEmphasized: Int = -1

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

  private val styleMapping = mapOf(
    XS to arrayOf(R.style.bpkTextXs, R.style.bpkTextXsEmphasized, null),
    SM to arrayOf(R.style.bpkTextSm, R.style.bpkTextSmEmphasized, null),
    CAPS to arrayOf(R.style.bpkTextCaps, R.style.bpkTextCapsEmphasized, null),
    BASE to arrayOf(R.style.bpkTextBase, R.style.bpkTextBaseEmphasized, null),
    LG to arrayOf(R.style.bpkTextLg, R.style.bpkTextLgEmphasized, null),
    XL to arrayOf(R.style.bpkTextXl, R.style.bpkTextXlEmphasized, R.style.bpkTextXlHeavy),
    XXL to arrayOf(R.style.bpkTextXxl, R.style.bpkTextXxlEmphasized, R.style.bpkTextXxlHeavy),
    XXXL to arrayOf(R.style.bpkTextXxxl, R.style.bpkTextXxxlEmphasized, R.style.bpkTextXxxlHeavy)
  )

  init {
    initialize(attrs, defStyleAttr)
    setup()
  }

  private fun initialize(attrs: AttributeSet?, defStyleAttr: Int) {

    val styleAttr = when (defStyleAttr) {
      0 -> R.attr.bpkTextStyle
      else -> defStyleAttr
    }

    val a: TypedArray = context.theme.obtainStyledAttributes(
      attrs,
      R.styleable.BpkText,
      styleAttr, 0)

    textStyle = a.getInt(R.styleable.BpkText_textStyle, BASE)
    val weightArg = a.getInt(R.styleable.BpkText_weight, -1)
    if (weightArg == -1) {
      // if weight has not been set we still read the emphasize property
      emphasize = a.getBoolean(R.styleable.BpkText_emphasize, false)
    } else {
      weight = Weight.values()[weightArg]
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

    val drawableTint = a.getColorStateList(R.styleable.BpkText_drawableTint)
    if (drawableTint != null) {
      setDrawableTint(drawableTint.getColorForState(EMPTY_STATE_SET, Color.WHITE))
    }

    fontBase = a.getResourceId(R.styleable.BpkText_fontFamilyBase, -1)
    fontEmphasized = a.getResourceId(R.styleable.BpkText_fontFamilyEmphasized, -1)
    fontHeavy = a.getResourceId(R.styleable.BpkText_fontFamilyHeavy, -1)

    if (fontBase == -1 || fontEmphasized == -1 || fontHeavy == -1) {
      Log.w("BpkText", "Values for one or more of fontBase, fontEmphasized, fontHeavy is not set.")
    }
    a.recycle()
  }

  fun setDrawableTint(color: Int) {
    for (drawable in compoundDrawablesRelative) {
      drawable?.colorFilter = PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN)
    }
  }

  private fun setup() {

    val styleProps = styleMapping[textStyle]
    styleProps ?: throw IllegalStateException("Invalid textStyle")
    val textAppearance = styleProps[weight.ordinal]
    textAppearance
      ?: throw IllegalStateException("Weight $weight is not supported for the current size")

    if (textStyle == CAPS) {
      isAllCaps = true
    }

    TextViewCompat.setTextAppearance(this, textAppearance)

    val fontResource = when (weight) {
      Weight.EMPHASIZED -> fontEmphasized
      Weight.HEAVY -> fontHeavy
      Weight.NORMAL -> fontBase
    }

    if (fontResource != -1) {
      this.typeface = FontCache[fontResource, context]
    }
  }
}

internal fun getFontFromTheme(context: Context, weight: BpkText.Weight = BpkText.Weight.NORMAL): Int? {
  val a = context.theme.obtainStyledAttributes(null, R.styleable.BpkText, R.attr.bpkTextStyle, 0)

  val nullIfInvalid = { resId: Int -> if (resId == -1) null else resId }

  val fontBase = a.getResourceId(R.styleable.BpkText_fontFamilyBase, -1).let(nullIfInvalid)
  val fontEmphasized = a.getResourceId(R.styleable.BpkText_fontFamilyEmphasized, -1).let(nullIfInvalid)
  val fontHeavy = a.getResourceId(R.styleable.BpkText_fontFamilyHeavy, -1).let(nullIfInvalid)

  val fontResource = when (weight) {
    BpkText.Weight.EMPHASIZED -> fontEmphasized
    BpkText.Weight.HEAVY -> fontHeavy
    BpkText.Weight.NORMAL -> fontBase
  }

  a.recycle()

  return fontResource
}
