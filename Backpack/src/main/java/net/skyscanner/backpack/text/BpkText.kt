package net.skyscanner.backpack.text

import android.content.Context
import android.content.res.TypedArray
import android.support.annotation.IntDef
import android.support.v4.widget.TextViewCompat
import android.support.v7.widget.AppCompatTextView
import android.util.AttributeSet
import net.skyscanner.backpack.R


open class BpkText(
  context: Context,
  attrs: AttributeSet?,
  defStyleAttr: Int) : AppCompatTextView(context, attrs, defStyleAttr) {

  @IntDef(XS, SM, BASE, LG, XL, XXL)

  annotation class Styles

  companion object {
    const val XS = 0
    const val SM = 1
    const val BASE = 2
    const val LG = 3
    const val XL = 4
    const val XXL = 5
  }

  @Styles
  var textStyle: Int = BASE
    set(value) {
      field = value
      this.setup()
    }

  var emphasize: Boolean = false
    set(value) {
      field = value
      this.setup()
    }

  private val styleMapping = mapOf(
    XS to Pair(R.style.bpkTextXsEmphasized, R.style.bpkTextXs),
    SM to Pair(R.style.bpkTextSmEmphasized, R.style.bpkTextSm),
    BASE to Pair(R.style.bpkTextBaseEmphasized, R.style.bpkTextBase),
    LG to Pair(R.style.bpkTextLgEmphasized, R.style.bpkTextLg),
    XL to Pair(R.style.bpkTextXlEmphasized, R.style.bpkTextXl),
    XXL to Pair(R.style.bpkTextXxlEmphasized, R.style.bpkTextXxl)
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
    emphasize = a.getBoolean(R.styleable.BpkText_emphasize, false)

    a.recycle()
  }

  private fun setup() {

    val styleProps = styleMapping[textStyle]

    if (styleProps != null) {
      TextViewCompat.setTextAppearance(this, if (emphasize) styleProps.first else styleProps.second)
    } else {
      throw IllegalStateException("Invalid textStyle")
    }
  }
}
