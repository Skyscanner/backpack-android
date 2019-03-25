package net.skyscanner.backpack.util

import android.content.Context
import android.content.ContextWrapper
import android.util.AttributeSet
import android.util.TypedValue
import androidx.appcompat.view.ContextThemeWrapper
import net.skyscanner.backpack.R
import androidx.core.content.ContextCompat

class ThemesUtil {
  companion object {

    /***
     * Utility function to get bpkPrimaryColor attribute
     * A detailed reasoning of the logic can be found at
     * https://mbcdev.com/2017/01/16/resolving-android-theme-colours-programmatically/
     */
    fun getPrimaryColor(context: Context): Int {
      val typedValue = TypedValue()
      val wasResolved = context.theme.resolveAttribute(R.attr.bpkPrimaryColor, typedValue, true)

      return if (wasResolved && typedValue.resourceId == 0) {
        typedValue.data
      } else if (wasResolved) {
        ContextCompat.getColor(
          context, typedValue.resourceId)
      } else {
        ContextCompat.getColor(context, R.color.bpkBlue500)
      }
    }
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

/**
 * Return a [ContextThemeWrapper] wrapping the current context with [R.attr.bpkThemeOverlay] if `context`
 * has been wrapped with [ThemeOverlayEnforcement]
 *
 * @param context Context to be wrapped.
 * @param attributeSet List of attributes for the current component.
 */
internal fun createContextThemeOverlayWrapper(context: Context, attributeSet: AttributeSet?): Context {
  val a = context.obtainStyledAttributes(attributeSet, intArrayOf(R.attr.bpkThemeOverlay), 0, 0)
  val themeId = a.getResourceId(0, 0)
  a.recycle()

  if (shouldEnforceThemeOverlay(context)) {
    return ContextThemeWrapper(context, themeId)
  }

  return context
}

/**
 * Apply a list of wrappers to a context. The order of precedence is last to first, meaning
 * the last wrapper will be checked first and then the one before than, an so on.
 *
 * @param context The base context.
 * @param wrappers A list of functions to wrap the context.
 */
internal fun withContextWrappers(context: Context, vararg wrappers: (Context) -> Context): Context {
  return wrappers.fold(context) { acc, wrapper -> wrapper(acc) }
}

/**
 * Check if the context, or any of its base contexts, are wrapped with ThemeOverlayEnforcement.
 *
 * @see [ThemeOverlayEnforcement]
 */
private fun shouldEnforceThemeOverlay(context: Context?): Boolean {
  if (context == null) {
    return false
  }

  return context is ThemeOverlayEnforcement ||
    context is ContextWrapper && shouldEnforceThemeOverlay(context.baseContext)
}

/**
 * ThemeOverlayEnforcement is a dummy [ContextWrapper] used to indicate to Backpack components
 * that the theme defined inside the `bpkThemeOverlay` should be used to style components.
 *
 * Consider this theme:
 *
 * ```xml
 *  <style name="AppTheme" parent="Theme.AppCompat.Light.NoActionBar">
 *    <item name="colorPrimary">@color/bpkBlue500</item>
 *    <item name="colorPrimaryDark">@color/bpkBlue700</item>
 *    <item name="colorAccent">@color/bpkGreen500</item>
 *    <item name="android:windowBackground">@color/bpkWhite</item>
 *    <item name="bpkThemeOverlay">@style/DohaTheme</item>
 *  </style>
 *```
 *
 * Now you can wrap any context during runtime with [ThemeOverlayEnforcement] to apply "DohaTheme"
 * to all Backpack components.
 *
 */
class ThemeOverlayEnforcement(context: Context) : ContextWrapper(context)
