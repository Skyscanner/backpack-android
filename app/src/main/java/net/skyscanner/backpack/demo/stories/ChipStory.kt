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

import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import net.skyscanner.backpack.chip.BpkChip
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.demo.components.ChipComponent
import net.skyscanner.backpack.demo.meta.ViewStory
import net.skyscanner.backpack.demo.ui.AndroidLayout

@Composable
@ChipComponent
@ViewStory("Default")
fun ChipStoryDefault(modifier: Modifier = Modifier) =
    ChipDemo(R.layout.fragment_chip, modifier)

@Composable
@ChipComponent
@ViewStory("On Dark")
fun ChipStoryOnDark(modifier: Modifier = Modifier) =
    ChipDemo(R.layout.fragment_chip_ondark, modifier)

@Composable
@ChipComponent
@ViewStory("On Image")
fun ChipStoryOnImage(modifier: Modifier = Modifier) =
    ChipDemo(R.layout.fragment_chip_on_image, modifier)

@Composable
private fun ChipDemo(
    @LayoutRes layoutId: Int,
    modifier: Modifier = Modifier,
) =
    AndroidLayout(
        layoutId = layoutId,
        modifier = modifier.fillMaxSize(),
    ) {
        forEachChip(this as ViewGroup) { chip ->
            chip.setOnClickListener {
                chip.toggle()
            }
        }
    }

private fun forEachChip(view: ViewGroup, block: (chip: BpkChip) -> Unit) {
    (0..(view.childCount - 1)).forEach {
        val child = view.getChildAt(it)
        if (child is ViewGroup) {
            forEachChip(child, block)
        } else if (child is BpkChip) {
            block(child)
        }
    }
}
