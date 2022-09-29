/**
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2018 Skyscanner Ltd
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.skyscanner.backpack.demo.stories

import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.demo.stories.IconsAdapter.ViewHolder
import net.skyscanner.backpack.toast.BpkToast

class IconsAdapter(
  private var icons: ArrayList<Drawable>,
  private var names: ArrayList<String>,
  private val direction: Int
) : RecyclerView.Adapter<ViewHolder>() {

  private var rtlIconBackgroundColor: Int = 0

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val view = LayoutInflater.from(parent.context)
      .inflate(R.layout.icon_item, parent, false)

    rtlIconBackgroundColor = parent.context.getColor(R.color.bpkLine)
    return ViewHolder(view)
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    holder.img.setImageDrawable(icons[position])
    holder.img.setOnClickListener {
      BpkToast.makeText(holder.itemView.context, names[position], BpkToast.LENGTH_SHORT).show()
    }
    holder.img.contentDescription = names[position]
      .replace("bpk_", "")
      .replace("_sm", "")
      .replace("_", " ")

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
