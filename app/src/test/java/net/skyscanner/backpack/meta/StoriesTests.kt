package net.skyscanner.backpack.meta

import net.skyscanner.backpack.demo.meta.StoriesRepository
import org.junit.Assert
import org.junit.Test

class StoriesTests {

    private val repository = StoriesRepository.getInstance()

    @Test
    fun assertNoDemoOnlyStoriesInScreenshots() {
        Assert.assertTrue(repository.screenshotStories.none { it.kind == StoryKind.DemoOnly })
    }

    @Test
    fun assertNoScreenshotOnlyStoriesInDemos() {
        Assert.assertTrue(repository.uiComponents.none {
            repository.storiesOf(it.name, compose = true).any { it.kind == StoryKind.ScreenshotOnly }
        })
        Assert.assertTrue(repository.uiComponents.none {
            repository.storiesOf(it.name, compose = false).any { it.kind == StoryKind.ScreenshotOnly }
        })
        Assert.assertTrue(repository.tokenComponents.none {
            repository.storiesOf(it.name, compose = true).any { it.kind == StoryKind.ScreenshotOnly }
        })
        Assert.assertTrue(repository.tokenComponents.none {
            repository.storiesOf(it.name, compose = false).any { it.kind == StoryKind.ScreenshotOnly }
        })
    }
}
