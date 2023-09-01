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

import androidx.compose.runtime.Composable
import androidx.compose.ui.test.isPopup
import androidx.compose.ui.test.junit4.createComposeRule
import net.skyscanner.backpack.compose.BpkSnapshotTest
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.demo.compose.ModalStory
import net.skyscanner.backpack.demo.compose.ModalWithBackIcon
import net.skyscanner.backpack.demo.compose.ModalWithoutAction
import net.skyscanner.backpack.demo.compose.ModalWithoutActionAndTitle
import org.junit.Rule
import org.junit.Test

class BpkModalTest : BpkSnapshotTest() {

    @get:Rule
    val rule = createComposeRule()

    @Test
    fun modalDefault() = record {
        ModalStory()
    }

    @Test
    fun modalWithBackIcon() = record {
        ModalWithBackIcon()
    }

    @Test
    fun modalWithoutAction() = record {
        ModalWithoutAction()
    }

    @Test
    fun modalWithoutActionAndTitle() = record {
        ModalWithoutActionAndTitle()
    }

    private fun record(content: @Composable () -> Unit) {
        rule.mainClock.autoAdvance = true
        rule.setContent { BpkTheme { content() } }
        rule.mainClock.advanceTimeBy(1000) // for skipping the animation
        rule.waitForIdle()
        compareScreenshot(rule.onNode(isPopup()))
    }
}
