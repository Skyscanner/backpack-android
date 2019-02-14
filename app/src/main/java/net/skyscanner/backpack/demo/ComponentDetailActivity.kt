package net.skyscanner.backpack.demo

import android.os.Bundle
import android.view.KeyEvent
import android.view.MenuItem
import android.view.View
import kotlinx.android.synthetic.main.activity_component_detail.*
import net.skyscanner.backpack.demo.data.ComponentRegistry

/**
 * An activity representing a single Component detail screen. This
 * activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a [MainActivity].
 */
class ComponentDetailActivity : BpkBaseActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_component_detail)
    setSupportActionBar(detail_toolbar)
    supportActionBar?.setDisplayHomeAsUpEnabled(true)

    // savedInstanceState is non-null when there is fragment state
    // saved from previous configurations of this activity
    // (e.g. when rotating the screen from portrait to landscape).
    // In this case, the fragment will automatically be re-added
    // to its container so we don't need to manually add it.
    // For more information, see the Fragments API guide at:
    //
    // http://developer.android.com/guide/components/fragments.html
    //
    if (savedInstanceState == null) {
      // Create the detail fragment and add it to the activity
      // using a fragment transaction.
      val itemId = intent.getStringExtra(ComponentDetailFragment.ARG_ITEM_ID)
      detail_toolbar.title = itemId

      val createFragment = ComponentRegistry.getStoryCreator(itemId)
      val fragment = createFragment.createStory()

      val arguments = fragment.arguments ?: Bundle()
      arguments.putString(ComponentDetailFragment.ARG_ITEM_ID, itemId)

      fragment.arguments = arguments
      supportFragmentManager.beginTransaction()
        .add(R.id.component_detail_container, fragment)
        .commit()
    }
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when (item.itemId) {
      android.R.id.home -> this.onBackPressed()
    }
    return super.onOptionsItemSelected(item)
  }

  /*
   Hide/Un-hide toolbar: Shift + T
   toggle layout direction: Shift + D
   toggle markers: Shift + H
  */
  override fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {
    if (keyCode == KeyEvent.KEYCODE_T && event?.isShiftPressed == true) {
      detail_toolbar.visibility = if (detail_toolbar.visibility == View.VISIBLE) {
        View.GONE
      } else {
        View.VISIBLE
      }
    }
    if (keyCode == KeyEvent.KEYCODE_D && event?.isShiftPressed == true) {
      if (component_detail_container.layoutDirection == View.LAYOUT_DIRECTION_LTR) {
        component_detail_container.layoutDirection = View.LAYOUT_DIRECTION_RTL
      } else {
        component_detail_container.layoutDirection = View.LAYOUT_DIRECTION_LTR
      }
    }
    if (keyCode == KeyEvent.KEYCODE_H && event?.isShiftPressed == true) {
      BackpackDemoApplication.highlight = !BackpackDemoApplication.highlight
      BackpackDemoApplication.triggerRebirth(this)
    }
    return super.onKeyUp(keyCode, event)
  }
}
