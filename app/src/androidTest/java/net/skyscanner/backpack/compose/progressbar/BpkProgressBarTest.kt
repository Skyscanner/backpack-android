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

package net.skyscanner.backpack.compose.progressbar

import net.skyscanner.backpack.compose.BpkSnapshotTest
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class BpkProgressBarTest(val size: BpkProgressBarSize) : BpkSnapshotTest(listOf(size)) {

    @Test
    fun noProgress() = snap {
        BpkProgressBar(
            value = 0F,
            size = size,
        )
    }

    @Test
    fun progressBelowMaximum() = snap {
        BpkProgressBar(
            value = 0.9F,
            size = size,
        )
    }

    @Test
    fun progressAboveMaximum() = snap {
        BpkProgressBar(
            value = 1.1F,
            size = size,
        )
    }

    @Test
    fun steppedNoProgress() = snap {
        BpkProgressBar(
            value = 0F,
            max = 5,
            stepped = true,
            size = size,
        )
    }

    @Test
    fun steppedProgressInBetweenSteps() = snap {
        BpkProgressBar(
            value = 3.5F,
            max = 5,
            stepped = true,
            size = size,
        )
    }

    @Test
    fun steppedProgressAtStep() = snap {
        BpkProgressBar(
            value = 3F,
            max = 5,
            stepped = true,
            size = size,
        )
    }

    @Test
    fun steppedProgressAboveMaximum() = snap {
        BpkProgressBar(
            value = 6F,
            max = 5,
            stepped = true,
            size = size,
        )
    }

    companion object {
        @JvmStatic
        @Parameterized.Parameters(name = "{0} Screenshot")
        fun flavours(): List<BpkProgressBarSize> = BpkProgressBarSize.entries.toList()
    }
}
