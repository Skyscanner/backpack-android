/**
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2025 Skyscanner Ltd
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

package net.skyscanner.backpack.navbar.internal

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.util.AttributeSet
import com.google.android.material.appbar.CollapsingToolbarLayout
import net.skyscanner.backpack.R
import net.skyscanner.backpack.util.createContextThemeWrapper
import net.skyscanner.backpack.util.resolveThemeId

internal class BpkCollapsingToolbarLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : CollapsingToolbarLayout(
    createContextThemeWrapper(
        androidx.appcompat.view.ContextThemeWrapper(context, R.style.Widget_Material3_CollapsingToolbar_Medium),
        attrs,
        0,
    ),
    attrs,
    defStyleAttr,
) {

    init {
        setExpandedTitleTextAppearance(resolveThemeId(context, R.attr.bpkTextHeading2Appearance))
        setCollapsedTitleTextAppearance(resolveThemeId(context, R.attr.bpkTextHeading4Appearance))

        expandedTitleMarginStart = resources.getDimensionPixelSize(R.dimen.bpk_nav_bar_expanded_spacing_horizontal)
        expandedTitleMarginEnd = resources.getDimensionPixelSize(R.dimen.bpk_nav_bar_expanded_spacing_horizontal)
        expandedTitleMarginTop = resources.getDimensionPixelSize(R.dimen.bpk_nav_bar_expanded_spacing_top)
        expandedTitleMarginBottom = resources.getDimensionPixelSize(R.dimen.bpk_nav_bar_expanded_spacing_bottom)

        setScrimsShown(false)
        contentScrim = ColorDrawable(context.getColor(R.color.bpkSurfaceDefault))
    }
}
