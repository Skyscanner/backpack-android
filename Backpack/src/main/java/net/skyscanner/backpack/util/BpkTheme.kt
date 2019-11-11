package net.skyscanner.backpack.util

import android.content.Context
import android.content.ContextWrapper
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.TypedValue
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.appcompat.view.ContextThemeWrapper
import net.skyscanner.backpack.R
import androidx.core.content.ContextCompat
import java.lang.IllegalStateException

class BpkTheme {
  companion object {

    /***
     * Utility function to fetch Backpack's primary color.
     *
     * The primary color can set via theming via the `bpkPrimaryColor` attribute.
     *
     * The attribute is expected to be either a color or a color reference.
     * In case the attribute is not found the default [R.color.bpkPrimary]
     * will be returned.
     *
     * @param context the current ui context.
     * @return a color integer
     */
    @JvmStatic
    @ColorInt
    fun getPrimaryColor(context: Context) =
      resolveThemeColorWithDefault(context, R.attr.bpkPrimaryColor)

    /**
     * Wrap the current `context` with default Backpack colors. After
     * this call the context is guaranteed to have all Backpack default
     * theme colors defined.
     *
     * This function will not replace any property that has already been
     * defined in the current context, only properties that are not present
     * will be added with its default value.
     *
     * @param context The context to be wrapped
     * @return a new [Context] with defaults
     */
    @JvmStatic
    fun wrapContextWithDefaults(context: Context) =
      net.skyscanner.backpack.util.wrapContextWithDefaults(context)

    /**
     * Apply the default Backpack colors to the current context`. After
     * this call the context is guaranteed to have all Backpack default
     * theme colors defined.
     *
     * This function will not replace any property that has already been
     * defined in the current context, only properties that are not present
     * will be added with its default value.
     *
     * @param context The context to be wrapped
     */
    @JvmStatic
    fun applyDefaultsToContext(context: Context) =
      net.skyscanner.backpack.util.applyDefaultsToContext(context)
  }
}

internal fun wrapContextWithDefaults(context: Context): Context {
  val copy = ContextWrapper(context)
  applyDefaultsToContext(copy)
  return copy
}

internal fun applyDefaultsToContext(context: Context) {
  context.theme?.applyStyle(R.style.BpkDefaultTheme, false)
}

internal fun resolveThemeColorWithDefault(context: Context, resId: Int): Int {
  return resolveThemeColor(BpkTheme.wrapContextWithDefaults(context), resId)
    // This should only ever happen if the value defined for the color is wrong as the property
    // is guaranteed to be there because of the ContextThemeWrapper
    ?: throw IllegalStateException("Could not resolve themed color!")
}

/**
 * Resolve a color attribute in the current theme.
 * The color can be either a color or a color reference.
 *
 * @param context the current ui context.
 * @param resId the attribute id.
 */
internal fun resolveThemeColor(context: Context, resId: Int): Int? {
  val typedValue = TypedValue()
  val wasResolved = context.theme.resolveAttribute(resId, typedValue, true)

  return if (wasResolved && typedValue.resourceId == 0) {
    typedValue.data
  } else if (wasResolved) {
    ContextCompat.getColor(context, typedValue.resourceId)
  } else {
    null
  }
}

internal fun resolveThemeDrawable(context: Context, @AttrRes resId: Int): Drawable? {
  val typedValue = TypedValue()
  val wasResolved = context.theme.resolveAttribute(resId, typedValue, true)

  return if (wasResolved && typedValue.resourceId == 0) {
    ContextCompat.getDrawable(context, typedValue.data)
  } else if (wasResolved) {
    ContextCompat.getDrawable(context, typedValue.resourceId)
  } else {
    null
  }
}

/**
 * Return a [ContextThemeWrapper] wrapping the current context with the properties defined in `styleAttr`.
 *
 * @param context Context to be wrapped.
 * @param attributeSet List of attributes for the current component.
 * @param styleAttr Style attribute to provide default values for the current context.
 */
internal fun createContextThemeWrapper(context: Context, attributeSet: AttributeSet?, styleAttr: Int): Context {
  val a = context.obtainStyledAttributes(attributeSet, intArrayOf(styleAttr), 0, 0)
  val themeId = a.getResourceId(0, 0)
  a.recycle()
  return ContextThemeWrapper(context, themeId)
}
