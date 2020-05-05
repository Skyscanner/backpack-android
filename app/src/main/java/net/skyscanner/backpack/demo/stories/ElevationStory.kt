/**
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2018-2020 Skyscanner Ltd
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

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.LinearLayoutCompat
import net.skyscanner.backpack.card.BpkCardView
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.text.BpkText
import java.lang.reflect.Field

class ElevationStory : Story() {

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    return inflater.inflate(R.layout.fragment_elevation, container, false)
  }

  @SuppressLint("SetTextI18n")
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    val elevationResources = arrayListOf<Field>()

    R.dimen::class.java.fields.forEach {
      if (it.name.startsWith("bpkElevation")) {
        elevationResources.add(it)
      }
    }

    elevationResources.sortBy { resources.getDimension(it.getInt(null)) }

    elevationResources.forEach {
      val card: BpkCardView = BpkCardView(requireContext()).apply {
        elevation = resources.getDimension(it.getInt(null))
        useCompatPadding = true
      }
      val text: BpkText = BpkText(requireContext()).apply {
        textStyle = BpkText.LG
        text = it.name + " = " + resources.getString(it.getInt(null))
      }
      text.gravity = Gravity.CENTER_VERTICAL or Gravity.CENTER_HORIZONTAL
      card.addView(text)
      view.findViewById<LinearLayoutCompat>(R.id.layout_elevation_container).addView(card)
    }
  }
}
