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

  val map = data
    .map {
      BpkColorModel(
        name = it.key.trimName(),
        defaultValue = it.value.getValue("value").trimColor(),
        defaultReference = it.value.getValue("originalValue").trimReference().trimName(),
        darkReference = it.value["originalDarkValue"]?.trimReference()?.trimName(),
        darkValue = it.value["darkValue"]?.trimColor(),
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
private fun toCompose(
  source: BpkColors,
  namespace: String,
): TypeSpec {

  fun String.toComposeName() =
    CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, this)

  fun String.toHexColor() =
    "0xFF${uppercase()}"

  fun referenceColorProperty(model: BpkColorModel) =
    PropertySpec
      .builder(model.name.toComposeName(), ColorClass)
      .getter(
        FunSpec.getterBuilder()
          .addStatement("return %N", model.defaultReference!!.toComposeName())
          .build()
      )
      .build()

  fun constantColorProperty(model: BpkColorModel) =
    PropertySpec
      .builder(model.name.toComposeName(), ColorClass)
      .initializer(buildCodeBlock { add("%T(%L)", ColorClass, model.defaultValue.toHexColor()) })
      .build()

  fun dynamicColorProperty(model: BpkColorModel) =
    PropertySpec
      .builder(model.name.toComposeName(), ColorClass)
      .getter(
        FunSpec.getterBuilder()
          .addAnnotation(ComposableAnnotation)
          .addStatement(
            "return if (%T.colors.isLight) %N else %N",
            MaterialTheme,
            model.defaultReference!!.toComposeName(),
            model.darkReference!!.toComposeName(),
          )
          .build()
      )
      .build()

  return TypeSpec.objectBuilder(namespace)
    .addProperties(
      source.map { (_, model) ->
        when {
          model.darkValue != null -> dynamicColorProperty(model)
          model.defaultReference != model.name -> referenceColorProperty(model)
          else -> constantColorProperty(model)
        }
      }
    )
    .build()
}
