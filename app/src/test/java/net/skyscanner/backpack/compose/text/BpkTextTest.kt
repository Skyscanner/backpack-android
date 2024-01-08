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

package net.skyscanner.backpack.compose.text

import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import net.skyscanner.backpack.compose.BpkSnapshotTest
import net.skyscanner.backpack.BpkTestVariant
import net.skyscanner.backpack.Variants
import net.skyscanner.backpack.compose.theme.BpkTheme
import org.junit.Test

@Variants(BpkTestVariant.Default)
class BpkTextTest : BpkSnapshotTest() {

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.Rtl, BpkTestVariant.DarkMode)
    fun default() {
        snap {
            BpkText(text = "Sample")
        }
    }

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    fun colored() = snap {
        BpkText(
            text = "Sample",
            color = BpkTheme.colors.textLink,
        )
    }

    @Test
    fun styled() = snap {
        BpkText(
            text = "Sample",
            style = BpkTheme.typography.heading4,
        )
    }

    @Test
    fun annotated() = snap {
        BpkText(
            text = buildAnnotatedString {
                append("Sample ")
                withStyle(style = SpanStyle(color = BpkTheme.colors.textLink)) {
                    append("Text")
                }
            },
        )
    }

    @Test
    fun bodyDefault() = snap {
        BpkText(text = "Body Default", style = BpkTheme.typography.bodyDefault)
    }

    @Test
    fun bodyLongform() = snap {
        BpkText(text = "Body Longform", style = BpkTheme.typography.bodyLongform)
    }

    @Test
    fun footnote() = snap {
        BpkText(text = "Footnote", style = BpkTheme.typography.footnote)
    }

    @Test
    fun caption() = snap {
        BpkText(text = "Caption", style = BpkTheme.typography.caption)
    }

    @Test
    fun subheading() = snap {
        BpkText(text = "Subheading", style = BpkTheme.typography.subheading)
    }

    @Test
    fun label1() = snap {
        BpkText(text = "Label 1", style = BpkTheme.typography.label1)
    }

    @Test
    fun label2() = snap {
        BpkText(text = "Label 2", style = BpkTheme.typography.label2)
    }

    @Test
    fun label3() = snap {
        BpkText(text = "Label 3", style = BpkTheme.typography.label3)
    }

    @Test
    fun heading1() = snap {
        BpkText(text = "Heading 1", style = BpkTheme.typography.heading1)
    }

    @Test
    fun heading2() = snap {
        BpkText(text = "Heading 2", style = BpkTheme.typography.heading2)
    }

    @Test
    fun heading3() = snap {
        BpkText(text = "Heading 3", style = BpkTheme.typography.heading3)
    }

    @Test
    fun heading4() = snap {
        BpkText(text = "Heading 4", style = BpkTheme.typography.heading4)
    }

    @Test
    fun heading5() = snap {
        BpkText(text = "Heading 5", style = BpkTheme.typography.heading5)
    }

    @Test
    fun hero1() = snap {
        BpkText(text = "Hero 1", style = BpkTheme.typography.hero1)
    }

    @Test
    fun hero2() = snap {
        BpkText(text = "Hero 2", style = BpkTheme.typography.hero2)
    }

    @Test
    fun hero3() = snap {
        BpkText(text = "Hero 3", style = BpkTheme.typography.hero3)
    }

    @Test
    fun hero4() = snap {
        BpkText(text = "Hero 4", style = BpkTheme.typography.hero4)
    }

    @Test
    fun hero5() = snap {
        BpkText(text = "Hero 5", style = BpkTheme.typography.hero5)
    }
}
