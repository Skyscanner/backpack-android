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

package net.skyscanner.backpack.demo

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.view.View
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.roundToInt
import net.skyscanner.backpack.demo.StoriesRecyclerViewAdapter.Companion.STORY_VIEW_TYPE

class StoryItemDecoration(context: Context) : RecyclerView.ItemDecoration() {

  private val divider = AppCompatResources.getDrawable(context, R.drawable.item_divider)!!
  private val dividerPadding = context.resources.getDimensionPixelSize(R.dimen.bpkSpacingLg)
  private val bounds = Rect()

  override fun onDraw(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
    val left = dividerPadding
    val right = parent.width - dividerPadding
    for (i in 0 until parent.childCount) {
      val view = parent.getChildAt(i)
      if (shouldDrawDivider(parent, view)) {
        parent.getDecoratedBoundsWithMargins(view, bounds)

        val bottom: Int = bounds.bottom + view.translationY.roundToInt()
        val top: Int = bottom - divider.intrinsicHeight
        divider.setBounds(left, top, right, bottom)
        divider.draw(canvas)
      }
    }
  }

  override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
    if (shouldDrawDivider(parent, view)) {
      outRect.set(0, 0, 0, divider.intrinsicHeight)
    } else {
      outRect.set(0, 0, 0, 0)
    }
  }

  private fun shouldDrawDivider(parent: RecyclerView, view: View): Boolean {
    val position = parent.getChildAdapterPosition(view)
    val adapter = parent.adapter!!
    if (position == adapter.itemCount - 1) {
      return false
    }
    val viewType = adapter.getItemViewType(position)
    return viewType == STORY_VIEW_TYPE && adapter.getItemViewType(position + 1) == STORY_VIEW_TYPE
  }
}
