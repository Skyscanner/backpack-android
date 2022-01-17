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
import com.squareup.kotlinpoet.buildCodeBlock
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.MemberName
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec

interface BpkTextUnits : Map<String, Double>

object BpkTextUnit {

  sealed class Category : BpkParser<Map<String, Any>, BpkTextUnits> {

    object FontSize : Category() {
      override fun invoke(source: Map<String, Any>): BpkTextUnits =
        parseTextUnits(source, "font-size", "typesettings", "FONT_SIZE_")
    }

    object LetterSpacing : Category() {
      override fun invoke(source: Map<String, Any>): BpkTextUnits =
        parseTextUnits(source, "letter-spacing", "letter-spacings", "LETTER_SPACING_") {
          !it.key.startsWith("TEXT_")
        }
    }

    object LineHeight : Category() {
      override fun invoke(source: Map<String, Any>): BpkTextUnits =
        parseTextUnits(source, "size", "typesettings", "LINE_HEIGHT_")
    }

  }

  sealed class Format : BpkTransformer<BpkTextUnits, TypeSpec> {

    data class Compose(val namespace: String, val internal: Boolean = false) : Format() {
      override fun invoke(source: BpkTextUnits): TypeSpec =
        toCompose(source, namespace, internal)
    }

  }

}

@Suppress("UNCHECKED_CAST")
private fun parseTextUnits(
  source: Map<String, Any>,
  type: String,
  category: String,
  prefixToRemove: String,
  filter: (Map.Entry<String, Double>) -> Boolean = { true },
): BpkTextUnits {

  val props = source.getValue("props") as Map<String, Map<String, String>>
  val data = props.filter { (_, value) -> value["type"] == type && value["category"] == category }

  val map = data
    .mapValues { it.value.getValue("value").toDoubleOrNull() }
    .mapKeys { it.key.removePrefix(prefixToRemove) }
    .filterValues { it != null }
    .let { it as Map<String, Double> }
    .filter(filter)

  return object : BpkTextUnits, Map<String, Double> by map {
    override fun toString(): String =
      map.toString()
  }
}

private val TextUnitClass = ClassName("androidx.compose.ui.unit", "TextUnit")
private val spExtension = MemberName("androidx.compose.ui.unit", "sp", isExtension = true)

private fun toCompose(
  source: BpkTextUnits,
  namespace: String,
  internal: Boolean,
): TypeSpec =
  TypeSpec.objectBuilder(namespace)
    .addModifiers(if (internal) KModifier.INTERNAL else KModifier.PUBLIC)
    .addProperties(
      source.map { (name, value) ->
        PropertySpec
          .builder(CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, name), TextUnitClass)
          .addModifiers(if (internal) KModifier.INTERNAL else KModifier.PUBLIC)
          .initializer(buildCodeBlock {
            if (value < 0) {
              add("-(%L).%M", -value, spExtension)
            } else {
              add("%L.%M", value, spExtension)
            }
          })
          .build()
      }
    )
    .build()
