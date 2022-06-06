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
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec
import java.io.File

data class BpkIcon(
  val name: String,
  val types: Map<Type, String>,
  val autoMirror: Boolean,
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
            name = transformIconName(name),
            types = Type.values()
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
            autoMirror = files
              .filter { it.nameWithoutExtension.startsWith(name) }
              .any { it.readText().contains("android:automirrored=\"true\"", ignoreCase = true) },
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

  sealed class Format : BpkTransformer<BpkIcons, TypeSpec> {

    data class Compose(val namespace: String, val rClass: ClassName) : Format() {
      override fun invoke(source: BpkIcons): TypeSpec =
        toCompose(namespace, rClass, source)
    }
  }
}

typealias BpkIcons = List<BpkIcon>

private fun toCompose(
  namespace: String,
  rClass: ClassName,
  source: BpkIcons,
): TypeSpec =
  TypeSpec
    .enumBuilder(namespace)
    .addProperty("small", Int::class, KModifier.INTERNAL, KModifier.ABSTRACT)
    .addProperty("large", Int::class, KModifier.INTERNAL, KModifier.ABSTRACT)
    .addProperty(
      PropertySpec.builder("autoMirror", Boolean::class, KModifier.INTERNAL, KModifier.OPEN)
        .initializer("%L", false)
        .build()
    )
    .apply {
      source.sortedBy { it.name }.forEach { icon ->

        val small = icon.types[BpkIcon.Type.Sm] ?: icon.types[BpkIcon.Type.Lg] ?: error("Invalid icon format! : $icon")
        val large = icon.types[BpkIcon.Type.Lg] ?: icon.types[BpkIcon.Type.Sm] ?: error("Invalid icon format! : $icon")

        addEnumConstant(
          name = icon.name,
          typeSpec = TypeSpec.anonymousClassBuilder()
            .addProperty(
              PropertySpec.builder("small", Int::class, KModifier.OVERRIDE)
                .initializer("%T.drawable.%N", rClass, small)
                .build()
            )
            .addProperty(
              PropertySpec.builder("large", Int::class, KModifier.OVERRIDE)
                .initializer("%T.drawable.%N", rClass, large)
                .build()
            )
            .apply {
              if (icon.autoMirror) {
                addProperty(
                  PropertySpec.builder("autoMirror", Boolean::class, KModifier.OVERRIDE)
                    .initializer("%L", icon.autoMirror)
                    .build()
                )
              }
            }
            .build(),
        )
      }
    }
    .build()
