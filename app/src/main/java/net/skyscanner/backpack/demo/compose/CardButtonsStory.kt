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

package net.skyscanner.backpack.demo.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import net.skyscanner.backpack.compose.cardbutton.BpkCardButtonSize
import net.skyscanner.backpack.compose.cardbutton.BpkCardButtonStyle
import net.skyscanner.backpack.compose.cardbutton.BpkSaveButton
import net.skyscanner.backpack.compose.cardbutton.BpkShareButton
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkDimension
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.demo.components.CardButtonComponent
import net.skyscanner.backpack.demo.meta.ComposeStory

@Composable
@CardButtonComponent
@ComposeStory("Default")
fun CardButtonStoryDefault(modifier: Modifier = Modifier) =
    CardButtonsDemo(BpkCardButtonSize.Default, modifier)

@Composable
@CardButtonComponent
@ComposeStory("Small")
fun CardButtonStoryLarge(modifier: Modifier = Modifier) =
    CardButtonsDemo(BpkCardButtonSize.Small, modifier)

@Composable
private fun CardButtonsDemo(
    size: BpkCardButtonSize,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier
            .fillMaxSize()
            .padding(top = BpkDimension.Spacing.Md),
    ) {
        item {
            Row(
                modifier = Modifier
                    .background(BpkTheme.colors.textPrimaryInverse)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {
                CardRow(size = size, style = BpkCardButtonStyle.Default)
            }
        }
        item {
            Row(
                modifier = Modifier
                    .background(BpkTheme.colors.surfaceContrast)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {
                CardRow(size = size, style = BpkCardButtonStyle.OnDark)
            }
        }
        item {
            Box(modifier = Modifier.height(BpkSpacing.Xxl + BpkSpacing.Md), contentAlignment = Alignment.Center) {
                Image(
                    modifier = Modifier.fillMaxWidth(),
                    painter = painterResource(id = R.drawable.canadian_rockies_canada),
                    contentScale = ContentScale.FillWidth,
                    contentDescription = "",
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                ) {
                    CardRow(size = size, style = BpkCardButtonStyle.Contained)
                }
            }
        }
    }
}

@Composable
private fun CardRow(size: BpkCardButtonSize, style: BpkCardButtonStyle) {
    var checked1 by remember { mutableStateOf(false) }
    BpkSaveButton(
        checked = checked1,
        size = size,
        style = style,
        contentDescription = "",
        onCheckedChange = { checked1 = !checked1 },
    )
    var checked2 by remember { mutableStateOf(true) }
    BpkSaveButton(
        checked = checked2,
        size = size,
        style = style,
        contentDescription = "",
        onCheckedChange = { checked2 = !checked2 },
    )
    BpkShareButton(
        size = size,
        style = style,
        contentDescription = "",
        onClick = {},
    )
}
