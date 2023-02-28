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

  fun allComponents(): List<Component>

  fun tokenComponents(): List<Component>

  fun screenshotStories(): List<Story>

  fun storiesOf(component: Component, compose: Boolean): List<Story>

  fun isComposeSupportedFor(component: Component): Boolean

  @TestOnly
  fun testStories(): List<Story>

  companion object {

    fun getInstance(): StoriesRepository = StoriesRepositoryImpl
  }
}

private object StoriesRepositoryImpl : StoriesRepository {

  private val generatedStories = Story.all()
  private val testStories = generatedStories.filter { it.component.name == "TestComponent" }
  private val allStories = generatedStories - testStories

  private val map = allStories
    .groupBy { it.component }
    .filter { (_, stories) -> stories.isNotEmpty() }
    .toSortedMap(compareBy { it.name })

  private val allComponents = map.keys.toList()

  override fun allComponents() = allComponents.filter { !it.isToken }

  override fun tokenComponents() = allComponents.filter { it.isToken }

  override fun screenshotStories() = allStories.filter { it.isScreenshot }

  override fun storiesOf(component: Component, compose: Boolean): List<Story> =
    map[component]
      ?.filter { it.isCompose == compose }
      ?.sortedBy { it.name }
      ?: emptyList()

  override fun isComposeSupportedFor(component: Component): Boolean =
    map[component]?.any { it.isCompose } ?: false

  override fun testStories() = testStories
}

@Component(name = "TestComponent")
private annotation class TestComponent

@ComposeStory(name = "TestComposeStory", screenshot = false)
@TestComponent
@Composable
internal fun TestComposeStory(modifier: Modifier = Modifier) {
  Box(modifier = modifier)
}

@ViewStory(name = "TestViewStory", screenshot = false)
@TestComponent
@Composable
internal fun TestViewStory(modifier: Modifier = Modifier) {
  Box(modifier = modifier)
}

@ComposeStory(name = "TestComposeScreenshot", screenshot = true)
@TestComponent
@Composable
internal fun TestComposeScreenshot(modifier: Modifier = Modifier) {
  Box(modifier = modifier)
}

@ComposeStory(screenshot = true)
@TestComponent
@Composable
internal fun TestDefaultStory(modifier: Modifier = Modifier) {
  Box(modifier = modifier)
}

@ViewStory(name = "TestViewScreenshot", screenshot = true)
@TestComponent
@Composable
internal fun TestViewScreenshot(modifier: Modifier = Modifier) {
  AndroidView<BpkText>(modifier = modifier) {
    text = context.getString(R.string.app_name)
  }
}
