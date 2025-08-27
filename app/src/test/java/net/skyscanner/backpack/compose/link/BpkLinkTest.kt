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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import net.skyscanner.backpack.BpkTestVariant
import net.skyscanner.backpack.Variants
import net.skyscanner.backpack.compose.BpkSnapshotTest
import net.skyscanner.backpack.compose.theme.BpkTheme
import org.junit.Test

class BpkLinkTest : BpkSnapshotTest() {

    @Test
    @Variants(BpkTestVariant.Default)
    fun simpleLinkDefault() {
        snap {
            BpkLink(
                text = "Link",
                onLinkClicked = {},
            )
        }
    }

    @Test
    @Variants(BpkTestVariant.Default)
    fun simpleLinkWithLongText() {
        snap {
            BpkLink(
                text = "This is a very long link text that should wrap properly",
                onLinkClicked = {},
            )
        }
    }

    @Test
    @Variants(BpkTestVariant.Default)
    fun mixedTextWithLinks() {
        snap {
            BpkLink(
                text = listOf(
                    TextType.RawText("This is normal text with a "),
                    TextType.LinkText("link"),
                    TextType.RawText(" in the middle and another "),
                    TextType.LinkText("link"),
                    TextType.RawText(" at the end."),
                ),
                onLinkClicked = {},
            )
        }
    }

    @Test
    @Variants(BpkTestVariant.Default)
    fun linkOnContrastBackground() {
        snap(
            background = { BpkTheme.colors.corePrimary },
        ) {
            BpkLink(
                text = "Link",
                onLinkClicked = {},
                style = BpkLinkStyle.OnContrast,
                modifier = Modifier.padding(16.dp),
            )
        }
    }

    @Test
    @Variants(BpkTestVariant.DarkMode)
    fun simpleLinkDarkMode() {
        snap {
            BpkLink(
                text = "Link",
                onLinkClicked = {},
            )
        }
    }

    @Test
    @Variants(BpkTestVariant.DarkMode)
    fun linkOnContrastDarkMode() {
        snap(
            background = { Color.DarkGray },
        ) {
            BpkLink(
                text = "Link",
                onLinkClicked = {},
                style = BpkLinkStyle.OnContrast,
                modifier = Modifier.padding(16.dp),
            )
        }
    }

    @Test
    @Variants(BpkTestVariant.Rtl)
    fun linkRtl() {
        snap {
            BpkLink(
                text = "رابط",
                onLinkClicked = {},
            )
        }
    }

    @Test
    @Variants(BpkTestVariant.Rtl)
    fun mixedTextWithLinksRtl() {
        snap {
            BpkLink(
                text = listOf(
                    TextType.RawText("هذا نص عادي مع "),
                    TextType.LinkText("رابط"),
                    TextType.RawText(" في المنتصف و "),
                    TextType.LinkText("رابط آخر"),
                    TextType.RawText(" في النهاية."),
                ),
                onLinkClicked = {},
            )
        }
    }

    @Test
    @Variants(BpkTestVariant.Default)
    fun multipleLinksParagraph() {
        snap {
            BpkLink(
                text = listOf(
                    TextType.RawText("By clicking \"Accept\" you agree to our "),
                    TextType.LinkText("Terms of Service"),
                    TextType.RawText(" and "),
                    TextType.LinkText("Privacy Policy"),
                    TextType.RawText("."),
                ),
                onLinkClicked = {},
            )
        }
    }
}
