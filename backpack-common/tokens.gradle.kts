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
  val src = project.projectDir.resolve("src/main/res/drawable-nodpi").path
  val source = project.nodeFileOf("@skyscanner/bpk-svgs", "dist/svgs/icons")
    .readAs(BpkFormat.Folder)

  val generateIcons by creating {
    this.group = group
    source
      .parseAs(BpkIcon.Parser.Svg)
      .transformTo(BpkIcon.Format.Xml)
      .saveTo(BpkOutput.XmlIconFiles(src))
      .execute()
  }

  val generateTokens by creating {
    this.group = group
    dependsOn(generateIcons)
  }
}
