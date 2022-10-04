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

package net.skyscanner.backpack.snackbar

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.text.SpannableStringBuilder
import android.text.style.ImageSpan
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.content.res.AppCompatResources
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import net.skyscanner.backpack.R
import net.skyscanner.backpack.snackbar.internal.createIconDrawable
import net.skyscanner.backpack.snackbar.internal.customiseText
import net.skyscanner.backpack.snackbar.internal.setBackgroundColorCompat
import net.skyscanner.backpack.text.BpkFontSpan
import net.skyscanner.backpack.text.BpkText
import net.skyscanner.backpack.util.isScreenReaderOn
import net.skyscanner.backpack.util.use

/**
 * A wrapper around [Snackbar] providing the required customization.
 *
 * Use [BpkSnackbar.make] to create a new instance, then use it in the same way as a [Snackbar].
 *
 * @see BpkSnackbar.make
 * @see BpkSnackbar.setText
 * @see BpkSnackbar.setAction
 * @see BpkSnackbar.setDuration
 * @see BpkSnackbar.setDuration
 * @see BpkSnackbar.setOnDismissed
 * @see BpkSnackbar.setBehaviour
 * @see BpkSnackbar.show
 */
@Suppress("MemberVisibilityCanBePrivate")
class BpkSnackbar private constructor(
  private val context: Context,
  view: View,
  duration: Int,
  @ColorInt private val textColor: Int,
  @ColorInt actionColor: Int,
  @ColorInt backgroundColor: Int
) {

  private val callbacks = ArrayList<BaseTransientBottomBar.BaseCallback<BpkSnackbar>>()

  private val snackbar = Snackbar.make(view, "", duration).apply {
    setBackgroundColorCompat(backgroundColor)
    addCallback(object : BaseTransientBottomBar.BaseCallback<Snackbar>() {
      override fun onShown(transientBottomBar: Snackbar?) {
        callbacks.forEach { it.onShown(this@BpkSnackbar) }
      }

      override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
        callbacks.forEach { it.onDismissed(this@BpkSnackbar, event) }
      }
    })
  }

  private val titleFontSpan = BpkFontSpan(context, BpkText.TextStyle.Label2)
  private val textFontSpan = BpkFontSpan(context, BpkText.TextStyle.Footnote)

  private val textView = snackbar.view.findViewById<TextView>(R.id.snackbar_text).apply {
    gravity = Gravity.START or Gravity.CENTER_VERTICAL
    compoundDrawablePadding = context.resources.getDimensionPixelSize(R.dimen.bpkSpacingMd)
    setTextColor(textColor)
    minimumHeight = context.resources.getDimensionPixelSize(R.dimen.bpk_snackbar_min_height)
  }

  private val actionView = snackbar.view.findViewById<TextView>(R.id.snackbar_action).apply {
    setTextColor(actionColor)
    BpkText.getFont(context, BpkText.TextStyle.Label2).applyTo(this)
    transformationMethod = null
  }

  private var title: CharSequence? = null
  private var text: CharSequence? = null

  fun setTitle(title: CharSequence): BpkSnackbar = apply {
    this.title = title.toString().uppercase(context.resources.configuration.locales[0])
    updateTitleIfShown(isShown)
  }

  fun setText(message: CharSequence): BpkSnackbar = apply {
    this.text = message
    updateTitleIfShown(isShown)
  }

  fun setIcon(icon: Drawable?): BpkSnackbar = apply {
    textView.setCompoundDrawablesRelative(createIconDrawable(icon, textColor), null, null, null)
  }

  fun setIcon(@DrawableRes icon: Int): BpkSnackbar =
    setIcon(AppCompatResources.getDrawable(context, icon))

  fun setText(@StringRes resId: Int): BpkSnackbar =
    setText(context.getString(resId))

  fun setAction(text: CharSequence, listener: View.OnClickListener): BpkSnackbar = apply {
    setActionInternal(text = text, icon = null, callback = listener, contentDescription = null)
  }

  fun setAction(@StringRes resId: Int, listener: View.OnClickListener): BpkSnackbar = apply {
    return setAction(context.getText(resId), listener)
  }

  fun setAction(@DrawableRes resId: Int, contentDescription: String, listener: View.OnClickListener): BpkSnackbar = apply {
    return setAction(AppCompatResources.getDrawable(context, resId)!!, contentDescription, listener)
  }

  fun setAction(icon: Drawable, contentDescription: String, listener: View.OnClickListener): BpkSnackbar = apply {
    setActionInternal(text = null, icon = icon, callback = listener, contentDescription = contentDescription)
  }

  val duration: Int
    get() = snackbar.duration

  /**
   * @param duration one of the [LENGTH_SHORT], [LENGTH_LONG], [LENGTH_INDEFINITE]
   */
  fun setDuration(duration: Int): BpkSnackbar = apply {
    snackbar.duration = duration
  }

  val isShown: Boolean
    get() = snackbar.isShown

  fun show() =
    snackbar
      .show()
      .also { updateTitleIfShown(true) }

  fun dismiss() =
    snackbar.dismiss()

  fun addCallback(callback: BaseTransientBottomBar.BaseCallback<BpkSnackbar>?): BpkSnackbar = apply {
    callback?.let(callbacks::add)
  }

  fun setOnDismissed(ignoreDismissAfterAction: Boolean = true, callback: () -> Unit) =
    addCallback(object : Callback() {
      override fun onDismissed(transientBottomBar: BpkSnackbar?, event: Int) {
        super.onDismissed(transientBottomBar, event)
        if (ignoreDismissAfterAction && event == DISMISS_EVENT_ACTION) return
        callback()
      }
    })

  fun removeCallback(callback: BaseTransientBottomBar.BaseCallback<BpkSnackbar>?): BpkSnackbar = apply {
    callbacks.remove(callback)
  }

  val behaviour: BaseTransientBottomBar.Behavior?
    get() = snackbar.behavior

  fun setBehaviour(behavior: BaseTransientBottomBar.Behavior?): BpkSnackbar = apply {
    snackbar.behavior = behavior
  }

  /**
   * Provides access to internal [Snackbar] instance.
   * This method should only be used for accessing properties and methods
   * that [BpkSnackbar] does not have.
   *
   * Neither of [Snackbar.setAction], [Snackbar.setActionTextColor], [Snackbar.setText]
   * should be used with the instance.
   */
  val rawSnackbar: Snackbar =
    snackbar

  private fun updateTitleIfShown(isShown: Boolean) {
    if (isShown) {
      val ssb = SpannableStringBuilder()
      title?.let {
        ssb.append(customiseText(it, titleFontSpan))
        ssb.append(" ")
      }
      text?.let {
        ssb.append(customiseText(it, textFontSpan))
      }
      snackbar.setText(ssb)
    }
  }

  private fun setActionInternal(
    text: CharSequence?,
    icon: Drawable?,
    contentDescription: String?,
    callback: View.OnClickListener
  ) {
    actionView.gravity = when {
      icon != null -> Gravity.CENTER
      else -> Gravity.START or Gravity.CENTER_VERTICAL
    }
    snackbar.setAction(
      when {
        !text.isNullOrEmpty() -> text
        icon != null -> customiseText(contentDescription ?: " ", ImageSpan(createIconDrawable(icon, textColor)!!))
        else -> ""
      },
      callback
    )
  }

  companion object {

    const val LENGTH_INDEFINITE = Snackbar.LENGTH_INDEFINITE

    const val LENGTH_SHORT = Snackbar.LENGTH_SHORT

    const val LENGTH_LONG = Snackbar.LENGTH_LONG

    private const val LENGTH_SCREENREADER = 30 * 1000 // 30 seconds

    /**
     * Creates a new [Snackbar] instance using the given [text] and [duration].
     * [view] is used for accessing the themed context and hierarchy, to decide where to render the [Snackbar]
     *
     * @param view the view to render the snackbar
     * @param text the snackbar message
     * @param duration the snackbar duration
     *
     * @see [BpkSnackbar.LENGTH_INDEFINITE]
     * @see [BpkSnackbar.LENGTH_SHORT]
     * @see [BpkSnackbar.LENGTH_LONG]
     */
    @SuppressLint("Recycle")
    @JvmStatic
    fun make(view: View, text: CharSequence, duration: Int): BpkSnackbar {
      val context = view.context

      @ColorInt var textColor = context.getColor(R.color.bpkTextOnDark)
      @ColorInt var actionColor = context.getColor(R.color.bpkTextOnDark)
      @ColorInt var backgroundColor = context.getColor(R.color.bpkCorePrimary)

      val outValue = TypedValue()
      context.theme.resolveAttribute(R.attr.bpkSnackbarStyle, outValue, true)

      context.obtainStyledAttributes(outValue.resourceId, R.styleable.BpkSnackbar).use {
        textColor = it.getColor(R.styleable.BpkSnackbar_snackbarTextColor, textColor)
        actionColor = it.getColor(R.styleable.BpkSnackbar_snackbarActionColor, actionColor)
        backgroundColor = it.getColor(R.styleable.BpkSnackbar_snackbarBackgroundColor, backgroundColor)
      }

      val adjustedDuration = if (duration != LENGTH_INDEFINITE && context.isScreenReaderOn()) {
        LENGTH_SCREENREADER
      } else {
        duration
      }

      return BpkSnackbar(
        context = context,
        view = view,
        duration = adjustedDuration,
        textColor = textColor,
        actionColor = actionColor,
        backgroundColor = backgroundColor
      )
        .setText(text)
    }

    /**
     * Creates a new [Snackbar] instance using the given [text] and [duration].
     * [view] is used for accessing the themed context and hierarchy, to decide where to render the [Snackbar]
     *
     * @param view the view to render the snackbar
     * @param text the snackbar message
     * @param duration the snackbar duration
     *
     * @see [BpkSnackbar.LENGTH_INDEFINITE]
     * @see [BpkSnackbar.LENGTH_SHORT]
     * @see [BpkSnackbar.LENGTH_LONG]
     */
    @JvmStatic
    fun make(view: View, @StringRes text: Int, duration: Int) =
      make(view, view.resources.getString(text), duration)
  }

  abstract class Callback : BaseTransientBottomBar.BaseCallback<BpkSnackbar>()
}
