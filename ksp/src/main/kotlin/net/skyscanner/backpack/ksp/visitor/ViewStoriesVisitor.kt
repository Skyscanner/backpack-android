package net.skyscanner.backpack.ksp.visitor

import com.google.devtools.ksp.symbol.FileLocation
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.google.devtools.ksp.symbol.KSName
import com.google.devtools.ksp.symbol.KSNode
import com.google.devtools.ksp.visitor.KSDefaultVisitor
import net.skyscanner.backpack.ksp.ComponentDefinition
import net.skyscanner.backpack.ksp.StoryDefinition
import net.skyscanner.backpack.ksp.ViewStoryAnnotation
import net.skyscanner.backpack.ksp.find
import net.skyscanner.backpack.ksp.get

object ViewStoriesVisitor : KSDefaultVisitor<Map<KSName, ComponentDefinition>, StoryDefinition?>() {

  override fun visitFunctionDeclaration(function: KSFunctionDeclaration, data: Map<KSName, ComponentDefinition>): StoryDefinition? {
    val annotation = function.annotations.find(ViewStoryAnnotation)
    val location = function.location

    val componentAnnotation = function.annotations.firstNotNullOfOrNull {
      it.annotationType.resolve().declaration.qualifiedName?.let(data::get)
    }

    return when {
      annotation != null && location is FileLocation -> StoryDefinition(
        component = componentAnnotation ?: error("No component definition is found!"),
        name = annotation[ViewStoryAnnotation.paramName],
        isCompose = false,
        isScreenshot = annotation[ViewStoryAnnotation.paramScreenshot],
        layoutId = annotation[ViewStoryAnnotation.paramLayoutId],
        reference = function.qualifiedName!!.asString(),
        location = location,
      )
      else -> super.visitFunctionDeclaration(function, data)
    }
  }

  override fun defaultHandler(node: KSNode, data: Map<KSName, ComponentDefinition>): StoryDefinition? {
    // do nothing
    return null
  }
}
