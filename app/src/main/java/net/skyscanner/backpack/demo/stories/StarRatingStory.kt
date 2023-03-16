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
import net.skyscanner.backpack.demo.components.StarRatingComponent
import net.skyscanner.backpack.demo.meta.StoryKind
import net.skyscanner.backpack.demo.meta.ViewStory
import net.skyscanner.backpack.demo.ui.AndroidLayout

@Composable
@StarRatingComponent
@ViewStory("Default")
fun StarRatingStoryDefault(modifier: Modifier = Modifier) =
    AndroidLayout(R.layout.fragment_star_rating_default, modifier)

@Composable
@StarRatingComponent
@ViewStory("Different values", StoryKind.DemoOnly)
fun StarRatingStoryValues(modifier: Modifier = Modifier) =
    AndroidLayout(R.layout.fragment_star_rating_values, modifier)

@Composable
@StarRatingComponent
@ViewStory("Custom Max Rating", StoryKind.DemoOnly)
fun StarRatingStoryMax(modifier: Modifier = Modifier) =
    AndroidLayout(R.layout.fragment_star_rating_max, modifier)
