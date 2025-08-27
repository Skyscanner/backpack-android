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

package net.skyscanner.backpack.compose.link

import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import net.skyscanner.backpack.BpkTestVariant
import net.skyscanner.backpack.Variants
import net.skyscanner.backpack.compose.BpkSnapshotTest
import net.skyscanner.backpack.compose.theme.BpkTheme
import org.junit.Test

class BpkLinkTest : BpkSnapshotTest() {

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    fun simpleLinkDefault() {
        snap {
            BpkLink(
                text = "[Link](https://skyscanner.net)",
                onLinkClicked = { _ -> },
            )
        }
    }

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    fun simpleLinkOnContrast() {
        snap(
            background = { BpkTheme.colors.corePrimary },
        ) {
            BpkLink(
                text = "[Link](https://skyscanner.net)",
                style = BpkLinkStyle.OnContrast,
                onLinkClicked = { _ -> },
                modifier = Modifier.padding(16.dp),
            )
        }
    }

    @Test
    @Variants(BpkTestVariant.Default)
    fun linkWithTextStyles() {
        snap {
            BpkLink(
                text = "[£1,367](https://deals.com/flight)",
                textStyle = BpkTheme.typography.heading4,
                onLinkClicked = { _ -> },
            )
        }
    }

    @Test
    @Variants(BpkTestVariant.Default)
    fun sentenceWithTwoLinks() {
        snap {
            BpkLink(
                segments = buildTextSegments(autoSpace = true) {
                    text("By clicking Accept you agree to our ")
                    link(
                        text = "Terms of Service",
                        url = "https://example.com/terms",
                    )
                    text(" and ")
                    link(
                        text = "Privacy Policy",
                        url = "https://example.com/privacy",
                    )
                },
                onLinkClicked = { _ -> },
            )
        }
    }
}
