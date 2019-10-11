package net.skyscanner.backpack.demo.stories

import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.demo.stories.IconsAdapter.ViewHolder

class IconsAdapter(
  private var icons: ArrayList<Drawable>,
  private var names: ArrayList<String>,
  private val direction: Int
) : RecyclerView.Adapter<ViewHolder>() {

  private var rtlIconBackgroundColor: Int = 0

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val view = LayoutInflater.from(parent.context)
      .inflate(R.layout.icon_item, parent, false)

    rtlIconBackgroundColor = ContextCompat.getColor(parent.context, R.color.bpkHarbour)
//    ColorUtils.setAlphaComponent(rtlIconBackgroundColor, 85)
    return ViewHolder(view)
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    holder.img.setImageDrawable(icons[position])
    holder.img.setOnClickListener {
      Toast.makeText(holder.itemView.context, names[position], Toast.LENGTH_SHORT).show()
    }

    // We do this instead of setting the parent's layout direction to avoid changing the
    // position of all icons to make it easier to see which icons currently support RTL
    if (direction == View.LAYOUT_DIRECTION_RTL && icons[position].isAutoMirrored) {
      holder.img.rotationY = 180f
      holder.img.background = ColorDrawable(rtlIconBackgroundColor)
    }
  }

  override fun getItemCount(): Int {
    return icons.size
  }

  inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var img: ImageView = itemView.findViewById(R.id.img_icon_item)
  }
}
