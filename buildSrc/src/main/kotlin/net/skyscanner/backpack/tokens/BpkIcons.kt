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
import com.squareup.kotlinpoet.MemberName
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.asClassName
import java.io.File

data class BpkIcon(
  val name: String,
  val type: Type,
  val value: String
) {

  enum class Type {
    Sm,
    Lg,
  }

  sealed class Parser : BpkParser<List<File>, BpkIcons> {

    object Xml : Parser() {
      override fun invoke(files: List<File>): BpkIcons {

        val iconFiles = files.filter { it.extension == "xml" }

        return iconFiles
          .map { file ->
            BpkIcon(
              name = transformIconName(file.nameWithoutExtension),
              type = if (file.nameWithoutExtension.endsWith("_sm")) Type.Sm else Type.Lg,
              value = file.nameWithoutExtension
            )
          }
          .sortedBy { it.name }
      }

      private fun transformIconName(name: String): String =
        CaseFormat.UPPER_UNDERSCORE.to(
          CaseFormat.UPPER_CAMEL,
          name.removePrefix("bpk_")
            .removeSuffix("_sm")
            .replace("__", "_"),
        )
    }
  }

  sealed class Format : BpkTransformer<BpkIcons, List<PropertySpec>> {

    data class Compose(val rClass: ClassName) : Format() {
      override fun invoke(source: BpkIcons): List<PropertySpec> =
        toCompose(rClass, source)
    }
  }
}

typealias BpkIcons = List<BpkIcon>

private val DelegatesClass = ClassName("kotlin.properties", "Delegates")
private val SingletonMethod = MemberName("net.skyscanner.backpack.compose.utils", "singleton")
private val BpkIconClass = ClassName("net.skyscanner.backpack.compose.icon", "BpkIcon")
private val BpkIconReceiverClass = ClassName("net.skyscanner.backpack.compose.icon", "BpkIcon.Companion")
private val BpkIconsType = List::class.asClassName().parameterizedBy(BpkIconClass)

private fun toCompose(
  rClass: ClassName,
  source: BpkIcons,
): List<PropertySpec> = source
  .groupBy { it.name }
  .map { (name, icons) ->

    val small = icons.firstOrNull { it.type == BpkIcon.Type.Sm }?.value ?: icons.firstOrNull { it.type == BpkIcon.Type.Lg }?.value ?: error("Invalid icon format! : $name")
    val large = icons.firstOrNull { it.type == BpkIcon.Type.Lg }?.value ?: icons.firstOrNull { it.type == BpkIcon.Type.Sm }?.value ?: error("Invalid icon format! : $name")

    PropertySpec.builder(
      name = name,
      type = BpkIconClass,
    )
      .receiver(BpkIconReceiverClass)
      .delegate(
        CodeBlock
          .builder()
          .addStatement("%T.%M(", DelegatesClass, SingletonMethod)
          .indent()
          .addStatement("%T(", BpkIconClass)
          .indent()
          .addStatement("name = %S,", name)
          .addStatement("small = %T.drawable.%N,", rClass, small)
          .addStatement("large = %T.drawable.%N,", rClass, large)
          .unindent()
          .addStatement(")")
          .unindent()
          .addStatement(")")
          .build()
      )
      .build()
  }
  .plusElement(
    PropertySpec.builder(
      name = "values",
      type = BpkIconsType,
    )
      .receiver(BpkIconReceiverClass)
      .delegate(
        CodeBlock
          .builder()
          .addStatement("%T.%M(", DelegatesClass, SingletonMethod)
          .indent()
          .addStatement("listOf(", BpkIconClass)
          .indent()
          .apply {
            source.map { it.name }.distinct().forEach {
              add("%T.%N, ", BpkIconClass, it)
            }
          }
          .unindent()
          .addStatement(")")
          .unindent()
          .addStatement(")")
          .build()
      )
      .build()

  )
