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

package net.skyscanner.backpack.compose.dialog.internal

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import net.skyscanner.backpack.compose.button.BpkButtonType
import net.skyscanner.backpack.compose.dialog.DialogButton
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.theme.BpkTheme

internal object Dialog {
  internal sealed class Icon {
    abstract val icon: BpkIcon

    @get:Composable
    abstract val backgroundColor: Color

    data class Success(override val icon: BpkIcon) : Icon() {

      override val backgroundColor
        @Composable
        get() = BpkTheme.colors.coreAccent

    }

    data class Warning(override val icon: BpkIcon) : Icon() {

      override val backgroundColor
        @Composable
        get() = BpkTheme.colors.statusWarningSpot

    }

    data class Destructive(override val icon: BpkIcon) : Icon() {

      override val backgroundColor
        @Composable
        get() = BpkTheme.colors.statusDangerSpot

    }
  }

  internal data class Button(val type: BpkButtonType, val button: DialogButton)
}
