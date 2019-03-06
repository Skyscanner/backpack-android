package net.skyscanner.backpack.demo.stories

import android.os.Bundle
import android.view.View
import net.skyscanner.backpack.calendar.BpkCalendar
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.demo.data.ExampleBpkCalendarController
import net.skyscanner.backpack.toggle.BpkSwitch

class CalendarStory : Story() {

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    val controller = ExampleBpkCalendarController(requireContext())
    val bpkCalendar = view.findViewById<BpkCalendar>(R.id.bpkCalendar)
    bpkCalendar.setController(controller)

    val coloredCalendarSwitch = view.findViewById<BpkSwitch>(R.id.coloredCalendarSwitch)
    controller.isColoredCalendar = coloredCalendarSwitch.isChecked
    coloredCalendarSwitch.setOnCheckedChangeListener { _, isChecked ->
      controller.isColoredCalendar = isChecked
      bpkCalendar.setController(controller)
    }
  }

  companion object {
    private const val LAYOUT_ID = "fragment_id"

    infix fun of(fragmentLayout: Int) = CalendarStory().apply {
      arguments = Bundle()
      arguments?.putInt(LAYOUT_ID, fragmentLayout)
    }
  }
}
