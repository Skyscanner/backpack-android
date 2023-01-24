package net.skyscanner.backpack.ksp.visitor

import com.google.devtools.ksp.symbol.FileLocation
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.google.devtools.ksp.symbol.KSNode
import com.google.devtools.ksp.visitor.KSDefaultVisitor
import net.skyscanner.backpack.ksp.ComponentDefinition
import net.skyscanner.backpack.ksp.Names
import net.skyscanner.backpack.ksp.StoryDefinition
import net.skyscanner.backpack.ksp.get

object StoriesVisitor : KSDefaultVisitor<Map<String, ComponentDefinition>, StoryDefinition?>() {

  override fun visitFunctionDeclaration(function: KSFunctionDeclaration, data: Map<String, ComponentDefinition>): StoryDefinition? {
    val annotation = function.annotations.find { it.shortName.getShortName() == Names.StoryAnnotation }
    val location = function.location

    return when {
      annotation != null && location is FileLocation -> StoryDefinition(
        name = annotation["name"],
        screenshot = annotation["screenshot"],
        component = data[location.filePath] ?: error("No component definition is found!"),
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
