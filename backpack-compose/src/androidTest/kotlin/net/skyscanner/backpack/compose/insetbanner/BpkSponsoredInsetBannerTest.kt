/**
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2018 - 2025 Skyscanner Ltd
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

package net.skyscanner.backpack.compose.insetbanner

import androidx.compose.runtime.Composable
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import net.skyscanner.backpack.compose.insetbanner.BpkSponsoredInsetBannerVariant.OnLight
import net.skyscanner.backpack.compose.insetbanner.internal.SPONSORED_INSET_BANNER_IMAGE_TEST_TAG
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.theme.BpkTheme
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class BpkSponsoredInsetBannerTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun givenTitleAndSubHeadline_whenRendered_thenTextsAreShown() {
        composeTestRule.setContent {
            BpkTheme {
                BpkSponsoredInsetBanner(
                    variant = OnLight,
                    backgroundColor = androidx.compose.ui.graphics.Color(0xFFFFE300),
                    title = "Title",
                    subHeadline = "subhead",
                    callToAction = BpkSponsoredInsetBannerCTA(
                        text = "Sponsored",
                        accessibilityLabel = "Sponsored accessibility label",
                        onClick = { /* no-op */ },
                    ),
                    image = null,
                    logo = { },
                )
            }
        }

        composeTestRule.onNodeWithText("Title", useUnmergedTree = true).assertIsDisplayed()
        composeTestRule.onNodeWithText("subhead", useUnmergedTree = true).assertIsDisplayed()
        composeTestRule.onNodeWithText("Sponsored", useUnmergedTree = true).assertIsDisplayed()
    }

    @Test
    fun givenImage_whenRendered_thenImageSectionIsShown() {
        val someImageComposableLambda = @Composable {
            BpkText(text = "Image")
        }
        composeTestRule.setContent {
            BpkTheme {
                BpkSponsoredInsetBanner(
                    variant = OnLight,
                    backgroundColor = androidx.compose.ui.graphics.Color(0xFFFFE300),
                    title = "Title",
                    subHeadline = "subhead",
                    callToAction = BpkSponsoredInsetBannerCTA(
                        text = "Sponsored",
                        accessibilityLabel = "Sponsored accessibility label",
                        onClick = { /* no-op */ },
                    ),
                    image = someImageComposableLambda,
                    logo = { },
                )
            }
        }

        composeTestRule.onNodeWithTag(SPONSORED_INSET_BANNER_IMAGE_TEST_TAG).assertIsDisplayed()
    }

    @Test
    fun givenNoImage_whenRendered_thenNoImageSectionIsShown() {
        composeTestRule.setContent {
            BpkTheme {
                BpkSponsoredInsetBanner(
                    variant = OnLight,
                    backgroundColor = androidx.compose.ui.graphics.Color(0xFFFFE300),
                    title = "Title",
                    subHeadline = "subhead",
                    callToAction = BpkSponsoredInsetBannerCTA(
                        text = "Sponsored",
                        accessibilityLabel = "Sponsored accessibility label",
                        onClick = { /* no-op */ },
                    ),
                    image = null,
                    logo = { },
                )
            }
        }

        composeTestRule.onNodeWithTag(SPONSORED_INSET_BANNER_IMAGE_TEST_TAG).assertDoesNotExist()
    }

    @Test
    fun whenClickOnCta_thenTheCtaActionIsExecuted() {
        var clicked = false
        composeTestRule.setContent {
            BpkTheme {
                BpkSponsoredInsetBanner(
                    variant = OnLight,
                    backgroundColor = androidx.compose.ui.graphics.Color(0xFFFFE300),
                    title = "Title",
                    subHeadline = "subhead",
                    callToAction = BpkSponsoredInsetBannerCTA(
                        text = "Sponsored",
                        accessibilityLabel = "Sponsored accessibility label",
                        onClick = {
                            clicked = true
                        },
                    ),
                    image = null,
                    logo = { },
                )
            }
        }

        composeTestRule.onNodeWithText("Sponsored", useUnmergedTree = true).performClick()

        assertEquals(true, clicked)
    }

    @Test
    fun givenSemanticInfo_whenRendered_thenSemanticsSet() {
        val label = "Sponsored accessibility label"
        val text = "Sponsored"
        composeTestRule.setContent {
            BpkTheme {
                BpkSponsoredInsetBanner(
                    variant = OnLight,
                    backgroundColor = androidx.compose.ui.graphics.Color(0xFFFFE300),
                    title = "Title",
                    subHeadline = "subhead",
                    callToAction = BpkSponsoredInsetBannerCTA(
                        text = text,
                        accessibilityLabel = label,
                        onClick = { /* no-op */ },
                    ),
                    image = null,
                    logo = { },
                )
            }
        }

        val hasRoleButton = SemanticsMatcher.expectValue(SemanticsProperties.Role, Role.Button)
        composeTestRule.onNode(
            matcher = hasContentDescription(label) and hasRoleButton,
            useUnmergedTree = false,
        ).assertExists()
        composeTestRule.onNode(matcher = hasText(text), useUnmergedTree = true)
            .assertTextEquals(text)
    }
}
