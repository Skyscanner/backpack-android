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

package net.skyscanner.backpack.util

import android.content.Context
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

internal abstract class ItemHolder<T>(
  protected val view: View
) : RecyclerView.ViewHolder(view), Consumer<T> {

  val context: Context
    get() = itemView.context

  val resources: Resources
    get() = itemView.resources

  var model: T? = null
    private set

  constructor(parent: ViewGroup, @LayoutRes layout: Int) :
    this(LayoutInflater.from(parent.context).inflate(layout, parent, false))

  final override fun invoke(model: T) {
    if (this.model != model) {
      this.model = model
      bind(model)
    }
  }

  protected abstract fun bind(model: T)

  fun <T : View> findViewById(@IdRes id: Int): T =
    view.findViewById(id)
}
