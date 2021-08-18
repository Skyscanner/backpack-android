package net.skyscanner.backpack.demo.stories

import android.os.Bundle
import android.view.View
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.slider.BpkSlider
import java.text.NumberFormat
import java.util.*

class SliderStory : Story() {
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    view.findViewById<BpkSlider>(R.id.formatted_slider).setLabelFormatter { value: Float ->
      val format = NumberFormat.getCurrencyInstance()
      format.maximumFractionDigits = 0
      format.currency = Currency.getInstance("GBP")
      format.format(value.toDouble())
    }
  }

  companion object {
    private const val LAYOUT_ID = "fragment_id"

    infix fun of(fragmentLayout: Int) = SliderStory().apply {
      arguments = Bundle()
      arguments?.putInt(LAYOUT_ID, fragmentLayout)
    }
  }
}
