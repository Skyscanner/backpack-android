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
import net.skyscanner.backpack.demo.components.FlareComponent
import net.skyscanner.backpack.demo.meta.ViewStory
import net.skyscanner.backpack.demo.ui.AndroidLayout

@Composable
@FlareComponent
@ViewStory("Default")
fun FlareStoryDefault(modifier: Modifier = Modifier) =
  AndroidLayout(R.layout.fragment_flare, modifier)

@Composable
@FlareComponent
@ViewStory("Pointing up")
fun FlareStoryUp(modifier: Modifier = Modifier) =
  AndroidLayout(R.layout.fragment_flare_up, modifier)

@Composable
@FlareComponent
@ViewStory("Pointer offset")
fun FlareStoryOffset(modifier: Modifier = Modifier) =
  AndroidLayout(R.layout.fragment_flare_pointer_offset, modifier)

@Composable
@FlareComponent
@ViewStory("Rounded")
fun FlareStoryRounded(modifier: Modifier = Modifier) =
  AndroidLayout(R.layout.fragment_flare_rounded, modifier)

@Composable
@FlareComponent
@ViewStory("Inset padding mode")
fun FlareStoryInsetPaddingMode(modifier: Modifier = Modifier) =
  AndroidLayout(R.layout.fragment_flare_inset_padding_mode, modifier)
