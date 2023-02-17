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

import net.skyscanner.backpack.demo.meta.Story
import net.skyscanner.backpack.demo.meta.all
import org.junit.Assert.assertTrue
import org.junit.Ignore
import org.junit.Test

@Ignore("These tests are ignored because we need to migrate the stories first")
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
