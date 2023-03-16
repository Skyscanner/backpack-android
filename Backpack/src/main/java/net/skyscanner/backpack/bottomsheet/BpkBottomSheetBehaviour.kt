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

package net.skyscanner.backpack.bottomsheet

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.res.ResourcesCompat
import com.google.android.material.bottomsheet.BottomSheetBehavior
import net.skyscanner.backpack.R

class BpkBottomSheetBehaviour<V : View>(context: Context, attrs: AttributeSet? = null) :
    BottomSheetBehavior<V>(context, attrs) {

    private val maxRadius: Float
    private val background: LayerDrawable

    init {
        maxRadius = context.resources.getDimension(R.dimen.bpkBorderRadiusLg)
        background =
            ResourcesCompat.getDrawable(context.resources, R.drawable.bpk_bottom_sheet_background, null) as LayerDrawable

        addBottomSheetCallback(
            object : BottomSheetCallback() {
                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    (bottomSheet as? ViewGroup)?.apply {
                        clipToPadding = false
                        clipChildren = false
                    }
                    when (newState) {
                        STATE_COLLAPSED -> updateBackground(0f)
                        STATE_EXPANDED -> updateBackground(1f)
                        else -> {}
                    }
                }

                override fun onSlide(bottomSheet: View, slideOffset: Float) {
                    updateBackground(slideOffset)
                }
            },
        )
    }

    override fun onLayoutChild(parent: CoordinatorLayout, child: V, layoutDirection: Int): Boolean {
        return super.onLayoutChild(parent, child, layoutDirection).apply { child.background = background }
    }

    override fun setState(state: Int) {
        super.setState(state)
        if (this.state == state) {
            // this means that the state was updated immediately, rather than transitioning.
            // this is the case when the state is set when the bottom sheet it being set up, rather than later on
            when (this.state) {
                STATE_COLLAPSED -> updateBackground(0f)
                STATE_EXPANDED -> updateBackground(1f)
                else -> {}
            }
        }
    }

    private fun updateBackground(slideOffset: Float) {
        val radius = maxRadius * (1 - slideOffset)
        val radii = floatArrayOf(radius, radius, radius, radius, 0f, 0f, 0f, 0f)
        (background.findDrawableByLayerId(R.id.bottomSheetSurface) as GradientDrawable).cornerRadii = radii
        background.findDrawableByLayerId(R.id.bottomSheetHandle).alpha = ((1 - slideOffset) * 255).toInt()
    }
}
