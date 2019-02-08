package net.skyscanner.backpack.demo.stories

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.text.BpkText
import java.lang.reflect.Field

class ColorAdapter(private val colorResources: ArrayList<Field>) : RecyclerView.Adapter<ColorAdapter.ViewHolder>() {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val view = LayoutInflater.from(parent.context)
      .inflate(R.layout.color_item, parent, false)
    return ViewHolder(view)
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    holder.background.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, colorResources[position].getInt(null)))
    holder.name.text = colorResources[position].name.replace("bpk", "", true)
    holder.colorValue.text = holder.itemView.resources.getString(colorResources[position].getInt(null)).replace("#ff", "")
    if (colorResources[position].name.contains("900") || colorResources[position].name.contains("800")) {
      holder.name.setTextColor(ContextCompat.getColor(holder.name.context, R.color.bpkGray100))
      holder.colorValue.setTextColor(ContextCompat.getColor(holder.colorValue.context, R.color.bpkGray100))
    }
  }

  override fun getItemCount(): Int {
    return colorResources.size
  }

  inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var background: LinearLayoutCompat = itemView.findViewById(R.id.color_item)
    var name: BpkText = itemView.findViewById(R.id.txt_color_name)
    var colorValue: BpkText = itemView.findViewById(R.id.txt_color_value)
  }
}
