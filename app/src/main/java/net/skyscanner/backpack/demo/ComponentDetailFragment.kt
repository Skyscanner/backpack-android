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

    /**
     * The dummy content this fragment is presenting.
     */
    private var mItem: ComponentRegistry.Component? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (arguments!!.containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem = ComponentRegistry.ITEM_MAP[arguments!!.getString(ARG_ITEM_ID)]

            val activity = this.activity
            val appBarLayout = activity!!.findViewById<CollapsingToolbarLayout>(R.id.toolbar_layout)
            if (appBarLayout != null) {
                appBarLayout.title = mItem!!.id
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
