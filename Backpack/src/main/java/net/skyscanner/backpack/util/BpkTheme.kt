package net.skyscanner.backpack.util

import android.content.Context
import android.content.ContextWrapper
import android.util.AttributeSet
import android.util.TypedValue
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.appcompat.view.ContextThemeWrapper
import net.skyscanner.backpack.R
import androidx.core.content.ContextCompat
import java.lang.IllegalStateException

private val COLOR_MAPPINGS = mapOf(
  R.color.bpkGray50 to R.attr.bpkGray50Color,
  R.color.bpkGray100 to R.attr.bpkGray100Color,
  R.color.bpkGray300 to R.attr.bpkGray300Color,
  R.color.bpkGray500 to R.attr.bpkGray500Color,
  R.color.bpkGray700 to R.attr.bpkGray700Color,
  R.color.bpkGray900 to R.attr.bpkGray900Color
)

class BpkTheme {
  companion object {

    /***
     * Utility function to fetch Backpack's primary color.
     *
     * The primary color can set via theming via the `bpkPrimaryColor` attribute.
     *
     * The attribute is expected to be either a color or a color reference.
     * In case the attribute is not found the default [R.color.bpkBlue500]
     * will be returned.
     *
     * @param context the current ui context.
     * @return a color integer
     */
    @JvmStatic
    @ColorInt
    fun getPrimaryColor(context: Context) =
      resolveThemeColorWithDefault(context, R.attr.bpkPrimaryColor)

    /***
     * Utility function to fetch a Backpack color.
     *
     * If the color is replaced via theming then that color will be returned.
     *
     * @param context the current ui context.
     * @param colorRes the color resource
     * @return a color integer
     *
     * @see [R.style.BpkDefaultThemeColors]
     */
    @JvmStatic
    @ColorInt
    fun getColor(context: Context, @ColorRes colorRes: Int): Int {
      return COLOR_MAPPINGS[colorRes]?.let {
        resolveThemeColorWithDefault(context, it)
      } ?: ContextCompat.getColor(context, colorRes)
    }

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
  context.theme?.applyStyle(R.style.BpkDefaultThemeColors, false)
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
