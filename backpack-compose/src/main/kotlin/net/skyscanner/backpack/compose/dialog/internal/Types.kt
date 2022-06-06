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

import androidx.compose.ui.graphics.Color
import net.skyscanner.backpack.compose.button.BpkButtonType
import net.skyscanner.backpack.compose.dialog.DialogButton
import net.skyscanner.backpack.compose.tokens.BpkColor
import net.skyscanner.backpack.compose.tokens.BpkIcon

internal object Dialog {
  internal sealed class Icon {
    abstract val icon: BpkIcon
    abstract val backgroundColor: Color

    data class Success(override val icon: BpkIcon) : Icon() {
      override val backgroundColor = BpkColor.Monteverde
    }

    data class Warning(override val icon: BpkIcon) : Icon() {
      override val backgroundColor = BpkColor.Kolkata
    }

    data class Destructive(override val icon: BpkIcon) : Icon() {
      override val backgroundColor = BpkColor.Panjin
    }
  }

  internal data class Button(val type: BpkButtonType, val button: DialogButton)
}
