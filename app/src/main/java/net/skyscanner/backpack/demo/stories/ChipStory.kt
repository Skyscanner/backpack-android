package net.skyscanner.backpack.demo.stories

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import net.skyscanner.backpack.chip.BpkChip

class ChipStory : Story() {
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    forEachChip(view as ViewGroup) { chip ->
      chip.setOnClickListener {
        chip.toggle()
      }
    }
  }

  private fun forEachChip(view: ViewGroup, cb: (chip: BpkChip) -> Unit) {
    (0..(view.childCount - 1)).forEach {
      val child = view.getChildAt(it)
      if (child is ViewGroup) {
        forEachChip(child, cb)
      } else if (child is BpkChip) {
        cb(child)
      }
    }
  }

  companion object {
    private const val LAYOUT_ID = "fragment_id"

    infix fun of(fragmentLayout: Int) = ChipStory().apply {
      arguments = Bundle()
      arguments?.putInt(LAYOUT_ID, fragmentLayout)
    }
  }
}
