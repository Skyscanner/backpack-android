package net.skyscanner.backpack.demo

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import net.skyscanner.backpack.demo.data.ComponentRegistry

class StoriesRecyclerViewAdapter internal constructor(
  private val mValues: List<ListItem>
) : RecyclerView.Adapter<StoriesRecyclerViewAdapter.ViewHolder>() {

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
    holder.name.text = ComponentRegistry.getStoryName(mValues[position].getText())
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

  inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    val name: TextView = view.findViewById(R.id.component_name)
  }

  interface ListItem {
    fun getText(): String
  }

  class ListItemImpl(private val text: String) : ListItem {
    override fun getText(): String = text
  }

  class HeaderItem(text: String) : ListItem by ListItemImpl(text)
  class StoryItem(text: String) : ListItem by ListItemImpl(text)
}
