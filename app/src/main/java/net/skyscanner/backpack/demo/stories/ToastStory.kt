package net.skyscanner.backpack.demo.stories

import android.os.Bundle
import android.view.View
import android.widget.TextView
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.toast.BpkToast

class ToastStory : Story() {

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    view.findViewById<TextView>(R.id.widget_low).setOnClickListener {
      it as TextView
      view.findViewById<BpkToast>(R.id.toast_low).show(it.text)
    }

    view.findViewById<TextView>(R.id.widget_high).setOnClickListener {
      it as TextView
      view.findViewById<BpkToast>(R.id.toast_high).show(it.text)
    }

    view.findViewById<TextView>(R.id.static_low).setOnClickListener {
      it as TextView
      BpkToast.makeText(activity!!, it.text).show()
    }

    view.findViewById<TextView>(R.id.static_high).setOnClickListener {
      it as TextView
      BpkToast.makeText(activity!!, it.text).show()
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
