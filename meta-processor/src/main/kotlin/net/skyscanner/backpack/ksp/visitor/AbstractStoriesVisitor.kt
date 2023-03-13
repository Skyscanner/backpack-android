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
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.google.devtools.ksp.symbol.KSName
import com.google.devtools.ksp.symbol.KSNode
import com.google.devtools.ksp.visitor.KSDefaultVisitor
import net.skyscanner.backpack.ksp.AnnotationDefinition
import net.skyscanner.backpack.ksp.AnnotationParam
import net.skyscanner.backpack.ksp.ComponentDefinition
import net.skyscanner.backpack.ksp.EnumValue
import net.skyscanner.backpack.ksp.StoryDefinition
import net.skyscanner.backpack.ksp.find
import net.skyscanner.backpack.ksp.get

sealed class AbstractStoriesVisitor(
  private val annotationDefinition: AnnotationDefinition,
  private val paramName: AnnotationParam<String>,
  private val paramKind: AnnotationParam<EnumValue>,
  private val isCompose: Boolean,
) : KSDefaultVisitor<Map<KSName, ComponentDefinition>, StoryDefinition?>() {

  override fun visitFunctionDeclaration(function: KSFunctionDeclaration, data: Map<KSName, ComponentDefinition>): StoryDefinition? {
    val annotation = function.annotations.find(annotationDefinition)
    val location = function.location

    val componentAnnotation = function.annotations.firstNotNullOfOrNull {
      it.annotationType.resolve().declaration.qualifiedName?.let(data::get)
    }

    return when {
      annotation != null && location is FileLocation -> StoryDefinition(
        component = componentAnnotation ?: error("No component definition is found!"),
        name = annotation[paramName],
        isCompose = isCompose,
        kind = annotation[paramKind],
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
