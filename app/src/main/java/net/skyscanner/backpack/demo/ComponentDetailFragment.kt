package net.skyscanner.backpack.demo

import android.os.Bundle
import android.support.design.widget.CollapsingToolbarLayout
import android.support.v4.app.Fragment
import net.skyscanner.backpack.demo.data.ComponentRegistry

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
        super.onCreate(savedInstanceState)

        if (arguments!!.containsKey(ARG_ITEM_ID)) {

            val activity = this.activity
            val appBarLayout = activity!!.findViewById<CollapsingToolbarLayout>(R.id.toolbar_layout)
            if (appBarLayout != null) {
                appBarLayout.title = arguments!!.getString(ARG_ITEM_ID)
            }
        }
    }

    companion object {
        /**
         * The fragment argument representing the item ID that this fragment
         * represents.
         */
        val ARG_ITEM_ID = "item_id"
    }

}
