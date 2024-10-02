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

package net.skyscanner.backpack.compose.searchinputsummary

import net.skyscanner.backpack.compose.BpkSnapshotTest
import net.skyscanner.backpack.demo.compose.DefaultExample
import net.skyscanner.backpack.demo.compose.IconPrefixExample
import net.skyscanner.backpack.demo.compose.NoPrefixExample
import net.skyscanner.backpack.demo.compose.ReadOnlyExample
import net.skyscanner.backpack.demo.compose.TextPrefixExample
import org.junit.Test

class BpkSearchInputSummaryTest : BpkSnapshotTest() {

    @Test
    fun defaultSearchInputSummary() = snap {
        DefaultExample()
    }

    @Test
    fun textSearchInputSummary() = snap {
        TextPrefixExample()
    }

    @Test
    fun iconSearchInputSummary() = snap {
        IconPrefixExample()
    }

    @Test
    fun noPrefixSearchInputSummary() = snap {
        NoPrefixExample()
    }

    @Test
    fun readOnlySearchInputSummary() = snap {
        ReadOnlyExample()
    }
}
