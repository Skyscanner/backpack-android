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
    fun `view story generation`() {
        val input = """
    import net.skyscanner.backpack.meta.StoryKind

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
    """

        val output = """
    package net.skyscanner.backpack.demo.meta

    import FabStory
    import kotlin.collections.List
    import net.skyscanner.backpack.meta.StoryKind

    public fun Story.Companion.all(): List<Story> = listOf(
      Story(
        name = "Fail",
        kind = StoryKind.StoryAndScreenshot,
        isCompose = false,
        component = Component(
          name = "Floating Action Button",
          isToken = false,
        ),
        content = { FabStory() },
      ),
    )
    """

        testKsp(input, output)
    }

    @Test
    fun `compose story generation`() {
        val input = """
    import net.skyscanner.backpack.meta.StoryKind

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
    """

        val output = """
    package net.skyscanner.backpack.demo.meta

    import FabStory
    import kotlin.collections.List
    import net.skyscanner.backpack.meta.StoryKind

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
    """

        testKsp(input, output)
    }

    @Test
    fun `custom story name`() {
        val input = """
    import net.skyscanner.backpack.meta.StoryKind

    @net.skyscanner.backpack.meta.ComponentMarker("Floating Action Button")
    annotation class FabComponent

    @net.skyscanner.backpack.meta.StoryMarker(isCompose = true)
    annotation class ComposeStory(
        @net.skyscanner.backpack.meta.StoryNameMarker val name: String = "Default",
        @net.skyscanner.backpack.meta.StoryKindMarker val kind: StoryKind = StoryKind.StoryAndScreenshot,
    )

    @FabComponent
    @ComposeStory("Custom name")
    fun FabStory() {

    }
    """

        val output = """
    package net.skyscanner.backpack.demo.meta

    import FabStory
    import kotlin.collections.List
    import net.skyscanner.backpack.meta.StoryKind

    public fun Story.Companion.all(): List<Story> = listOf(
      Story(
        name = "Custom name",
        kind = StoryKind.StoryAndScreenshot,
        isCompose = true,
        component = Component(
          name = "Floating Action Button",
          isToken = false,
        ),
        content = { FabStory() },
      ),
    )
    """

        testKsp(input, output)
    }

    @Test
    fun `kind is DemoOnly`() {
        val input = """
    import net.skyscanner.backpack.meta.StoryKind

    @net.skyscanner.backpack.meta.ComponentMarker("Floating Action Button")
    annotation class FabComponent

    @net.skyscanner.backpack.meta.StoryMarker(isCompose = true)
    annotation class ComposeStory(
        @net.skyscanner.backpack.meta.StoryNameMarker val name: String = "Default",
        @net.skyscanner.backpack.meta.StoryKindMarker val kind: StoryKind = StoryKind.StoryAndScreenshot,
    )

    @FabComponent
    @ComposeStory(kind = StoryKind.DemoOnly)
    fun FabStory() {

    }
    """

        val output = """
    package net.skyscanner.backpack.demo.meta

    import FabStory
    import kotlin.collections.List
    import net.skyscanner.backpack.meta.StoryKind

    public fun Story.Companion.all(): List<Story> = listOf(
      Story(
        name = "Default",
        kind = StoryKind.DemoOnly,
        isCompose = true,
        component = Component(
          name = "Floating Action Button",
          isToken = false,
        ),
        content = { FabStory() },
      ),
    )
    """

        testKsp(input, output)
    }

    @Test
    fun `kind is ScreenshotOnly`() {
        val input = """
    import net.skyscanner.backpack.meta.StoryKind

    @net.skyscanner.backpack.meta.ComponentMarker("Floating Action Button")
    annotation class FabComponent

    @net.skyscanner.backpack.meta.StoryMarker(isCompose = true)
    annotation class ComposeStory(
        @net.skyscanner.backpack.meta.StoryNameMarker val name: String = "Default",
        @net.skyscanner.backpack.meta.StoryKindMarker val kind: StoryKind = StoryKind.StoryAndScreenshot,
    )

    @FabComponent
    @ComposeStory(kind = StoryKind.ScreenshotOnly)
    fun FabStory() {

    }
    """

        val output = """
    package net.skyscanner.backpack.demo.meta

    import FabStory
    import kotlin.collections.List
    import net.skyscanner.backpack.meta.StoryKind

    public fun Story.Companion.all(): List<Story> = listOf(
      Story(
        name = "Default",
        kind = StoryKind.ScreenshotOnly,
        isCompose = true,
        component = Component(
          name = "Floating Action Button",
          isToken = false,
        ),
        content = { FabStory() },
      ),
    )
    """

        testKsp(input, output)
    }

    @Test
    fun `token component generation`() {
        val input = """
    import net.skyscanner.backpack.meta.StoryKind

    @net.skyscanner.backpack.meta.ComponentMarker("Floating Action Button", isToken = true)
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
    """

        val output = """
    package net.skyscanner.backpack.demo.meta

    import FabStory
    import kotlin.collections.List
    import net.skyscanner.backpack.meta.StoryKind

    public fun Story.Companion.all(): List<Story> = listOf(
      Story(
        name = "Default",
        kind = StoryKind.StoryAndScreenshot,
        isCompose = true,
        component = Component(
          name = "Floating Action Button",
          isToken = true,
        ),
        content = { FabStory() },
      ),
    )
    """

        testKsp(input, output)
    }
}
