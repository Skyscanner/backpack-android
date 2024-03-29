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

package net.skyscanner.backpack.rating

import android.content.Context
import androidx.appcompat.content.res.AppCompatResources
import net.skyscanner.backpack.demo.R

internal fun createTestRating(
    context: Context,
    style: BpkRating.Style = BpkRating.Style.Horizontal,
    size: BpkRating.Size = BpkRating.Size.Base,
    scale: BpkRating.Scale = BpkRating.Scale.ZeroToTen,
    value: Float = 7f,
) =
    BpkRating(context, style, size, scale).apply {
        icon = {
            when (it) {
                BpkRating.Score.Low -> AppCompatResources.getDrawable(context, R.drawable.bpk_star_outline)
                BpkRating.Score.Medium -> AppCompatResources.getDrawable(context, R.drawable.bpk_star_half)
                BpkRating.Score.High -> AppCompatResources.getDrawable(context, R.drawable.bpk_star)
            }
        }
        title = {
            val array = resources.getStringArray(R.array.rating_sample_titles)

            when (it) {
                BpkRating.Score.Low -> array[0]
                BpkRating.Score.Medium -> array[1]
                BpkRating.Score.High -> array[2]
            }
        }
        subtitle = {
            val array = resources.getStringArray(R.array.rating_sample_subtitles)

            when (it) {
                BpkRating.Score.Low -> array[0]
                BpkRating.Score.Medium -> array[1]
                BpkRating.Score.High -> array[2]
            }
        }
        this.value = value
    }
