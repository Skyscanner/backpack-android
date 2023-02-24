/**
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2018 Skyscanner Ltd
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
