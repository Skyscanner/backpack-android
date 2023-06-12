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

package net.skyscanner.backpack.demo.meta

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import net.skyscanner.backpack.meta.StoryKind
import kotlin.reflect.full.memberFunctions

@Immutable
data class Story(
    val component: Component,
    val name: String,
    val isCompose: Boolean,
    val kind: StoryKind,
    val content: @Composable () -> Unit,
) {

    companion object {

        val all: List<Story> by lazy {
            val kclass = try {
                Class.forName("net.skyscanner.backpack.demo.meta.KspGeneratedStories").kotlin
            } catch (e: ClassNotFoundException) {
                null
            }
            val listFunction = kclass?.memberFunctions?.find { it.name == "list" }
            listFunction?.call(kclass.objectInstance) as? List<Story> ?: emptyList()
        }
    }
}
