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

package net.skyscanner.backpack.compose.button.internal

import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import net.skyscanner.backpack.compose.theme.BpkTheme
import org.junit.Rule
import org.junit.Test

class BpkButtonImplTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun givenContentDescriptionNotNull_whenBpkButton_thenSemanticsSet() {
        composeTestRule.setContent {
            BpkTheme {
                BpkButtonImpl(
                    enabled = true,
                    loading = false,
                    contentDescription = "Custom content description",
                    onClick = { },
                    content = { },
                )
            }
        }

        composeTestRule
            .onNodeWithContentDescription("Custom content description")
            .assertExists()

        composeTestRule
            .onNode(SemanticsMatcher.expectValue(SemanticsProperties.Role, Role.Button))
            .assertIsEnabled()
            .assertHasClickAction()
            .assertExists()
    }
}