/**
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2018-2021 Skyscanner Ltd
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

package net.skyscanner.backpack.map.internal

import android.content.Context
import android.view.View
import android.widget.ImageView
import androidx.appcompat.content.res.AppCompatResources
import net.skyscanner.backpack.R
import net.skyscanner.backpack.text.BpkText

internal fun createBpkMarkerView(
  context: Context,
  title: String,
  icon: Int,
  showPointer: Boolean,
): View {
  val root: View
  if (icon != 0) {
    root = View.inflate(context, R.layout.view_bpk_map_marker_label_with_icon, null)
    root.findViewById<ImageView>(R.id.icon).setImageDrawable(AppCompatResources.getDrawable(context, icon))
  } else {
    root = View.inflate(context, R.layout.view_bpk_map_marker_label, null)
  }
  root.findViewById<BpkText>(R.id.text).text = title
  root.findViewById<View>(R.id.pointer).visibility = if (showPointer) View.VISIBLE else View.GONE
  return root
}
