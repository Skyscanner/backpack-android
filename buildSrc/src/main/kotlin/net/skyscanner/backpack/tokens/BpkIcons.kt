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
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.MemberName
import com.squareup.kotlinpoet.PropertySpec
import java.io.File

data class BpkIcon(
  val name1: String,
  val flavors: Map<Type, String>,
) {

  enum class Type {
    Sm,
    Lg,
  }

  object Parser : BpkParser<List<File>, BpkIcons> {
    override fun invoke(files: List<File>): BpkIcons {

      val iconFiles = files.filter { it.extension == "xml" }

      return iconFiles
        .map { it.nameWithoutExtension.removeSuffix("_sm") }
        .distinct()
        .map { name ->
          BpkIcon(
            name1 = transformIconName(name),
            flavors = Type.values()
              .associateWith { type ->
                iconFiles.find {
                  when (type) {
                    Type.Sm -> it.nameWithoutExtension == "${name}_sm"
                    Type.Lg -> it.nameWithoutExtension == name
                  }
                }?.nameWithoutExtension
              }
              .filterValues { it != null }
              .let { it as Map<Type, String> },
          )
        }
    }

    private fun transformIconName(name: String): String =
      CaseFormat.UPPER_UNDERSCORE.to(
        CaseFormat.UPPER_CAMEL,
        name.removePrefix("bpk_")
          .removeSuffix("_sm")
          .replace("__", "_"),
      )
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

    data class ComposeAdaptive(val parent: ClassName, val rClass: ClassName) : Format() {
      override fun invoke(source: BpkIcons): List<PropertySpec> =
        toComposeAdaptive(parent, rClass, source)
    }
  }
}

typealias BpkIcons = List<BpkIcon>

private val PainterClass = ClassName("androidx.compose.ui.graphics.painter", "Painter")
private val ComposableAnnotation = ClassName("androidx.compose.runtime", "Composable")
private val PainterResource = MemberName("androidx.compose.ui.res", "painterResource")
private val DelegatesClass = ClassName("kotlin.properties", "Delegates")
private val SingletonMethod = MemberName("net.skyscanner.backpack.compose.utils", "singleton")
private val BpkIconClass = ClassName("net.skyscanner.backpack.compose.icon", "BpkIcon")

private fun toCompose(
  parent: ClassName,
  rClass: ClassName,
  source: BpkIcons,
  type: BpkIcon.Type,
): List<PropertySpec> {

  val receiverClass = parent.nestedClass(type.toString())

  return source
    .filter { type in it.flavors }
    .map { icon ->
      PropertySpec.builder(
        name = icon.name1,
        type = PainterClass,
      )
        .receiver(receiverClass)
        .getter(
          FunSpec
            .getterBuilder()
            .addAnnotation(ComposableAnnotation)
            .addStatement("return %M(id = %T.drawable.%N)", PainterResource, rClass, icon.flavors[type]!!)
            .build()
        )
        .build()
    }
}

private fun toComposeAdaptive(
  parent: ClassName,
  rClass: ClassName,
  source: BpkIcons,
): List<PropertySpec> {

  return source
    .filter { it.flavors.keys.containsAll(BpkIcon.Type.values().toList()) }
    .map { icon ->
      PropertySpec.builder(
        name = icon.name1,
        type = BpkIconClass,
      )
        .receiver(parent)
        .delegate(
          CodeBlock
            .builder()
            .addStatement("%T.%M(", DelegatesClass, SingletonMethod)
            .indent()
            .addStatement("%T(", BpkIconClass)
            .indent()
            .addStatement("small = %T.drawable.%N,", rClass, icon.flavors[BpkIcon.Type.Sm])
            .addStatement("large = %T.drawable.%N,", rClass, icon.flavors[BpkIcon.Type.Lg])
            .unindent()
            .addStatement(")")
            .unindent()
            .addStatement(")")
            .build()
        )
        .build()
    }
}
