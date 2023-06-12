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

import net.skyscanner.backpack.meta.StoryKind

interface StoriesRepository {

    val uiComponents: List<Component>

    val tokenComponents: List<Component>

    val screenshotStories: List<Story>

    fun storiesOf(component: String, compose: Boolean): List<Story>

    fun storyOf(component: String, story: String, compose: Boolean): Story

    fun isComposeOnly(component: String): Boolean

    fun isViewOnly(component: String): Boolean

    companion object {

        fun getInstance(): StoriesRepository = StoriesRepositoryImpl
    }
}

private object StoriesRepositoryImpl : StoriesRepository {

    private val stories = Story
        .all
        .asSequence()

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
}
