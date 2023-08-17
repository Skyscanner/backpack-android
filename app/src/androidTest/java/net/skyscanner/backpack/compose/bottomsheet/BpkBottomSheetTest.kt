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

package net.skyscanner.backpack.compose.bottomsheet

import androidx.compose.ui.test.isPopup
import androidx.compose.ui.unit.dp
import androidx.test.ext.junit.runners.AndroidJUnit4
import net.skyscanner.backpack.BpkTestVariant
import net.skyscanner.backpack.Variants
import net.skyscanner.backpack.compose.BpkSnapshotTest
import net.skyscanner.backpack.compose.bottomsheet.internal.BpkDragHandleStyle
import net.skyscanner.backpack.demo.compose.BottomSheetStory
import net.skyscanner.backpack.demo.compose.ImageBottomSheetDarkStory
import net.skyscanner.backpack.demo.compose.ImageBottomSheetStory
import net.skyscanner.backpack.demo.compose.ImageContent
import net.skyscanner.backpack.demo.compose.SheetContent
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BpkBottomSheetTest : BpkSnapshotTest() {

    @Test
    fun default() {
        snap(height = 400.dp, padding = 0.dp) {
            BottomSheetStory()
        }
    }

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    fun expanded() {
        snap(height = 400.dp, padding = 0.dp) {
            BottomSheetStory(initialValue = BpkBottomSheetValue.Expanded)
        }
    }

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    fun onImageLight() {
        snap(height = 400.dp, padding = 0.dp) {
            ImageBottomSheetStory(initialValue = BpkBottomSheetValue.Expanded)
        }
    }
    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    fun onImageDark() {
        snap(height = 400.dp, padding = 0.dp) {
            ImageBottomSheetDarkStory(initialValue = BpkBottomSheetValue.Expanded)
        }
    }

    @Test
    fun modal() {
        snap(height = 400.dp, padding = 0.dp, comparison = { name ->
            compareScreenshot(onNode(isPopup()), name)
        }) {
            BpkModalBottomSheet(
                content = { SheetContent() },
                onDismissRequest = {},
            )
        }
    }

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    fun imageModalLight() {
        snap(height = 400.dp, padding = 0.dp, comparison = { name ->
            compareScreenshot(onNode(isPopup()), name)
        }) {
            BpkModalBottomSheet(
                content = { ImageContent() },
                dragHandleStyle = BpkDragHandleStyle.OnImage(),
                onDismissRequest = {},
            )
        }
    }

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    fun imageModelDark() {
        snap(height = 400.dp, padding = 0.dp, comparison = { name ->
            compareScreenshot(onNode(isPopup()), name)
        }) {
            BpkModalBottomSheet(
                content = { ImageContent() },
                dragHandleStyle = BpkDragHandleStyle.OnImage(BpkDragHandleStyle.OnImage.Type.Dark),
                onDismissRequest = {},
            )
        }
    }
}
