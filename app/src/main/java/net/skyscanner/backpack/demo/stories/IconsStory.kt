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

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat
import net.skyscanner.backpack.demo.R

private const val PLATFORM_VD_CLAZZ = "android.graphics.drawable.VectorDrawable"

class IconsStory : Story() {

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
    super.onCreateView(inflater, container, savedInstanceState)?.apply {
      // we force LTR layout here as we need to show LTR grid alignment with LTR/RTL icons
      layoutDirection = View.LAYOUT_DIRECTION_LTR
    }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    val iconsGridView: RecyclerView = view.findViewById(R.id.lst_icons)

    val drawableResources = ArrayList<Drawable>()
    val iconNames = ArrayList<String>()

    for (field in R.drawable::class.java.fields) {
      if (field.name.startsWith("bpk_")) {
        try {
          ResourcesCompat.getDrawable(resources, field.getInt(null), null)?.apply {
            if (isVectorDrawable(this)) {
              drawableResources.add(this)
              iconNames.add(field.name)
            }
          }
        } catch (e: Exception) {
          Log.w("IconStory", "unable to load ${field.name}")
        }
      }
    }

    iconsGridView.layoutManager = GridLayoutManager(context, 10)
    iconsGridView.adapter = IconsAdapter(
      drawableResources,
      iconNames,
      if (isRtl) View.LAYOUT_DIRECTION_RTL else View.LAYOUT_DIRECTION_LTR)
  }

  private fun isVectorDrawable(d: Drawable): Boolean {
    return d is VectorDrawableCompat || PLATFORM_VD_CLAZZ == d.javaClass.name
  }

  companion object {
    private const val LAYOUT_ID = "fragment_id"

    infix fun of(fragmentLayout: Int) = IconsStory().apply {
      arguments = Bundle()
      arguments?.putInt(LAYOUT_ID, fragmentLayout)
    }
  }
}
