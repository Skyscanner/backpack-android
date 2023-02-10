package net.skyscanner.backpack.ksp.visitor

import com.google.devtools.ksp.symbol.FileLocation
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSNode
import com.google.devtools.ksp.visitor.KSDefaultVisitor
import net.skyscanner.backpack.ksp.ComponentAnnotation
import net.skyscanner.backpack.ksp.ComponentDefinition
import net.skyscanner.backpack.ksp.find
import net.skyscanner.backpack.ksp.get

object ComponentsVisitor : KSDefaultVisitor<Unit, ComponentDefinition?>() {

  override fun visitClassDeclaration(classDeclaration: KSClassDeclaration, data: Unit): ComponentDefinition? {
    val annotation = classDeclaration.annotations.find(ComponentAnnotation)
    val location = classDeclaration.location
    val qualifiedName = classDeclaration.qualifiedName

    return when {
      annotation != null && qualifiedName != null && location is FileLocation ->
        ComponentDefinition(
          id = qualifiedName,
          name = annotation[ComponentAnnotation.paramName],
          link = annotation[ComponentAnnotation.paramLink],
          location = location,
        )
      else -> super.visitClassDeclaration(classDeclaration, data)
    }
  }

  override fun defaultHandler(node: KSNode, data: Unit): ComponentDefinition? {
    // do nothing
    return null
  }
}
