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

package net.skyscanner.backpack.compose.modal

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import net.skyscanner.backpack.compose.navigationbar.NavIcon
import net.skyscanner.backpack.compose.theme.BpkTheme
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import java.util.concurrent.atomic.AtomicInteger

class BpkModalAnimatedTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val dismissCount = AtomicInteger(0)

    @Test
    fun givenAnimated_whenCloseClicked_thenOnDismissIsCalledOnceAfterTheAnimationFinishes() {
        composeTestRule.mainClock.autoAdvance = false
        setContent(animated = true)
        composeTestRule.mainClock.advanceTimeBy(ModalAnimationDurationMs + FRAME_MS)

        composeTestRule.onNodeWithContentDescription(CLOSE).performClick()
        composeTestRule.mainClock.advanceTimeBy(ModalAnimationDurationMs - FRAME_MS * 2)
        assertEquals(0, dismissCount.get())

        composeTestRule.mainClock.advanceTimeBy(FRAME_MS * 4)
        assertEquals(1, dismissCount.get())
    }

    @Test
    fun givenNotAnimated_whenCloseClicked_thenOnDismissIsCalledOnceWithoutWaitingForAnimation() {
        composeTestRule.mainClock.autoAdvance = false
        setContent(animated = false)
        composeTestRule.mainClock.advanceTimeBy(FRAME_MS * 2)

        composeTestRule.onNodeWithContentDescription(CLOSE).performClick()
        composeTestRule.mainClock.advanceTimeBy(FRAME_MS * 2)

        assertEquals(1, dismissCount.get())
    }

    private fun setContent(animated: Boolean) {
        composeTestRule.setContent {
            BpkTheme {
                // Like real callers, remove the modal from the composition once it has been dismissed. BpkModal calls
                // onDismiss on every recomposition while it is hidden, so this is what makes the call count exact.
                var showModal by remember { mutableStateOf(true) }
                if (showModal) {
                    val state = rememberBpkModalState()
                    BpkModal(
                        state = state,
                        navIcon = NavIcon.Close(contentDescription = CLOSE, onClick = { state.hide() }),
                        onDismiss = {
                            dismissCount.incrementAndGet()
                            showModal = false
                        },
                        animated = animated,
                    ) {}
                }
            }
        }
    }

    private companion object {
        const val CLOSE = "Close"
        const val FRAME_MS = 16L
    }
}
