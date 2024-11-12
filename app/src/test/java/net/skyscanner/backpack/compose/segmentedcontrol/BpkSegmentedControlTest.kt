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

package net.skyscanner.backpack.compose.segmentedcontrol

import androidx.compose.ui.res.stringResource
import net.skyscanner.backpack.compose.BpkSnapshotTest
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.demo.compose.SegmentedControlsSample
import net.skyscanner.backpack.demo.compose.toColor
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.ParameterizedRobolectricTestRunner

@RunWith(ParameterizedRobolectricTestRunner::class)
class BpkSegmentedControlTest(private val permutation: Permutation) : BpkSnapshotTest(listOf(permutation)) {

    @Test
    fun regularValue() = snap({ permutation.type.toColor() }) {
        SegmentedControlsSample(
            type = permutation.type,
            shadow = permutation.shadowEnabled,
            selectedIndex = permutation.selectedInt,
            numberOfButtons = permutation.numberOfButtons,
        )
    }

    @Test
    fun longValue() = snap({ permutation.type.toColor() }) {
        SegmentedControlsSample(
            type = permutation.type,
            shadow = permutation.shadowEnabled,
            selectedIndex = permutation.selectedInt,
            numberOfButtons = permutation.numberOfButtons,
            content = stringResource(R.string.button_long_text),
        )
    }

    companion object {
        enum class Permutation(
            val type: BpkSegmentedControlStyle,
            val shadowEnabled: Boolean = false,
            val numberOfButtons: Int = 2,
            val selectedInt: Int = 0,
        ) {
            CanvasDefaultTwoButtons(type = BpkSegmentedControlStyle.CanvasDefault),
            CanvasContrastTwoButtons(type = BpkSegmentedControlStyle.CanvasContrast),
            SurfaceDefaultTwoButtons(type = BpkSegmentedControlStyle.SurfaceDefault),
            SurfaceContrastTwoButtons(type = BpkSegmentedControlStyle.SurfaceContrast),
            CanvasDefaultThreeButtons(type = BpkSegmentedControlStyle.CanvasDefault, numberOfButtons = 3),
            CanvasDefaultFourButtons(type = BpkSegmentedControlStyle.CanvasDefault, numberOfButtons = 4),
            CanvasDefaultTwoButtonsAndShadow(type = BpkSegmentedControlStyle.CanvasDefault, shadowEnabled = true),
        }

        @JvmStatic
        @ParameterizedRobolectricTestRunner.Parameters(name = "{0} Screenshot")
        fun data(): Collection<Array<Any>> {
            return Permutation.entries.map { arrayOf(it) }
        }
    }
}
