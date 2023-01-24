package net.skyscanner.backpack.ksp.visitor

import com.google.devtools.ksp.symbol.FileLocation
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.google.devtools.ksp.symbol.KSNode
import com.google.devtools.ksp.visitor.KSDefaultVisitor
import net.skyscanner.backpack.ksp.ComponentDefinition
import net.skyscanner.backpack.ksp.SampleAnnotation
import net.skyscanner.backpack.ksp.SampleDefinition
import net.skyscanner.backpack.ksp.find

object SamplesVisitor : KSDefaultVisitor<Map<String, ComponentDefinition>, SampleDefinition?>() {

  override fun visitFunctionDeclaration(function: KSFunctionDeclaration, data: Map<String, ComponentDefinition>): SampleDefinition? {
    val annotation = function.annotations.find(SampleAnnotation)
    val location = function.location

    return when {
      annotation != null && location is FileLocation -> SampleDefinition(
        kDocs = function.docString,
        body = "TODO: function body here",
        component = data[location.filePath] ?: error("No component definition is found!"),
        location = location,
      )
      else -> super.visitFunctionDeclaration(function, data)
    }
  }

  override fun defaultHandler(node: KSNode, data: Map<String, ComponentDefinition>): SampleDefinition? {
    // do nothing
    return null
  }
}
