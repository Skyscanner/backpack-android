package net.skyscanner.backpack.demo.stories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import net.skyscanner.backpack.demo.ComponentDetailFragment

open class Story : ComponentDetailFragment() {

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    val layoutId = arguments?.getInt(LAYOUT_ID) ?: savedInstanceState?.getInt(LAYOUT_ID)
    if (layoutId != null) {
      return inflater.inflate(layoutId, container, false)
    } else {
      throw IllegalStateException("Story has not been property initialized")
    }
  }

  companion object {
    const val LAYOUT_ID = "fragment_id"

    infix fun of(fragmentLayout: Int) = Story().apply {
      arguments = Bundle()
      arguments?.putInt(LAYOUT_ID, fragmentLayout)
    }
  }
}
