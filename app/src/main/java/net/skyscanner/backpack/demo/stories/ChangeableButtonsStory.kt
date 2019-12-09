package net.skyscanner.backpack.demo.stories

import android.os.Bundle
import android.view.View
import android.widget.TextView
import net.skyscanner.backpack.demo.R
import kotlin.math.max

class ChangeableButtonsStory : Story() {

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    view.findViewById<TextView>(R.id.button_increase).setOnClickListener {
      it as TextView
      it.text = it.text.toString() + " increased"
    }

    view.findViewById<TextView>(R.id.button_decrease).setOnClickListener {
      it as TextView
      it.text = it.text.substring(0, max(0, it.length() - 1))
    }
  }

  companion object {
    private const val LAYOUT_ID = "fragment_id"

    infix fun of(fragmentLayout: Int) = ChangeableButtonsStory().apply {
      arguments = Bundle()
      arguments?.putInt(LAYOUT_ID, fragmentLayout)
    }
  }
}
