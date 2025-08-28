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

package net.skyscanner.backpack.overlay

import android.graphics.Color
import android.view.Gravity
import android.widget.FrameLayout.LayoutParams
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import net.skyscanner.backpack.BpkSnapshotTest
import net.skyscanner.backpack.BpkTestVariant
import net.skyscanner.backpack.Variants
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.text.BpkText
import org.junit.Before
import org.junit.Ignore
import org.junit.Test

class BpkOverlayTest : BpkSnapshotTest() {

    private val overlay: BpkOverlay = BpkOverlay(testContext).apply {
        addView(
            AppCompatImageView(context).apply {
                setImageResource(R.drawable.canadian_rockies_canada)
                adjustViewBounds = true
                scaleType = ImageView.ScaleType.CENTER_CROP
            },
            LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT),
        )
        addView(
            BpkText(context).apply {
                textStyle = BpkText.TextStyle.Caption
                setTextColor(Color.WHITE)
                text = "Text"
            },
            LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.CENTER),
        )
    }

    @Before
    fun setup() {
        overlay.cornerType = BpkOverlay.CornerType.None
        overlay.overlayType = BpkOverlay.OverlayType.None
    }

    @Test
    fun default() {
        snap(overlay, width = 100)
    }

    @Test
    @Variants(BpkTestVariant.Default)
    @Ignore("Screenshot testing library does not capture rounded corners")
    fun roundedCorners() {
        overlay.cornerType = BpkOverlay.CornerType.Rounded
        snap(overlay, width = 100)
    }

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    fun tint() {
        overlay.overlayType = BpkOverlay.OverlayType.Tint
        snap(overlay, width = 100)
    }

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    @Ignore("Screenshot testing library does not capture rounded corners")
    fun tintAndCorners() {
        overlay.cornerType = BpkOverlay.CornerType.Rounded
        overlay.overlayType = BpkOverlay.OverlayType.Tint
        snap(overlay, width = 100)
    }
}
