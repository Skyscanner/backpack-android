package net.skyscanner.backpack.demo

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import net.skyscanner.backpack.demo.data.ComponentRegistry

/**
 * An activity representing a single Component detail screen. This
 * activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a [MainActivity].
 */
class ComponentDetailActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_component_detail)
    val toolbar = findViewById<View>(R.id.detail_toolbar) as Toolbar
    setSupportActionBar(toolbar)

    // Show the Up button in the action bar.
    val actionBar = supportActionBar
    actionBar?.setDisplayHomeAsUpEnabled(true)

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
      toolbar.title = itemId

      val createFragment = ComponentRegistry.getStoryCreator(itemId)
      var fragment = createFragment?.createStory()

      val arguments = fragment?.arguments ?: Bundle()
      arguments.putString(ComponentDetailFragment.ARG_ITEM_ID, itemId)

      fragment?.arguments = arguments
      supportFragmentManager.beginTransaction()
        .add(R.id.component_detail_container, fragment!!)
        .commit()
    }
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when (item.itemId) {
      android.R.id.home -> this.onBackPressed()
    }
    return true
  }
}
