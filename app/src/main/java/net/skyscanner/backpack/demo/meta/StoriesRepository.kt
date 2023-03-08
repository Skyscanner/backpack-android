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

// todo: refactor with properties
private object StoriesRepositoryImpl : StoriesRepository {

  private val generatedStories = Story.all()

  override val testStories =
    generatedStories.filter { it.component.name == "TestComponent" }

  private val allStories = generatedStories - testStories

  private val allComponents = allStories
    .filter { it.kind == StoryKind.StoryAndScreenshot || it.kind == StoryKind.StoryOnly }
    .map { it.component }
    .distinct()
    .sortedBy { it.name }

  override val uiComponents get() = allComponents.filter { !it.isToken }

  override val tokenComponents get() = allComponents.filter { it.isToken }

  override val screenshotStories get() = allStories.filter { it.kind == StoryKind.StoryAndScreenshot || it.kind == StoryKind.ScreenshotOnly }

  private val storiesMap = allStories
    .groupBy { it.component.name }
    .filter { (_, stories) -> stories.isNotEmpty() }

  override fun storiesOf(component: String, compose: Boolean): List<Story> =
    storiesMap.getValue(component)
      .filter { it.isCompose == compose }

  override fun storyOf(component: String, story: String, compose: Boolean): Story =
    storiesMap.getValue(component)
      .first { it.name == story && it.isCompose == compose }

  override fun isComposeOnly(component: String): Boolean =
    storiesMap.getValue(component)
      .all { it.isCompose }

  override fun isViewOnly(component: String): Boolean =
    storiesMap.getValue(component)
      .all { !it.isCompose }
}

@Component(name = "TestComponent", isToken = true)
private annotation class TestComponent

@ComposeStory(name = "TestComposeStory", kind = StoryKind.StoryOnly)
@TestComponent
@Composable
internal fun TestComposeStory(modifier: Modifier = Modifier) {
  Box(modifier = modifier)
}

@ViewStory(name = "TestViewStory", kind = StoryKind.StoryOnly)
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
