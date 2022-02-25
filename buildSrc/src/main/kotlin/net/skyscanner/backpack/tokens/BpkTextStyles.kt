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
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec

interface BpkTextStyles : List<BpkTextStyleModel>

data class BpkTextStyleModel(
  val name: String,
  val fontSize: String,
  val fontWeight: String,
  val lineHeight: String,
  val letterSpacing: String?,
)

object BpkTextStyle {

  object Category : BpkParser<Map<String, Any>, BpkTextStyles> {

    override fun invoke(source: Map<String, Any>): BpkTextStyles =
      parseTextStyles(source)
  }

  sealed class Format : BpkTransformer<BpkTextStyles, TypeSpec> {

    data class Compose(val className: String) : BpkTextStyle.Format() {
      override fun invoke(source: BpkTextStyles): TypeSpec =
        toCompose(source, className)
    }
  }
}

@Suppress("UNCHECKED_CAST")
private fun parseTextStyles(source: Map<String, Any>): BpkTextStyles {

  fun String.trimReference(): String =
    removePrefix("{!").removeSuffix("}")

  fun String.toSemanticName() =
    CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, this.replace('-', '_'))

  fun String.wrapIfDigits() = if (first().isDigit()) { "`$this`" } else this

  fun List<Map.Entry<String, Map<String, String>>>.findTokenName(type: String): String? {
    return firstOrNull { it.key.endsWith(type) }
      ?.let {
        it.value["originalValue"]!!
          .trimReference()
          .removePrefix("${type}_")
          .toSemanticName()
          .wrapIfDigits()
      }
  }

  val props = source.getValue("props") as Map<String, Map<String, String>>
  val data = props.filter { (key, value) ->
    value["deprecated"] != "true" &&
      key.startsWith("TEXT_") &&
      value["type"] != "color"
  }

  val list = data
    .mapKeys { it.key.removePrefix("TEXT_") }
    .entries.groupBy { it.key.substringBefore("_") }
    .map {
      BpkTextStyleModel(
        name = it.key,
        fontSize = it.value.findTokenName("FONT_SIZE")!!,
        fontWeight = it.value.findTokenName("FONT_WEIGHT")!!.let { if (it == "Book") "Normal" else it },
        lineHeight = it.value.findTokenName("LINE_HEIGHT")!!,
        letterSpacing = it.value.findTokenName("LETTER_SPACING"),
      )
    }

  return object : BpkTextStyles, List<BpkTextStyleModel> by list {
    override fun toString(): String =
      list.toString()
  }
}

private val TextStyleClass = ClassName("androidx.compose.ui.text", "TextStyle")
private val FontFamilyClass = ClassName("androidx.compose.ui.text.font", "FontFamily")
private val FontWeightClass = ClassName("androidx.compose.ui.text.font", "FontWeight")
private val BpkFontSizeClass = ClassName("net.skyscanner.backpack.compose.tokens", "BpkFontSize")
private val BpkLineHeightClass = ClassName("net.skyscanner.backpack.compose.tokens", "BpkLineHeight")
private val BpkLetterSpacingClass = ClassName("net.skyscanner.backpack.compose.tokens", "BpkLetterSpacing")


private fun toCompose(source: BpkTextStyles, className: String): TypeSpec {
  val defaultFontFamily = "defaultFontFamily"

  fun String.toSemanticName() =
    CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, this.replace('-', '_'))

  fun BpkTextStyles.toTextStyleClass(): TypeSpec {

    fun BpkTextStyles.toConstructorFunction(): FunSpec {

      fun BpkTextStyleModel.toParameter(): String =
        "%textStyle:T(\n" +
          "    fontWeight = %fontWeight:T.$fontWeight,\n" +
          "    fontSize = %fontSize:T.$fontSize,\n" +
          "    lineHeight = %lineHeight:T.$lineHeight,\n" + if (letterSpacing != null) {
          "    letterSpacing = %letterSpacing:T.$letterSpacing,\n"
        } else {
          ""
        } +
          "    fontFamily = $defaultFontFamily,\n" +
          "  )"

      return FunSpec.constructorBuilder()
        .addModifiers(KModifier.INTERNAL)
        .addParameter(ParameterSpec
          .builder(defaultFontFamily, FontFamilyClass)
          .defaultValue("%T.SansSerif", FontFamilyClass)
          .build())
        .callThisConstructor(CodeBlock.builder().addNamed(joinToString(
          prefix = "\n  ",
          separator = ",\n  ",
          postfix = ",\n",
        ) {
          "${it.name.toSemanticName()} = ${it.toParameter()}"
        }, mapOf(
          "textStyle" to TextStyleClass,
          "fontWeight" to FontWeightClass,
          "fontSize" to BpkFontSizeClass,
          "lineHeight" to BpkLineHeightClass,
          "letterSpacing" to BpkLetterSpacingClass,
        )).build())
        .build()
    }

    fun BpkTextStyleModel.toParameter(): ParameterSpec =
      ParameterSpec
        .builder(name.toSemanticName(), TextStyleClass)
        .build()

    fun BpkTextStyleModel.toProperty(): PropertySpec =
      PropertySpec
        .builder(name.toSemanticName(), TextStyleClass)
        .initializer(name.toSemanticName())
        .build()

    return TypeSpec.classBuilder(className)
      .primaryConstructor(
        FunSpec.constructorBuilder()
          .addModifiers(KModifier.INTERNAL)
          .addParameters(map(BpkTextStyleModel::toParameter))
          .build()
      )
      .addFunction(source.toConstructorFunction())
      .addProperties(map(BpkTextStyleModel::toProperty))
      .addModifiers(KModifier.DATA)
      .build()
  }

  return source.toTextStyleClass()
}
