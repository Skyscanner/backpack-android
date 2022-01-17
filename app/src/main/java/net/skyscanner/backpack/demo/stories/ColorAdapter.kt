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

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import java.lang.reflect.Field
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.text.BpkText

class ColorAdapter(private val colorResources: ArrayList<Field>) : RecyclerView.Adapter<ColorAdapter.ViewHolder>() {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val view = LayoutInflater.from(parent.context)
      .inflate(R.layout.color_item, parent, false)
    return ViewHolder(view)
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    holder.background.setBackgroundColor(
      holder.itemView.context.getColor(colorResources[position].getInt(null))
    )
    holder.name.text = colorResources[position].name.replace("bpk", "", true)
    holder.colorValue.text = holder.itemView.resources.getString(colorResources[position].getInt(null))
      .replace("#ff", "")
    if (colorResources[position].name.contains("900") || colorResources[position].name.contains("800")) {
      holder.name.setTextColor(holder.name.context.getColor(R.color.bpkSkyGrayTint06))
      holder.colorValue.setTextColor(holder.colorValue.context.getColor(R.color.bpkSkyGrayTint06))
    }
  }

  override fun getItemCount(): Int {
    return colorResources.size
  }

  inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var background: View = itemView.findViewById(R.id.color_item)
    var name: BpkText = itemView.findViewById(R.id.txt_color_name)
    var colorValue: BpkText = itemView.findViewById(R.id.txt_color_value)
  }
}
