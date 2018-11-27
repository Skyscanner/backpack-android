package net.skyscanner.backpack.text

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import androidx.annotation.IntDef
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.widget.TextViewCompat
import net.skyscanner.backpack.R
import net.skyscanner.backpack.util.enableDebugHighlight

open class BpkText(
  context: Context,
  attrs: AttributeSet?,
  defStyleAttr: Int
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

  constructor(context: Context) : this(context, null)
  constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, R.style.bpkTextBase)

  init {
    initialize(context, attrs, defStyleAttr)
    setup()
  }

  private fun initialize(context: Context, attrs: AttributeSet?, defStyleAttr: Int) {

    val a: TypedArray = context.theme.obtainStyledAttributes(
      attrs,
      R.styleable.BpkText,
      defStyleAttr, 0)

    textStyle = a.getInt(R.styleable.BpkText_textStyle, BASE)
    val weightArg = a.getInt(R.styleable.BpkText_weight, -1)
    if (weightArg == -1) {
      // if weight has not been set we still read the emphasize property
      emphasize = a.getBoolean(R.styleable.BpkText_emphasize, false)
    } else {
      weight = Weight.values()[weightArg]
    }

    a.recycle()
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
    enableDebugHighlight()
  }
}
