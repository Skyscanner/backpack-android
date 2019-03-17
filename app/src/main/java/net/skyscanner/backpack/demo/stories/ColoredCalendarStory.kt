package net.skyscanner.backpack.demo.stories

import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.fragment_calendar_colored.shiftColorsButton
import net.skyscanner.backpack.calendar.BpkCalendar
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.demo.data.ExampleBpkCalendarController

class ColoredCalendarStory : Story() {

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    val controller = ExampleBpkCalendarController(requireContext())
    val bpkCalendar = view.findViewById<BpkCalendar>(R.id.bpkCalendar)
    bpkCalendar.isSelectionTypeSwitcherVisible = true
    controller.isColoredCalendar = true
    shiftColorsButton.setOnClickListener {
      controller.newColors()
      controller.updateContent()
    }
    bpkCalendar.setController(controller)
  }

  companion object {
    private const val LAYOUT_ID = "fragment_id"

    infix fun of(fragmentLayout: Int) = ColoredCalendarStory().apply {
      arguments = Bundle()
      arguments?.putInt(LAYOUT_ID, fragmentLayout)
    }
  }
}
