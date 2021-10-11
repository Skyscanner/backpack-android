/**
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2018-2021 Skyscanner Ltd
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
import net.skyscanner.backpack.tokens.BpkDimension
import net.skyscanner.backpack.tokens.BpkFormat
import net.skyscanner.backpack.tokens.BpkOutput
import net.skyscanner.backpack.tokens.nodeFileOf
import net.skyscanner.backpack.tokens.parseAs
import net.skyscanner.backpack.tokens.readAs
import net.skyscanner.backpack.tokens.saveTo
import net.skyscanner.backpack.tokens.transformTo

tasks {

  val tokensPackage = "net.skyscanner.backpack.compose.tokens"
  val group = "tokens"
  val src = project.projectDir.resolve("src/main/kotlin").path

  val source = project.nodeFileOf("@skyscanner/bpk-foundations-android", "tokens/base.raw.android.json")
    .readAs(BpkFormat.Json)

  val generateElevationTokens by creating {
    this.group = group
    doLast {
      source
        .parseAs(BpkDimension.Category.Elevation)
        .transformTo(BpkDimension.Format.Compose(namespace = "BpkElevation"))
        .saveTo(BpkOutput.KotlinFile(src, tokensPackage))
        .execute()
    }
  }

  val generateSpacingTokens by creating {
    this.group = group
    doLast {
      source
        .parseAs(BpkDimension.Category.Spacing)
        .transformTo(BpkDimension.Format.Compose(namespace = "BpkSpacing"))
        .saveTo(BpkOutput.KotlinFile(src, tokensPackage))
        .execute()
    }
  }

  val generateRadiiTokens by creating {
    this.group = group
    doLast {
      source
        .parseAs(BpkDimension.Category.Radii)
        .transformTo(BpkDimension.Format.Compose(namespace = "BpkBorderRadius"))
        .saveTo(BpkOutput.KotlinFile(src, tokensPackage))
        .execute()
    }
  }

  val generateSizeTokens by creating {
    this.group = group
    dependsOn(generateElevationTokens, generateSpacingTokens, generateRadiiTokens)
  }

  val generateTokens by creating {
    this.group = group
    dependsOn(generateSizeTokens)
  }
}
