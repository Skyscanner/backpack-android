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

package net.skyscanner.backpack.compose.switch

import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.InputMode
import androidx.compose.ui.input.InputModeManager
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.platform.LocalInputModeManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.SemanticsActions
import androidx.compose.ui.test.assertIsFocused
import androidx.compose.ui.test.junit4.v2.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performKeyInput
import androidx.compose.ui.test.performSemanticsAction
import androidx.compose.ui.test.pressKey
import androidx.compose.ui.unit.dp
import net.skyscanner.backpack.compose.theme.BpkTheme
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import java.util.concurrent.atomic.AtomicBoolean

class BpkSwitchAccessibilityTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    /**
     * Regresses TEMPURA-4461: the outer Row's toggleable() and Material3 Switch's own toggleable()
     * used to each register a focus target, so an external keyboard's Tab traversal stopped on the
     * same switch twice instead of once.
     */
    @Test
    fun givenExternalKeyboard_whenTabbingPastSwitch_thenItIsOnlyOneFocusStop() {
        setUpSwitch()

        composeTestRule.onNodeWithTag(BEFORE_TAG)
            .performSemanticsAction(SemanticsActions.RequestFocus)
            .assertIsFocused()
            .performKeyInput { pressKey(Key.Tab) }
        composeTestRule.onNodeWithTag(SWITCH_TAG).assertIsFocused()

        composeTestRule.onNodeWithTag(SWITCH_TAG).performKeyInput { pressKey(Key.Tab) }
        composeTestRule.onNodeWithTag(AFTER_TAG).assertIsFocused()
    }

    @Test
    fun givenFocusedSwitch_whenEnterPressed_thenItStillToggles() {
        val checked = setUpSwitch()

        composeTestRule.onNodeWithTag(SWITCH_TAG)
            .performSemanticsAction(SemanticsActions.RequestFocus)
            .assertIsFocused()
            .performKeyInput { pressKey(Key.Enter) }

        assertTrue(checked.get())
    }

    private fun setUpSwitch(): AtomicBoolean {
        val checked = AtomicBoolean(false)
        lateinit var inputModeManager: InputModeManager
        composeTestRule.setContent {
            inputModeManager = LocalInputModeManager.current
            BpkTheme {
                var current by remember { mutableStateOf(false) }
                Column {
                    Box(modifier = Modifier.size(1.dp).focusable().testTag(BEFORE_TAG))
                    BpkSwitch(
                        text = "Notifications",
                        checked = current,
                        onCheckedChange = {
                            current = it
                            checked.set(it)
                        },
                        modifier = Modifier.testTag(SWITCH_TAG),
                    )
                    Box(modifier = Modifier.size(1.dp).focusable().testTag(AFTER_TAG))
                }
            }
        }
        composeTestRule.waitForIdle()
        composeTestRule.runOnUiThread { inputModeManager.requestInputMode(InputMode.Keyboard) }
        composeTestRule.waitForIdle()
        return checked
    }

    private companion object {
        const val BEFORE_TAG = "before"
        const val SWITCH_TAG = "switch"
        const val AFTER_TAG = "after"
    }
}
