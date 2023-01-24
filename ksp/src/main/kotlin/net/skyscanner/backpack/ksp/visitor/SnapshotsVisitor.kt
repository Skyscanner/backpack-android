package net.skyscanner.backpack.ksp.visitor

import com.google.devtools.ksp.symbol.FileLocation
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.google.devtools.ksp.symbol.KSNode
import com.google.devtools.ksp.visitor.KSDefaultVisitor
import net.skyscanner.backpack.ksp.ComponentDefinition
import net.skyscanner.backpack.ksp.SnapshotAnnotation
import net.skyscanner.backpack.ksp.SnapshotDefinition
import net.skyscanner.backpack.ksp.find
import net.skyscanner.backpack.ksp.get

object SnapshotsVisitor : KSDefaultVisitor<Map<String, ComponentDefinition>, SnapshotDefinition?>() {

  override fun visitFunctionDeclaration(function: KSFunctionDeclaration, data: Map<String, ComponentDefinition>): SnapshotDefinition? {
    val annotation = function.annotations.find(SnapshotAnnotation)
    val location = function.location

    return when {
      annotation != null && location is FileLocation -> SnapshotDefinition(
        variants = annotation[SnapshotAnnotation.paramVariants],
        component = data[location.filePath] ?: error("No component definition is found!"),
        location = location,
      )
      else -> super.visitFunctionDeclaration(function, data)
    }
  }

  override fun defaultHandler(node: KSNode, data: Map<String, ComponentDefinition>): SnapshotDefinition? {
    // do nothing
    return null
  }
}
