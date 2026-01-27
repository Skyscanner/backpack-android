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

import net.skyscanner.backpack.tokens.BpkFormat
import net.skyscanner.backpack.tokens.BpkDimension
import net.skyscanner.backpack.tokens.BpkColor
import net.skyscanner.backpack.lint.BpkDimensionLintRules
import net.skyscanner.backpack.lint.BpkColorLintRules
import net.skyscanner.backpack.lint.BpkLintingOutput
import net.skyscanner.backpack.lint.saveToLint
import net.skyscanner.backpack.tokens.nodeFileOf
import net.skyscanner.backpack.tokens.parseAs
import net.skyscanner.backpack.tokens.readAs
import net.skyscanner.backpack.tokens.saveTo
import net.skyscanner.backpack.tokens.transformTo

tasks {
    val tokensPackage = "net.skyscanner.backpack.lint.check"
    val group = "tokens"
    val src = project.projectDir.resolve("src/main/java").path

    val source = project.nodeFileOf("@skyscanner/bpk-foundations-android", "tokens/base.raw.android.json")
        .readAs(BpkFormat.Json)

    val generateDeprecatedTokens by registering {
        this.group = group
        doLast {
            source
                .parseAs(net.skyscanner.backpack.tokens.BpkDeprecatedToken.Category)
                .transformTo(net.skyscanner.backpack.tokens.BpkDeprecatedToken.Format.Kotlin("BpkDeprecatedTokens"))
                .saveTo(net.skyscanner.backpack.tokens.BpkOutput.KotlinFile(src, tokensPackage))
                .execute()
        }
    }

    val generateSpacingLintMap by registering {
        this.group = group
        doLast {
            source
                .parseAs(BpkDimension.Category.Spacing)
                .transformTo(BpkDimensionLintRules.Format.SpacingLintDetectorMap(namespace = "BpkSpacing"))
                .saveToLint(BpkLintingOutput.KotlinFile(src, tokensPackage, "GeneratedSpacingTokenMap.kt"))
                .execute()
        }
    }

    val generateBorderRadiusLintMap by registering {
        this.group = group
        doLast {
            source
                .parseAs(BpkDimension.Category.Radii)
                .transformTo(BpkDimensionLintRules.Format.BorderRadiusLintDetectorMap(namespace = "BpkBorderRadius"))
                .saveToLint(BpkLintingOutput.KotlinFile(src, tokensPackage, "GeneratedBorderRadiusTokenMap.kt"))
                .execute()
        }
    }

    val generateColorLintMap by registering {
        this.group = group
        doLast {
            source
                .parseAs(BpkColor.LintLightMode)
                .transformTo(BpkColorLintRules.Format.ColorLintDetectorMap(namespace = "BpkTheme.colors"))
                .saveToLint(BpkLintingOutput.KotlinFile(src, tokensPackage, "GeneratedColorTokenMap.kt"))
                .execute()
        }
    }

    val generateTokens by registering {
        this.group = group
        dependsOn(generateDeprecatedTokens, generateSpacingLintMap, generateBorderRadiusLintMap, generateColorLintMap)
    }
}
