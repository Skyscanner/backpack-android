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

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.iconbullet.BpkIconBullet
import net.skyscanner.backpack.compose.iconbullet.BpkIconBulletSize
import net.skyscanner.backpack.compose.iconbullet.BpkIconBulletType
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.compose.tokens.TrendDown
import net.skyscanner.backpack.demo.components.IconBulletComponent
import net.skyscanner.backpack.demo.meta.ComposeStory

@Composable
@IconBulletComponent
@ComposeStory
fun IconBulletStory(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.padding(BpkSpacing.Base),
        verticalArrangement = Arrangement.spacedBy(BpkSpacing.Base),
    ) {
        BpkIconBulletType.entries.forEach { type ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(BpkSpacing.Base),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                BpkIconBulletSize.entries.forEach { size ->
                    BpkIconBullet(
                        icon = BpkIcon.TrendDown,
                        size = size,
                        type = type,
                    )
                }
            }
        }
    }
}
