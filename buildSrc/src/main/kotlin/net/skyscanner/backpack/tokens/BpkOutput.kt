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

import com.google.common.io.Resources
import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec
import java.io.File
import java.nio.charset.StandardCharsets

sealed class BpkOutput<Input> : (Input) -> Boolean {

    data class KotlinFile(
        val srcDir: String,
        val pkg: String,
    ) : BpkOutput<TypeSpec>() {

        override fun invoke(typeSpec: TypeSpec): Boolean {
            FileSpec.builder(pkg, typeSpec.name!!)
                .indent(" ".repeat(4))
                .suppressWarningTypes("RedundantVisibilityModifier", "unused")
                .addType(typeSpec)
                .build()
                .writeWithCopyright(File(srcDir))
            return true
        }
    }

    data class KotlinFiles(
        val srcDir: String,
        val pkg: String,
    ) : BpkOutput<List<TypeSpec>>() {

        override fun invoke(typeSpecs: List<TypeSpec>): Boolean {
            typeSpecs.forEach { typeSpec -> KotlinFile(srcDir, pkg).invoke(typeSpec) }
            return true
        }
    }

    data class KotlinExtensionFile(
        val srcDir: String,
        val pkg: String,
        val name: String,
        val rClass: ClassName? = null,
    ) : BpkOutput<List<PropertySpec>>() {

        override fun invoke(properties: List<PropertySpec>): Boolean {
            FileSpec.builder(pkg, name)
                .indent(" ".repeat(4))
                .suppressWarningTypes("RedundantVisibilityModifier", "unused")
                .apply {
                    // Add R class import if provided (for icon tokens)
                    rClass?.let { addImport(it.packageName, it.simpleName) }
                    properties.forEach { addProperty(it) }
                }
                .build()
                .writeWithCopyright(File(srcDir))
            return true
        }
    }

    data class XmlFile(
        val srcDir: String,
        val folder: String,
        val name: String,
    ) : BpkOutput<String>() {
        override fun invoke(content: String): Boolean {
            content.writeToFile(srcDir, folder, name)
            return true
        }
    }

    data class XmlFiles(
        val srcDir: String,
        val folder: String,
        val name: String,
    ) : BpkOutput<Map<String, String>>() {
        override fun invoke(contents: Map<String, String>): Boolean {
            contents.forEach { (folderSuffix, content) -> content.writeToFile(srcDir, folder + folderSuffix, name) }
            return true
        }
    }

    data class XmlIconFiles(
        val srcDir: String,
    ) : BpkOutput<Map<String, String>>() {
        override fun invoke(contents: Map<String, String>): Boolean {
            val folder = File(srcDir)
            folder.deleteRecursively()
            folder.mkdir()
            contents.forEach { (name, content) ->
                val target = File(srcDir, "$name.xml")
                target.createNewFile()

                target.writeText(content)
            }
            return true
        }
    }
}

private fun String.writeToFile(srcDir: String, folder: String, name: String) {
    val target = File(File(srcDir, folder), "backpack.$name.xml")
    target.createNewFile()

    val template = Resources.toString(Resources.getResource("resource_file_template.txt"), StandardCharsets.UTF_8)

    target.writeText(template.replace("{{content}}", this))
}

private fun FileSpec.writeWithCopyright(directory: File) {
    toBuilder()
        .addFileComment("Auto-generated: do not edit")
        .build()
        .writeTo(directory)

    val target = File(File(directory, packageName.replace(".", "/")), "$name.kt")
    require(target.exists()) { "Unable to write copyright header" }

    val source = target.readText()
    val copyright = Resources.toString(Resources.getResource("copyright.txt"), StandardCharsets.UTF_8)

    target.writeText(copyright + source)
}

private fun FileSpec.Builder.suppressWarningTypes(vararg types: String): FileSpec.Builder {
    if (types.isEmpty()) {
        return this
    }

    val format = "%S, ".repeat(types.count()).trimEnd(',', ' ')
    return addAnnotation(
        AnnotationSpec.builder(ClassName("", "Suppress"))
            .addMember(format, *types)
            .build(),
    )
}
