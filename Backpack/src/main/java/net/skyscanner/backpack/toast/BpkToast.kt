package net.skyscanner.backpack.toast

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.TextPaint
import android.text.style.CharacterStyle
import android.util.AttributeSet
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import net.skyscanner.backpack.R
import net.skyscanner.backpack.text.BpkText
import net.skyscanner.backpack.util.createContextThemeWrapper

open class BpkToast @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : View(createContextThemeWrapper(context, attrs, R.attr.bpkToastStyle), attrs, defStyleAttr),
  (CharSequence?) -> Unit {

  private val font = BpkText.getFont(context, BpkText.LG, BpkText.Weight.NORMAL)

  @SuppressLint("MissingSuperCall")
  final override fun draw(canvas: Canvas) = Unit

  final override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
    setMeasuredDimension(0, 0)
  }

  fun show(text: CharSequence?) {
    text?.let { show(context, font, it, Toast.LENGTH_SHORT) }
  }

  fun show(@StringRes text: Int) {
    show(context, font, resources.getText(text), Toast.LENGTH_SHORT)
  }

  fun showLong(text: CharSequence?) {
    text?.let { show(context, font, it, Toast.LENGTH_LONG) }
  }

  fun showLong(@StringRes text: Int) {
    show(context, font, resources.getText(text), Toast.LENGTH_LONG)
  }

  override fun invoke(text: CharSequence?) =
    show(text)

  companion object {

    @JvmStatic
    fun show(context: Context, text: CharSequence?) {
      text?.let { show(context, BpkText.getFont(context), it, Toast.LENGTH_SHORT) }
    }

    @JvmStatic
    fun show(context: Context, @StringRes text: Int) {
      show(context, context.resources.getText(text))
    }

    @JvmStatic
    fun showLong(context: Context, text: CharSequence?) {
      text?.let { show(context, BpkText.getFont(context), it, Toast.LENGTH_LONG) }
    }

    @JvmStatic
    fun showLong(context: Context, @StringRes text: Int) {
      showLong(context, context.resources.getText(text))
    }

    @JvmStatic
    private fun show(context: Context, font: BpkText.FontDefinition, text: CharSequence, duration: Int) {
      val ssb = SpannableStringBuilder()
      ssb.append(text, BpkTextSpan(font), Spanned.SPAN_INCLUSIVE_INCLUSIVE)
      Toast.makeText(context, ssb, duration).show()
    }
  }

  private class BpkTextSpan(
    private val font: BpkText.FontDefinition
  ) : CharacterStyle() {
    override fun updateDrawState(tp: TextPaint) {
      font.applyTo(tp)
    }
  }
}
