/**
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2018 - 2026 Skyscanner Ltd
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

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import kotlinx.coroutines.launch
import net.skyscanner.backpack.compose.button.BpkButton
import net.skyscanner.backpack.compose.tooltip.BpkTooltip
import net.skyscanner.backpack.compose.tooltip.rememberBpkTooltipState
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.demo.components.TooltipComponent
import net.skyscanner.backpack.demo.meta.ComposeStory
import net.skyscanner.backpack.demo.ui.LocalAutomationMode
import net.skyscanner.backpack.util.ExperimentalBackpackApi

@Composable
@OptIn(ExperimentalBackpackApi::class)
@TooltipComponent
@ComposeStory
fun TooltipStory(modifier: Modifier = Modifier) {
    val state = rememberBpkTooltipState()
    val scope = rememberCoroutineScope()
    val automationMode = LocalAutomationMode.current
    LaunchedEffect(automationMode) {
        if (automationMode) state.show()
    }
    Column(modifier = modifier.padding(BpkSpacing.Base)) {
        BpkTooltip(
            title = stringResource(R.string.generic_title),
            text = stringResource(R.string.generic_description),
            closeAccessibilityLabel = stringResource(R.string.navigation_close),
            state = state,
        ) { BpkButton(text = stringResource(R.string.generic_show), onClick = { scope.launch { state.show() } }) }
    }
}
