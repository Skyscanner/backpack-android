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

import com.android.ide.common.vectordrawable.Svg2Vector
import com.google.common.base.CaseFormat
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.MemberName
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.asClassName
import java.io.ByteArrayOutputStream
import java.io.File

data class BpkIcon(
    val name: String,
    val type: Type,
    val value: String,
) {

    enum class Type {
        Sm,
        Lg,
    }

    sealed class Parser : BpkParser<List<File>, BpkIcons> {

        object Xml : Parser() {
            override fun invoke(files: List<File>): BpkIcons {

                val iconFiles = files.filter { it.extension == "xml" }

                return iconFiles
                    .map { file ->
                        BpkIcon(
                            name = transformIconName(file.nameWithoutExtension),
                            type = if (file.nameWithoutExtension.endsWith("_sm")) Type.Sm else Type.Lg,
                            value = file.nameWithoutExtension,
                        )
                    }
                    .sortedBy { it.name.toComposeName() }
            }

            private fun transformIconName(name: String): String =
                name.removePrefix("bpk_").removeSuffix("_sm")
        }

        object Svg : Parser() {
            override fun invoke(folders: List<File>): BpkIcons {
                return folders.flatMap { folder ->
                    val type = when (folder.name) {
                        "lg" -> Type.Lg
                        "sm" -> Type.Sm
                        else -> null
                    }
                    folder.listFiles()!!.mapNotNull { file ->
                        val stream = ByteArrayOutputStream()
                        Svg2Vector.parseSvgToXml(file.toPath(), stream)
                        type?.let {
                            BpkIcon(
                                name = transformIconName(file.name),
                                type = type,
                                value = String(stream.toByteArray()),
                            )
                        }
                    }
                }
            }

            private fun transformIconName(name: String): String =
                name.removeSuffix(".svg")
        }
    }

    sealed class Format<Output> : BpkTransformer<BpkIcons, Output> {

        data class Compose(val rClass: ClassName) : Format<List<PropertySpec>>() {
            override fun invoke(source: BpkIcons): List<PropertySpec> =
                toCompose(rClass, source)
        }

        data class Xml(val rootDir: String, val metadataPath: String) : Format<Map<String, String>>() {
            override fun invoke(source: BpkIcons): Map<String, String> =
                toXml(source, rootDir, metadataPath)
        }
    }
}

typealias BpkIcons = List<BpkIcon>

private val DelegatesClass = ClassName("kotlin.properties", "Delegates")
private val SingletonMethod = MemberName("net.skyscanner.backpack.compose.utils", "singleton")
private val BpkIconClass = ClassName("net.skyscanner.backpack.compose.icon", "BpkIcon")
private val BpkIconReceiverClass = ClassName("net.skyscanner.backpack.compose.icon", "BpkIcon.Companion")
private val BpkIconsType = List::class.asClassName().parameterizedBy(BpkIconClass)

private fun String.toComposeName(): String =
    CaseFormat.UPPER_UNDERSCORE.to(
        CaseFormat.UPPER_CAMEL,
        replace("__", "_"),
    )
private fun toCompose(
    rClass: ClassName,
    source: BpkIcons,
): List<PropertySpec> = source
    .groupBy { it.name }
    .map { (name, icons) ->

        val small =
            icons.firstOrNull { it.type == BpkIcon.Type.Sm }?.value ?: icons.firstOrNull { it.type == BpkIcon.Type.Lg }?.value
                ?: error("Invalid icon format! : $name")
        val large =
            icons.firstOrNull { it.type == BpkIcon.Type.Lg }?.value ?: icons.firstOrNull { it.type == BpkIcon.Type.Sm }?.value
                ?: error("Invalid icon format! : $name")

        PropertySpec.builder(
            name = name.toComposeName(),
            type = BpkIconClass,
        )
            .receiver(BpkIconReceiverClass)
            .delegate(
                CodeBlock
                    .builder()
                    .addStatement("%T.%M(", DelegatesClass, SingletonMethod)
                    .indent()
                    .addStatement("%T(", BpkIconClass)
                    .indent()
                    .addStatement("name = %S,", name.replace("_", "-"))
                    .addStatement("small = %T.drawable.%N,", rClass, small)
                    .addStatement("large = %T.drawable.%N,", rClass, large)
                    .unindent()
                    .addStatement(")")
                    .unindent()
                    .addStatement(")")
                    .build(),
            )
            .build()
    }
    .plusElement(
        PropertySpec.builder(
            name = "values",
            type = BpkIconsType,
        )
            .receiver(BpkIconReceiverClass)
            .delegate(
                CodeBlock
                    .builder()
                    .addStatement("%T.%M(", DelegatesClass, SingletonMethod)
                    .indent()
                    .addStatement("listOf(", BpkIconClass)
                    .indent()
                    .apply {
                        source.map { it.name.toComposeName() }.distinct().forEach {
                            add("%T.%N,\n", BpkIconClass, it)
                        }
                    }
                    .unindent()
                    .addStatement(")")
                    .unindent()
                    .addStatement(")")
                    .build(),
            )
            .build(),
    )

private fun toXml(source: BpkIcons, rootDir: String, metadataPath: String): Map<String, String> {
    fun BpkIcon.fileName() =
        CaseFormat.LOWER_HYPHEN.to(CaseFormat.LOWER_UNDERSCORE, name)
            .removeSuffix(".svg")
            .let {
                val suffix = if (type == BpkIcon.Type.Sm) "_sm" else ""
                "bpk_$it$suffix"
            }

    fun BpkIcon.fileContent(metadata: Map<String, String?>) =
        value
            .replace("android:fillColor=\"#FF000000\"", "  android:fillColor=\"@color/bpkTextPrimary\"")
            .replace("android:fillColor=\"#161616\"", "  android:fillColor=\"@color/bpkTextPrimary\"")
            .replace("<path", "  <path")
            .replace("android:pathData=", "  android:pathData=")
            .replace("\"/>", "\" />")
            .let {
                if (metadata.containsKey(name) && metadata[name] == "true") {
                    it.replaceFirst("\n", "\n    android:autoMirrored=\"true\"\n")
                } else {
                    it
                }
            }

    val metadata = BpkFormat.Json(File(rootDir, metadataPath)).mapValues { (it.value as Map<String, String>)["autoMirror"] }
    return source.associate { icon ->
        icon.fileName() to icon.fileContent(metadata)
    }
}
