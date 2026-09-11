/**
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2018 - 2026 Skyscanner Ltd
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.skyscanner.backpack.compose.nudger

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.focus.Focusability
import androidx.compose.ui.input.InputMode
import androidx.compose.ui.input.InputModeManager
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.platform.LocalInputModeManager
import androidx.compose.ui.semantics.SemanticsActions
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsFocused
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.assertIsNotFocused
import androidx.compose.ui.test.isFocusable
import androidx.compose.ui.test.junit4.v2.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performKeyInput
import androidx.compose.ui.test.performSemanticsAction
import androidx.compose.ui.test.pressKey
import net.skyscanner.backpack.compose.button.BpkButton
import net.skyscanner.backpack.compose.theme.BpkTheme
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import java.util.concurrent.atomic.AtomicInteger

class BpkNudgerAccessibilityTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun givenFocusOnDecrement_whenMinReached_thenFocusMovesToIncrement() {
        val value = setUpNudger(initialValue = MIN + 1)

        composeTestRule.onNodeWithTag(DECREMENT_TAG)
            .assert(isFocusable())
            .performSemanticsAction(SemanticsActions.RequestFocus)
            .assertIsFocused()
            .performKeyInput { pressKey(Key.Enter) }

        assertEquals(MIN, value.get())
        composeTestRule.onNodeWithTag(INCREMENT_TAG).assertIsFocused()
    }

    @Test
    fun givenFocusOnIncrement_whenMaxReached_thenFocusMovesToDecrement() {
        val value = setUpNudger(initialValue = MAX - 1)

        composeTestRule.onNodeWithTag(INCREMENT_TAG)
            .assert(isFocusable())
            .performSemanticsAction(SemanticsActions.RequestFocus)
            .assertIsFocused()
            .performKeyInput { pressKey(Key.Enter) }

        assertEquals(MAX, value.get())
        composeTestRule.onNodeWithTag(DECREMENT_TAG).assertIsFocused()
    }

    /**
     * With a range of one step the receiving button is itself disabled until this very click
     * enables it, so it has no focus target while `onClick` runs. Guards against regressing to a
     * synchronous `requestFocus()`, which would throw because the target does not exist yet.
     */
    @Test
    fun givenSingleStepRange_whenMinReached_thenFocusStillMovesToIncrement() {
        val value = setUpNudger(initialValue = 1, min = 0, max = 1)

        composeTestRule.onNodeWithTag(DECREMENT_TAG)
            .performSemanticsAction(SemanticsActions.RequestFocus)
            .assertIsFocused()
            .performKeyInput { pressKey(Key.Enter) }

        assertEquals(0, value.get())
        composeTestRule.onNodeWithTag(INCREMENT_TAG).assertIsFocused()
    }

    /**
     * In touch mode the buttons are not focusable at all, so reaching a bound by tapping must not
     * hand a touch user a focus highlight they never asked for.
     */
    @Test
    fun givenTouchMode_whenMinReached_thenNoFocusIsGranted() {
        val value = setUpNudger(initialValue = MIN + 1, emulateKeyboard = false)

        composeTestRule.onNodeWithTag(DECREMENT_TAG).performClick()

        assertEquals(MIN, value.get())
        composeTestRule.onNodeWithTag(INCREMENT_TAG).assertIsNotFocused()
    }

    /** The row overload merges semantics on its own parent, so it needs covering separately. */
    @Test
    fun givenRowVariant_whenMinReached_thenFocusMovesToIncrement() {
        val value = setUpNudger(initialValue = MIN + 1, title = "Adults")

        composeTestRule.onNodeWithTag(DECREMENT_TAG)
            .performSemanticsAction(SemanticsActions.RequestFocus)
            .assertIsFocused()
            .performKeyInput { pressKey(Key.Enter) }

        assertEquals(MIN, value.get())
        composeTestRule.onNodeWithTag(INCREMENT_TAG).assertIsFocused()
    }

    /**
     * With an empty range both buttons are permanently disabled, so there is nowhere to move focus
     * to. Documents that this degenerate case renders without crashing.
     */
    @Test
    fun givenMinEqualsMax_thenBothButtonsAreDisabled() {
        setUpNudger(initialValue = 0, min = 0, max = 0)

        composeTestRule.onNodeWithTag(DECREMENT_TAG).assertIsNotEnabled()
        composeTestRule.onNodeWithTag(INCREMENT_TAG).assertIsNotEnabled()
    }

    /**
     * [BpkButton]'s focus target is [Focusability.SystemDefined], so it can only be focused outside
     * of touch input mode. Emulating a hardware keyboard is therefore a precondition for the focus
     * assertions - in touch mode the buttons are not focusable at all.
     */
    private fun setUpNudger(
        initialValue: Int,
        min: Int = MIN,
        max: Int = MAX,
        title: String? = null,
        emulateKeyboard: Boolean = true,
    ): AtomicInteger {
        val value = AtomicInteger(initialValue)
        lateinit var inputModeManager: InputModeManager
        composeTestRule.setContent {
            inputModeManager = LocalInputModeManager.current
            BpkTheme {
                var current by remember { mutableIntStateOf(initialValue) }
                val onValueChange: (Int) -> Unit = {
                    current = it
                    value.set(it)
                }
                if (title == null) {
                    BpkNudger(
                        value = current,
                        onValueChange = onValueChange,
                        min = min,
                        max = max,
                        testTag = NUDGER_TEST_TAG,
                    )
                } else {
                    BpkNudger(
                        value = current,
                        onValueChange = onValueChange,
                        min = min,
                        max = max,
                        title = title,
                        subtitle = "Aged 16+",
                        testTag = NUDGER_TEST_TAG,
                    )
                }
            }
        }
        composeTestRule.waitForIdle()
        if (emulateKeyboard) {
            composeTestRule.runOnUiThread { inputModeManager.requestInputMode(InputMode.Keyboard) }
            composeTestRule.waitForIdle()
        }
        return value
    }

    private companion object {
        const val MIN = 0
        const val MAX = 10
        const val NUDGER_TEST_TAG = "nudger"
        const val DECREMENT_TAG = "${NUDGER_TEST_TAG}Decrement"
        const val INCREMENT_TAG = "${NUDGER_TEST_TAG}Increment"
    }
}
