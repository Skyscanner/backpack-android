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
package net.skyscanner.backpack.tokens

import com.google.common.base.CaseFormat
import com.squareup.kotlinpoet.buildCodeBlock
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec

data class BpkColorModel(
  val name: String,
  val defaultValue: String,
  val defaultReference: String?,
  val darkReference: String?,
  val darkValue: String?,
)

interface BpkColors : Map<String, BpkColorModel>

object BpkColor {

  object Static : BpkParser<BpkColors> {

    override fun invoke(source: Map<String, Any>): BpkColors =
      parseColors(source, resolveReferences = false) {
        it.name == it.defaultReference
      }

  }

  object Semantic : BpkParser<BpkColors> {

    @OptIn(ExperimentalStdlibApi::class)
    override fun invoke(source: Map<String, Any>): BpkColors =
      parseColors(source, resolveReferences = true) {
        it.name != it.defaultReference && !it.name.lowercase().endsWith("light") && !it.name.lowercase().endsWith("dark")
      }

  }

  sealed class Format : BpkTransformer<BpkColors, TypeSpec> {

    data class StaticCompose(val namespace: String) : Format() {
      override fun invoke(source: BpkColors): TypeSpec =
        toStaticCompose(source, namespace)
    }

    data class SemanticCompose(val staticNameSpace: String, val className: String) : Format() {
      override fun invoke(source: BpkColors): TypeSpec =
        toSemanticCompose(source, staticNameSpace, className)
    }

  }

}

@Suppress("UNCHECKED_CAST")
private fun parseColors(
  source: Map<String, Any>,
  resolveReferences: Boolean,
  filter: (BpkColorModel) -> Boolean = { true },
): BpkColors {

  val props = source.getValue("props") as Map<String, Map<String, String>>
  val data = props.filter { (_, value) -> value["type"] == "color" }

  fun String.trimColor(): String =
    removePrefix("#").removeSuffix("ff")

  fun String.trimName(): String =
    removePrefix("COLOR_").removeSuffix("_COLOR")

  fun String.trimReference(): String =
    removePrefix("{!").removeSuffix("}")

  fun resolveReference(key: String?, isDark: Boolean): String? {
    if (!resolveReferences) return key
    val value = data[key] ?: return key
    val referencing = if (!isDark) value["originalValue"] else value["originalDarkValue"]
    if (referencing == key || referencing == null) return key
    return resolveReference(referencing, isDark)
  }

  val map = data
    .map {
      BpkColorModel(
        name = it.key.trimName(),
        defaultValue = it.value.getValue("value").trimColor(),
        darkValue = it.value["darkValue"]?.trimColor(),
        defaultReference = resolveReference(it.value["originalValue"], isDark = false)?.trimReference()?.trimName(),
        darkReference = resolveReference(it.value["originalDarkValue"], isDark = true)?.trimReference()?.trimName(),
      )
    }
    .filter(filter)
    .sortedBy { it.name }
    .associateBy { it.name }

  return object : BpkColors, Map<String, BpkColorModel> by map {
    override fun toString(): String =
      map.toString()
  }
}

private val ColorClass = ClassName("androidx.compose.ui.graphics", "Color")
private val ComposableAnnotation = ClassName("androidx.compose.runtime", "Composable")
private val MaterialTheme = ClassName("androidx.compose.material", "MaterialTheme")

@OptIn(ExperimentalStdlibApi::class)
private fun toStaticCompose(
  source: BpkColors,
  namespace: String,
): TypeSpec {

  fun String.toComposeName() =
    CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, this)


  fun String.toHexColor() =
    "0xFF${uppercase()}"

  return TypeSpec.objectBuilder(namespace)
    .addProperties(
      source.map { (_, model) ->
        // Generated code sample:
        // public val ColorName: Color = Color(0xFFXXXXXX)
        PropertySpec
          .builder(model.name.toComposeName(), ColorClass)
          .initializer(buildCodeBlock { add("%T(%L)", ColorClass, model.defaultValue.toHexColor()) })
          .build()
      }
    )
    .build()
}

@OptIn(ExperimentalStdlibApi::class)
private fun toSemanticCompose(
  source: BpkColors,
  staticNameSpace: String,
  className: String,
): TypeSpec {

  fun String.toComposeName() =
    CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, this)

  fun String.toComposeStaticName() =
    CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, this)

  fun String.toHexColor() =
    "0xFF${uppercase()}"

  return TypeSpec.classBuilder(className)
    .primaryConstructor(
      FunSpec.constructorBuilder()
        .addModifiers(KModifier.PRIVATE)
        .addParameter("isLight", Boolean::class)
        .addParameters(
          source.map { (_, model) ->
            // Generated code sample:
            // public val ColorName: Color = Color(0xFFXXXXXX)
            ParameterSpec
              .builder(model.name.toComposeName(), ColorClass)
              .build()
          }
        )
        .build()
    )
    .addProperty(PropertySpec.builder("isLight", Boolean::class).initializer("isLight").build())
    .addProperties(
      source.map { (_, model) ->
        // Generated code sample:
        // public val ColorName: Color = Color(0xFFXXXXXX)
        PropertySpec
          .builder(model.name.toComposeName(), ColorClass)
          .initializer(model.name.toComposeName())
          .build()
      }
    )
    .addType(TypeSpec.companionObjectBuilder()
      .addModifiers(KModifier.INTERNAL)
      .addFunction(
        FunSpec.builder("light")
          .addParameters(
            source.map { (_, model) ->
              // Generated code sample:
              // public val ColorName: Color = Color(0xFFXXXXXX)
              ParameterSpec
                .builder(model.name.toComposeName(), ColorClass)
                .defaultValue("$staticNameSpace.%N", model.defaultReference!!.toComposeStaticName())
                .build()
            }
          )
          .addStatement(
            source.map { it.value }.joinToString(
              separator = ",\n    ",
              prefix = "return $className(\n    isLight = true,\n    ",
              postfix = ",\n)",
            ) {
              it.name.toComposeName() + " = " + it.name.toComposeName()
            }
          )
          .build()
      )
      .addFunction(
        FunSpec.builder("dark")
          .addParameters(
            source.map { (_, model) ->
              // Generated code sample:
              // public val ColorName: Color = Color(0xFFXXXXXX)
              ParameterSpec
                .builder(model.name.toComposeName(), ColorClass)
                .defaultValue("$staticNameSpace.%N", (model.darkReference ?: model.defaultReference!!).toComposeStaticName())
                .build()
            }
          )
          .addStatement(
            source.map { it.value }.joinToString(
              separator = ",\n    ",
              prefix = "return $className(\n    isLight = false,\n    ",
              postfix = ",\n)",
            ) {
              it.name.toComposeName() + " = " + it.name.toComposeName()
            }
          )
          .build()
      )
      .build()
    )
    .build()
}
