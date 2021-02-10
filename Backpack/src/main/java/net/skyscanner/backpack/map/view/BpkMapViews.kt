package net.skyscanner.backpack.map.view

import android.content.Context
import android.view.View
import android.widget.ImageView
import androidx.appcompat.content.res.AppCompatResources
import net.skyscanner.backpack.R
import net.skyscanner.backpack.text.BpkText

internal fun createMarkerView(
  context: Context,
  title: String,
  icon: Int = 0,
  selected: Boolean,
): View {
  val root: View
  if (icon != 0) {
    root = View.inflate(context, R.layout.view_bpk_map_marker_label_with_icon, null)
    root.findViewById<ImageView>(R.id.icon).setImageDrawable(AppCompatResources.getDrawable(context, icon))
  } else {
    root = View.inflate(context, R.layout.view_bpk_map_marker_label, null)
  }
  root.findViewById<BpkText>(R.id.text).text = title
  root.isSelected = selected
  return root
}
