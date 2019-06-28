package net.skyscanner.backpack.demo.stories

import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.view.View
import android.widget.TextView
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.text.BpkFontSpan
import net.skyscanner.backpack.text.BpkPrimaryColorSpan
import net.skyscanner.backpack.text.BpkText

class TextSpansStory : Story() {

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    view.findViewById<TextView>(R.id.text).text = SpannableStringBuilder().apply {
      append("This is an example of \n")
      append("primary color span \n", BpkPrimaryColorSpan(activity!!), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

      append("This is an example of \n")
      append("Backpack font span \n", BpkFontSpan(activity!!, BpkText.LG, BpkText.Weight.EMPHASIZED), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    }
  }

  companion object {
    private const val LAYOUT_ID = "fragment_id"

    infix fun of(fragmentLayout: Int) = TextSpansStory().apply {
      arguments = Bundle()
      arguments?.putInt(LAYOUT_ID, fragmentLayout)
    }
  }
}
