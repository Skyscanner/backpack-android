package net.skyscanner.backpack.demo.stories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import net.skyscanner.backpack.chip.BpkChip
import net.skyscanner.backpack.demo.R

class ChipStory : Story() {
  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    return inflater.inflate(R.layout.fragment_chip, container, false)
  }

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
}
