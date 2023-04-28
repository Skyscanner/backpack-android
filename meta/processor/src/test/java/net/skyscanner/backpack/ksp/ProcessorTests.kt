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

import org.junit.Test

class ProcessorTests {

    @Test
    fun `view story generation`() = testKsp("""
import net.skyscanner.backpack.demo.meta.StoryKind

@net.skyscanner.backpack.meta.ComponentMarker("Floating Action Button")
annotation class FabComponent

@net.skyscanner.backpack.meta.StoryMarker(isCompose = false)
annotation class ViewStory(
    @net.skyscanner.backpack.meta.StoryNameMarker val name: String = "Default",
    @net.skyscanner.backpack.meta.StoryKindMarker val kind: StoryKind = StoryKind.StoryAndScreenshot,
)

@FabComponent
@ViewStory
fun FabStory() {

}
""", """
package net.skyscanner.backpack.demo.meta

import FabStory
import kotlin.collections.List

public fun Story.Companion.all(): List<Story> = listOf(
  Story(
    name = "Default",
    kind = StoryKind.StoryAndScreenshot,
    isCompose = false,
    component = Component(
      name = "Floating Action Button",
      isToken = false,
    ),
    content = { FabStory() },
  ),
)
""",)

    @Test
    fun `compose story generation`() = testKsp("""
import net.skyscanner.backpack.demo.meta.StoryKind

@net.skyscanner.backpack.meta.ComponentMarker("Floating Action Button")
annotation class FabComponent

@net.skyscanner.backpack.meta.StoryMarker(isCompose = true)
annotation class ComposeStory(
    @net.skyscanner.backpack.meta.StoryNameMarker val name: String = "Default",
    @net.skyscanner.backpack.meta.StoryKindMarker val kind: StoryKind = StoryKind.StoryAndScreenshot,
)

@FabComponent
@ComposeStory
fun FabStory() {

}
""", """
package net.skyscanner.backpack.demo.meta

import FabStory
import kotlin.collections.List

public fun Story.Companion.all(): List<Story> = listOf(
  Story(
    name = "Default",
    kind = StoryKind.StoryAndScreenshot,
    isCompose = true,
    component = Component(
      name = "Floating Action Button",
      isToken = false,
    ),
    content = { FabStory() },
  ),
)
""",)
}
