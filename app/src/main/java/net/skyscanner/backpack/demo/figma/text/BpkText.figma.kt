/*
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

package net.skyscanner.backpack.demo.figma.text

import androidx.compose.runtime.Composable
import com.figma.code.connect.FigmaConnect
import com.figma.code.connect.FigmaProperty
import com.figma.code.connect.FigmaType
import com.figma.code.connect.FigmaVariant
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.theme.BpkTheme

@FigmaConnect("FIGMA_TEXT")
@FigmaVariant(key = "Style", value = "Hero 1")
class BpkTextHero1CodeConnect {
    @FigmaProperty(FigmaType.Text, "Text Prop")
    val text: String = "Sample text"

    @Composable
    fun TextExample() {
        BpkText(
            text = text,
            style = BpkTheme.typography.hero1,
        )
    }
}

@FigmaConnect("FIGMA_TEXT")
@FigmaVariant(key = "Style", value = "Hero 2")
class BpkTextHero2CodeConnect {
    @FigmaProperty(FigmaType.Text, "Text Prop")
    val text: String = "Sample text"

    @Composable
    fun TextExample() {
        BpkText(
            text = text,
            style = BpkTheme.typography.hero2,
        )
    }
}

@FigmaConnect("FIGMA_TEXT")
@FigmaVariant(key = "Style", value = "Hero 3")
class BpkTextHero3CodeConnect {
    @FigmaProperty(FigmaType.Text, "Text Prop")
    val text: String = "Sample text"

    @Composable
    fun TextExample() {
        BpkText(
            text = text,
            style = BpkTheme.typography.hero3,
        )
    }
}

@FigmaConnect("FIGMA_TEXT")
@FigmaVariant(key = "Style", value = "Hero 4")
class BpkTextHero4CodeConnect {
    @FigmaProperty(FigmaType.Text, "Text Prop")
    val text: String = "Sample text"

    @Composable
    fun TextExample() {
        BpkText(
            text = text,
            style = BpkTheme.typography.hero4,
        )
    }
}

@FigmaConnect("FIGMA_TEXT")
@FigmaVariant(key = "Style", value = "Hero 5")
class BpkTextHero5CodeConnect {
    @FigmaProperty(FigmaType.Text, "Text Prop")
    val text: String = "Sample text"

    @Composable
    fun TextExample() {
        BpkText(
            text = text,
            style = BpkTheme.typography.hero5,
        )
    }
}

@FigmaConnect("FIGMA_TEXT")
@FigmaVariant(key = "Style", value = "Headline 1")
class BpkTextHeading1CodeConnect {
    @FigmaProperty(FigmaType.Text, "Text Prop")
    val text: String = "Sample text"

    @Composable
    fun TextExample() {
        BpkText(
            text = text,
            style = BpkTheme.typography.heading1,
        )
    }
}

@FigmaConnect("FIGMA_TEXT")
@FigmaVariant(key = "Style", value = "Headline 2")
class BpkTextHeading2CodeConnect {
    @FigmaProperty(FigmaType.Text, "Text Prop")
    val text: String = "Sample text"

    @Composable
    fun TextExample() {
        BpkText(
            text = text,
            style = BpkTheme.typography.heading2,
        )
    }
}

@FigmaConnect("FIGMA_TEXT")
@FigmaVariant(key = "Style", value = "Headline 3")
class BpkTextHeading3CodeConnect {
    @FigmaProperty(FigmaType.Text, "Text Prop")
    val text: String = "Sample text"

    @Composable
    fun TextExample() {
        BpkText(
            text = text,
            style = BpkTheme.typography.heading3,
        )
    }
}

@FigmaConnect("FIGMA_TEXT")
@FigmaVariant(key = "Style", value = "Headline 4")
class BpkTextHeading4CodeConnect {
    @FigmaProperty(FigmaType.Text, "Text Prop")
    val text: String = "Sample text"

    @Composable
    fun TextExample() {
        BpkText(
            text = text,
            style = BpkTheme.typography.heading4,
        )
    }
}

@FigmaConnect("FIGMA_TEXT")
@FigmaVariant(key = "Style", value = "Headline 5")
class BpkTextHeading5CodeConnect {
    @FigmaProperty(FigmaType.Text, "Text Prop")
    val text: String = "Sample text"

    @Composable
    fun TextExample() {
        BpkText(
            text = text,
            style = BpkTheme.typography.heading5,
        )
    }
}

@FigmaConnect("FIGMA_TEXT")
@FigmaVariant(key = "Style", value = "Subheading")
class BpkTextSubheadingCodeConnect {
    @FigmaProperty(FigmaType.Text, "Text Prop")
    val text: String = "Sample text"

    @Composable
    fun TextExample() {
        BpkText(
            text = text,
            style = BpkTheme.typography.subheading,
        )
    }
}

@FigmaConnect("FIGMA_TEXT")
@FigmaVariant(key = "Style", value = "Body Default")
class BpkTextBodyDefaultCodeConnect {
    @FigmaProperty(FigmaType.Text, "Text Prop")
    val text: String = "Sample text"

    @Composable
    fun TextExample() {
        BpkText(
            text = text,
            style = BpkTheme.typography.bodyDefault,
        )
    }
}

@FigmaConnect("FIGMA_TEXT")
@FigmaVariant(key = "Style", value = "Body Longform")
class BpkTextBodyLongformCodeConnect {
    @FigmaProperty(FigmaType.Text, "Text Prop")
    val text: String = "Sample text"

    @Composable
    fun TextExample() {
        BpkText(
            text = text,
            style = BpkTheme.typography.bodyLongform,
        )
    }
}

@FigmaConnect("FIGMA_TEXT")
@FigmaVariant(key = "Style", value = "Body Footnote")
class BpkTextFootnoteCodeConnect {
    @FigmaProperty(FigmaType.Text, "Text Prop")
    val text: String = "Sample text"

    @Composable
    fun TextExample() {
        BpkText(
            text = text,
            style = BpkTheme.typography.footnote,
        )
    }
}

@FigmaConnect("FIGMA_TEXT")
@FigmaVariant(key = "Style", value = "Body Caption")
class BpkTextCaptionCodeConnect {
    @FigmaProperty(FigmaType.Text, "Text Prop")
    val text: String = "Sample text"

    @Composable
    fun TextExample() {
        BpkText(
            text = text,
            style = BpkTheme.typography.caption,
        )
    }
}

@FigmaConnect("FIGMA_TEXT")
@FigmaVariant(key = "Style", value = "Label 1")
class BpkTextLabel1CodeConnect {
    @FigmaProperty(FigmaType.Text, "Text Prop")
    val text: String = "Sample text"

    @Composable
    fun TextExample() {
        BpkText(
            text = text,
            style = BpkTheme.typography.label1,
        )
    }
}

@FigmaConnect("FIGMA_TEXT")
@FigmaVariant(key = "Style", value = "Label 2")
class BpkTextLabel2CodeConnect {
    @FigmaProperty(FigmaType.Text, "Text Prop")
    val text: String = "Sample text"

    @Composable
    fun TextExample() {
        BpkText(
            text = text,
            style = BpkTheme.typography.label2,
        )
    }
}

@FigmaConnect("FIGMA_TEXT")
@FigmaVariant(key = "Style", value = "Label 3")
class BpkTextLabel3CodeConnect {
    @FigmaProperty(FigmaType.Text, "Text Prop")
    val text: String = "Sample text"

    @Composable
    fun TextExample() {
        BpkText(
            text = text,
            style = BpkTheme.typography.label3,
        )
    }
}
