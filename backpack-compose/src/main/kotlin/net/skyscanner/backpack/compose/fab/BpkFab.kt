/*
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
 *
 */

package net.skyscanner.backpack.compose.fab

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.icon.BpkIconSize
import net.skyscanner.backpack.compose.theme.BpkTheme

@Composable
fun BpkFab(
  onClick: () -> Unit,
  contentDescription: String,
  icon: BpkIcon,
  modifier: Modifier = Modifier,
  interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
  FloatingActionButton(
    onClick = onClick,
    interactionSource = interactionSource,
    modifier = modifier,
    shape = MaterialTheme.shapes.small.copy(CornerSize(percent = 50)),
    backgroundColor = BpkTheme.colors.coreAccent,
    contentColor = contentColorFor(backgroundColor = BpkTheme.colors.coreAccent),
  ) {
    BpkIcon(
      icon = icon,
      contentDescription = contentDescription,
      size = BpkIconSize.Large,
    )
  }
}
