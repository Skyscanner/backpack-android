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

package net.skyscanner.backpack.demo.stories

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.demo.components.TextComponent
import net.skyscanner.backpack.demo.meta.StoryKind
import net.skyscanner.backpack.demo.meta.ViewStory
import net.skyscanner.backpack.demo.ui.AndroidLayout

@Composable
@TextComponent
@ViewStory("Body")
fun TextStoryBody(modifier: Modifier = Modifier) =
  AndroidLayout(R.layout.fragment_text_body, modifier)

@Composable
@TextComponent
@ViewStory("Heading")
fun TextStoryHeading(modifier: Modifier = Modifier) =
  AndroidLayout(R.layout.fragment_text_heading, modifier)

@Composable
@TextComponent
@ViewStory("Hero")
fun TextStoryHero(modifier: Modifier = Modifier) =
  AndroidLayout(R.layout.fragment_text_hero, modifier)

@Composable
@TextComponent
@ViewStory("With drawables", StoryKind.StoryOnly)
fun TextStoryDrawables(modifier: Modifier = Modifier) =
  AndroidLayout(R.layout.fragment_text_drawables, modifier)

@Composable
@TextComponent
@ViewStory("With links", StoryKind.StoryOnly)
fun TextStoryLinks(modifier: Modifier = Modifier) =
  AndroidLayout(R.layout.fragment_text_links, modifier)
