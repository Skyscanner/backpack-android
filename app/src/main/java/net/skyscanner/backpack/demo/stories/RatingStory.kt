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
import net.skyscanner.backpack.demo.components.RatingComponent
import net.skyscanner.backpack.meta.StoryKind
import net.skyscanner.backpack.demo.meta.ViewStory
import net.skyscanner.backpack.demo.ui.AndroidLayout

@Composable
@RatingComponent
@ViewStory("Default")
fun RatingStoryDefault(modifier: Modifier = Modifier) =
    AndroidLayout(R.layout.fragment_rating_default, modifier)

@Composable
@RatingComponent
@ViewStory("Icons", StoryKind.DemoOnly)
fun RatingStoryIcons(modifier: Modifier = Modifier) =
    AndroidLayout(R.layout.fragment_rating_icons, modifier)

@Composable
@RatingComponent
@ViewStory("No selectors", StoryKind.DemoOnly)
fun RatingStoryNoSelectors(modifier: Modifier = Modifier) =
    AndroidLayout(R.layout.fragment_rating_no_selectors, modifier)

@Composable
@RatingComponent
@ViewStory("Horizontal")
fun RatingStoryHorizontal(modifier: Modifier = Modifier) =
    AndroidLayout(R.layout.fragment_rating_sizes, modifier)

@Composable
@RatingComponent
@ViewStory("Vertical")
fun RatingStoryVertical(modifier: Modifier = Modifier) =
    AndroidLayout(R.layout.fragment_rating_sizes_vertical, modifier)

@Composable
@RatingComponent
@ViewStory("Pill")
fun RatingStoryPill(modifier: Modifier = Modifier) =
    AndroidLayout(R.layout.fragment_rating_sizes_pill, modifier)

@Composable
@RatingComponent
@ViewStory("Zero to Five Scale", StoryKind.DemoOnly)
fun RatingStoryZeroToFive(modifier: Modifier = Modifier) =
    AndroidLayout(R.layout.fragment_rating_zero_to_five, modifier)
