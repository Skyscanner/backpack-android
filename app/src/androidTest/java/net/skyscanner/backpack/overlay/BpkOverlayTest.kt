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

package net.skyscanner.backpack.overlay

import android.graphics.Color
import android.view.Gravity
import android.widget.FrameLayout.LayoutParams
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import androidx.test.ext.junit.runners.AndroidJUnit4
import net.skyscanner.backpack.BpkSnapshotTest
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.text.BpkText
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)

class BpkOverlayTest : BpkSnapshotTest() {

  private val overlay: BpkOverlay = BpkOverlay(testContext).apply {
    addView(
      AppCompatImageView(context).apply {
        setImageResource(R.drawable.canadian_rockies_canada)
        adjustViewBounds = true
        scaleType = ImageView.ScaleType.CENTER_CROP
      },
      LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
    )
    addView(
      BpkText(context).apply {
        textStyle = BpkText.TextStyle.Caption
        setTextColor(Color.WHITE)
        text = "Text"
      },
      LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.CENTER)
    )
  }

  @Before
  fun setup() {
    overlay.cornerType = BpkOverlay.CornerType.None
    overlay.overlayType = BpkOverlay.OverlayType.None
  }

  @Test
  fun screenshotTestOverlay_Default() {
    snap(overlay)
  }

  @Test
  @Ignore("Screenshot testing library does not capture rounded corners")
  fun screenshotTestOverlay_RoundedCorners() {
    overlay.cornerType = BpkOverlay.CornerType.Rounded
    snap(overlay)
  }

  @Test
  fun screenshotTestOverlay_Tint() {
    overlay.overlayType = BpkOverlay.OverlayType.Tint
    snap(overlay)
  }

  @Test
  @Ignore("Screenshot testing library does not capture rounded corners")
  fun screenshotTestOverlay_TintAndCorners() {
    overlay.cornerType = BpkOverlay.CornerType.Rounded
    overlay.overlayType = BpkOverlay.OverlayType.Tint
    snap(overlay)
  }
}
