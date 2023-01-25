package net.skyscanner.backpack.ksp.writer

import androidx.room.compiler.processing.XFiler
import androidx.room.compiler.processing.writeTo
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.MemberName
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.asClassName
import net.skyscanner.backpack.ksp.ComponentDefinition
import net.skyscanner.backpack.ksp.PreviewProviderDefinition
import net.skyscanner.backpack.ksp.StoryDefinition

private const val MetaPackage = "net.skyscanner.backpack.demo.meta"
private val StoriesClass = ClassName(MetaPackage, "Stories")
private val StoryEntryClass = ClassName(MetaPackage, "StoryEntry")
private val ComponentEntryClass = ClassName(MetaPackage, "ComponentEntry")
private val StoryEntriesClass = List::class.asClassName().parameterizedBy(StoryEntryClass)

fun writeListOfStories(stories: List<StoryDefinition>, output: XFiler) {
  FunSpec
    .builder("all")
    .receiver(StoriesClass)
    .returns(StoryEntriesClass)
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
  addStatement("%T(", StoryEntryClass)
    .indent()
    .addStatement("name = %S,", story.name)
    .addStatement("screenshot = %L,", story.screenshot)
    .writeComponent("component", story.component)
    .writeContentBlock("content", story.reference, story.previewProvider)
    .unindent()
    .addStatement("),")

private val RememberReference = MemberName("androidx.compose.runtime", "remember")
private val ColumnReference = MemberName("androidx.compose.foundation.layout", "Column")
private val TextReference = MemberName("net.skyscanner.backpack.compose.text", "BpkText")

private fun CodeBlock.Builder.writeContentBlock(name: String, reference: String, provider: PreviewProviderDefinition?) =
  if (provider == null) {
    addStatement("$name = { %T() },", ClassName.bestGuess(reference))
  } else {
    beginControlFlow("$name =")
      .addStatement("val provider = %M { %T() }", RememberReference, ClassName.bestGuess(provider.type))
      .addStatement("val entries = provider.values.take(%L)", provider.limit)
      .beginControlFlow("%M(%T.%M)", ColumnReference)
      .beginControlFlow("for (entry in entries)")
      .addStatement("%M(entry.toString())", TextReference)
      .addStatement("%T(${provider.name} = entry)", ClassName.bestGuess(reference))
      .endControlFlow()
      .endControlFlow()
      .endControlFlow()
  }

private fun CodeBlock.Builder.writeComponent(name: String, component: ComponentDefinition) =
  addStatement("$name = %T(", ComponentEntryClass)
    .indent()
    .addStatement("name = %S,", component.name)
    .addStatement("link = %S,", component.link)
    .addStatement("kind = %T.%N,", ClassName.bestGuess(component.kind.type), component.kind.value)
    .unindent()
    .addStatement("),")
