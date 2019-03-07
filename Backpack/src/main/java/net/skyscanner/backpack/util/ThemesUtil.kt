package net.skyscanner.backpack.util

import android.content.Context
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
internal fun createContextThemeWrapper(context: Context, attributeSet: AttributeSet?, styleAttr: Int): ContextThemeWrapper {
  val a = context.obtainStyledAttributes(attributeSet, intArrayOf(styleAttr), 0, 0)
  val themeId = a.getResourceId(0, 0)
  a.recycle()
  return ContextThemeWrapper(context, themeId)
}
