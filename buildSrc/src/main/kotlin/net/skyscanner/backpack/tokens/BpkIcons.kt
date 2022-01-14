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

package net.skyscanner.backpack.tokens

import com.google.common.base.CaseFormat
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.MemberName
import com.squareup.kotlinpoet.PropertySpec
import java.io.File

data class BpkIcon(
  val name: String,
  val type: Type,
) {

  enum class Type {
    Sm,
    Lg,
  }

  object Parser : BpkParser<List<File>, BpkIcons> {
    override fun invoke(files: List<File>): BpkIcons =
      files
        .filter { it.extension == "xml" }
        .map {
          BpkIcon(
            name = it.nameWithoutExtension,
            type = when {
              it.nameWithoutExtension.endsWith("_sm") -> Type.Sm
              else -> Type.Lg
            }
          )
        }
  }

  sealed class Format : BpkTransformer<BpkIcons, List<PropertySpec>> {

    data class ComposeSm(val parent: ClassName, val rClass: ClassName) : Format() {
      override fun invoke(source: BpkIcons): List<PropertySpec> =
        toCompose(parent, rClass, source, Type.Sm)
    }

    data class ComposeLg(val parent: ClassName, val rClass: ClassName) : Format() {
      override fun invoke(source: BpkIcons): List<PropertySpec> =
        toCompose(parent, rClass, source, Type.Lg)
    }

  }

}

typealias BpkIcons = List<BpkIcon>

private val PainterClass = ClassName("androidx.compose.ui.graphics.painter", "Painter")
private val ComposableAnnotation = ClassName("androidx.compose.runtime", "Composable")
private val PainterResource = MemberName("androidx.compose.ui.res", "painterResource")


private fun toCompose(
  parent: ClassName,
  rClass: ClassName,
  source: BpkIcons,
  type: BpkIcon.Type,
): List<PropertySpec> {

  fun transformName(name: String): String =
    CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, name.removePrefix("bpk_").removeSuffix("_sm").replace("__", "_"))

  val receiverClass = parent.nestedClass(type.toString())

  return source
    .filter { it.type == type }
    .map { icon ->
      PropertySpec.builder(
        name = transformName(icon.name),
        type = PainterClass,
      )
        .receiver(receiverClass)
        .getter(FunSpec
          .getterBuilder()
          .addAnnotation(ComposableAnnotation)
          .addStatement("return %M(id = %T.drawable.${icon.name})", PainterResource, rClass)
          .build()
        )
        .build()
    }
}
