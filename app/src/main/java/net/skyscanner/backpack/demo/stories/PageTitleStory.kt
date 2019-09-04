package net.skyscanner.backpack.demo.stories

import android.os.Bundle
import android.view.View
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.demo.components.LockableNestedScrollView
import net.skyscanner.backpack.pagetitle.BpkPageTitle

class PageTitleStory : Story() {

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    activity!!.findViewById<LockableNestedScrollView>(R.id.component_detail_container).apply {
      scrollingEnabled = false
    }
    view.findViewById<BpkPageTitle>(R.id.appBar).title = if (view.layoutDirection == View.LAYOUT_DIRECTION_RTL) {
      "عنوان الصفحة"
    } else {
      "Page Title"
    }
  }

  companion object {
    private const val LAYOUT_ID = "fragment_id"

    infix fun of(fragmentLayout: Int) = PageTitleStory().apply {
      arguments = Bundle()
      arguments?.putInt(LAYOUT_ID, fragmentLayout)
    }
  }
}
