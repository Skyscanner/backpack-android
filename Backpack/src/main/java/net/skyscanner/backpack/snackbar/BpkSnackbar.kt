package net.skyscanner.backpack.snackbar

import android.annotation.SuppressLint
import android.content.Context
import android.text.style.ForegroundColorSpan
import android.util.TypedValue
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.StringRes
import com.google.android.material.snackbar.BaseTransientBottomBar
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import net.skyscanner.backpack.R
import net.skyscanner.backpack.snackbar.internal.customiseText
import net.skyscanner.backpack.snackbar.internal.setActionAppearanceCompat
import net.skyscanner.backpack.snackbar.internal.setBackgroundColorCompat
import net.skyscanner.backpack.snackbar.internal.setMessageAppearanceCompat
import net.skyscanner.backpack.text.BpkFontSpan
import net.skyscanner.backpack.text.BpkText
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
  @ColorInt textColor: Int,
  @ColorInt actionColor: Int,
  @ColorInt backgroundColor: Int
) {

  private val textFontSpan = BpkFontSpan(context, BpkText.SM, BpkText.Weight.NORMAL)
  private val textColorSpan = ForegroundColorSpan(textColor)

  private val actionFontSpan = BpkFontSpan(context, BpkText.SM, BpkText.Weight.EMPHASIZED)
  private val actionColorSpan = ForegroundColorSpan(actionColor)

  private val callbacks = ArrayList<BaseTransientBottomBar.BaseCallback<BpkSnackbar>>()

  private val snackbar = Snackbar.make(view, "", duration)

  init {
    snackbar.setBackgroundColorCompat(backgroundColor)
    snackbar.setMessageAppearanceCompat(textFontSpan, textColorSpan)
    snackbar.setActionAppearanceCompat(actionFontSpan, actionColorSpan)
    snackbar.addCallback(object : BaseTransientBottomBar.BaseCallback<Snackbar>() {
      override fun onShown(transientBottomBar: Snackbar?) {
        callbacks.forEach { it.onShown(this@BpkSnackbar) }
      }

      override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
        callbacks.forEach { it.onDismissed(this@BpkSnackbar, event) }
      }
    })
  }

  fun setText(message: CharSequence): BpkSnackbar {
    snackbar.setText(snackbar.customiseText(message, textFontSpan, textColorSpan))
    return this
  }

  fun setText(@StringRes resId: Int): BpkSnackbar =
    setText(context.getString(resId))

  fun setAction(text: CharSequence, listener: View.OnClickListener): BpkSnackbar {
    snackbar.setAction(snackbar.customiseText(text, actionFontSpan, actionColorSpan), listener)
    return this
  }

  fun setAction(@StringRes resId: Int, listener: View.OnClickListener): BpkSnackbar =
    setAction(context.getText(resId), listener)

  val duration: Int
    get() = snackbar.duration

  /**
   * @param duration one of the [LENGTH_SHORT], [LENGTH_LONG], [LENGTH_INDEFINITE]
   */
  fun setDuration(duration: Int): BpkSnackbar {
    snackbar.duration = duration
    return this
  }

  val isShown: Boolean
    get() = snackbar.isShown

  fun show() =
    snackbar.show()

  fun dismiss() =
    snackbar.dismiss()

  fun addCallback(callback: BaseTransientBottomBar.BaseCallback<BpkSnackbar>?): BpkSnackbar {
    callback?.let(callbacks::add)
    return this
  }

  fun removeCallback(callback: BaseTransientBottomBar.BaseCallback<BpkSnackbar>?): BpkSnackbar {
    callbacks.remove(callback)
    return this
  }

  val behaviour: BaseTransientBottomBar.Behavior?
    get() = snackbar.behavior

  fun setBehaviour(behavior: BaseTransientBottomBar.Behavior?): BpkSnackbar {
    snackbar.behavior = behavior
    return this
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

  companion object {

    const val LENGTH_INDEFINITE = Snackbar.LENGTH_INDEFINITE

    const val LENGTH_SHORT = Snackbar.LENGTH_SHORT

    const val LENGTH_LONG = Snackbar.LENGTH_LONG

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

      @ColorInt var textColor = ContextCompat.getColor(context, R.color.bpkBackground)
      @ColorInt var actionColor = ContextCompat.getColor(context, R.color.bpkSkyBlue)
      @ColorInt var backgroundColor = ContextCompat.getColor(context, R.color.bpkSkyGray)

      val outValue = TypedValue()
      context.theme.resolveAttribute(R.attr.bpkSnackbarStyle, outValue, true)

      context.obtainStyledAttributes(outValue.resourceId, R.styleable.BpkSnackbar).use {
        textColor = it.getColor(R.styleable.BpkSnackbar_snackbarTextColor, textColor)
        actionColor = it.getColor(R.styleable.BpkSnackbar_snackbarActionColor, actionColor)
        backgroundColor = it.getColor(R.styleable.BpkSnackbar_snackbarBackgroundColor, backgroundColor)
      }

      return BpkSnackbar(
        context = context,
        view = view,
        duration = duration,
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
