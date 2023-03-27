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

package net.skyscanner.backpack.ksp

import com.google.devtools.ksp.symbol.KSAnnotation
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.google.devtools.ksp.symbol.KSPropertyDeclaration
import com.google.devtools.ksp.symbol.impl.kotlin.KSFunctionDeclarationImpl
import com.google.devtools.ksp.symbol.impl.kotlin.KSPropertyDeclarationImpl
import org.jetbrains.kotlin.psi.KtDeclarationWithBody

operator fun <T> KSAnnotation.get(param: AnnotationParam<T>): T =
    arguments.first { it.name!!.getShortName() == param.name }.value!!.let(param::parse)

fun Sequence<KSAnnotation>.find(definition: AnnotationDefinition): KSAnnotation? =
    find { it.shortName.getShortName() == definition.simpleName }

val KSFunctionDeclaration.bodyText: String
    get() =
        (this as KSFunctionDeclarationImpl).ktFunction.bodyText

val KSPropertyDeclaration.bodyText: String
    get() =
        (this as KSPropertyDeclarationImpl).ktProperty.getter?.bodyText ?: ""

private val KtDeclarationWithBody.bodyText: String
    get() =
        bodyExpression?.text ?: bodyBlockExpression?.statements?.joinToString("\n") { it.text } ?: ""
