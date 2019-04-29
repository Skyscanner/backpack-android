package net.skyscanner.backpack.demo.stories

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.LinearLayoutCompat
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.text.BpkText
import android.view.Gravity
import java.lang.reflect.Field

class SpacingStory : Story() {

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    return inflater.inflate(R.layout.fragment_spacing, container, false)
  }

  @SuppressLint("SetTextI18n")
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    val dimenResources = arrayListOf<Field>()

    R.dimen::class.java.fields.forEach {
      if (it.name.startsWith("bpkSpacing")) {
        dimenResources.add(it)
      }
    }

    dimenResources.sortBy { resources.getDimension(it.getInt(null)) }

    dimenResources.forEach {
      val text: BpkText = BpkText(requireContext()).apply {
        textStyle = BpkText.LG
        text = it.name + " = " + resources.getString(it.getInt(null))
      }
      text.gravity = Gravity.CENTER_VERTICAL or Gravity.CENTER_HORIZONTAL
      view.findViewById<LinearLayoutCompat>(R.id.layout_spacing_container).addView(text)
    }
  }
}
