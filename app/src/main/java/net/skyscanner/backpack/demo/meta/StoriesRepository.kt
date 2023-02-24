package net.skyscanner.backpack.demo.meta

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.jetbrains.annotations.TestOnly

interface StoriesRepository {

  fun allComponents(): List<Component>

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
  private val testStories = generatedStories.filter { it.component.name != "TestComponent" }
  private val allStories = generatedStories - testStories

  private val map = allStories
    .groupBy { it.component }
    .filter { (_, stories) -> stories.isNotEmpty() }
    .toSortedMap(compareBy { it.name })

  private val components = map.keys.toList()

  override fun allComponents() = components

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

@Component(name = "TestComponent", link = "test")
private annotation class TestComponent

@ComposeStory(screenshot = false)
@TestComponent
@Composable
internal fun TestComposeStory(modifier: Modifier = Modifier) {
  Box(modifier = modifier)
}

@ViewStory(screenshot = false)
@TestComponent
@Composable
internal fun TestViewStory(modifier: Modifier = Modifier) {
  Box(modifier = modifier)
}

@ComposeStory(screenshot = true)
@TestComponent
@Composable
internal fun TestComposeScreenshot(modifier: Modifier = Modifier) {
  Box(modifier = modifier)
}

@ViewStory(screenshot = true)
@TestComponent
@Composable
internal fun TestViewScreenshot(modifier: Modifier = Modifier) {
  Box(modifier = modifier)
}
