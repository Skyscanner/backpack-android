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

import com.squareup.kotlinpoet.ClassName
import net.skyscanner.backpack.tokens.BpkColor
import net.skyscanner.backpack.tokens.BpkDimension
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
    val tokensPackage = "net.skyscanner.backpack.compose.tokens"
    val rClass = ClassName("net.skyscanner.backpack.compose", "R")
    val group = "tokens"
    val src = project.projectDir.resolve("src/main/kotlin").path

    val source = project.nodeFileOf("@skyscanner/bpk-foundations-android", "tokens/base.raw.android.json")
        .readAs(BpkFormat.Json)


    // VDL2 Typography tokens - manual additions until available in foundations package
    val vdlLetterSpacingTokens = mapOf(
        "VDL_HERO" to -0.03, // -1.2px at 40px size ≈ -0.03em
        "VDL_HEADING_1" to -0.03, // -1.2px at 40px size ≈ -0.03em
        "VDL_HEADING_2" to -0.025, // -1.0px at 40px size ≈ -0.025em
        "VDL_HEADING_3" to -0.02 // -0.6px at 30px size ≈ -0.02em
    )

    val generateElevationTokens by registering {
        this.group = group
        doLast {
            source
                .parseAs(BpkDimension.Category.Elevation)
                .transformTo(BpkDimension.Format.Compose(namespace = "BpkElevation"))
                .saveTo(BpkOutput.KotlinFile(src, tokensPackage))
                .execute()
        }
    }

    val generateSpacingTokens by registering {
        this.group = group
        doLast {
            source
                .parseAs(BpkDimension.Category.Spacing)
                .transformTo(BpkDimension.Format.Compose(namespace = "BpkSpacing"))
                .saveTo(BpkOutput.KotlinFile(src, tokensPackage))
                .execute()
        }
    }

    val generateRadiiTokens by registering {
        this.group = group
        doLast {
            source
                .parseAs(BpkDimension.Category.Radii)
                .transformTo(BpkDimension.Format.Compose(namespace = "BpkBorderRadius"))
                .saveTo(BpkOutput.KotlinFile(src, tokensPackage))
                .execute()
        }
    }

    val generateBorderSizeTokens by registering {
        this.group = group
        doLast {
            source
                .parseAs(BpkDimension.Category.Border)
                .transformTo(BpkDimension.Format.Compose(namespace = "BpkBorderSize"))
                .saveTo(BpkOutput.KotlinFile(src, tokensPackage))
                .execute()
        }
    }

    val generateFontSizeTokens by registering {
        this.group = group
        doLast {
            source
                .parseAs(BpkTextUnit.Category.FontSize)
                .transformTo(BpkTextUnit.Format.Compose(namespace = "BpkFontSize", internal = true))
                .saveTo(BpkOutput.KotlinFile(src, tokensPackage))
                .execute()
        }
    }

    val generateLetterSpacingTokens by registering {
        this.group = group
        doLast {
            source
                .parseAs(BpkTextUnit.Category.LetterSpacing)
                .transformTo(BpkTextUnit.Format.Compose(namespace = "BpkLetterSpacing", internal = true, customTokens = vdlLetterSpacingTokens))
                .saveTo(BpkOutput.KotlinFile(src, tokensPackage))
                .execute()
        }
    }

    val generateLineHeightTokens by registering {
        this.group = group
        doLast {
            source
                .parseAs(BpkTextUnit.Category.LineHeight)
                .transformTo(BpkTextUnit.Format.Compose(namespace = "BpkLineHeight", internal = true))
                .saveTo(BpkOutput.KotlinFile(src, tokensPackage))
                .execute()
        }
    }

    val generateTypographyTokens by registering {
        this.group = group
        doLast {
            source
                .parseAs(BpkTextStyle.Category)
                .transformTo(BpkTextStyle.Format.Compose(className = "BpkTypography"))
                .saveTo(BpkOutput.KotlinFile(src, tokensPackage))
                .execute()
        }
    }

    // VDL2 companion object is now automatically included in BpkTypography
    // No separate task needed

    val generateSemanticColors by registering {
        this.group = group
        doLast {
            source
                .parseAs(BpkColor.Semantic)
                .transformTo(BpkColor.Format.SemanticCompose(className = "BpkColors"))
                .saveTo(BpkOutput.KotlinFile(src, tokensPackage))
                .execute()
        }
    }

    val generateInternalColors by registering {
        this.group = group
        doLast {
            source
                .parseAs(BpkColor.Internal)
                .transformTo(BpkColor.Format.InternalCompose)
                .saveTo(BpkOutput.KotlinFiles(src, tokensPackage + ".internal"))
                .execute()
        }
    }

    val generateSizeTokens by registering {
        this.group = group
        dependsOn(generateElevationTokens, generateSpacingTokens, generateRadiiTokens, generateBorderSizeTokens)
    }

    val generateTextTokens by registering {
        this.group = group
        dependsOn(generateFontSizeTokens, generateLetterSpacingTokens, generateLineHeightTokens, generateTypographyTokens)
    }

    val generateColorTokens by registering {
        this.group = group
        dependsOn(generateSemanticColors, generateInternalColors)
    }

    val generateIcons by registering {
        this.group = group
        doLast {
            project.androidFileOf("backpack-common", "src/main/res/drawable-nodpi")
                .readAs(BpkFormat.Folder)
                .parseAs(BpkIcon.Parser.Xml)
                .transformTo(BpkIcon.Format.Compose(rClass))
                .saveTo(BpkOutput.KotlinExtensionFile(src, tokensPackage, "BpkIcon"))
                .execute()
        }
    }

    val generateTokens by registering {
        this.group = group
        dependsOn(generateSizeTokens, generateColorTokens, generateTextTokens, generateIcons)
    }
}
