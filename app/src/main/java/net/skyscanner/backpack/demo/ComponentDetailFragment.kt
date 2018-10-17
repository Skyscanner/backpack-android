package net.skyscanner.backpack.demo

import android.os.Bundle
import android.support.v4.app.Fragment

/**
 * A fragment representing a single Component detail screen.
 * This fragment is either contained in a [MainActivity]
 * in two-pane mode (on tablets) or a [ComponentDetailActivity]
 * on handsets.
 */
/**
 * Mandatory empty constructor for the fragment manager to instantiate the
 * fragment (e.g. upon screen orientation changes).
 */
open class ComponentDetailFragment : Fragment() {

  override fun onCreate(savedInstanceState: Bundle?) {
    if (arguments?.containsKey(ARG_ITEM_ID) == true) {
      val activity = this.activity as ComponentDetailActivity
      activity.title = arguments!!.getString(ARG_ITEM_ID)
    }

    super.onCreate(savedInstanceState)
  }

  companion object {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    val ARG_ITEM_ID = "item_id"
  }
}
