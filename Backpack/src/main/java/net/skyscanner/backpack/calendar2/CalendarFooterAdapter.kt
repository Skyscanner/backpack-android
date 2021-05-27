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

package net.skyscanner.backpack.calendar2

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.threeten.bp.YearMonth

interface CalendarFooterAdapter<VH : RecyclerView.ViewHolder> {

  fun onCreateViewHolder(parent: ViewGroup): VH

  fun onBindViewHolder(holder: VH, data: YearMonth)
}

internal object DefaultCalendarFooterAdapter : CalendarFooterAdapter<RecyclerView.ViewHolder> {

  override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
    error("A custom footer adapter has to be assigned!")

  override fun onBindViewHolder(holder: RecyclerView.ViewHolder, data: YearMonth) =
    error("A custom footer adapter has to be assigned!")
}
