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

package net.skyscanner.backpack.ksp.writer

import androidx.room.compiler.processing.XFiler
import androidx.room.compiler.processing.writeTo
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.asClassName
import net.skyscanner.backpack.ksp.ComponentDefinition
import net.skyscanner.backpack.ksp.StoryDefinition

private const val MetaPackage = "net.skyscanner.backpack.demo.meta"
private val StoryClass = ClassName(MetaPackage, "Story")
private val StoryCompanion = ClassName(MetaPackage, "Story.Companion")
private val ComponentClass = ClassName(MetaPackage, "Component")
private val StoriesClass = List::class.asClassName().parameterizedBy(StoryClass)

fun writeListOfStories(stories: List<StoryDefinition>, output: XFiler) {
  FunSpec
    .builder("all")
    .receiver(StoryCompanion)
    .returns(StoriesClass)
    .addCode(CodeBlock
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
    .build()
    .let {
      FileSpec
        .builder(MetaPackage, "GeneratedStories")
        .addFunction(it)
        .build()
        .writeTo(output, mode = XFiler.Mode.Aggregating)
    }
}

private fun CodeBlock.Builder.writeStoryCreator(story: StoryDefinition) =
  addStatement("%T(", StoryClass)
    .indent()
    .addStatement("name = %S,", story.name)
    .addStatement("isScreenshot = %L,", story.isScreenshot)
    .addStatement("isCompose = %L,", story.isCompose)
    .writeComponent("component", story.component)
    .writeContentBlock("content", story)
    .unindent()
    .addStatement("),")

private val AndroidLayout = ClassName("net.skyscanner.backpack.demo.ui", "AndroidLayout")

private fun CodeBlock.Builder.writeContentBlock(name: String, story: StoryDefinition) =
  when {
    story.layoutId != 0 -> addStatement("$name = { %T(%L) },", AndroidLayout, story.layoutId)
    else -> addStatement("$name = { %T() },", ClassName.bestGuess(story.reference))
  }

private fun CodeBlock.Builder.writeComponent(name: String, component: ComponentDefinition) =
  addStatement("$name = %T(", ComponentClass)
    .indent()
    .addStatement("name = %S,", component.name)
    .addStatement("link = %S,", component.link)
    .unindent()
    .addStatement("),")
