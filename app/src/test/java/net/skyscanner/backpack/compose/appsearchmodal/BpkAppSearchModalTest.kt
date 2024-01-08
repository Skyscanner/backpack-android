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

import net.skyscanner.backpack.compose.BpkSnapshotTest
import net.skyscanner.backpack.demo.compose.AppSearchModalStoryContent
import net.skyscanner.backpack.demo.compose.AppSearchModalStoryContentInputText
import net.skyscanner.backpack.demo.compose.AppSearchModalStoryError
import net.skyscanner.backpack.demo.compose.AppSearchModalStoryLoading
import org.junit.Test

class BpkAppSearchModalTest : BpkSnapshotTest() {

    @Test
    fun content() = snap(captureFullScreen = true) {
        AppSearchModalStoryContent()
    }

    @Test
    fun content_inputText() = snap(captureFullScreen = true) {
        AppSearchModalStoryContentInputText()
    }

    @Test
    fun loading() = snap(captureFullScreen = true) {
        AppSearchModalStoryLoading()
    }

    @Test
    fun error() = snap(captureFullScreen = true) {
        AppSearchModalStoryError()
    }
}
