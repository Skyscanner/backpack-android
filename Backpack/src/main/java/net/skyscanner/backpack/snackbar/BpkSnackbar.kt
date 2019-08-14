package net.skyscanner.backpack.snackbar

import android.annotation.SuppressLint
import android.content.Context
import android.text.style.ForegroundColorSpan
import android.util.TypedValue
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.StringRes
import com.google.android.material.snackbar.Snackbar
import net.skyscanner.backpack.R
import net.skyscanner.backpack.text.BpkFontSpan
import net.skyscanner.backpack.text.BpkText
import net.skyscanner.backpack.util.BpkTheme
import net.skyscanner.backpack.util.use

class BpkSnackbar private constructor(
  private val context: Context,
  private val snackbar: Snackbar,
  @ColorInt textColor: Int,
  @ColorInt actionColor: Int,
  @ColorInt backgroundColor: Int
) {

  private val textFontSpan = BpkFontSpan(context, BpkText.SM, BpkText.Weight.NORMAL)
  private val textColorSpan = ForegroundColorSpan(textColor)

  private val actionFontSpan = BpkFontSpan(context, BpkText.SM, BpkText.Weight.EMPHASIZED)
  private val actionColorSpan = ForegroundColorSpan(actionColor)

  init {
    snackbar.setBackgroundColorCompat(backgroundColor)
    snackbar.setMessageAppearanceCompat(textFontSpan, textColorSpan)
    snackbar.setActionAppearanceCompat(actionFontSpan, actionColorSpan)
  }

  fun build(): Snackbar =
     snackbar

  fun setText(message: CharSequence): BpkSnackbar {
    snackbar.setText(snackbar.customiseText(message, textFontSpan, textColorSpan))
    return this
  }

  fun setText(@StringRes resId: Int): BpkSnackbar =
    setText(context.getString(resId))

  fun setAction(text: CharSequence, listener: View.OnClickListener): BpkSnackbar {
    snackbar.setAction(snackbar.customiseText(text, textFontSpan, textColorSpan), listener)
    return this
  }

  fun setAction(@StringRes resId: Int, listener: View.OnClickListener): BpkSnackbar =
    setAction(context.getText(resId), listener)

  inline fun setAction(text: CharSequence, crossinline listener: (View) -> Unit): BpkSnackbar =
    setAction(text, View.OnClickListener { listener(it) })

  inline fun setAction(@StringRes resId: Int, crossinline listener: (View) -> Unit): BpkSnackbar =
    setAction(resId, View.OnClickListener { listener(it) })

  fun setDuration(duration: Int): BpkSnackbar {
    snackbar.duration = duration
    return this
  }

  companion object {

    @JvmStatic
    val LENGTH_INDEFINITE = Snackbar.LENGTH_INDEFINITE

    @JvmStatic
    val LENGTH_SHORT = Snackbar.LENGTH_SHORT

    @JvmStatic
    val LENGTH_LONG = Snackbar.LENGTH_LONG

    @SuppressLint("Recycle")
    @JvmStatic
    fun builder(view: View, text: CharSequence, duration: Int): BpkSnackbar {
      val context = view.context

      @ColorInt var textColor = BpkTheme.getColor(context, R.color.bpkWhite)
      @ColorInt var actionColor = BpkTheme.getColor(context, R.color.bpkBlue500)
      @ColorInt var backgroundColor = BpkTheme.getColor(context, R.color.bpkGray900)

      val outValue = TypedValue()
      context.theme.resolveAttribute(R.attr.bpkSnackbarStyle, outValue, true)

      context.obtainStyledAttributes(outValue.resourceId, R.styleable.BpkSnackbar).use {
        textColor = it.getColor(R.styleable.BpkSnackbar_snackbarTextColor, textColor)
        actionColor = it.getColor(R.styleable.BpkSnackbar_snackbarActionColor, actionColor)
        backgroundColor = it.getColor(R.styleable.BpkSnackbar_snackbarBackgroundColor, backgroundColor)
      }

      return BpkSnackbar(
        context = context,
        snackbar = Snackbar.make(view, "", duration),
        textColor = textColor,
        actionColor = actionColor,
        backgroundColor = backgroundColor
      )
        .setText(text)
    }

    @JvmStatic
    fun builder(view: View, @StringRes text: Int, duration: Int) =
      builder(view, view.resources.getString(text), duration)
  }
}
