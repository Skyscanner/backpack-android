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
import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.buildCodeBlock

data class BpkColorModel(
  val name: String,
  val defaultValue: String,
  val defaultReference: String?,
  val darkReference: String?,
  val darkValue: String?,
  val deprecated: Boolean,
)

interface BpkColors : List<BpkColorModel>

object BpkColor {

  object Static : BpkParser<Map<String, Any>, BpkColors> {

    override fun invoke(source: Map<String, Any>): BpkColors =
      parseColors(source, resolveReferences = false) {
        it.name == it.defaultReference && !it.isMarcomms()
      }
  }

  object Semantic : BpkParser<Map<String, Any>, BpkColors> {

    override fun invoke(source: Map<String, Any>): BpkColors =
      parseColors(source, resolveReferences = true) {
        it.name != it.defaultReference && !it.hasSemanticSuffix() && !it.isMarcomms()
      }

    @OptIn(ExperimentalStdlibApi::class)
    private fun BpkColorModel.hasSemanticSuffix(): Boolean {
      val name = name.lowercase()
      return semanticSuffixes.any { name.endsWith(it) }
    }

    private val semanticSuffixes = listOf("light", "dark", "day", "night")

  }

  sealed class Format : BpkTransformer<BpkColors, TypeSpec> {

    data class StaticCompose(val namespace: String) : Format() {
      override fun invoke(source: BpkColors): TypeSpec =
        toStaticCompose(source, namespace)
    }

    data class SemanticCompose(val className: String) : Format() {
      override fun invoke(source: BpkColors): TypeSpec =
        toSemanticCompose(source, className)
    }

  }

  private fun BpkColorModel.isMarcomms(): Boolean = name.startsWith("MARCOMMS_")
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

  val list = data
    .map {
      BpkColorModel(
        name = it.key.trimName(),
        defaultValue = it.value.getValue("value").trimColor(),
        darkValue = it.value["darkValue"]?.trimColor(),
        defaultReference = resolveReference(it.value["originalValue"], isDark = false)?.trimReference()?.trimName(),
        darkReference = resolveReference(it.value["originalDarkValue"], isDark = true)?.trimReference()?.trimName(),
        deprecated = it.value["deprecated"].toBoolean()
      )
    }
    .filter(filter)
    .sortedBy { it.name }

  return object : BpkColors, List<BpkColorModel> by list {
    override fun toString(): String =
      list.toString()
  }
}

private val ColorClass = ClassName("androidx.compose.ui.graphics", "Color")

private const val isLightProperty = "isLight"
private const val deprecationMessageProperty = "DEPRECATION_MESSAGE"

private fun String.toHexColorBlock() =
  buildCodeBlock { add("%T(%L)", ColorClass, toHexColor()) }

private fun PropertySpec.Builder.withDeprecation(model: BpkColorModel): PropertySpec.Builder {
  return if (model.deprecated) {
    addAnnotation(AnnotationSpec.builder(Deprecated::class).addMember(deprecationMessageProperty).build())
  } else {
    this
  }
}

private fun deprecationProperty() : PropertySpec =
  PropertySpec.builder(deprecationMessageProperty, String::class)
    .initializer(
      "%S",
      "This colour is now deprecated. Please switch to the new semantic colours - see internal New Colours documentation"
    )
    .addModifiers(KModifier.CONST, KModifier.PRIVATE)
    .build()

@OptIn(ExperimentalStdlibApi::class)
private fun String.toHexColor() =
  "0xFF${uppercase()}"

private fun toStaticCompose(
  source: BpkColors,
  namespace: String,
): TypeSpec {

  fun BpkColorModel.toProperty(): PropertySpec {

    fun String.toComposeStaticName() =
      CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, this)

    return PropertySpec
      .builder(name.toComposeStaticName(), ColorClass)
      .initializer(defaultValue.toHexColorBlock())
      .withDeprecation(this)
      .build()
  }

  return TypeSpec.objectBuilder(namespace)
    .addProperties(source.map(BpkColorModel::toProperty))
    .addProperty(deprecationProperty())
    .build()
}

private fun toSemanticCompose(
  source: BpkColors,
  className: String,
): TypeSpec {

  fun String.toSemanticName() =
    CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, this)

  fun BpkColors.toColorsClass(): TypeSpec {

    fun BpkColorModel.toParameter(): ParameterSpec =
      ParameterSpec
        .builder(name.toSemanticName(), ColorClass)
        .build()

    fun BpkColorModel.toProperty(): PropertySpec =
      PropertySpec
        .builder(name.toSemanticName(), ColorClass)
        .initializer(name.toSemanticName())
        .withDeprecation(this)
        .build()

    return TypeSpec.classBuilder(className)
      .primaryConstructor(
        FunSpec.constructorBuilder()
          .addModifiers(KModifier.PRIVATE)
          .addParameter(isLightProperty, Boolean::class)
          .addParameters(map(BpkColorModel::toParameter))
          .build()
      )
      .addProperty(
        PropertySpec
          .builder(isLightProperty, Boolean::class)
          .initializer(isLightProperty)
          .build()
      )
      .addProperties(map(BpkColorModel::toProperty))
      .build()
  }

  fun BpkColors.toFactoryFunction(isLight: Boolean): FunSpec {

    val functionName = if (isLight) "light" else "dark"

    fun BpkColorModel.value() = if (isLight) {
      defaultValue
    } else {
      darkValue ?: defaultValue
    }

    fun BpkColorModel.toDefaultValue(): CodeBlock = value().toHexColorBlock()

    fun BpkColorModel.toParameter(): ParameterSpec =
      ParameterSpec
        .builder(name.toSemanticName(), ColorClass)
        .defaultValue(toDefaultValue())
        .build()

    return FunSpec.builder(functionName)
      .addParameters(map(BpkColorModel::toParameter))
      .addStatement(
        joinToString(
          separator = ",\n    ",
          prefix = "return $className(\n" +
            "    $isLightProperty = $isLight,\n" +
            "    ",
          postfix = ",\n)",
        ) {
          "${it.name.toSemanticName()} = ${it.name.toSemanticName()}"
        }
      )
      .build()
  }

  return source
    .toColorsClass()
    .toBuilder()
    .addType(
      TypeSpec.companionObjectBuilder()
        .addModifiers(KModifier.INTERNAL)
        .addFunction(source.toFactoryFunction(isLight = true))
        .addFunction(source.toFactoryFunction(isLight = false))
        .addProperty(deprecationProperty())
        .build()
    )
    .build()
}
