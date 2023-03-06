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
 */

package net.skyscanner.backpack.demo.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import net.skyscanner.backpack.compose.button.BpkButton
import net.skyscanner.backpack.compose.button.BpkButtonType
import net.skyscanner.backpack.compose.fieldset.BpkFieldStatus
import net.skyscanner.backpack.demo.R

@Composable
internal fun FieldStatusSwitcher(
  modifier: Modifier = Modifier,
  initialStatus: BpkFieldStatus = BpkFieldStatus.Default,
  errorText: String = stringResource(R.string.generic_error_text),
  verticalArrangement: Arrangement.Vertical = Arrangement.Top,
  horizontalAlignment: Alignment.Horizontal = Alignment.Start,
  content: @Composable ColumnScope.(status: BpkFieldStatus) -> Unit,
) {

  var status by remember { mutableStateOf(initialStatus) }

  Column(
    modifier = modifier,
    verticalArrangement = verticalArrangement,
    horizontalAlignment = horizontalAlignment,
  ) {

    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
      BpkButton(text = BpkFieldStatus.Default::class.simpleName!!, type = BpkButtonType.Featured) {
        status = BpkFieldStatus.Default
      }
      BpkButton(text = BpkFieldStatus.Disabled::class.simpleName!!, type = BpkButtonType.Secondary) {
        status = BpkFieldStatus.Disabled
      }
      BpkButton(text = BpkFieldStatus.Validated::class.simpleName!!, type = BpkButtonType.Primary) {
        status = BpkFieldStatus.Validated
      }
      BpkButton(text = BpkFieldStatus.Error::class.simpleName!!, type = BpkButtonType.Destructive) {
        status = BpkFieldStatus.Error(errorText)
      }
    }

    content(status)
  }
}
