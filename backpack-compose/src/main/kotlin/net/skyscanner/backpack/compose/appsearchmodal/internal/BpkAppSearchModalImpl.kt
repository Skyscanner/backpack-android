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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import net.skyscanner.backpack.compose.appsearchmodal.AppSearchModalResult
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.sleketon.BpkBodyTextSkeleton
import net.skyscanner.backpack.compose.textfield.BpkTextField
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.compose.tokens.Search

private const val SkeletonCount = 10
private const val SkeletonItemWidth = 200

@Composable
internal fun BpkAppSearchModalImpl(
    inputText: String,
    inputHint: String,
    results: AppSearchModalResult,
    onInputChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    when (results) {
        is AppSearchModalResult.Error -> {
            BpkSearchModalError(results = results)
        }

        else -> {
            Column(modifier = modifier) {
                BpkTextField(
                    icon = BpkIcon.Search,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(BpkSpacing.Base),
                    value = inputText,
                    placeholder = inputHint,
                    onValueChange = onInputChanged,
                )
                LazyColumn {
                    if (results is AppSearchModalResult.Content) {
                        results.shortcuts?.let {
                            item {
                                BpkShortcuts(it)
                            }
                        }
                        items(results.sections) {
                            BpkSection(
                                section = it,
                                Modifier.padding(BpkSpacing.Base),
                            )
                        }
                    } else if (results is AppSearchModalResult.Loading) {
                        items(SkeletonCount) {
                            BpkBodyTextSkeleton(
                                modifier = Modifier
                                    .padding(BpkSpacing.Base)
                                    .width(SkeletonItemWidth.dp),
                            )
                        }
                    }
                }
            }
        }
    }
}
