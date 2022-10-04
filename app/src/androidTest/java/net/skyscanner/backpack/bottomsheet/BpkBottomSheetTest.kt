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

import android.widget.FrameLayout
import androidx.appcompat.content.res.AppCompatResources
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout.LayoutParams
import androidx.test.ext.junit.runners.AndroidJUnit4
import net.skyscanner.backpack.BpkSnapshotTest
import net.skyscanner.backpack.demo.R
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BpkBottomSheetTest : BpkSnapshotTest() {

  private val root = CoordinatorLayout(testContext)

  init {
    val frameLayout = FrameLayout(root.context)
    frameLayout.background = AppCompatResources.getDrawable(frameLayout.context, R.color.bpkSurfaceHighlight)
    frameLayout.layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
    root.addView(frameLayout)

    val bottomSheet = BpkBottomSheet(frameLayout.context)
    val bottomSheetParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
    val bottomSheetBehaviour = BpkBottomSheetBehaviour<BpkBottomSheet>()
    bottomSheetBehaviour.peekHeight = bottomSheet.resources.getDimensionPixelSize(R.dimen.bpkSpacingXxl)
    bottomSheetParams.behavior = bottomSheetBehaviour
    bottomSheet.layoutParams = bottomSheetParams
    root.addView(bottomSheet)
  }

  @Before
  fun setup() {
    setDimensions(200, 200)
  }

  @Test
  fun screenshotTestFab_Default() {
    snap(root)
  }
}
