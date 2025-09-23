/**
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2018 - 2025 Skyscanner Ltd
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
    val fontSize: Token,
    val fontWeight: Token,
    val lineHeight: Token,
    val letterSpacing: Token?,
)

data class Token(
    val name: String,
    val value: String,
)

object BpkTextStyle {

    object Category : BpkParser<Map<String, Any>, BpkTextStyles> {

        override fun invoke(source: Map<String, Any>): BpkTextStyles =
            parseTextStyles(source)
    }

    sealed class Format<Output> : BpkTransformer<BpkTextStyles, Output> {

        data class Compose(val className: String) : Format<TypeSpec>() {
            override fun invoke(source: BpkTextStyles): TypeSpec =
                toCompose(source, className)
        }

        object Xml : Format<String>() {
            override fun invoke(source: BpkTextStyles): String =
                toXml(source)
        }
    }
}

@Suppress("UNCHECKED_CAST")
private fun parseTextStyles(source: Map<String, Any>): BpkTextStyles {

    fun String.trimReference(): String =
        removePrefix("{!").removeSuffix("}")

    fun List<Map.Entry<String, Map<String, String>>>.findToken(type: String): Token? {
        return firstOrNull { it.key.endsWith(type) }
            ?.let {
                Token(
                    name = it.value["originalValue"]!!
                        .trimReference()
                        .removePrefix("${type}_"),
                    value = it.value["value"]!!,
                )
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
                fontSize = it.value.findToken("FONT_SIZE")!!,
                fontWeight = it.value.findToken("FONT_WEIGHT")!!,
                lineHeight = it.value.findToken("LINE_HEIGHT")!!,
                letterSpacing = it.value.findToken("LETTER_SPACING"),
            )
        }
        .sortedBy { it.name }

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
private val PlatformTextStyleClass = ClassName("androidx.compose.ui.text", "PlatformTextStyle")
private val LineHeightStyleClass = ClassName("androidx.compose.ui.text.style", "LineHeightStyle")

private fun toCompose(source: BpkTextStyles, className: String): TypeSpec {
    val defaultFontFamily = "defaultFontFamily"

    fun String.toSemanticName() =
        CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, this.replace('-', '_'))

    // VDL2 typography style models based on VDL2-14 requirements
    val vdl2Styles = listOf(
        // Hero 4 - Amended - -1.2px letter spacing, Black
        BpkTextStyleModel(
            name = "HERO_4",
            fontSize = Token("5XL", "60.0.sp"),
            fontWeight = Token("BLACK", "FontWeight.Black"),
            lineHeight = Token("5XL", "72.0.sp"),
            letterSpacing = Token("VDL_HERO", "-(0.03).em"),
        ),
        // Hero 5 - Amended - -1.2px letter spacing, Black
        BpkTextStyleModel(
            name = "HERO_5",
            fontSize = Token("XXXXL", "48.0.sp"),
            fontWeight = Token("BLACK", "FontWeight.Black"),
            lineHeight = Token("XXXXL", "56.0.sp"),
            letterSpacing = Token("VDL_HERO", "-(0.03).em"),
        ),
        // Heading 1 - Amended - -1.2px letter spacing, Black
        BpkTextStyleModel(
            name = "HEADING_1",
            fontSize = Token("XXXL", "40.0.sp"),
            fontWeight = Token("BLACK", "FontWeight.Black"),
            lineHeight = Token("XXXL", "48.0.sp"),
            letterSpacing = Token("VDL_HEADING_1", "-(0.03).em"),
        ),
        // Heading 2 - Amended - -1.0px letter spacing, Black
        BpkTextStyleModel(
            name = "HEADING_2",
            fontSize = Token("XXL", "32.0.sp"),
            fontWeight = Token("BLACK", "FontWeight.Black"),
            lineHeight = Token("XXL", "40.0.sp"),
            letterSpacing = Token("VDL_HEADING_2", "-(0.025).em"),
        ),
        // Heading 3 - Amended - -0.6px letter spacing, Black
        BpkTextStyleModel(
            name = "HEADING_3",
            fontSize = Token("XL", "24.0.sp"),
            fontWeight = Token("BLACK", "FontWeight.Black"),
            lineHeight = Token("XL_TIGHT", "28.0.sp"),
            letterSpacing = Token("VDL_HEADING_3", "-(0.02).em"),
        ),
        // Heading 4 - Amended - -0.6px letter spacing, Black
        BpkTextStyleModel(
            name = "HEADING_4",
            fontSize = Token("LG", "20.0.sp"),
            fontWeight = Token("BLACK", "FontWeight.Black"),
            lineHeight = Token("LG_TIGHT", "24.0.sp"),
            letterSpacing = Token("VDL_HEADING_3", "-(0.02).em"),
        ),
        // Heading 5 - Amended - -0.6px letter spacing, Black
        BpkTextStyleModel(
            name = "HEADING_5",
            fontSize = Token("BASE", "16.0.sp"),
            fontWeight = Token("BLACK", "FontWeight.Black"),
            lineHeight = Token("BASE_TIGHT", "20.0.sp"),
            letterSpacing = Token("VDL_HEADING_3", "-(0.02).em"),
        ),
    )

    fun BpkTextStyles.toTextStyleClass(): TypeSpec {

        fun BpkTextStyles.toConstructorFunction(): FunSpec {

            fun String.wrapIfDigits() = if (first().isDigit()) {
                "`$this`"
            } else this

            fun Token.toName() =
                CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, name.replace('-', '_'))
                    .wrapIfDigits()

            fun BpkTextStyleModel.toParameter(): String =
                "%textStyle:T(\n" +
                    "    fontWeight = %fontWeight:T.${fontWeight.toName().let { if (it == "Book") "Normal" else it }},\n" +
                    "    fontSize = %fontSize:T.${fontSize.toName()},\n" +
                    "    lineHeight = %lineHeight:T.${lineHeight.toName()},\n" + if (letterSpacing != null) {
                        "    letterSpacing = %letterSpacing:T.${letterSpacing.toName()},\n"
                    } else {
                        ""
                    } +
                    "    fontFamily = $defaultFontFamily,\n" +
                    "    lineHeightStyle = %lineHeightStyle:T(\n" +
                    "        alignment = %lineHeightStyle:T.Alignment(topRatio = 0.2f),\n" +
                    "        trim = %lineHeightStyle:T.Trim.None,\n" +
                    "    ),\n" +
                    "  )"

            return FunSpec.constructorBuilder()
                .addModifiers(KModifier.INTERNAL)
                .addParameter(
                    ParameterSpec
                        .builder(defaultFontFamily, FontFamilyClass)
                        .defaultValue("%T.SansSerif", FontFamilyClass)
                        .build(),
                )
                .callThisConstructor(
                    CodeBlock.builder().addNamed(
                        joinToString(
                            prefix = "\n  ",
                            separator = ",\n  ",
                            postfix = ",\n",
                        ) {
                            "${it.name.toSemanticName()} = ${it.toParameter()}"
                        },
                        mapOf(
                            "textStyle" to TextStyleClass,
                            "fontWeight" to FontWeightClass,
                            "fontSize" to BpkFontSizeClass,
                            "lineHeight" to BpkLineHeightClass,
                            "letterSpacing" to BpkLetterSpacingClass,
                            "platformTextStyle" to PlatformTextStyleClass,
                            "lineHeightStyle" to LineHeightStyleClass,
                        ),
                    ).build(),
                )
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

        fun createVdl2CompanionFunction(styles: List<BpkTextStyleModel>): FunSpec {
            fun String.wrapIfDigits() = if (first().isDigit()) {
                "`$this`"
            } else this

            fun Token.toName() =
                CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, name.replace('-', '_'))
                    .wrapIfDigits()

            fun BpkTextStyleModel.toVdlParameter(): String =
                "%textStyle:T(\n" +
                    "    fontWeight = %fontWeight:T.${fontWeight.toName().let { if (it == "Book") "Normal" else it }},\n" +
                    "    fontSize = %fontSize:T.${fontSize.toName()},\n" +
                    "    lineHeight = %lineHeight:T.${lineHeight.toName()},\n" + if (letterSpacing != null) {
                        "    letterSpacing = %letterSpacing:T.${letterSpacing.toName()},\n"
                    } else {
                        ""
                    } +
                    "    fontFamily = $defaultFontFamily,\n" +
                    "    lineHeightStyle = %lineHeightStyle:T(\n" +
                    "        alignment = %lineHeightStyle:T.Alignment(topRatio = 0.2f),\n" +
                    "        trim = %lineHeightStyle:T.Trim.None,\n" +
                    "    ),\n" +
                    ")"

            val vdlUpdates = styles.joinToString(
                separator = ",\n            ",
            ) { style ->
                "${style.name.toSemanticName()} = ${style.toVdlParameter()}"
            }

            return FunSpec.builder("VDL2")
                .addParameter(
                    ParameterSpec
                        .builder(defaultFontFamily, FontFamilyClass)
                        .defaultValue("%T.SansSerif", FontFamilyClass)
                        .build(),
                )
                .returns(ClassName("", className))
                .addCode(
                    CodeBlock.builder().addNamed(
                        """
                        /**
                         * VDL2 typography with enhanced letter spacing and optimized styles.
                         * Updated styles: Hero 4-5, Heading 1-5 with enhanced letter spacing and Black font weight
                         */
                        return BpkTypography(
                            $defaultFontFamily
                        ).copy(
                            $vdlUpdates
                        )
                        """.trimIndent(),
                        mapOf(
                            "textStyle" to TextStyleClass,
                            "fontWeight" to FontWeightClass,
                            "fontSize" to BpkFontSizeClass,
                            "lineHeight" to BpkLineHeightClass,
                            "letterSpacing" to BpkLetterSpacingClass,
                            "lineHeightStyle" to LineHeightStyleClass,
                        ),
                    ).build(),
                )
                .build()
        }

        return TypeSpec.classBuilder(className)
            .primaryConstructor(
                FunSpec.constructorBuilder()
                    .addModifiers(KModifier.INTERNAL)
                    .addParameters(map(BpkTextStyleModel::toParameter))
                    .build(),
            )
            .addFunction(source.toConstructorFunction())
            .addProperties(map(BpkTextStyleModel::toProperty))
            .addModifiers(KModifier.DATA)
            .addType(
                TypeSpec.companionObjectBuilder()
                    .addFunction(createVdl2CompanionFunction(vdl2Styles))
                    .build(),
            )
            .build()
    }

    return source.toTextStyleClass()
}

private fun toXml(source: BpkTextStyles): String {
    val textStylesDeclaration = """
  <declare-styleable name="BpkTextStyle">
      <attr name="android:fontFamily" />
      <attr name="android:textSize" />
      <attr name="android:letterSpacing" />
      <attr name="lineHeight" />
  </declare-styleable>
  """

    fun String.toSemanticName() =
        CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, this.replace('-', '_'))

    fun String.toFontWeightName() =
        toSemanticName().let {
            when (it) {
                "Book" -> "Base"
                "Bold" -> "Emphasized"
                else -> it
            }
        }

    fun BpkTextStyleModel.toXml(): String {
        val content = listOfNotNull(
            "    <item name=\"android:fontFamily\">?bpkFontFamily${fontWeight.name.toFontWeightName()}</item>",
            "    <item name=\"android:textColor\">@color/bpkTextPrimary</item>",
            "    <item name=\"android:textSize\">@dimen/bpkText${fontSize.name.toSemanticName()}Size</item>",
            "    <item name=\"lineHeight\">${lineHeight.value}sp</item>",
            letterSpacing?.let { "    <item name=\"android:letterSpacing\">${letterSpacing.value}</item>" },
        )
        return """
  <style name="bpkText${name.toSemanticName()}">
${content.joinToString("\n")}
  </style>"""
    }

    // VDL2 enhanced typography styles with improved letter spacing and line heights
    fun createVdlStyle(
        baseName: String,
        letterSpacing: Double,
        fontWeight: String = "Base",
    ): String {
        val content = listOfNotNull(
            "    <item name=\"android:fontFamily\">?bpkFontFamily$fontWeight</item>",
            "    <item name=\"android:letterSpacing\">$letterSpacing</item>",
        )
        return """
  <style name="bpkText${baseName}Vdl" parent="bpkText$baseName">
${content.joinToString("\n")}
  </style>"""
    }

    val vdlStyles = listOf(
        // VDL2 Typography styles with enhanced letter spacing and line heights
//        createVdlStyle("Display7", -0.05, "Emphasized"), // 64sp with 85% line height
        createVdlStyle("Hero5", -0.03, "Black"),
//        createVdlStyle("Hero6", -0.03, "Emphasized"),
        createVdlStyle("Heading1", -0.03, "Black"),
        createVdlStyle("Heading2", -0.025, "Black"),
        createVdlStyle("Heading3", -0.02, "Black"),
        createVdlStyle("Heading4", -0.02, "Black"),
        createVdlStyle("Heading5", -0.02, "Black"),
    )

    return textStylesDeclaration + source.joinToString("\n") { it.toXml() } + "\n\n  <!-- VDL2 Enhanced Typography Styles -->" + vdlStyles.joinToString(
        "\n",
    )
}
