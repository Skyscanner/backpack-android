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
import android.util.AttributeSet
import android.util.TypedValue
import androidx.annotation.AttrRes
import androidx.annotation.DimenRes
import androidx.appcompat.view.ContextThemeWrapper

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
