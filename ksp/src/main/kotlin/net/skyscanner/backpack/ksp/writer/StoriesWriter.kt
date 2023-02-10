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
