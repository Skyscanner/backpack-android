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

import org.junit.Assert.assertEquals
import org.junit.Test

class ProcessorTests {

    @Test
    fun `view story generation`() = testKsp("""
import net.skyscanner.backpack.demo.meta.StoryKind

@net.skyscanner.backpack.meta.ComponentMarker("Floating Action Button")
annotation class FabComponent

@net.skyscanner.backpack.meta.StoryMarker(isCompose = false)
annotation class ViewStory(
    @net.skyscanner.backpack.meta.StoryKindMarker val name: String = "Default",
    @net.skyscanner.backpack.meta.StoryNameMarker val kind: StoryKind = StoryKind.StoryAndScreenshot,
)

@FabComponent
@ViewStory
fun FabStory() {

}
        """.trimIndent(),) {
        assertEquals("", it)
    }

    @Test
    fun `compose story generation`() = testKsp("""
import androidx.compose.runtime.Composable
import net.skyscanner.backpack.meta.StoryKindMarker
import net.skyscanner.backpack.meta.StoryMarker
import net.skyscanner.backpack.meta.StoryNameMarker

@ComponentMarker("Floating Action Button")
annotation class FabComponent

@StoryMarker(isCompose = true)
annotation class ComposeStory(
    @StoryNameMarker val name: String = "Default",
    @StoryKindMarker val kind: StoryKind = StoryKind.StoryAndScreenshot,
)

@FabComponent
@Composable
@ComposeStory
fun FabStory() {

}
        """.trimIndent(),) {
        assertEquals("", it)
    }
}
