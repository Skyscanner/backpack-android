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
                        }, mapOf(
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

        // Generate companion object with VDL2 function
        val companionObject = createVdl2CompanionObject(className)

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
            .addType(companionObject)
            .build()
    }

    return source.toTextStyleClass()
}

private fun createVdl2CompanionObject(typographyClassName: String): TypeSpec {
    val defaultFontFamily = "defaultFontFamily"
    val bpkTypographyClass = ClassName("net.skyscanner.backpack.compose.tokens", typographyClassName)

    fun String.toSemanticName() =
        CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, this.replace('-', '_'))

    val vdl2Overrides = listOf(
        BpkTextStyleModel(
            name = "HERO_5",
            fontSize = Token("XXXXL", "48.0.sp"),
            fontWeight = Token("BLACK", "FontWeight.Black"),
            lineHeight = Token("XXXXL", "56.0.sp"),
            letterSpacing = Token("VDL_HERO", "-(0.03).em"),
        ),
        BpkTextStyleModel(
            name = "HEADING_1",
            fontSize = Token("XXXL", "40.0.sp"),
            fontWeight = Token("BLACK", "FontWeight.Black"),
            lineHeight = Token("XXXL", "48.0.sp"),
            letterSpacing = Token("VDL_HEADING_1", "-(0.03).em"),
        ),
        BpkTextStyleModel(
            name = "HEADING_2",
            fontSize = Token("XXL", "32.0.sp"),
            fontWeight = Token("BLACK", "FontWeight.Black"),
            lineHeight = Token("XXL", "40.0.sp"),
            letterSpacing = Token("VDL_HEADING_2", "-(0.025).em"),
        ),
        BpkTextStyleModel(
            name = "HEADING_3",
            fontSize = Token("XL", "24.0.sp"),
            fontWeight = Token("BLACK", "FontWeight.Black"),
            lineHeight = Token("XL_TIGHT", "28.0.sp"),
            letterSpacing = Token("VDL_HEADING_3", "-(0.02).em"),
        ),
        BpkTextStyleModel(
            name = "HEADING_4",
            fontSize = Token("LG", "20.0.sp"),
            fontWeight = Token("BLACK", "FontWeight.Black"),
            lineHeight = Token("LG_TIGHT", "24.0.sp"),
            letterSpacing = Token("VDL_HEADING_3", "-(0.02).em"),
        ),
        BpkTextStyleModel(
            name = "HEADING_5",
            fontSize = Token("BASE", "16.0.sp"),
            fontWeight = Token("BLACK", "FontWeight.Black"),
            lineHeight = Token("BASE_TIGHT", "20.0.sp"),
            letterSpacing = Token("VDL_HEADING_3", "-(0.02).em"),
        ),
    )

    fun String.wrapIfDigits() = if (first().isDigit()) {
        "`$this`"
    } else this

    fun Token.toName() =
        CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, name.replace('-', '_'))
            .wrapIfDigits()

    val codeBlockBuilder = CodeBlock.builder()
    codeBlockBuilder.add("return %T(\n", bpkTypographyClass)
    codeBlockBuilder.indent()
    codeBlockBuilder.add("$defaultFontFamily\n")
    codeBlockBuilder.unindent()
    codeBlockBuilder.add(").copy(\n")
    codeBlockBuilder.indent()

    vdl2Overrides.forEachIndexed { index, style ->
        codeBlockBuilder.add("${style.name.toSemanticName()} = %T(\n", TextStyleClass)
        codeBlockBuilder.indent()
        codeBlockBuilder.add("fontWeight = %T.Black,\n", FontWeightClass)
        codeBlockBuilder.add("fontSize = %T.${style.fontSize.toName()},\n", BpkFontSizeClass)
        codeBlockBuilder.add("lineHeight = %T.${style.lineHeight.toName()},\n", BpkLineHeightClass)
        codeBlockBuilder.add("letterSpacing = %T.${style.letterSpacing!!.toName()},\n", BpkLetterSpacingClass)
        codeBlockBuilder.add("fontFamily = %T.SansSerif,\n", FontFamilyClass)
        codeBlockBuilder.add("lineHeightStyle = %T(\n", LineHeightStyleClass)
        codeBlockBuilder.indent()
        codeBlockBuilder.add("alignment = %T.Alignment(topRatio = 0.2f),\n", LineHeightStyleClass)
        codeBlockBuilder.add("trim = %T.Trim.None,\n", LineHeightStyleClass)
        codeBlockBuilder.unindent()
        codeBlockBuilder.add("),\n")
        codeBlockBuilder.unindent()
        codeBlockBuilder.add(")")
        if (index < vdl2Overrides.size - 1) {
            codeBlockBuilder.add(",\n")
        } else {
            codeBlockBuilder.add(",\n")
        }
    }

    codeBlockBuilder.unindent()
    codeBlockBuilder.add(")\n")

    val vdl2Function = FunSpec.builder("VDL2")
        .addKdoc("""
            VDL2 typography with enhanced letter spacing and optimized styles.
            Updated styles: Hero 5, Heading 1-5 with enhanced letter spacing and Black font weight
            Note: VDL2 does not include Display 7, Editorial 4-6 as they don't map to existing properties
        """.trimIndent())
        .addModifiers(KModifier.PUBLIC)
        .addParameter(
            ParameterSpec
                .builder(defaultFontFamily, FontFamilyClass)
                .defaultValue("%T.SansSerif", FontFamilyClass)
                .build(),
        )
        .returns(bpkTypographyClass)
        .addCode(codeBlockBuilder.build())
        .build()

    return TypeSpec.companionObjectBuilder()
        .addFunction(vdl2Function)
        .build()
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
        fontSize: String,
        letterSpacing: Double,
        lineHeight: Int,
        fontFamily: String = "Base",
    ): String {
        val content = listOfNotNull(
            "    <item name=\"android:fontFamily\">?bpkFontFamily$fontFamily</item>",
            "    <item name=\"android:textColor\">@color/bpkTextPrimary</item>",
            "    <item name=\"android:textSize\">@dimen/bpkText${fontSize}Size</item>",
            "    <item name=\"lineHeight\">${lineHeight}sp</item>",
            "    <item name=\"android:letterSpacing\">$letterSpacing</item>",
        )
        return """
  <style name="bpkText${baseName}Vdl">
${content.joinToString("\n")}
  </style>"""
    }

    val vdlStyles = listOf(
        // VDL2 Typography styles with enhanced letter spacing and line heights
        createVdlStyle("Hero5", "Xxxxl", -0.03, 56, "Black"),
        createVdlStyle("Heading1", "Xxxl", -0.03, 48, "Black"),
        createVdlStyle("Heading2", "Xxl", -0.025, 40, "Black"),
        createVdlStyle("Heading3", "Xl", -0.02, 28, "Black"),
        createVdlStyle("Heading4", "Lg", -0.02, 24, "Black"),
        createVdlStyle("Heading5", "Base", -0.02, 20, "Black"),
    )

    return textStylesDeclaration + source.joinToString("\n") { it.toXml() } + "\n\n  <!-- VDL2 Enhanced Typography Styles -->" + vdlStyles.joinToString("\n")
}
