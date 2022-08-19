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

package net.skyscanner.backpack.skeleton

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.AttrRes
import net.skyscanner.backpack.R
import net.skyscanner.backpack.overlay.internal.CornerRadiusViewOutlineProvider

class BpkBodyTextSkeleton @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  @AttrRes defStyleAttr: Int = 0,
) : FrameLayout(context, attrs, defStyleAttr) {

  init {
    inflate(this.context, R.layout.view_bpk_skeleton_body_text, this)

    initSubBlock(findViewById(R.id.firstLine))
    initSubBlock(findViewById(R.id.secondLine))
    initSubBlock(findViewById(R.id.thirdLine))
  }

  private fun initSubBlock(view: View) {
    view.apply {
      setBackgroundColor(context.getColor(R.color.__skeletonBackground))
      outlineProvider = CornerRadiusViewOutlineProvider(R.dimen.bpk_skeleton_xxs_border_radius)
      clipToOutline = true
    }
  }
}
