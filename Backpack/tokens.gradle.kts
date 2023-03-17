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
import com.squareup.kotlinpoet.ClassName
import net.skyscanner.backpack.tokens.BpkColor
import net.skyscanner.backpack.tokens.BpkDimension
import net.skyscanner.backpack.tokens.BpkDuration
import net.skyscanner.backpack.tokens.BpkFormat
import net.skyscanner.backpack.tokens.BpkIcon
import net.skyscanner.backpack.tokens.BpkOutput
import net.skyscanner.backpack.tokens.BpkTextStyle
import net.skyscanner.backpack.tokens.BpkTextUnit
import net.skyscanner.backpack.tokens.androidFileOf
import net.skyscanner.backpack.tokens.nodeFileOf
import net.skyscanner.backpack.tokens.parseAs
import net.skyscanner.backpack.tokens.readAs
import net.skyscanner.backpack.tokens.saveTo
import net.skyscanner.backpack.tokens.transformTo

tasks {
    val group = "tokens"

    val src = project.projectDir.resolve("src/main/res").path
    val valuesFolder = "values"
    val source = project.nodeFileOf("@skyscanner/bpk-foundations-android", "tokens/base.raw.android.json")
        .readAs(BpkFormat.Json)

    val generateElevationTokens by creating {
        this.group = group
        doLast {
            source
                .parseAs(BpkDimension.Category.Elevation)
                .transformTo(BpkDimension.Format.Xml(namespace = "bpkElevation"))
                .saveTo(BpkOutput.XmlFile(src, valuesFolder, "elevation"))
                .execute()
        }
    }

    val generateSpacingTokens by creating {
        this.group = group
        doLast {
            source
                .parseAs(BpkDimension.Category.Spacing)
                .transformTo(BpkDimension.Format.Xml(namespace = "bpkSpacing"))
                .saveTo(BpkOutput.XmlFile(src, valuesFolder, "dimensions.spacing"))
                .execute()
        }
    }

    val generateRadiiTokens by creating {
        this.group = group
        doLast {
            source
                .parseAs(BpkDimension.Category.Radii)
                .transformTo(BpkDimension.Format.Xml(namespace = "bpkBorderRadius"))
                .saveTo(BpkOutput.XmlFile(src, valuesFolder, "radii"))
                .execute()
        }
    }

    val generateBorderSizeTokens by creating {
        this.group = group
        doLast {
            source
                .parseAs(BpkDimension.Category.Border)
                .transformTo(BpkDimension.Format.Xml(namespace = "bpkBorderSize"))
                .saveTo(BpkOutput.XmlFile(src, valuesFolder, "borders"))
                .execute()
        }
    }

    val generateAnimationDurationTokens by creating {
        this.group = group
        doLast {
            source
                .parseAs(BpkDuration.Category.Animation)
                .transformTo(BpkDuration.Format.Xml(namespace = "bpkAnimationDuration"))
                .saveTo(BpkOutput.XmlFile(src, valuesFolder, "animation"))
                .execute()
        }
    }

    val generateTextSizeTokens by creating {
        this.group = group
        doLast {
            source
                .parseAs(BpkTextUnit.Category.FontSize)
                .transformTo(BpkTextUnit.Format.Xml)
                .saveTo(BpkOutput.XmlFile(src, valuesFolder, "text.size"))
                .execute()
        }
    }

    val generateTypographyTokens by creating {
        this.group = group
        doLast {
            source
                .parseAs(BpkTextStyle.Category)
                .transformTo(BpkTextStyle.Format.Xml)
                .saveTo(BpkOutput.XmlFile(src, valuesFolder, "text"))
                .execute()
        }
    }

    val generateStaticColors by creating {
        this.group = group
        doLast {
            source
                .parseAs(BpkColor.Static)
                .transformTo(BpkColor.Format.StaticXml)
                .saveTo(BpkOutput.XmlFile(src, valuesFolder, "color"))
                .execute()
        }
    }

    val generateSemanticColors by creating {
        this.group = group
        doLast {
            source
                .parseAs(BpkColor.Semantic)
                .transformTo(BpkColor.Format.SemanticXml)
                .saveTo(BpkOutput.XmlFiles(src, valuesFolder, "semantic.color"))
                .execute()
        }
    }

    val generateInternalColors by creating {
        this.group = group
        doLast {
            source
                .parseAs(BpkColor.Internal)
                .transformTo(BpkColor.Format.SemanticXml)
                .saveTo(BpkOutput.XmlFiles(src, valuesFolder, "internal.color"))
                .execute()
        }
    }

    val generateSizeTokens by creating {
        this.group = group
        dependsOn(generateElevationTokens, generateSpacingTokens, generateRadiiTokens, generateBorderSizeTokens)
    }

    val generateDurationTokens by creating {
        this.group = group
        dependsOn(generateAnimationDurationTokens)
    }

    val generateTextTokens by creating {
        this.group = group
        dependsOn(generateTextSizeTokens, generateTypographyTokens)
    }

    val generateColorTokens by creating {
        this.group = group
        dependsOn(generateStaticColors, generateSemanticColors, generateInternalColors)
    }

    val generateTokens by creating {
        this.group = group
        dependsOn(generateSizeTokens, generateColorTokens, generateTextTokens, generateDurationTokens)
    }
}
