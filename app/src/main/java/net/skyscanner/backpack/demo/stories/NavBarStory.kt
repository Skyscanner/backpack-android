package net.skyscanner.backpack.demo.stories

import android.os.Bundle
import android.view.View
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.demo.components.LockableNestedScrollView
import net.skyscanner.backpack.navbar.BpkNavBar
import net.skyscanner.backpack.toast.BpkToast

class NavBarStory : Story() {

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    activity!!.findViewById<LockableNestedScrollView>(R.id.component_detail_container).apply {
      scrollingEnabled = false
    }
    val navBar = view.findViewById<BpkNavBar>(R.id.appBar)
    navBar.title = if (view.layoutDirection == View.LAYOUT_DIRECTION_RTL) {
      "عنوان الصفحة"
    } else {
      "Nav Bar"
    }
    navBar.navAction = {
      BpkToast.makeText(requireContext(), "Nav is clicked!", BpkToast.LENGTH_SHORT).show()
    }
    navBar.menuAction = {
      BpkToast.makeText(requireContext(), "${it.itemId.let(resources::getResourceEntryName)} is clicked!", BpkToast.LENGTH_SHORT).show()
    }
  }

  companion object {
    private const val LAYOUT_ID = "fragment_id"

    infix fun of(fragmentLayout: Int) = NavBarStory().apply {
      arguments = Bundle()
      arguments?.putInt(LAYOUT_ID, fragmentLayout)
    }
  }
}
