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
      BpkSnackbar.make(view, it.text, BpkSnackbar.LENGTH_SHORT)
        .setAction("Action!") {}
        .show()
    }

    view.findViewById<TextView>(R.id.snackbar_long).setOnClickListener {
      it as TextView
      BpkSnackbar.make(view, it.text, BpkSnackbar.LENGTH_LONG)
        .setAction("Action!") {}
        .show()
    }

    view.findViewById<TextView>(R.id.snackbar_indefinite).setOnClickListener {
      it as TextView
      BpkSnackbar.make(view, it.text, BpkSnackbar.LENGTH_INDEFINITE)
        .setAction("Action!") {}
        .show()
    }

    view.findViewById<TextView>(R.id.snackbar_titled).setOnClickListener {
      it as TextView
      BpkSnackbar.make(view, "description here", BpkSnackbar.LENGTH_INDEFINITE)
        .setTitle("ABCDEF")
        .setAction("Action!") {}
        .show()
    }

    view.findViewById<TextView>(R.id.snackbar_iconed).setOnClickListener {
      it as TextView
      BpkSnackbar.make(view, "description here", BpkSnackbar.LENGTH_INDEFINITE)
        .setIcon(R.drawable.bpk_tick_circle)
        .setAction("Action!") {}
        .show()
    }

    view.findViewById<TextView>(R.id.snackbar_titled_iconed).setOnClickListener {
      it as TextView
      BpkSnackbar.make(view, "description here", BpkSnackbar.LENGTH_INDEFINITE)
        .setTitle("ABCDEF")
        .setIcon(R.drawable.bpk_tick_circle)
        .setAction("Action!") {}
        .show()
    }

    view.findViewById<TextView>(R.id.snackbar_iconized_action).setOnClickListener {
      it as TextView
      BpkSnackbar.make(view, "description here", BpkSnackbar.LENGTH_INDEFINITE)
        .setAction(R.drawable.bpk_close) { }
        .show()
    }

    view.findViewById<TextView>(R.id.snackbar_titled_iconized_action).setOnClickListener {
      it as TextView
      BpkSnackbar.make(view, "description here", BpkSnackbar.LENGTH_INDEFINITE)
        .setTitle("ABCDEF")
        .setAction(R.drawable.bpk_close) { }
        .show()
    }

    view.findViewById<TextView>(R.id.snackbar_iconed_iconized_action).setOnClickListener {
      it as TextView
      BpkSnackbar.make(view, "description here", BpkSnackbar.LENGTH_INDEFINITE)
        .setIcon(R.drawable.bpk_tick_circle)
        .setAction(R.drawable.bpk_close) { }
        .show()
    }

    view.findViewById<TextView>(R.id.snackbar_titled_iconed_iconized_action).setOnClickListener {
      it as TextView
      BpkSnackbar.make(view, "description here", BpkSnackbar.LENGTH_INDEFINITE)
        .setTitle("ABCDEF")
        .setAction(R.drawable.bpk_close) { }
        .setIcon(R.drawable.bpk_tick_circle)
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
