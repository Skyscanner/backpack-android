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
import androidx.core.graphics.ColorUtils
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
    val color = holder.itemView.context.getColor(colorResources[position].getInt(null))
    val lightTextColor = holder.name.context.getColor(R.color.bpkTextOnLight)
    holder.background.setBackgroundColor(color)
    holder.name.text = colorResources[position].name.replace("bpk", "", true)
    holder.colorValue.text = holder.itemView.resources.getString(colorResources[position].getInt(null))
      .replace("#ff", "")
    val textColor = if (ColorUtils.calculateContrast(color, lightTextColor) >= 4.5) {
      lightTextColor
    } else {
      holder.name.context.getColor(R.color.bpkTextOnDark)
    }
    holder.name.setTextColor(textColor)
    holder.colorValue.setTextColor(textColor)
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
