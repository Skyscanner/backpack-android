package net.skyscanner.backpack.demo.stories

import android.os.Bundle
import android.view.View
import net.skyscanner.backpack.bottomnav.BpkBottomNav
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.toast.BpkToast

class BottomNavStory : Story() {

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    view.findViewById<BpkBottomNav>(R.id.bottom_nav).apply {
      addItem(1, R.string.bottom_nav_home, R.drawable.bpk_hotels)
      addItem(2, R.string.bottom_nav_explore, R.drawable.bpk_navigation)
      addItem(3, R.string.bottom_nav_trips, R.drawable.bpk_trips)
      addItem(4, R.string.bottom_nav_profile, R.drawable.bpk_account_circle)
      addOnNavigationItemReselectedListener { item, index ->
        BpkToast.makeText(requireContext(), "${item.title} #$index is reselected!", BpkToast.LENGTH_SHORT).show()
      }
      addOnNavigationItemSelectedListener { item, index ->
        BpkToast.makeText(requireContext(), "${item.title} #$index is selected!", BpkToast.LENGTH_SHORT).show()
      }
    }
  }

  companion object {
    private const val LAYOUT_ID = "fragment_id"

    infix fun of(fragmentLayout: Int) = BottomNavStory().apply {
      arguments = Bundle()
      arguments?.putInt(LAYOUT_ID, fragmentLayout)
    }
  }
}
