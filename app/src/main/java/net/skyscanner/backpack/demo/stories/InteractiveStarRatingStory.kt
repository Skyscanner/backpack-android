package net.skyscanner.backpack.demo.stories

import android.os.Bundle
import android.util.Log
import android.view.View
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.starrating.BpkInteractiveStarRating

class InteractiveStarRatingStory : Story() {

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    view.findViewById<BpkInteractiveStarRating>(R.id.star_rating_empty).onRatingChangedListener = { current, max ->
      Log.d("InteractiveStarRating", "$current/$max")
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
