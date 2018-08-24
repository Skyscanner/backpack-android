package net.skyscanner.backpack.demo

import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.design.widget.CollapsingToolbarLayout
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutCompat
import android.support.v7.widget.SwitchCompat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
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
  var childView: LinearLayoutCompat? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    if (arguments!!.containsKey(ARG_ITEM_ID)) {
      // Load the dummy content specified by the fragment
      // arguments. In a real-world scenario, use a Loader
      // to load content from a content provider.
      mItem = ComponentRegistry.ITEM_MAP[arguments!!.getString(ARG_ITEM_ID)]
    }
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    val view: View = inflater.inflate(R.layout.fragment_detail, container, false)
    val switch = view.findViewById<SwitchCompat>(R.id.toggle_rtl)
    switch.setOnCheckedChangeListener({ _, isChecked ->
      Log.i("switch_compat", "checked state is $isChecked")
      childView?.layoutDirection = if (isChecked) View.LAYOUT_DIRECTION_RTL else View.LAYOUT_DIRECTION_LTR
    })
    return view
  }

  fun merge(@LayoutRes layout: Int) {

    childView = view?.findViewById(R.id.story_container)
    childView?.addView(layoutInflater.inflate(layout, childView, false))
  }

  companion object {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    val ARG_ITEM_ID = "item_id"
  }

}
