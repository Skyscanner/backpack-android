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

import net.skyscanner.backpack.meta.ComponentMarker
import net.skyscanner.backpack.meta.StoryKindMarker
import net.skyscanner.backpack.meta.StoryMarker
import net.skyscanner.backpack.meta.StoryNameMarker

object StoryNameAnnotation : AnnotationDefinition(StoryNameMarker::class)

object StoryKindAnnotation : AnnotationDefinition(StoryKindMarker::class)

object StoryMarkerAnnotation : AnnotationDefinition(StoryMarker::class) {
    val paramIsCompose = booleanParamOf("isCompose")
}

object ComponentAnnotation : AnnotationDefinition(ComponentMarker::class) {
    val paramName = stringParamOf("name")
    val paramToken = booleanParamOf("isToken")
}
