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

import com.google.devtools.ksp.symbol.FileLocation
import com.google.devtools.ksp.symbol.KSName

data class ComponentDefinition(
    val id: KSName,
    val name: String,
    val isToken: Boolean,
    val location: FileLocation,
)

data class StoryDefinition(
    val component: ComponentDefinition,
    val name: String,
    val isCompose: Boolean,
    val kind: EnumValue,
    val reference: String,
    val location: FileLocation,
)

data class SampleDefinition(
    val component: ComponentDefinition,
    val kDocs: String,
    val sourceCode: String,
    val reference: String,
    val location: FileLocation,
)

data class EnumValue(
    val value: String,
    val type: String,
)
