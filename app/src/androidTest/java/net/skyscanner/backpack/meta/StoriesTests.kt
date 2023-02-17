package net.skyscanner.backpack.meta

import net.skyscanner.backpack.demo.meta.Story
import net.skyscanner.backpack.demo.meta.all
import org.junit.Assert.assertTrue
import org.junit.Test

class StoriesTests {

  @Test
  fun assertStoriesAreNotEmpty() {
    assertTrue(Story.all().isNotEmpty())
  }

  @Test
  fun assertStoriesIncludeView() {
    assertTrue(Story.all().any { !it.isCompose })
  }

  @Test
  fun assertStoriesIncludeCompose() {
    assertTrue(Story.all().any { it.isCompose })
  }

}
