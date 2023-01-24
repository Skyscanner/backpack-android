package net.skyscanner.backpack.ksp.visitor

import com.google.devtools.ksp.symbol.FileLocation
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.google.devtools.ksp.symbol.KSVisitorVoid
import net.skyscanner.backpack.ksp.ComponentDefinition
import net.skyscanner.backpack.ksp.Names
import net.skyscanner.backpack.ksp.StoryDefinition
import net.skyscanner.backpack.ksp.get

class StoriesVisitor(
  private val components: Map<String, ComponentDefinition>,
  private val output: MutableList<StoryDefinition>,
) : KSVisitorVoid() {

  override fun visitFunctionDeclaration(function: KSFunctionDeclaration, data: Unit) {
    super.visitFunctionDeclaration(function, data)
    val annotation = function.annotations.find { it.shortName.getShortName() == Names.StoryAnnotation }
    val location = function.location
    if (annotation != null && location is FileLocation) {
      output += StoryDefinition(
        name = annotation["name"],
        screenshot = annotation["screenshot"],
        component = components[location.filePath]!!,
        location = location,
      )
    }
  }
}
