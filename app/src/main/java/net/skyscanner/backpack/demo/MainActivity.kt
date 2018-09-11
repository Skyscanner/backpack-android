package net.skyscanner.backpack.demo

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import net.skyscanner.backpack.demo.data.ComponentRegistry

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

    val tokensList = findViewById<View>(R.id.tokensList)!!
    setupRecyclerView(tokensList as RecyclerView, ComponentRegistry.TOKENS)

    val componentsList = findViewById<View>(R.id.componentsList)!!
    setupRecyclerView(componentsList as RecyclerView, ComponentRegistry.COMPONENTS)
  }

  private fun setupRecyclerView(recyclerView: RecyclerView, values: List<String>) {
    recyclerView.adapter = SimpleItemRecyclerViewAdapter(values)
  }

  private class SimpleItemRecyclerViewAdapter internal constructor(
    private val mValues: List<String>) : RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder>() {
    private val mOnClickListener = View.OnClickListener { view ->
      val viewId = view.tag as String

      val context = view.context
      val intent = Intent(context, ComponentDetailActivity::class.java)
      intent.putExtra(ComponentDetailFragment.ARG_ITEM_ID, viewId)

      context.startActivity(intent)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
      val view = LayoutInflater.from(parent.context)
        .inflate(R.layout.component_list_content, parent, false)
      return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
      holder.name.text = mValues[position]

      holder.itemView.tag = mValues[position]
      holder.itemView.setOnClickListener(mOnClickListener)
    }

    override fun getItemCount(): Int {
      return mValues.size
    }

    internal inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
      val name: TextView = view.findViewById(R.id.component_name)
    }
  }
}
