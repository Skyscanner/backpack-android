package net.skyscanner.backpack.demo.stories

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.demo.stories.IconsAdapter.ViewHolder

class IconsAdapter(private var icons: ArrayList<Drawable>, private var names: ArrayList<String>) : RecyclerView.Adapter<ViewHolder>() {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val view = LayoutInflater.from(parent.context)
      .inflate(R.layout.icon_item, parent, false)
    return ViewHolder(view)
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    holder.img.setImageDrawable(icons[position])
    holder.img.setOnClickListener {
      Toast.makeText(holder.itemView.context, names[position], Toast.LENGTH_SHORT).show()
    }
  }

  override fun getItemCount(): Int {
    return icons.size
  }

  inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var img: ImageView = itemView.findViewById(R.id.img_icon_item)
  }
}
