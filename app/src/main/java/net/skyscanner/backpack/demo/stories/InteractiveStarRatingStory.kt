package net.skyscanner.backpack.demo.stories

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import net.skyscanner.backpack.starrating.BpkInteractiveStarRating
import net.skyscanner.backpack.toast.BpkToast

class InteractiveStarRatingStory : Story() {

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    view as ViewGroup

    (0 until view.childCount).forEach { idx ->
      val child = view.getChildAt(idx)
      if (child is BpkInteractiveStarRating) {
        child.onRatingChangedListener = { current, max ->
          BpkToast.makeText(context, "$current/$max", BpkToast.LENGTH_SHORT).show()
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
