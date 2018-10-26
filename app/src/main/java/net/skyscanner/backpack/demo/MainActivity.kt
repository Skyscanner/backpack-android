package net.skyscanner.backpack.demo

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView

import net.skyscanner.backpack.demo.data.ComponentRegistry

private interface ListItem {
  fun getText(): String
}

private class ListItemImpl(private val text: String) : ListItem {
  override fun getText(): String = text
}

private class HeaderItem(text: String) : ListItem by ListItemImpl(text)
private class StoryItem(text: String) : ListItem by ListItemImpl(text)

/**
 * An activity representing a list of Components. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a [ComponentDetailActivity] representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_component_list)

    val toolbar = findViewById<Toolbar>(R.id.toolbar)
    setSupportActionBar(toolbar)
    toolbar.title = title

    val componentsList = findViewById<View>(R.id.componentsList)!!
    val allItems = mutableListOf<ListItem>()
    allItems.add(HeaderItem("Tokens"))
    allItems.addAll(ComponentRegistry.TOKENS.map { StoryItem(it) })
    allItems.add(HeaderItem("Components"))
    allItems.addAll(ComponentRegistry.COMPONENTS.map { StoryItem(it) })

    setupRecyclerView(componentsList as RecyclerView, allItems)
  }

  private fun setupRecyclerView(recyclerView: RecyclerView, values: List<ListItem>) {
    recyclerView.adapter = SimpleItemRecyclerViewAdapter(values)
  }

  private class SimpleItemRecyclerViewAdapter internal constructor(
    private val mValues: List<ListItem>
  ) : RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder>() {
    private val mOnClickListener = View.OnClickListener { view ->
      val viewId = view.tag as String

      val context = view.context
      val intent = Intent(context, ComponentDetailActivity::class.java)
      intent.putExtra(ComponentDetailFragment.ARG_ITEM_ID, viewId)

      context.startActivity(intent)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
      return if (viewType == 0) {
        ViewHolder(LayoutInflater.from(parent.context)
          .inflate(R.layout.component_list_header, parent, false))
      } else {
        ViewHolder(LayoutInflater.from(parent.context)
          .inflate(R.layout.component_list_content, parent, false))
      }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
      holder.name.text = mValues[position].getText()
      holder.itemView.tag = mValues[position].getText()

      if (mValues[position] is StoryItem) {
        holder.itemView.setOnClickListener(mOnClickListener)

        if (position + 1 == itemCount || mValues[position + 1] is HeaderItem) {
          holder.view.findViewById<View>(R.id.component_divider).visibility = View.GONE
        }
      }
    }

    override fun getItemCount(): Int {
      return mValues.size
    }

    override fun getItemViewType(position: Int): Int {
      return if (mValues[position] is HeaderItem) 0 else 1
    }

    internal inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
      val name: TextView = view.findViewById(R.id.component_name)
    }
  }
}
