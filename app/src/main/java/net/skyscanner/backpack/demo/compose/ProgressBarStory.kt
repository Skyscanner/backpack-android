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

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import net.skyscanner.backpack.compose.button.BpkButton
import net.skyscanner.backpack.compose.progressbar.BpkProgressBar
import net.skyscanner.backpack.compose.progressbar.BpkProgressBarSize
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.demo.components.ProgressBarComponent
import net.skyscanner.backpack.demo.meta.ComposeStory
import net.skyscanner.backpack.demo.ui.LocalAutomationMode
import java.lang.Float.max
import java.lang.Float.min

@Composable
@ProgressBarComponent
@ComposeStory
fun ProgressBarStory(modifier: Modifier = Modifier) {

    Column(modifier = modifier.padding(BpkSpacing.Lg)) {

        ProgressBarSample(
            title = stringResource(id = R.string.progress_bar_small),
            value = 0.3F,
        )

        ProgressBarSample(
            title = stringResource(id = R.string.progress_bar_small_stepped),
            value = 3F,
            stepped = true,
            max = 10,
            interval = 1F,
        )

        ProgressBarSample(
            title = stringResource(id = R.string.progress_bar_large),
            value = 0.3F,
            size = BpkProgressBarSize.Large,
        )

        ProgressBarSample(
            title = stringResource(id = R.string.progress_bar_large_stepped),
            value = 3F,
            stepped = true,
            max = 10,
            interval = 1F,
            size = BpkProgressBarSize.Large,
        )
    }
}

@Composable
private fun ProgressBarSample(
    title: String,
    value: Float,
    stepped: Boolean = false,
    max: Int = 1,
    interval: Float = 0.1F,
    size: BpkProgressBarSize = BpkProgressBarSize.Small,
) {
    var progress by remember { mutableStateOf(value) }

    Column(
        modifier = Modifier
            .padding(BpkSpacing.Sm)
            .fillMaxWidth(),
    ) {
        BpkText(text = title)
        BpkProgressBar(
            value = progress,
            modifier = Modifier
                .padding(vertical = BpkSpacing.Base)
                .fillMaxWidth(),
            size = size,
            max = max,
            stepped = stepped,
        )

        val automationMode = LocalAutomationMode.current
        if (!automationMode) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(BpkSpacing.Base),
            ) {
                BpkButton(
                    modifier = Modifier.weight(1F),
                    text = stringResource(id = R.string.progress_bar_decrease),
                    onClick = { progress = max(0F, progress - interval) },
                )

                BpkButton(
                    modifier = Modifier.weight(1F),
                    text = stringResource(id = R.string.progress_bar_increase),
                    onClick = { progress = min(max.toFloat(), progress + interval) },
                )
            }
        }
    }
}
