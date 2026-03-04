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

package net.skyscanner.backpack.compose.graphicpromotion

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.performClick
import net.skyscanner.backpack.compose.theme.BpkTheme
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class BpkGraphicPromoTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private fun renderGraphicPromoWithSponsor(
        ctaLabel: String = CTA_LABEL,
        onCtaClick: () -> Unit = { },
    ) {
        composeTestRule.setContent {
            BpkTheme {
                BpkGraphicPromo(
                    headline = "Headline",
                    sponsor = BpkGraphicsPromoSponsor(
                        title = "Sponsored",
                        logo = "logo",
                        accessibilityLabel = "Sponsored",
                        callToAction = BpkGraphicPromoSponsorCTA(
                            accessibilityLabel = ctaLabel,
                            onClick = onCtaClick,
                        ),
                    ),
                    image = { Box(Modifier.fillMaxSize()) },
                    sponsorLogo = { },
                )
            }
        }
    }

    @Test
    fun givenSponsorWithCta_whenRendered_thenCtaIconHasCorrectSemantics() {
        // When
        renderGraphicPromoWithSponsor()

        // Then
        val hasRoleButton = SemanticsMatcher.expectValue(SemanticsProperties.Role, Role.Button)
        composeTestRule.onNode(
            matcher = hasContentDescription(CTA_LABEL) and hasRoleButton,
            useUnmergedTree = true,
        ).assertExists()
            .assertHasClickAction()
    }

    @Test
    fun givenSponsorWithCta_whenCtaClicked_thenOnClickIsExecuted() {
        // Given
        var clicked = false
        renderGraphicPromoWithSponsor(onCtaClick = { clicked = true })

        // When
        composeTestRule.onNode(
            matcher = hasContentDescription(CTA_LABEL),
            useUnmergedTree = true,
        ).performClick()

        // Then
        assertEquals(true, clicked)
    }

    private companion object {
        const val CTA_LABEL = "Learn more about our sponsor"
    }
}
