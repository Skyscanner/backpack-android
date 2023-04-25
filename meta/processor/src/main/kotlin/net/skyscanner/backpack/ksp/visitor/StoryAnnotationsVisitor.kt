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
import net.skyscanner.backpack.ksp.AnnotationDefinition
import net.skyscanner.backpack.ksp.StoryAnnotationDefinition
import net.skyscanner.backpack.ksp.StoryKindAnnotation
import net.skyscanner.backpack.ksp.StoryMarkerAnnotation
import net.skyscanner.backpack.ksp.StoryNameAnnotation
import net.skyscanner.backpack.ksp.find
import net.skyscanner.backpack.ksp.get

object StoryAnnotationsVisitor : KSDefaultVisitor<Unit, StoryAnnotationDefinition?>() {

    override fun visitClassDeclaration(classDeclaration: KSClassDeclaration, data: Unit): StoryAnnotationDefinition? {
        val annotation = classDeclaration.annotations.find(StoryMarkerAnnotation)
        val location = classDeclaration.location
        val qualifiedName = classDeclaration.qualifiedName

        val paramName = classDeclaration
            .primaryConstructor!!
            .parameters
            .find { it.annotations.find(StoryNameAnnotation) != null }
            ?.name

        val paramKind = classDeclaration
            .primaryConstructor!!
            .parameters
            .find { it.annotations.find(StoryKindAnnotation) != null }
            ?.name

        return when {
            annotation != null && qualifiedName != null && paramName != null && paramKind != null && location is FileLocation ->
                StoryAnnotationDefinition(
                    annotation = AnnotationDefinition(classDeclaration),
                    namePropertyName = paramName.getShortName(),
                    kindPropertyName = paramKind.getShortName(),
                    isCompose = annotation[StoryMarkerAnnotation.paramIsCompose],
                )

            else -> super.visitClassDeclaration(classDeclaration, data)
        }
    }

    override fun defaultHandler(node: KSNode, data: Unit): StoryAnnotationDefinition? {
        // do nothing
        return null
    }
}
