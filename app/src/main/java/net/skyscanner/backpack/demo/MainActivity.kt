package net.skyscanner.backpack.demo

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView
import net.skyscanner.backpack.demo.StoriesRecyclerViewAdapter.HeaderItem
import net.skyscanner.backpack.demo.StoriesRecyclerViewAdapter.StoryItem
import net.skyscanner.backpack.demo.data.ComponentRegistry

/**
 * An activity representing a list of Components. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a [ComponentDetailActivity] representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
class MainActivity : BpkBaseActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_component_list)

    val toolbar = findViewById<Toolbar>(R.id.toolbar)
    setSupportActionBar(toolbar)
    toolbar.title = title

    val componentsList = findViewById<View>(R.id.componentsList) as RecyclerView
    val allItems = mutableListOf<StoriesRecyclerViewAdapter.ListItem>()
    allItems.add(HeaderItem("Tokens"))
    allItems.addAll(ComponentRegistry.TOKENS.map { StoryItem(it) })
    allItems.add(HeaderItem("Components"))
    allItems.addAll(ComponentRegistry.COMPONENTS.map { StoryItem(it) })

    componentsList.adapter = StoriesRecyclerViewAdapter(allItems)
  }
}
