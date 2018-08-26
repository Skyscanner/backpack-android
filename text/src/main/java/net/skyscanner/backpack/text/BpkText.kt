package net.skyscanner.backpack.text

import android.content.Context
import android.content.res.TypedArray
import android.support.v4.widget.TextViewCompat
import android.support.v7.widget.AppCompatTextView
import android.util.AttributeSet


open class BpkText(
  context: Context,
  attrs: AttributeSet?,
  defStyleAttr: Int) : AppCompatTextView(context, attrs, defStyleAttr) {

  companion object {
    val XS = 0
    val SM = 1
    val BASE = 2
    val LG = 3
    val XL = 4
    val XXL = 5
  }

  var textStyle: Int = BASE;
  var emphasize: Boolean = false

  constructor(context: Context) : this(context, null)
  constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, R.style.bpkTextBase)

  init {
    initialize(context, attrs, defStyleAttr)
    setup()
  }

  private fun initialize(context: Context, attrs: AttributeSet?, defStyleAttr: Int) {

    val a: TypedArray = context.theme.obtainStyledAttributes(
      attrs,
      R.styleable.text,
      0, 0)

    textStyle = a.getInt(R.styleable.text_textStyle, BASE);
    emphasize = a.getBoolean(R.styleable.text_emphasize, false);

    a.recycle()
  }

  private fun setup() {
    when (textStyle) {
      XS -> TextViewCompat.setTextAppearance(this, if (emphasize) R.style.bpkTextXsEmphasized else R.style.bpkTextXs )
      SM -> TextViewCompat.setTextAppearance(this, if (emphasize) R.style.bpkTextSmEmphasized else R.style.bpkTextSm )
      BASE -> TextViewCompat.setTextAppearance(this, if (emphasize) R.style.bpkTextBaseEmphasized else R.style.bpkTextBase )
      LG -> TextViewCompat.setTextAppearance(this, if (emphasize) R.style.bpkTextLgEmphasized else R.style.bpkTextLg )
      XL -> TextViewCompat.setTextAppearance(this, if (emphasize) R.style.bpkTextXlEmphasized else R.style.bpkTextXl )
      XXL -> TextViewCompat.setTextAppearance(this, if (emphasize) R.style.bpkTextXxlEmphasized else R.style.bpkTextXxl )

    }

  }
}
