package net.skyscanner.backpack.ksp.visitor

import com.google.devtools.ksp.symbol.FileLocation
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.google.devtools.ksp.symbol.KSNode
import com.google.devtools.ksp.visitor.KSDefaultVisitor
import net.skyscanner.backpack.ksp.ComponentDefinition
import net.skyscanner.backpack.ksp.StoryAnnotation
import net.skyscanner.backpack.ksp.StoryDefinition
import net.skyscanner.backpack.ksp.find
import net.skyscanner.backpack.ksp.get

object StoriesVisitor : KSDefaultVisitor<Map<String, ComponentDefinition>, StoryDefinition?>() {

  override fun visitFunctionDeclaration(function: KSFunctionDeclaration, data: Map<String, ComponentDefinition>): StoryDefinition? {
    val annotation = function.annotations.find(StoryAnnotation)
    val location = function.location

    return when {
      annotation != null && location is FileLocation -> StoryDefinition(
        name = annotation[StoryAnnotation.paramName],
        screenshot = annotation[StoryAnnotation.paramScreenshot],
        component = data[location.filePath] ?: error("No component definition is found!"),
        previewProvider = PreviewProviderFinder.find(function.parameters),
        location = location,
      )
      else -> super.visitFunctionDeclaration(function, data)
    }
  }

  override fun defaultHandler(node: KSNode, data: Map<String, ComponentDefinition>): StoryDefinition? {
    // do nothing
    return null
  }
}
