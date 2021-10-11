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
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec

interface BpkColors : Map<String, String>

object BpkColor {

  object Parser : BpkParser<BpkColors> {

      override fun invoke(source: Map<String, Any>): BpkColors =
        parseColors(source)

  }

  sealed class Format : BpkTransformer<BpkColors, TypeSpec> {

    data class Compose(val namespace: String) : Format() {
      override fun invoke(source: BpkColors): TypeSpec =
        toCompose(source, namespace)
    }

  }

}

@Suppress("UNCHECKED_CAST")
private fun parseColors(
  source: Map<String, Any>,
  filter: (Map.Entry<String, String>) -> Boolean = { true },
): BpkColors {

  val props = source.getValue("props") as Map<String, Map<String, String>>
  val data = props.filter { (_, value) -> value["type"] == "color" }

  val map = data
    .mapValues { it.value.getValue("value").removePrefix("#").removeSuffix("ff") }
    .mapKeys { it.key.removePrefix("COLOR_").removeSuffix("_COLOR") }
    .filter(filter)

  return object : BpkColors, Map<String, String> by map {
    override fun toString(): String =
      map.toString()
  }
}

private val ColorClass = ClassName("androidx.compose.ui.graphics", "Color")
private val StableAnnotation = ClassName("androidx.compose.runtime", "Stable")

@OptIn(ExperimentalStdlibApi::class)
private fun toCompose(
  source: BpkColors,
  namespace: String,
): TypeSpec =
  TypeSpec.objectBuilder(namespace)
    .addProperties(
      source.map { (name, value) ->
        PropertySpec
          .builder(CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, name), ColorClass)
          .addAnnotation(StableAnnotation)
          .initializer(buildCodeBlock { add("%T(%L)", ColorClass, "0xFF${value.uppercase()}") })
          .build()
      }
    )
    .build()
