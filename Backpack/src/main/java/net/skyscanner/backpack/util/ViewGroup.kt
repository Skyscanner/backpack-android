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

package net.skyscanner.backpack.util

import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout

internal inline fun ViewGroup.forEachIndexed(block: (Int, View) -> Unit) {
  for (i in 0 until childCount) {
    block(i, getChildAt(i))
  }
}

internal inline fun ViewGroup.forEach(block: (View) -> Unit) {
  for (i in 0 until childCount) {
    block(getChildAt(i))
  }
}

internal inline fun FrameLayout.addView(view: View, builder: FrameLayout.LayoutParams.() -> Unit) {
  val params = FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
  builder(params)
  addView(view, params)
}

internal inline fun LinearLayout.addView(view: View, builder: LinearLayout.LayoutParams.() -> Unit) {
  val params = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
  builder(params)
  addView(view, params)
}
