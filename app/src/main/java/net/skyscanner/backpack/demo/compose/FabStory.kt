/*
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2018 - 2025 Skyscanner Ltd
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
 *
 */

package net.skyscanner.backpack.demo.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import kotlinx.coroutines.launch
import net.skyscanner.backpack.compose.fab.BpkFab
import net.skyscanner.backpack.compose.floatingnotification.BpkFloatingNotification
import net.skyscanner.backpack.compose.floatingnotification.rememberBpkFloatingNotificationState
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.compose.tokens.Flight
import net.skyscanner.backpack.compose.tokens.Search
import net.skyscanner.backpack.compose.tokens.Star
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.demo.components.FabComponent
import net.skyscanner.backpack.demo.meta.ComposeStory

@Composable
@FabComponent
@ComposeStory
fun FabStory(modifier: Modifier = Modifier) {
    val notificationState = rememberBpkFloatingNotificationState()
    val scope = rememberCoroutineScope()
    val message = stringResource(R.string.generic_message)

    Box(modifier) {
        Column(
            modifier = Modifier.padding(BpkSpacing.Xl),
            verticalArrangement = Arrangement.spacedBy(BpkSpacing.Xxl, Alignment.CenterVertically),
        ) {
            BpkFab(
                onClick = { scope.launch { notificationState.show(text = message) } },
                icon = BpkIcon.Search,
                contentDescription = stringResource(R.string.content_description),
            )

            BpkFab(
                onClick = { scope.launch { notificationState.show(text = message) } },
                icon = BpkIcon.Star,
                contentDescription = stringResource(R.string.content_description),
            )

            BpkFab(
                onClick = { scope.launch { notificationState.show(text = message) } },
                icon = BpkIcon.Flight,
                contentDescription = stringResource(R.string.content_description),
            )
        }
        BpkFloatingNotification(notificationState)
    }
}
