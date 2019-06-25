package net.skyscanner.backpack.demo.stories

import android.os.Bundle
import android.view.View
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.toast.BpkToast

class ToastStory : Story() {

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    view.findViewById<View>(R.id.base).setOnClickListener {
      view.findViewById<BpkToast>(R.id.toast_base).show("Base")
    }
    view.findViewById<View>(R.id.large).setOnClickListener {
      view.findViewById<BpkToast>(R.id.toast_large).show("Large")
    }
    view.findViewById<View>(R.id.prolonged).setOnClickListener {
      view.findViewById<BpkToast>(R.id.toast_prolonged).showLong("Prolonged")
    }

    view.findViewById<View>(R.id.base_static).setOnClickListener {
      BpkToast.show(activity!!, "Base Static")
    }
    view.findViewById<View>(R.id.prolonged_static).setOnClickListener {
      BpkToast.showLong(activity!!, "Prolonged Static")
    }
  }

  companion object {
    private const val LAYOUT_ID = "fragment_id"

    infix fun of(fragmentLayout: Int) = ToastStory().apply {
      arguments = Bundle()
      arguments?.putInt(LAYOUT_ID, fragmentLayout)
    }
  }
}
