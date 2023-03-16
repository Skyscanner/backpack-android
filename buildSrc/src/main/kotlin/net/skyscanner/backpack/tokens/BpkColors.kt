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
import com.squareup.kotlinpoet.MemberName
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.buildCodeBlock

data class BpkColorModel(
    val name: String,
    val category: String,
    val isPrivate: Boolean,
    val defaultValue: String,
    val defaultReference: String?,
    val darkReference: String?,
    val darkValue: String?,
    val deprecated: Boolean,
) {

    fun value(isLight: Boolean) = if (isLight) {
        defaultValue
    } else {
        darkValue ?: defaultValue
    }
}

interface BpkColors : List<BpkColorModel>

object BpkColor {

    object Static : BpkParser<Map<String, Any>, BpkColors> {

        override fun invoke(source: Map<String, Any>): BpkColors =
            parseColors(source, resolveReferences = false) {
                !it.isSemanticColor() && !it.hasSemanticSuffix(newSemanticSuffixes) && !it.isMarcomms()
            }.toBpkColors()
    }

    object Semantic : BpkParser<Map<String, Any>, BpkColors> {

        override fun invoke(source: Map<String, Any>): BpkColors =
            parseColors(source, resolveReferences = true) { it.isSemanticColor() && !it.isPrivate }.toBpkColors()
    }

    object Internal : BpkParser<Map<String, Any>, BpkColors> {

        override fun invoke(source: Map<String, Any>): BpkColors =
            parseColors(source, resolveReferences = true) { it.isSemanticColor() && it.isPrivate }.toBpkColors()
    }

    sealed class Format<In, Out> : BpkTransformer<In, Out> {

        data class SemanticCompose(val className: String) : Format<BpkColors, TypeSpec>() {
            override fun invoke(source: BpkColors): TypeSpec =
                toSemanticCompose(source, className)
        }

        object InternalCompose : Format<BpkColors, List<TypeSpec>>() {
            override fun invoke(source: BpkColors): List<TypeSpec> =
                source.groupBy { it.fileName() }
                    .map { toInternalCompose(it.value.toBpkColors(), it.key) }

            private fun BpkColorModel.fileName(): String = "Bpk${category.toCamelCase()}Colors"

            private fun String.toCamelCase() = CaseFormat.LOWER_HYPHEN.to(CaseFormat.UPPER_CAMEL, this)
        }

        object StaticXml : Format<BpkColors, String>() {
            override fun invoke(source: BpkColors): String =
                toXml(source, isLight = true)
        }

        object SemanticXml : Format<BpkColors, Map<String, String>>() {
            override fun invoke(source: BpkColors): Map<String, String> =
                toSemanticXml(source)
        }
    }

    private fun BpkColorModel.isMarcomms(): Boolean = name.startsWith("MARCOMMS_")

    private fun BpkColorModel.isSemanticColor(): Boolean =
        darkValue != null && !hasSemanticSuffix() && !isMarcomms()

    @OptIn(ExperimentalStdlibApi::class)
    private fun BpkColorModel.hasSemanticSuffix(suffixes: List<String> = semanticSuffixes): Boolean {
        val name = name.lowercase()
        return suffixes.any { name.endsWith("_$it") && !name.endsWith("_on_$it") }
    }

    private val newSemanticSuffixes = listOf("day", "night")
    private val semanticSuffixes = listOf("light", "dark", "day", "night")

    private fun List<BpkColorModel>.toBpkColors(): BpkColors =
        object : BpkColors, List<BpkColorModel> by this {
            override fun toString(): String =
                this.toString()
        }
}

@Suppress("UNCHECKED_CAST")
private fun parseColors(
    source: Map<String, Any>,
    resolveReferences: Boolean,
    filter: (BpkColorModel) -> Boolean = { true },
): List<BpkColorModel> {

    val props = source.getValue("props") as Map<String, Map<String, String>>
    val data = props.filter { (_, value) -> value["type"] == "color" }

    fun String.trimName(): String =
        removePrefix("PRIVATE_").removePrefix("COLOR_").removeSuffix("_COLOR")

    fun String.trimReference(): String =
        removePrefix("{!").removeSuffix("}")

    fun resolveReference(key: String?, isDark: Boolean): String? {
        if (!resolveReferences) return key
        val value = data[key] ?: return key
        val referencing = if (!isDark) value["originalValue"] else value["originalDarkValue"]
        if (referencing == key || referencing == null) return key
        return resolveReference(referencing, isDark)
    }

    return data
        .map {
            BpkColorModel(
                name = it.key.trimName(),
                category = it.value.getValue("category").removeSuffix("-colors"),
                isPrivate = it.key.startsWith("PRIVATE_"),
                defaultValue = it.value.getValue("value"),
                darkValue = it.value["darkValue"],
                defaultReference = resolveReference(it.value["originalValue"], isDark = false)?.trimReference()?.trimName(),
                darkReference = resolveReference(it.value["originalDarkValue"], isDark = true)?.trimReference()?.trimName(),
                deprecated = it.value["deprecated"].toBoolean(),
            )
        }
        .filter(filter)
        .sortedBy { it.name }
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

private fun deprecationProperty(): PropertySpec =
    PropertySpec.builder(deprecationMessageProperty, String::class)
        .initializer(
            "%S",
            "This colour is now deprecated. Please switch to the new semantic colours - see internal New Colours documentation",
        )
        .addModifiers(KModifier.CONST, KModifier.PRIVATE)
        .build()

private fun String.toArgb() = substring(7) + substring(1, 7)

@OptIn(ExperimentalStdlibApi::class)
private fun String.toHexColor() = uppercase().run { "0x${toArgb()}" }

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
                    .build(),
            )
            .addProperty(
                PropertySpec
                    .builder(isLightProperty, Boolean::class)
                    .initializer(isLightProperty)
                    .build(),
            )
            .addProperties(map(BpkColorModel::toProperty))
            .build()
    }

    fun BpkColors.toFactoryFunction(isLight: Boolean): FunSpec {

        val functionName = if (isLight) "light" else "dark"

        fun BpkColorModel.toDefaultValue(): CodeBlock = value(isLight).toHexColorBlock()

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
                },
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
                .build(),
        )
        .build()
}

private fun toInternalCompose(
    source: BpkColors,
    className: String,
): TypeSpec {

    fun BpkColorModel.toProperty(): PropertySpec {

        fun BpkColorModel.trimmedName() =
            if (isPrivate) {
                name.removePrefix("${CaseFormat.LOWER_HYPHEN.to(CaseFormat.UPPER_UNDERSCORE, category)}_")
            } else {
                name
            }

        fun BpkColorModel.toComposeInternalName() =
            CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, trimmedName())

        fun PropertySpec.Builder.addColorContent(): PropertySpec.Builder =
            if (darkValue != null) {
                getter(FunSpec.getterBuilder()
                    .addAnnotation(ClassName("androidx.compose.runtime", "Composable"))
                    .addCode(buildCodeBlock {
                        val dynamicColorOf = MemberName("net.skyscanner.backpack.compose.utils", "dynamicColorOf")
                        add("return %M(%T(%L), %T(%L))", dynamicColorOf, ColorClass, defaultValue.toHexColor(), ColorClass, darkValue.toHexColor())
                    },)
                    .build(),
                )
            } else {
                initializer(defaultValue.toHexColorBlock())
            }

        return PropertySpec
            .builder(toComposeInternalName(), ColorClass, KModifier.INTERNAL)
            .addColorContent()
            .build()
    }

    return TypeSpec.objectBuilder(className)
        .addModifiers(KModifier.INTERNAL)
        .addProperties(source.map(BpkColorModel::toProperty))
        .build()
}

private fun toXml(source: BpkColors, isLight: Boolean): String {
    fun String.toCamelCase() =
        CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, this)

    fun BpkColorModel.name() =
        if (isPrivate) {
            "__private${name.toCamelCase()}"
        } else {
            "bpk${name.toCamelCase()}"
        }

    return source.joinToString("\n") { model ->
        "    <color name=\"${model.name()}\">#${
        model.value(isLight).toArgb()
        }</color>"
    }
}

private fun toSemanticXml(source: BpkColors): Map<String, String> {
    return mapOf(
        "" to toXml(source, isLight = true),
        "-night" to toXml(source, isLight = false),
    )
}
