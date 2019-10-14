package net.skyscanner.backpack.demo.stories

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import net.skyscanner.backpack.button.BpkButton

class LoadingButtonStory : Story() {

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    view as ViewGroup
    for (i in 0 until view.childCount) {
      val child = view.getChildAt(i)
      if (child is BpkButton) {
        child.setOnClickListener {
          child.loading = true
          child.postDelayed({
            child.loading = false
          }, 2500)
        }
      }
    }
  }

  companion object {
    private const val LAYOUT_ID = "fragment_id"

    infix fun of(fragmentLayout: Int) = LoadingButtonStory().apply {
      arguments = Bundle()
      arguments?.putInt(LAYOUT_ID, fragmentLayout)
    }
  }
}
