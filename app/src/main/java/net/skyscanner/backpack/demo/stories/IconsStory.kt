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

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.content.res.AppCompatResources
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.demo.components.IconComponent
import net.skyscanner.backpack.demo.meta.ViewStory
import net.skyscanner.backpack.demo.ui.AndroidLayout
import net.skyscanner.backpack.toast.BpkToast

@Composable
@IconComponent
@ViewStory("Default")
fun IconsStoryDefault(modifier: Modifier = Modifier) =
  IconsDemo(IconType.Default, modifier)

@Composable
@IconComponent
@ViewStory("Small")
fun IconsStorySmall(modifier: Modifier = Modifier) =
  IconsDemo(IconType.Small, modifier)

@Composable
private fun IconsDemo(
  iconType: IconType,
  modifier: Modifier = Modifier,
) {
  AndroidLayout<RecyclerView>(R.layout.fragment_all_icons, R.id.lst_icons, modifier.fillMaxSize()) {
    layoutManager = GridLayoutManager(context, 10)
    adapter = IconsAdapter(
      fetchAllIcons(context, iconType),
      View.LAYOUT_DIRECTION_LTR,
    )
  }
}

private const val PLATFORM_VD_CLAZZ = "android.graphics.drawable.VectorDrawable"
private const val ICON_TYPE = "icon_type"

internal enum class IconType {
  Default {
    override fun matchesName(name: String): Boolean = !name.endsWith("_sm")
  },
  Small {
    override fun matchesName(name: String) = name.endsWith("_sm")
  }, ;

  abstract fun matchesName(name: String): Boolean
}

class IconsFragment : Story() {

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?,
  ): View? =
    super.onCreateView(inflater, container, savedInstanceState)?.apply {
      // we force LTR layout here as we need to show LTR grid alignment with LTR/RTL icons
      layoutDirection = View.LAYOUT_DIRECTION_LTR
    }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    val iconsGridView: RecyclerView = view.findViewById(R.id.lst_icons)

    val iconType: IconType = arguments?.getSerializable(ICON_TYPE) as? IconType? ?: IconType.Default

    iconsGridView.layoutManager = GridLayoutManager(context, 10)
    iconsGridView.adapter = IconsAdapter(
      fetchAllIcons(requireContext(), iconType),
      if (isRtl) View.LAYOUT_DIRECTION_RTL else View.LAYOUT_DIRECTION_LTR,
    )
  }

  companion object {
    private const val LAYOUT_ID = "fragment_id"

    internal infix fun of(type: IconType) = IconsFragment().apply {
      arguments = Bundle()
      arguments?.putInt(LAYOUT_ID, R.layout.fragment_all_icons)
      arguments?.putSerializable(ICON_TYPE, type)
    }
  }
}

private data class BpkIcon(
  val name: String,
  val drawable: Drawable,
)

private fun fetchAllIcons(context: Context, iconType: IconType): List<BpkIcon> {

  fun isVectorDrawable(d: Drawable): Boolean {
    return d is VectorDrawableCompat || PLATFORM_VD_CLAZZ == d.javaClass.name
  }

  val icons = mutableListOf<BpkIcon>()

  for (field in R.drawable::class.java.fields) {
    if (field.name.startsWith("bpk_") && iconType.matchesName(field.name)) {
      try {
        AppCompatResources.getDrawable(context, field.getInt(null))?.apply {
          if (isVectorDrawable(this)) {
            icons.add(BpkIcon(field.name, this))
          }
        }
      } catch (e: Exception) {
        Log.w("IconStory", "unable to load ${field.name}")
      }
    }
  }

  return icons
}

private class IconsAdapter(
  private var icons: List<BpkIcon>,
  private val direction: Int,
) : RecyclerView.Adapter<IconsAdapter.ViewHolder>() {

  private var rtlIconBackgroundColor: Int = 0

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val view = LayoutInflater.from(parent.context)
      .inflate(R.layout.icon_item, parent, false)

    rtlIconBackgroundColor = parent.context.getColor(R.color.bpkLine)
    return ViewHolder(view)
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    holder.img.setImageDrawable(icons[position].drawable)
    holder.img.setOnClickListener {
      BpkToast.makeText(holder.itemView.context, icons[position].name, BpkToast.LENGTH_SHORT).show()
    }
    holder.img.contentDescription = icons[position].name
      .replace("bpk_", "")
      .replace("_sm", "")
      .replace("_", " ")

    // We do this instead of setting the parent's layout direction to avoid changing the
    // position of all icons to make it easier to see which icons currently support RTL
    if (direction == View.LAYOUT_DIRECTION_RTL && icons[position].drawable.isAutoMirrored) {
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
