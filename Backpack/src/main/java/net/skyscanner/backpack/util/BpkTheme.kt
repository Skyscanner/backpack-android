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

package net.skyscanner.backpack.util

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.TypedValue
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.annotation.DimenRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.view.ContextThemeWrapper
import net.skyscanner.backpack.R

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
      resolveThemeColor(context, R.attr.bpkPrimaryColor)
        // This should only ever happen if the value defined for the color is wrong as the property
        // is guaranteed to be there because of the ContextThemeWrapper
        ?: throw IllegalStateException("Could not resolve themed color!")
  }
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
    context.getColor(typedValue.resourceId)
  } else {
    null
  }
}

internal fun resolveThemeDrawable(context: Context, @AttrRes resId: Int): Drawable? {
  val typedValue = TypedValue()
  val wasResolved = context.theme.resolveAttribute(resId, typedValue, true)

  return if (wasResolved && typedValue.resourceId == 0) {
    AppCompatResources.getDrawable(context, typedValue.data)
  } else if (wasResolved) {
    AppCompatResources.getDrawable(context, typedValue.resourceId)
  } else {
    null
  }
}

internal fun resolveThemeId(context: Context, @AttrRes id: Int, fallback: Int = 0): Int {
  val tv = TypedValue()
  if (context.theme.resolveAttribute(id, tv, true)) {
    return tv.resourceId
  }
  return fallback
}

internal fun resolveThemeDimen(context: Context, @AttrRes id: Int, @DimenRes fallback: Int = 0): Int {
  val tv = TypedValue()
  if (context.theme.resolveAttribute(id, tv, true)) {
    return TypedValue.complexToDimensionPixelSize(tv.data, context.resources.displayMetrics)
  }
  return context.resources.getDimensionPixelSize(fallback)
}

/**
 * Return a [ContextThemeWrapper] wrapping the current context with the properties defined in `styleAttr`.
 *
 * @param context Context to be wrapped.
 * @param attributeSet List of attributes for the current component.
 * @param styleAttr Style attribute to provide default values for the current context.
 */
internal fun createContextThemeWrapper(
  context: Context,
  attributeSet: AttributeSet?,
  styleAttr: Int,
): Context {
  val a = context.obtainStyledAttributes(attributeSet, intArrayOf(styleAttr), 0, 0)
  val themeId = a.getResourceId(0, 0)
  a.recycle()
  return ContextThemeWrapper(context, themeId)
}
