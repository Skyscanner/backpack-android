package net.skyscanner.backpack.demo.stories

import android.content.Context
import android.os.Bundle
import android.view.View
import net.skyscanner.backpack.calendar.BpkCalendar
import net.skyscanner.backpack.calendar.presenter.MonthFooterAdapter
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.demo.data.ExampleBpkCalendarController
import net.skyscanner.backpack.text.BpkText

private class FooterViewCalendarController(val context: Context) : ExampleBpkCalendarController(context) {
  override val monthFooterAdapter = object : MonthFooterAdapter {
    override fun hasFooterForMonth(month: Int, year: Int): Boolean {
      return month % 2 == 0
    }

    override fun onCreateView(month: Int, year: Int): BpkText {
      return BpkText(context)
    }

    override fun onBindView(view: View, month: Int, year: Int) {
      view as BpkText
      view.text = "Footer view for $month and $year"
    }
  }
}

class FooterViewCalendarStory : Story() {
  private lateinit var controller: FooterViewCalendarController

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    controller = FooterViewCalendarController(requireContext())
    val bpkCalendar = view.findViewById<BpkCalendar>(R.id.bpkCalendar)
    bpkCalendar.setController(controller)
  }

  companion object {
    private const val LAYOUT_ID = "fragment_id"

    infix fun of(fragmentLayout: Int) = FooterViewCalendarStory().apply {
      arguments = Bundle()
      arguments?.putInt(LAYOUT_ID, fragmentLayout)
    }
  }
}
