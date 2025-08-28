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

package net.skyscanner.backpack.bottomsheet

import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.content.res.AppCompatResources
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout.LayoutParams
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_COLLAPSED
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_EXPANDED
import net.skyscanner.backpack.BpkSnapshotTest
import net.skyscanner.backpack.BpkTestVariant
import net.skyscanner.backpack.Variants
import net.skyscanner.backpack.demo.R
import org.junit.Test

class BpkBottomSheetTest : BpkSnapshotTest() {

    private lateinit var bottomSheetBehaviour: BpkBottomSheetBehaviour<BpkBottomSheet>

    @Test
    fun default() {
        capture(STATE_COLLAPSED)
    }

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    fun expanded() {
        capture(STATE_EXPANDED)
    }

    private fun capture(state: Int) {
        val root = setupBottomSheet(state)
        snap(root, width = 200, height = 200, padding = 0)
    }

    private fun setupBottomSheet(state: Int): View {
        val root = CoordinatorLayout(testContext)
        val frameLayout = FrameLayout(root.context)
        frameLayout.background = AppCompatResources.getDrawable(frameLayout.context, R.color.bpkCanvasContrast)
        frameLayout.layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        root.addView(frameLayout)

        val bottomSheet = BpkBottomSheet(frameLayout.context)
        val bottomSheetParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        bottomSheetBehaviour = BpkBottomSheetBehaviour(root.context)
        bottomSheetBehaviour.peekHeight = bottomSheet.resources.getDimensionPixelSize(R.dimen.bpkSpacingXxl)
        bottomSheetBehaviour.state = state
        bottomSheetParams.behavior = bottomSheetBehaviour
        bottomSheet.layoutParams = bottomSheetParams
        root.addView(bottomSheet)

        return root
    }
}
