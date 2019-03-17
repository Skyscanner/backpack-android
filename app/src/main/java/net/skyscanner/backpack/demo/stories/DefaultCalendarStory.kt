package net.skyscanner.backpack.demo.stories

import android.os.Bundle
import android.view.View
import net.skyscanner.backpack.calendar.BpkCalendar
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.demo.data.ExampleBpkCalendarController

class DefaultCalendarStory : Story() {

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    val calendar = view.findViewById<BpkCalendar>(R.id.bpkCalendar)
    calendar.isSelectionTypeSwitcherVisible = true
    calendar.setController(ExampleBpkCalendarController(requireContext()))
  }

  companion object {
    private const val LAYOUT_ID = "fragment_id"

    infix fun of(fragmentLayout: Int) = DefaultCalendarStory().apply {
      arguments = Bundle()
      arguments?.putInt(LAYOUT_ID, fragmentLayout)
    }
  }
}
