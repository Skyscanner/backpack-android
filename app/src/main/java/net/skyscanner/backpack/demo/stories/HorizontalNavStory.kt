package net.skyscanner.backpack.demo.stories

import android.os.Bundle
import android.view.View
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.horisontalnav.BpkHorizontalNav
class HorizontalNavStory : Story() {

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    init(view.findViewById(R.id.horizontal_nav))
    init(view.findViewById(R.id.horizontal_nav_scrollable))
    init(view.findViewById(R.id.horizontal_nav_small))
    init(view.findViewById(R.id.horizontal_nav_alternate))
    init(view.findViewById(R.id.horizontal_nav_rtl))
    init(view.findViewById(R.id.horizontal_nav_badge))
  }

  private fun init(horizontalNav: BpkHorizontalNav) {
    horizontalNav.addTab(horizontalNav.newTab().setText("Flights"))
    horizontalNav.addTab(horizontalNav.newTab().setText("Hotels"))
    horizontalNav.addTab(horizontalNav.newTab().setText("Car Hire"))
    horizontalNav.setNotificationDot(0, true)
    if (horizontalNav.id == R.id.horizontal_nav_badge) {
      horizontalNav.setBadge(0, "BETA")
    }
  }

  companion object {
    private const val LAYOUT_ID = "fragment_id"

    infix fun of(fragmentLayout: Int) = HorizontalNavStory().apply {
      arguments = Bundle()
      arguments?.putInt(LAYOUT_ID, fragmentLayout)
    }
  }
}
