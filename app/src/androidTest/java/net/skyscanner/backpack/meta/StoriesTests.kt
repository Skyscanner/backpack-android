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

package net.skyscanner.backpack.meta

import net.skyscanner.backpack.demo.meta.StoriesRepository
import org.junit.Assert.assertTrue
import org.junit.Test

class StoriesTests {

  private val repository = StoriesRepository.getInstance()

  @Test
  fun assertStoriesAreNotEmpty() {
    assertTrue(repository.testStories().isNotEmpty())
  }

  @Test
  fun assertStoriesIncludeView() {
    assertTrue(repository.testStories().any { !it.isCompose })
  }

  @Test
  fun assertStoriesIncludeCompose() {
    assertTrue(repository.testStories().any { it.isCompose })
  }

  @Test
  fun assertStoriesIncludeScreenshot() {
    assertTrue(repository.testStories().any { it.isScreenshot })
  }

  @Test
  fun assertStoriesSupportCustomName() {
    assertTrue(repository.testStories().any { it.name == "TestCustomName" })
  }

  @Test
  fun assertTestComponentIsPresent() {
    assertTrue(repository.testStories().any { it.component.name == "TestComponent" })
  }

}
