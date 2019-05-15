package net.skyscanner.backpack.util

import android.content.Context
import android.content.ContextWrapper
import android.util.AttributeSet
import android.util.TypedValue
import androidx.appcompat.view.ContextThemeWrapper
import net.skyscanner.backpack.R
import androidx.core.content.ContextCompat
import java.lang.IllegalStateException

class ThemesUtil {
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
    fun getPrimaryColor(context: Context) =
      resolveThemeColorWithDefault(context, R.attr.bpkPrimaryColor)

    @JvmStatic
    fun getGrey50Color(context: Context) =
      resolveThemeColorWithDefault(context, R.attr.bpkGrey50Color)

    @JvmStatic
    fun getGrey100Color(context: Context) =
      resolveThemeColorWithDefault(context, R.attr.bpkGrey100Color)

    @JvmStatic
    fun getGrey300Color(context: Context) =
      resolveThemeColorWithDefault(context, R.attr.bpkGrey300Color)

    @JvmStatic
    fun getGrey500Color(context: Context) =
      resolveThemeColorWithDefault(context, R.attr.bpkGrey500Color)

    @JvmStatic
    fun getGrey700Color(context: Context) =
      resolveThemeColorWithDefault(context, R.attr.bpkGrey700Color)

    @JvmStatic
    fun getGrey900Color(context: Context) =
      resolveThemeColorWithDefault(context, R.attr.bpkGrey900Color)

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
    fun wrapContextWithBackpackDefaults(context: Context) =
      wrapContextWithDefaults(context)
  }
}

internal fun wrapContextWithDefaults(context: Context): Context {
  val copy = ContextWrapper(context)
  copy.theme?.applyStyle(R.style.BpkDefaultThemeColors, false)
  return copy
}

internal fun resolveThemeColorWithDefault(context: Context, resId: Int): Int {
  return resolveThemeColor(ThemesUtil.wrapContextWithBackpackDefaults(context), resId)
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
