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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.performClick
import net.skyscanner.backpack.compose.theme.BpkTheme
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class BpkGraphicPromoTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private fun renderGraphicPromo(
        headline: String = HEADLINE,
        kicker: String? = null,
        subHeadline: String? = null,
        sponsor: BpkGraphicsPromoSponsor? = null,
        sponsorLogo: (@Composable () -> Unit)? = null,
    ) {
        composeTestRule.setContent {
            BpkTheme {
                BpkGraphicPromo(
                    headline = headline,
                    kicker = kicker,
                    subHeadline = subHeadline,
                    sponsor = sponsor,
                    sponsorLogo = sponsorLogo,
                    image = { Box(Modifier.fillMaxSize()) },
                )
            }
        }
    }

    // region Sponsor CTA tests

    @Test
    fun givenSponsorWithCta_whenRendered_thenCtaIconHasCorrectSemantics() {
        // When
        renderGraphicPromo(sponsor = defaultSponsor(), sponsorLogo = { })

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

        // When
        renderGraphicPromo(
            sponsor = defaultSponsor(onCtaClick = { clicked = true }),
            sponsorLogo = { },
        )
        composeTestRule.onNode(
            matcher = hasContentDescription(CTA_LABEL),
            useUnmergedTree = true,
        ).performClick()

        // Then
        assertEquals(true, clicked)
    }

    // endregion

    // region Accessibility - no text duplication tests

    @Test
    fun givenPromoWithKickerAndSubHeadline_whenRendered_thenMergedTreeHasOnlyRootContentDescription() {
        // When
        renderGraphicPromo(
            headline = HEADLINE,
            kicker = KICKER,
            subHeadline = SUB_HEADLINE,
        )

        // Then - merged tree should expose only the root content description
        val expectedContentDescription = "$KICKER, $HEADLINE, $SUB_HEADLINE"
        composeTestRule.onNode(
            matcher = hasContentDescription(expectedContentDescription),
            useUnmergedTree = false,
        ).assertExists()

        // Text nodes should not be visible in merged tree (would cause TalkBack duplication)
        composeTestRule.onNode(
            matcher = hasText(HEADLINE),
            useUnmergedTree = false,
        ).assertDoesNotExist()
    }

    @Test
    fun givenSponsoredPromo_whenRendered_thenMergedTreeHasOnlyRootContentDescription() {
        // When
        renderGraphicPromo(sponsor = defaultSponsor(), sponsorLogo = { })

        // Then - merged tree should expose only the root content description
        val expectedContentDescription = "$HEADLINE, $SPONSOR_ACCESSIBILITY_LABEL"
        composeTestRule.onNode(
            matcher = hasContentDescription(expectedContentDescription),
            useUnmergedTree = false,
        ).assertExists()

        // Sponsor title text should not be visible in merged tree (would cause TalkBack duplication)
        composeTestRule.onNode(
            matcher = hasText(SPONSOR_TITLE),
            useUnmergedTree = false,
        ).assertDoesNotExist()
    }

    @Test
    fun givenSponsoredPromo_whenRendered_thenCtaIconIsStillFocusableInMergedTree() {
        // When
        renderGraphicPromo(sponsor = defaultSponsor(), sponsorLogo = { })

        // Then - CTA icon should be independently focusable in merged tree
        val hasRoleButton = SemanticsMatcher.expectValue(SemanticsProperties.Role, Role.Button)
        composeTestRule.onNode(
            matcher = hasContentDescription(CTA_LABEL) and hasRoleButton,
            useUnmergedTree = false,
        ).assertExists()
            .assertHasClickAction()
    }

    // endregion

    private fun defaultSponsor(
        onCtaClick: () -> Unit = { },
    ) = BpkGraphicsPromoSponsor(
        title = SPONSOR_TITLE,
        logo = "logo",
        accessibilityLabel = SPONSOR_ACCESSIBILITY_LABEL,
        callToAction = BpkGraphicPromoSponsorCTA(
            accessibilityLabel = CTA_LABEL,
            onClick = onCtaClick,
        ),
    )

    private companion object {
        const val HEADLINE = "Three Parks Challenge"
        const val KICKER = "Travel tips"
        const val SUB_HEADLINE = "How to complete the climb in 3 days"
        const val SPONSOR_TITLE = "Sponsored"
        const val SPONSOR_ACCESSIBILITY_LABEL = "Sponsored"
        const val CTA_LABEL = "Learn more about our sponsor"
    }
}
