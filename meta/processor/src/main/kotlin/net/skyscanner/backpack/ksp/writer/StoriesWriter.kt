/**
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2025 Skyscanner Ltd
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

package net.skyscanner.backpack.ksp.writer

import com.google.devtools.ksp.processing.CodeGenerator
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.asClassName
import com.squareup.kotlinpoet.ksp.writeTo
import net.skyscanner.backpack.ksp.ComponentDefinition
import net.skyscanner.backpack.ksp.StoryDefinition

private const val MetaPackage = "net.skyscanner.backpack.demo.meta"
private val StoryClass = ClassName(MetaPackage, "Story")
private val ComponentClass = ClassName(MetaPackage, "Component")
private val StoriesClass = List::class.asClassName().parameterizedBy(StoryClass)

fun writeListOfStories(stories: List<StoryDefinition>, output: CodeGenerator) {
    if (stories.isEmpty()) return

    TypeSpec
        .objectBuilder("KspGeneratedStories")
        .addModifiers(KModifier.INTERNAL)
        .addFunction(
            FunSpec
                .builder("list")
                .returns(StoriesClass)
                .addCode(
                    CodeBlock
                        .builder()
                        .add("return listOf(\n")
                        .indent()
                        .apply {
                            stories.forEach {
                                writeStoryCreator(it)
                            }
                        }
                        .unindent()
                        .add(")")
                        .build(),
                )
                .build(),
        )
        .build()
        .let {
            FileSpec
                .builder(MetaPackage, "KspGeneratedStories")
                .addType(it)
                .build()
                .writeTo(codeGenerator = output, aggregating = true)
        }
}

private fun CodeBlock.Builder.writeStoryCreator(story: StoryDefinition) =
    addStatement("%T(", StoryClass)
        .indent()
        .addStatement("name = %S,", story.name)
        .addStatement("kind = %T.%N,", ClassName.bestGuess(story.kind.type), story.kind.value)
        .addStatement("isCompose = %L,", story.isCompose)
        .writeComponent("component", story.component)
        .addStatement("${"content"} = { %T() },", ClassName.bestGuess(story.reference))
        .unindent()
        .addStatement("),")

private fun CodeBlock.Builder.writeComponent(name: String, component: ComponentDefinition) =
    addStatement("$name = %T(", ComponentClass)
        .indent()
        .addStatement("name = %S,", component.name)
        .addStatement("isToken = %L,", component.isToken)
        .unindent()
        .addStatement("),")
