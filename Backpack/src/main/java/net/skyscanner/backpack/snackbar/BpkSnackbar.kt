package net.skyscanner.backpack.snackbar

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.text.SpannableStringBuilder
import android.text.Spanned
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
  private val snackbar: Snackbar,
  @ColorInt private val textColor: Int,
  @ColorInt private val actionColor: Int,
  @ColorInt private val backgroundColor: Int
) {

  private val textFontSpan = BpkFontSpan(snackbar.context, BpkText.SM, BpkText.Weight.NORMAL)
  private val textColorSpan = ForegroundColorSpan(textColor)

  private val actionFontSpan = BpkFontSpan(snackbar.context, BpkText.SM, BpkText.Weight.EMPHASIZED)
  private val actionColorSpan = ForegroundColorSpan(actionColor)

  init {
    snackbar.view.backgroundTintList = ColorStateList.valueOf(backgroundColor)
    snackbar.setActionTextColor(actionColor)
  }

  fun build(): Snackbar =
     snackbar

  fun setText(message: CharSequence): BpkSnackbar {
    val spanned = SpannableStringBuilder(message).apply {
      setSpan(textFontSpan, 0, length, Spanned.SPAN_INCLUSIVE_INCLUSIVE)
      setSpan(textColorSpan, 0, length, Spanned.SPAN_INCLUSIVE_INCLUSIVE)
    }

    snackbar.setText(spanned)
    return this
  }

  fun setText(@StringRes resId: Int): BpkSnackbar =
    setText(snackbar.context.getString(resId))

  fun setAction(text: CharSequence, listener: View.OnClickListener): BpkSnackbar {
    val spanned = SpannableStringBuilder(text).apply {
      setSpan(actionFontSpan, 0, length, Spanned.SPAN_INCLUSIVE_INCLUSIVE)
      setSpan(actionColorSpan, 0, length, Spanned.SPAN_INCLUSIVE_INCLUSIVE)
    }

    snackbar.setAction(spanned, listener)
    return this
  }

  fun setAction(@StringRes resId: Int, listener: View.OnClickListener): BpkSnackbar =
    setAction(snackbar.context.getText(resId), listener)

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
    fun make(view: View, text: CharSequence, duration: Int): BpkSnackbar {
      val context = view.context

      var textColor = BpkTheme.getColor(context, R.color.bpkWhite)
      var actionColor = BpkTheme.getColor(context, R.color.bpkBlue500)
      var backgroundColor = BpkTheme.getColor(context, R.color.bpkGray900)

      val outValue = TypedValue()
      context.theme.resolveAttribute(R.attr.bpkSnackbarStyle, outValue, true)

      context.obtainStyledAttributes(outValue.resourceId, R.styleable.BpkSnackbar).use {
        textColor = it.getColor(R.styleable.BpkSnackbar_snackbarTextColor, textColor)
        actionColor = it.getColor(R.styleable.BpkSnackbar_snackbarActionColor, actionColor)
        backgroundColor = it.getColor(R.styleable.BpkSnackbar_snackbarBackgroundColor, backgroundColor)
      }

      return BpkSnackbar(
        snackbar = Snackbar.make(view, "", duration),
        textColor = textColor,
        actionColor = actionColor,
        backgroundColor = backgroundColor
      )
        .setText(text)
    }

    @JvmStatic
    fun make(view: View, @StringRes text: Int, duration: Int) =
      make(view, view.resources.getString(text), duration)
  }
}
