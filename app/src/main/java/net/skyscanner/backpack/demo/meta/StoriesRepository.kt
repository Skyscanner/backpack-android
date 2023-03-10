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

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.demo.ui.AndroidView
import net.skyscanner.backpack.text.BpkText
import org.jetbrains.annotations.TestOnly

interface StoriesRepository {

  val uiComponents: List<Component>

  val tokenComponents: List<Component>

  val screenshotStories: List<Story>

  fun storiesOf(component: String, compose: Boolean): List<Story>

  fun storyOf(component: String, story: String, compose: Boolean): Story

  fun isComposeOnly(component: String): Boolean

  fun isViewOnly(component: String): Boolean

  @get:TestOnly
  val testStories: List<Story>

  companion object {

    fun getInstance(): StoriesRepository = StoriesRepositoryImpl
  }
}

private object StoriesRepositoryImpl : StoriesRepository {

  private val generatedStories = Story
    .all()
    .asSequence()

  override val testStories =
    generatedStories
      .filter { it.isTestStory() }
      .toList()

  private val stories = generatedStories - testStories.toSet()

  private val visibleStories = stories
    .filter { it.kind == StoryKind.StoryAndScreenshot || it.kind == StoryKind.DemoOnly }

  override val uiComponents =
    visibleStories
      .filter { !it.component.isToken }
      .map(Story::component)
      .distinct()
      .sortedBy(Component::name)
      .toList()

  override val tokenComponents =
    visibleStories
      .filter { it.component.isToken }
      .map(Story::component)
      .distinct()
      .sortedBy(Component::name)
      .toList()

  override val screenshotStories =
    stories
      .filter { it.kind == StoryKind.StoryAndScreenshot || it.kind == StoryKind.ScreenshotOnly }
      .toList()

  override fun storiesOf(component: String, compose: Boolean): List<Story> =
    visibleStories
      .filter { it.component.name == component && it.isCompose == compose }
      .toList()

  override fun storyOf(component: String, story: String, compose: Boolean): Story =
    stories
      .filter { it.component.name == component && it.name == story && it.isCompose == compose }
      .first()

  override fun isComposeOnly(component: String): Boolean =
    visibleStories
      .filter { it.component.name == component }
      .all { it.isCompose }

  override fun isViewOnly(component: String): Boolean =
    visibleStories
      .filter { it.component.name == component }
      .none { it.isCompose }

  private fun Story.isTestStory() =
    this.component.name == "TestComponent"
}

@Component(name = "TestComponent", isToken = true)
private annotation class TestComponent

@ComposeStory(name = "TestComposeStory", kind = StoryKind.DemoOnly)
@TestComponent
@Composable
internal fun TestComposeStory(modifier: Modifier = Modifier) {
  Box(modifier = modifier)
}

@ViewStory(name = "TestViewStory", kind = StoryKind.DemoOnly)
@TestComponent
@Composable
internal fun TestViewStory(modifier: Modifier = Modifier) {
  Box(modifier = modifier)
}

@ComposeStory(name = "TestComposeScreenshot", kind = StoryKind.ScreenshotOnly)
@TestComponent
@Composable
internal fun TestComposeScreenshot(modifier: Modifier = Modifier) {
  Box(modifier = modifier)
}

@ComposeStory(kind = StoryKind.ScreenshotOnly)
@TestComponent
@Composable
internal fun TestDefaultStory(modifier: Modifier = Modifier) {
  Box(modifier = modifier)
}

@ViewStory(name = "TestViewScreenshot", kind = StoryKind.ScreenshotOnly)
@TestComponent
@Composable
internal fun TestViewScreenshot(modifier: Modifier = Modifier) {
  AndroidView<BpkText>(modifier = modifier) {
    text = context.getString(R.string.app_name)
  }
}
