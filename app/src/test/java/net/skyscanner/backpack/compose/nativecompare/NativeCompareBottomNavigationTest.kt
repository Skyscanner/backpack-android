/**
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2018 - 2026 Skyscanner Ltd
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

package net.skyscanner.backpack.compose.nativecompare

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import net.skyscanner.backpack.BpkTestVariant
import net.skyscanner.backpack.Variants
import net.skyscanner.backpack.compose.BpkSnapshotTest
import net.skyscanner.backpack.demo.compose.nativecompare.BottomNavigationComparison
import net.skyscanner.backpack.demo.compose.nativecompare.NativeBottomNavigationComparison
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.ParameterizedRobolectricTestRunner

/**
 * Captures the Backpack vs Material 3 bottom navigation comparison used in the "Backpack on native" audit,
 * one image per variant so each can sit next to its row in the audit page.
 */
@RunWith(ParameterizedRobolectricTestRunner::class)
internal class NativeCompareBottomNavigationTest(
    private val pair: BottomNavigationComparison,
) : BpkSnapshotTest(listOf(pair)) {

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    fun pairs() {
        // Density 1 gives the 720px test device 720dp: a landscape image the table reads well in.
        snap(providers = arrayOf(LocalDensity provides Density(density = 1f))) {
            Comparison()
        }
    }

    @Test
    @Variants(BpkTestVariant.Default)
    fun pairsAtLargeText() {
        snap(providers = arrayOf(LocalDensity provides Density(density = 1f, fontScale = 2f))) {
            Comparison()
        }
    }

    @Composable
    private fun Comparison() {
        NativeBottomNavigationComparison(pair)
    }

    companion object {
        @JvmStatic
        @ParameterizedRobolectricTestRunner.Parameters(name = "{0}")
        internal fun pairs(): List<BottomNavigationComparison> = BottomNavigationComparison.entries
    }
}
