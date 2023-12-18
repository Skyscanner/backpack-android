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

package net.skyscanner.backpack.compose.appsearchmodal.internal

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import net.skyscanner.backpack.compose.appsearchmodal.BpkAppSearchModalResult
import net.skyscanner.backpack.compose.fieldset.BpkFieldStatus
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.textfield.BpkClearAction
import net.skyscanner.backpack.compose.textfield.BpkTextField
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.compose.tokens.Search

@Composable
internal fun BpkAppSearchModalImpl(
    inputText: String,
    inputHint: String,
    results: BpkAppSearchModalResult,
    onInputChanged: (String) -> Unit,
    clearAction: BpkClearAction,
    modifier: Modifier = Modifier,
) {
    when (results) {
        is BpkAppSearchModalResult.Error -> {
            BpkSearchModalError(results = results)
        }

        else -> {
            Column(modifier = modifier) {
                BpkTextField(
                    icon = BpkIcon.Search,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(BpkSpacing.Base)
                        .testTag("searchModalTextField"),
                    value = inputText,
                    placeholder = inputHint,
                    onValueChange = onInputChanged,
                    status = BpkFieldStatus.Clear,
                    clearAction = clearAction,
                )
                if (results is BpkAppSearchModalResult.Content) {
                    BpkSearchModalContent(results)
                } else if (results is BpkAppSearchModalResult.Loading) {
                    BpkSearchModalLoading(results)
                }
            }
        }
    }
}
