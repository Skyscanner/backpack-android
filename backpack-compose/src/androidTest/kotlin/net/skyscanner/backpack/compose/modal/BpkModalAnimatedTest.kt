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

import android.view.Window
import android.view.WindowManager
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import androidx.compose.ui.window.DialogWindowProvider
import net.skyscanner.backpack.compose.navigationbar.NavIcon
import net.skyscanner.backpack.compose.theme.BpkTheme
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
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
        // Just before the exit animation ends, onDismiss must not have been called yet.
        composeTestRule.mainClock.advanceTimeBy(ModalAnimationDurationMs - BOUNDARY_MARGIN_MS)
        assertEquals(0, dismissCount.get())

        // Just after the exit animation ends, onDismiss must have been called exactly once.
        composeTestRule.mainClock.advanceTimeBy(BOUNDARY_MARGIN_MS * 2)
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

    @Test
    fun givenDimBackgroundIsDefault_whenShown_thenWindowDimsBehind() {
        setContent(animated = true)

        assertTrue(dialogDimsBehind())
    }

    @Test
    fun givenDimBackgroundIsFalse_whenShown_thenWindowDoesNotDimBehind() {
        setContent(animated = true, dimBackground = false)

        assertFalse(dialogDimsBehind())
    }

    @Test
    fun givenDimBackgroundChangesBackToTrue_whenRecomposed_thenWindowDimsBehindAgain() {
        setContent(animated = true, dimBackground = false)

        dimBackgroundState.value = true
        composeTestRule.waitForIdle()

        assertTrue(dialogDimsBehind())
    }

    @Test
    fun givenNotAnimated_whenShown_thenWindowAnimationsAreDisabled() {
        setContent(animated = false)

        assertEquals(0, dialogWindowAnimations())
    }

    @Test
    fun givenAnimatedChangesBackToTrue_whenRecomposed_thenDefaultWindowAnimationsAreRestored() {
        setContent(animated = true)
        val defaultWindowAnimations = dialogWindowAnimations()

        animatedState.value = false
        composeTestRule.waitForIdle()
        assertEquals(0, dialogWindowAnimations())

        animatedState.value = true
        composeTestRule.waitForIdle()
        assertEquals(defaultWindowAnimations, dialogWindowAnimations())
    }

    private var dialogWindow: Window? = null
    private val animatedState = mutableStateOf(true)
    private val dimBackgroundState = mutableStateOf(true)

    private fun dialogDimsBehind(): Boolean = readDialogWindow { attributes.flags and WindowManager.LayoutParams.FLAG_DIM_BEHIND != 0 }

    private fun dialogWindowAnimations(): Int = readDialogWindow { attributes.windowAnimations }

    private fun <T> readDialogWindow(read: Window.() -> T): T {
        var result: T? = null
        composeTestRule.runOnIdle {
            val window = checkNotNull(dialogWindow) { "The modal's dialog window was not composed" }
            result = window.read()
        }
        @Suppress("UNCHECKED_CAST")
        return result as T
    }

    private fun setContent(animated: Boolean, dimBackground: Boolean = true) {
        animatedState.value = animated
        dimBackgroundState.value = dimBackground
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
                        animated = animatedState.value,
                        dimBackground = dimBackgroundState.value,
                    ) {
                        dialogWindow = (LocalView.current.parent as? DialogWindowProvider)?.window
                    }
                }
            }
        }
    }

    private companion object {
        const val CLOSE = "Close"
        // Compose test clock frame interval; the clock is virtual so this is deterministic across devices.
        const val FRAME_MS = 16L
        const val BOUNDARY_MARGIN_MS = FRAME_MS * 2
    }
}
