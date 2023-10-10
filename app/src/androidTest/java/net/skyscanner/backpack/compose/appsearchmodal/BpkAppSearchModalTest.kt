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

package net.skyscanner.backpack.compose.appsearchmodal

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.test.isDialog
import net.skyscanner.backpack.compose.BpkSnapshotTest
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.demo.compose.AppSearchModalStoryContent
import net.skyscanner.backpack.demo.compose.AppSearchModalStoryError
import net.skyscanner.backpack.demo.compose.AppSearchModalStoryLoading
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class BpkAppSearchModalTest(private val permutation: Permutation) : BpkSnapshotTest(listOf(permutation)) {

    @Test
    fun default() = record {
        permutation.composable()
    }

    @Test
    fun on_dark() = record(background = { BpkTheme.colors.surfaceContrast }) {
        permutation.composable()
    }

    private fun record(
        background: @Composable () -> Color = { Color.Unspecified },
        content: @Composable () -> Unit,
    ) {
        snap(background, comparison = { name ->
            compareScreenshot(onNode(isDialog()), name)
        }) {
            content()
        }
    }

    companion object {
        enum class Permutation(
            val composable: @Composable () -> Unit,
        ) {
            Content(composable = { AppSearchModalStoryContent() }),
            Error(composable = { AppSearchModalStoryError() }),
            Loading(composable = { AppSearchModalStoryLoading() }),
        }

        @JvmStatic
        @Parameterized.Parameters(name = "{0} Screenshot")
        fun data(): Collection<Array<Any>> {
            return Permutation.entries.map { arrayOf(it) }
        }
    }
}
