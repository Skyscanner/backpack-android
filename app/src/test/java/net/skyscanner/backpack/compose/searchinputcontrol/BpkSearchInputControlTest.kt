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

package net.skyscanner.backpack.compose.searchinputcontrol

import net.skyscanner.backpack.compose.BpkSnapshotTest
import net.skyscanner.backpack.demo.compose.CornerControlExample
import net.skyscanner.backpack.demo.compose.DefaultControlExample
import net.skyscanner.backpack.demo.compose.IconPrefixControlExample
import net.skyscanner.backpack.demo.compose.NoPrefixControlExample
import net.skyscanner.backpack.demo.compose.ReadOnlyControlExample
import net.skyscanner.backpack.demo.compose.TextPrefixControlExample
import org.junit.Test

class BpkSearchInputControlTest : BpkSnapshotTest() {

    @Test
    fun defaultSearchInputControl() = snap {
        DefaultControlExample()
    }

    @Test
    fun textSearchInputControl() = snap {
        TextPrefixControlExample()
    }

    @Test
    fun iconSearchInputControl() = snap {
        IconPrefixControlExample()
    }

    @Test
    fun noPrefixSearchInputControl() = snap {
        NoPrefixControlExample()
    }

    @Test
    fun readOnlySearchInputControl() = snap {
        ReadOnlyControlExample()
    }

    @Test
    fun cornerSearchInputControl() = snap {
        CornerControlExample()
    }
}
