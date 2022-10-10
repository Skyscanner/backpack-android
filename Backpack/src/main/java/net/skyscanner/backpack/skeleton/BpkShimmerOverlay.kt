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

import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.AttrRes
import net.skyscanner.backpack.R

class BpkShimmerOverlay @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  @AttrRes defStyleAttr: Int = 0,
) : FrameLayout(context, attrs, defStyleAttr) {

  init {
    inflate(this.context, R.layout.bpk_shimmer_skeleton, this)
    startShimmer()
  }

  override fun addView(child: View?) {
    if (child?.id != R.id.bpk_skeleton_container) {
      super.addView(child)
    } else {
      val container = findViewById<FrameLayout>(R.id.bpk_skeleton_container)
      container.addView(child)
      container.bringChildToFront(findViewById(R.id.bpk_skeleton_shimmer))
    }
  }

  override fun onViewAdded(child: View?) {
    super.onViewAdded(child)
    if (child?.id != R.id.bpk_skeleton_container) {
      this.bringChildToFront(findViewById(R.id.bpk_skeleton_container))
    }
  }

  private fun startShimmer() {
    // Use ObjectAnimator to draw the animation for translationX, translate the position from left to right.
    ObjectAnimator.ofFloat(findViewById(R.id.bpk_skeleton_shimmer), "translationX", -500f, 500f).apply {
      duration = 1000 // Per specification.
      repeatCount = ObjectAnimator.INFINITE
      startDelay = 200 // Per specification.
      start()
    }
  }
}
