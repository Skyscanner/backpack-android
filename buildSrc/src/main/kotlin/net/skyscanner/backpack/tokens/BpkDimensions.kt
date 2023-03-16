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
import com.squareup.kotlinpoet.MemberName
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec

interface BpkDimensions : Map<String, Int>

object BpkDimension {

    sealed class Category : BpkParser<Map<String, Any>, BpkDimensions> {

        object Spacing : Category() {
            override fun invoke(source: Map<String, Any>): BpkDimensions =
                parseDimensions(source, "spacings", "SPACING_") {
                    it.key.toLowerCase() in setOf("base", "lg", "md", "none", "sm", "xl", "xxl")
                }
        }

        object Radii : Category() {
            override fun invoke(source: Map<String, Any>): BpkDimensions =
                parseDimensions(source, "radii", "BORDER_RADIUS_")
        }

        object Elevation : Category() {
            override fun invoke(source: Map<String, Any>): BpkDimensions =
                parseDimensions(source, "elevation", "ELEVATION_")
        }

        object Border : Category() {
            override fun invoke(source: Map<String, Any>): BpkDimensions =
                parseDimensions(source, "borders", "BORDER_SIZE_")
        }
    }

    sealed class Format<Output> : BpkTransformer<BpkDimensions, Output> {

        data class Compose(val namespace: String) : Format<TypeSpec>() {
            override fun invoke(source: BpkDimensions): TypeSpec =
                toCompose(source, namespace)
        }

        data class Xml(val namespace: String) : Format<String>() {
            override fun invoke(source: BpkDimensions): String =
                toXml(source, namespace)
        }
    }
}

@Suppress("UNCHECKED_CAST")
private fun parseDimensions(
    source: Map<String, Any>,
    category: String,
    prefixToRemove: String,
    filter: (Map.Entry<String, Int>) -> Boolean = { true },
): BpkDimensions {

    val props = source.getValue("props") as Map<String, Map<String, String>>
    val data = props.filter { (_, value) -> value["type"] == "size" && value["category"] == category }

    val map = data
        .mapValues { it.value.getValue("value").toIntOrNull() }
        .mapKeys { it.key.removePrefix(prefixToRemove) }
        .filterValues { it != null }
        .let { it as Map<String, Int> }
        .filter(filter)

    return object : BpkDimensions, Map<String, Int> by map {
        override fun toString(): String =
            map.toString()
    }
}

private val DpClass = ClassName("androidx.compose.ui.unit", "Dp")
private val dpExtension = MemberName("androidx.compose.ui.unit", "dp", isExtension = true)

private fun toCompose(
    source: BpkDimensions,
    namespace: String,
): TypeSpec =
    TypeSpec.objectBuilder(namespace)
        .addProperties(
            source.map { (name, value) ->
                PropertySpec
                    .builder(CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, name), DpClass)
                    .initializer(buildCodeBlock { add("%L.%M", value, dpExtension) })
                    .build()
            },
        )
        .build()

private fun toXml(source: BpkDimensions, type: String): String =
    source.map { (name, value) ->
        "  <dimen name=\"$type${CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, name)}\">${value}dp</dimen>"
    }.joinToString("\n")
