package net.skyscanner.backpack.demo.stories

import android.os.Bundle
import android.view.View
import android.widget.TextView
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.snackbar.BpkSnackbar
import net.skyscanner.backpack.snackbar.setAction

class SnackbarStory : Story() {

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    view.findViewById<TextView>(R.id.snackbar_short).setOnClickListener {
      it as TextView
      BpkSnackbar.builder(view, it.text, BpkSnackbar.LENGTH_SHORT)
        .setAction("Action!") {}
        .build()
        .show()
    }

    view.findViewById<TextView>(R.id.snackbar_long).setOnClickListener {
      it as TextView
      BpkSnackbar.builder(view, it.text, BpkSnackbar.LENGTH_LONG)
        .setAction("Action!") {}
        .build()
        .show()
    }

    view.findViewById<TextView>(R.id.snackbar_indefinite).setOnClickListener {
      it as TextView
      BpkSnackbar.builder(view, it.text, BpkSnackbar.LENGTH_INDEFINITE)
        .setAction("Action!") {}
        .build()
        .show()
    }
  }

  companion object {
    private const val LAYOUT_ID = "fragment_id"

    infix fun of(fragmentLayout: Int) = SnackbarStory().apply {
      arguments = Bundle()
      arguments?.putInt(LAYOUT_ID, fragmentLayout)
    }
  }
}
