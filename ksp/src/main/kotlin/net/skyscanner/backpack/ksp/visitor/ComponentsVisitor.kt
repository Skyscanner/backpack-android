package net.skyscanner.backpack.ksp.visitor

import com.google.devtools.ksp.symbol.FileLocation
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSVisitorVoid
import net.skyscanner.backpack.ksp.ComponentDefinition
import net.skyscanner.backpack.ksp.Names
import net.skyscanner.backpack.ksp.get

class ComponentsVisitor(
  private val output: MutableMap<String, ComponentDefinition>,
) : KSVisitorVoid() {

  override fun visitClassDeclaration(classDeclaration: KSClassDeclaration, data: Unit) {
    super.visitClassDeclaration(classDeclaration, data)
    val annotation = classDeclaration.annotations.find { it.shortName.getShortName() == Names.ComponentAnnotation }
    val location = classDeclaration.location
    if (annotation != null && location is FileLocation) {
      output[location.filePath] = ComponentDefinition(
        name = annotation["name"],
        link = annotation["link"],
        kind = annotation["kind"],
        location = location,
      )
    }
  }
}
