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

import android.annotation.SuppressLint
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import android.widget.TextView
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.text.BpkText
import java.lang.reflect.Field

class RadiiStory : Story() {

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?,
  ): View? {
    return inflater.inflate(R.layout.fragment_story, container, false)
  }

  @SuppressLint("SetTextI18n")
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    val radiiResources = arrayListOf<Field>()

    R.dimen::class.java.fields.forEach {
      if (it.name.startsWith("bpkBorderRadius")) {
        radiiResources.add(it)
      }
    }

    radiiResources.sortBy { resources.getDimension(it.getInt(null)) }

    val params = MarginLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT).apply {
      topMargin = resources.getDimensionPixelSize(R.dimen.bpkSpacingBase)
      bottomMargin = resources.getDimensionPixelSize(R.dimen.bpkSpacingBase)
    }

    radiiResources.forEach {
      val text: BpkText = BpkText(requireContext()).apply {
        textStyle = BpkText.TextStyle.BodyLongform
        text = it.name + " = " + resources.getString(it.getInt(null))
        setTextColor(context.getColor(R.color.bpkTextOnDark))
        textAlignment = TextView.TEXT_ALIGNMENT_CENTER
        background = GradientDrawable().apply {
          color = context.getColorStateList(R.color.bpkCorePrimary)
          cornerRadius = resources.getDimension(it.getInt(null))
        }
        val padding = resources.getDimensionPixelSize(R.dimen.bpkSpacingBase)
        setPaddingRelative(padding, padding, padding, padding)
      }

      view.findViewById<ViewGroup>(R.id.layout_story_container).addView(text, params)
    }
  }
}
