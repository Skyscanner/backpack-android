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
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.asClassName
import com.squareup.kotlinpoet.buildCodeBlock

interface BpkDeprecatedTokens : List<BpkDeprecatedTokenModel>

data class BpkDeprecatedTokenModel(
    val name: String,
)

object BpkDeprecatedToken {

    object Category : BpkParser<Map<String, Any>, BpkDeprecatedTokens> {

        override fun invoke(source: Map<String, Any>): BpkDeprecatedTokens =
            parseDeprecatedTokens(source)
    }

    sealed class Format : BpkTransformer<BpkDeprecatedTokens, TypeSpec> {

        data class Kotlin(val className: String) : Format() {
            override fun invoke(source: BpkDeprecatedTokens): TypeSpec =
                toKotlin(source, className)
        }
    }
}

@Suppress("UNCHECKED_CAST")
private fun parseDeprecatedTokens(source: Map<String, Any>): BpkDeprecatedTokens {

    fun String.trimName(): String =
        removePrefix("PRIVATE_").removePrefix("COLOR_").removeSuffix("_COLOR")

    val props = source.getValue("props") as Map<String, Map<String, String>>
    val data = props.filter { (_, value) ->
        value["deprecated"] == "true" &&
            value["type"] == "color"
    }.keys

    val list = data
        .map {
            BpkDeprecatedTokenModel(
                name = it.trimName(),
            )
        }

    return object : BpkDeprecatedTokens, List<BpkDeprecatedTokenModel> by list {
        override fun toString(): String =
            list.toString()
    }
}

private val StringClass = String::class.asClassName()
private val StringListClass = List::class.asClassName().parameterizedBy(StringClass)

private fun toKotlin(source: BpkDeprecatedTokens, className: String): TypeSpec {
    fun String.toSemanticName() =
        CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, this.replace('-', '_'))

    return TypeSpec.objectBuilder(className)
        .addModifiers(KModifier.INTERNAL)
        .addProperty(
            PropertySpec
                .builder("deprecatedColors", StringListClass)
                .initializer(buildCodeBlock {
                    addStatement("listOf(", StringClass)
                    indent()
                    apply {
                        source.forEach {
                            add("%S,\n", "bpk${it.name.toSemanticName()}")
                        }
                    }
                    unindent()
                    addStatement(")")
                },)
                .build(),
        )
        .build()
}
