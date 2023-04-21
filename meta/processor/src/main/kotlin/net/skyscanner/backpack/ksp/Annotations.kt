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

import com.google.devtools.ksp.symbol.KSType
import kotlin.reflect.KClass

abstract class AnnotationDefinition(kClass: KClass<*>) {
    val simpleName: String = kClass.simpleName!!
    val qualifiedName: String = kClass.qualifiedName!!
    val pkg = qualifiedName.removeSuffix(".$simpleName")
}

abstract class AnnotationParam<Type>(val name: String) {
    abstract fun parse(value: Any): Type
}

fun AnnotationDefinition.stringParamOf(name: String): AnnotationParam<String> =
    object : AnnotationParam<String>(name) {
        override fun parse(value: Any): String =
            value as String
    }

fun AnnotationDefinition.booleanParamOf(name: String): AnnotationParam<Boolean> =
    object : AnnotationParam<Boolean>(name) {
        override fun parse(value: Any): Boolean =
            value as Boolean
    }

fun AnnotationDefinition.intParamOf(name: String): AnnotationParam<Int> =
    object : AnnotationParam<Int>(name) {
        override fun parse(value: Any): Int =
            value as Int
    }

fun AnnotationDefinition.enumParamOf(name: String): AnnotationParam<EnumValue> =
    object : AnnotationParam<EnumValue>(name) {
        override fun parse(value: Any): EnumValue =
            EnumValue(
                value = (value as KSType).declaration.simpleName.getShortName(),
                type = value.declaration.qualifiedName!!.getQualifier(),
            )
    }

fun AnnotationDefinition.typeParamOf(name: String): AnnotationParam<String> =
    object : AnnotationParam<String>(name) {
        override fun parse(value: Any): String =
            (value as KSType).declaration.qualifiedName!!.asString()
    }

fun AnnotationDefinition.enumParamsOf(name: String): AnnotationParam<List<EnumValue>> =
    object : AnnotationParam<List<EnumValue>>(name) {
        override fun parse(value: Any): List<EnumValue> =
            (value as List<KSType>)
                .map {
                    EnumValue(
                        value = it.declaration.simpleName.getShortName(),
                        type = it.declaration.qualifiedName!!.getQualifier(),
                    )
                }
    }
