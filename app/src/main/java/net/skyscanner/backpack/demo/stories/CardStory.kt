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
import net.skyscanner.backpack.demo.components.CardComponent
import net.skyscanner.backpack.demo.meta.ViewStory
import net.skyscanner.backpack.demo.ui.AndroidLayout

@Composable
@CardComponent
@ViewStory("Default")
fun CardStoryDefault(modifier: Modifier = Modifier) =
  AndroidLayout(R.layout.fragment_card, modifier)

@Composable
@CardComponent
@ViewStory("Without padding")
fun CardStoryWithoutPadding(modifier: Modifier = Modifier) =
  AndroidLayout(R.layout.fragment_card_without_padding, modifier)

@Composable
@CardComponent
@ViewStory("Selected")
fun CardStorySelected(modifier: Modifier = Modifier) =
  AndroidLayout(R.layout.fragment_card_selected, modifier)

@Composable
@CardComponent
@ViewStory("No elevation")
fun CardStoryNoElevation(modifier: Modifier = Modifier) =
  AndroidLayout(R.layout.fragment_card_no_elevation, modifier)

@Composable
@CardComponent
@ViewStory("Corner style large")
fun CardStoryCornerStyleLarge(modifier: Modifier = Modifier) =
  AndroidLayout(R.layout.fragment_card_cornerstyle_large, modifier)

@Composable
@CardComponent
@ViewStory("With divider")
fun CardStoryWithDivider(modifier: Modifier = Modifier) =
  AndroidLayout(R.layout.fragment_card_with_divider, modifier)

@Composable
@CardComponent
@ViewStory("With divider arranged vertically")
fun CardStoryWithDividerVertical(modifier: Modifier = Modifier) =
  AndroidLayout(R.layout.fragment_card_with_divider_vertical, modifier)

@Composable
@CardComponent
@ViewStory("With divider without padding")
fun CardStoryWithDividerNoPadding(modifier: Modifier = Modifier) =
  AndroidLayout(R.layout.fragment_card_with_divider_no_padding, modifier)

@Composable
@CardComponent
@ViewStory("With divider and corner style large")
fun CardStoryWithDividerCornerStyleLarge(modifier: Modifier = Modifier) =
  AndroidLayout(R.layout.fragment_card_with_divider_cornerstyle_large, modifier)
