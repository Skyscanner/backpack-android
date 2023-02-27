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

package net.skyscanner.backpack.demo.stories

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.demo.components.StarRatingInteractiveComponent
import net.skyscanner.backpack.demo.meta.ViewStory
import net.skyscanner.backpack.demo.ui.AndroidLayout
import net.skyscanner.backpack.starrating.BpkInteractiveStarRating
import net.skyscanner.backpack.toast.BpkToast

@Composable
@StarRatingInteractiveComponent
@ViewStory
fun InteractiveStarRatingStory(modifier: Modifier = Modifier) =
  AndroidLayout(R.layout.fragment_star_rating_interactive, modifier) {
    this as ViewGroup
    (0 until childCount).forEach { idx ->
      val child = getChildAt(idx)
      if (child is BpkInteractiveStarRating) {
        child.onRatingChangedListener = { current, max ->
          BpkToast.makeText(context, "$current/$max", BpkToast.LENGTH_SHORT).show()
        }
      }
    }
  }

class InteractiveStarRatingStory : Story() {

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    view as ViewGroup

    (0 until view.childCount).forEach { idx ->
      val child = view.getChildAt(idx)
      if (child is BpkInteractiveStarRating) {
        child.onRatingChangedListener = { current, max ->
          BpkToast.makeText(requireContext(), "$current/$max", BpkToast.LENGTH_SHORT).show()
        }
      }
    }
  }

  companion object {
    private const val LAYOUT_ID = "fragment_id"

    infix fun of(fragmentLayout: Int) = InteractiveStarRatingStory().apply {
      arguments = Bundle()
      arguments?.putInt(LAYOUT_ID, fragmentLayout)
    }
  }
}
