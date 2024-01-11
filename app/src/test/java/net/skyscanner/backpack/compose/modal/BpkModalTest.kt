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

package net.skyscanner.backpack.compose.modal

import net.skyscanner.backpack.compose.BpkSnapshotTest
import net.skyscanner.backpack.demo.compose.ModalStory
import net.skyscanner.backpack.demo.compose.ModalWithBackIcon
import net.skyscanner.backpack.demo.compose.ModalWithoutAction
import net.skyscanner.backpack.demo.compose.ModalWithoutActionAndTitle
import org.junit.Test

class BpkModalTest : BpkSnapshotTest() {

    @Test
    fun modalDefault() = snap(captureFullScreen = true) {
        ModalStory()
    }

    @Test
    fun modalWithBackIcon() = snap(captureFullScreen = true) {
        ModalWithBackIcon()
    }

    @Test
    fun modalWithoutAction() = snap(captureFullScreen = true) {
        ModalWithoutAction()
    }

    @Test
    fun modalWithoutActionAndTitle() = snap(captureFullScreen = true) {
        ModalWithoutActionAndTitle()
    }
}
