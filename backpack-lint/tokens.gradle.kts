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
import net.skyscanner.backpack.tokens.BpkFormat
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

    val generateDeprecatedTokens by creating {
        this.group = group
        doLast {
            source
                .parseAs(net.skyscanner.backpack.tokens.BpkDeprecatedToken.Category)
                .transformTo(net.skyscanner.backpack.tokens.BpkDeprecatedToken.Format.Kotlin("BpkDeprecatedTokens"))
                .saveTo(net.skyscanner.backpack.tokens.BpkOutput.KotlinFile(src, tokensPackage))
                .execute()
        }
    }

    val generateTokens by creating {
        this.group = group
        dependsOn(generateDeprecatedTokens)
    }
}
