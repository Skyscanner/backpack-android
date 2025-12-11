/*
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2018 - 2025 Skyscanner Ltd
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
package net.skyscanner.backpack.demo.figma.chipgroup
import androidx.compose.runtime.Composable
import com.figma.code.connect.Figma
import com.figma.code.connect.FigmaConnect
import com.figma.code.connect.FigmaProperty
import com.figma.code.connect.FigmaType
import com.figma.code.connect.FigmaVariant
import net.skyscanner.backpack.compose.chip.BpkChipStyle
import net.skyscanner.backpack.compose.chipgroup.single.BpkSingleChipGroupType
import net.skyscanner.backpack.compose.chipgroup.single.BpkSingleChipItem
import net.skyscanner.backpack.compose.chipgroup.single.BpkSingleSelectChipGroup

@FigmaConnect("FIGMA_GROUP_CHIPS")
@FigmaVariant(key = "Breakpoint", value = "Mobile")
public class BpkSingleSelectChipGroupDoc {
    @FigmaProperty(FigmaType.Text, "Label")
    public val label: String = "Filter by"

    @FigmaProperty(FigmaType.Enum, "Style")
    public val style: BpkChipStyle = Figma.mapping(
        "Default" to BpkChipStyle.Default,
        "On Dark" to BpkChipStyle.OnDark,
        "On Image" to BpkChipStyle.OnImage,
    )

    @Composable
    public fun ComponentExample() {
        BpkSingleSelectChipGroup(
            chips = listOf(BpkSingleChipItem("London"), BpkSingleChipItem("Berlin")),
            selectedIndex = 0, // Index of the selected chip
            onItemClicked = {},
            style = style,
            type = BpkSingleChipGroupType.Rail,
            behaviouralEventWrapper = null,

        )
    }
}
