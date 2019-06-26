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
import androidx.annotation.CallSuper
import androidx.annotation.StringRes
import net.skyscanner.backpack.R
import net.skyscanner.backpack.text.BpkText
import net.skyscanner.backpack.util.use

open class BpkToast @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr), (CharSequence?) -> Unit {

  enum class Duration(internal val id: Int) {
    Low(Toast.LENGTH_SHORT),
    High(Toast.LENGTH_LONG)
  }

  private val font = BpkText.getFont(context, BpkText.SM, BpkText.Weight.NORMAL)
  private val defaultDuration: Duration

  init {
    val fallback = Duration.Low
    this.defaultDuration = context.theme.obtainStyledAttributes(
      attrs,
      R.styleable.BpkToast,
      defStyleAttr, 0
    ).use {
      val duration = it.getInt(R.styleable.BpkToast_toastDuration, fallback.id)
      Duration.values().find { it.id == duration }
    } ?: fallback
  }

  @SuppressLint("MissingSuperCall")
  final override fun draw(canvas: Canvas) = Unit

  final override fun onDraw(canvas: Canvas) = Unit

  final override fun dispatchDraw(canvas: Canvas?) =
    super.dispatchDraw(canvas)

  final override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
    setMeasuredDimension(0, 0)
  }

  @CallSuper
  open fun show(text: CharSequence?, duration: Duration = defaultDuration) {
    text?.let { render(context, font, it, duration.id) }
  }

  fun show(@StringRes text: Int, duration: Duration = defaultDuration) {
    show(resources.getText(text), duration)
  }

  final override fun invoke(text: CharSequence?) =
    show(text)

  interface Toaster {

    fun show()
  }

  companion object {

    @JvmStatic
    @Deprecated("Use widget instead", replaceWith = ReplaceWith("BpkToast"))
    fun makeText(context: Context, @StringRes text: Int, duration: Duration = Duration.Low) =
      makeText(context, text, duration.id)

    @JvmStatic
    @Deprecated("Use widget instead", replaceWith = ReplaceWith("BpkToast"))
    fun makeText(context: Context, @StringRes text: Int, duration: Int) =
      makeText(context, context.resources.getText(text), duration)

    @JvmStatic
    @Deprecated("Use widget instead", replaceWith = ReplaceWith("BpkToast"))
    fun makeText(context: Context, text: CharSequence?, duration: Duration = Duration.Low) =
      makeText(context, text, duration.id)

    @JvmStatic
    @Deprecated("Use widget instead", replaceWith = ReplaceWith("BpkToast"))
    fun makeText(context: Context, text: CharSequence?, duration: Int) = object : Toaster {
      override fun show() {
        text?.let { render(context, BpkText.getFont(context), it, duration) }
      }
    }

    @JvmStatic
    private fun render(context: Context, font: BpkText.FontDefinition, text: CharSequence, duration: Int) {
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
