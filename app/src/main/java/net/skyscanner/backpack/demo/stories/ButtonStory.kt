package net.skyscanner.backpack.demo.stories

import android.os.Bundle
import android.view.View
import net.skyscanner.backpack.button.BpkButton
import net.skyscanner.backpack.demo.R

class ButtonStory : Story() {

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    val type = arguments?.getString(ButtonStory.TYPE) ?: savedInstanceState?.getString(ButtonStory.TYPE)

    val buttonType = when (type) {
      "primary" -> BpkButton.Type.Primary
      "secondary" -> BpkButton.Type.Secondary
      "destructive" -> BpkButton.Type.Destructive
      "featured" -> BpkButton.Type.Featured
      else -> {
        BpkButton.Type.Primary
      }
    }

    view.findViewById<BpkButton>(R.id.btn_default).type = buttonType
    view.findViewById<BpkButton>(R.id.btn_disabled).type = buttonType
    view.findViewById<BpkButton>(R.id.btn_end_icon).type = buttonType
    view.findViewById<BpkButton>(R.id.btn_start_icon).type = buttonType
    view.findViewById<BpkButton>(R.id.btn_icon).type = buttonType
    super.onViewCreated(view, savedInstanceState)
  }

  companion object {
    const val LAYOUT_ID = "fragment_id"
    const val TYPE = "type"

    fun of(fragmentLayout: Int, type: String) = ButtonStory().apply {
      arguments = Bundle()
      arguments?.putInt(LAYOUT_ID, fragmentLayout)
      arguments?.putString(TYPE, type)
    }
  }
}
