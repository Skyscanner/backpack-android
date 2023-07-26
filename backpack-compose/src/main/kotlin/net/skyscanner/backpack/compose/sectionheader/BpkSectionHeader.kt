/**
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2023 Skyscanner Ltd
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

package net.skyscanner.backpack.compose.sectionheader

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import net.skyscanner.backpack.compose.sectionheader.internal.BpkSectionHeaderImpl

@Composable
fun BpkSectionHeader(
    title: String,
    modifier: Modifier = Modifier,
    description: String? = null,
    button: BpkSectionHeaderButton? = null,
    type: BpkSectionHeaderType = BpkSectionHeaderType.Default,
) {
    BpkSectionHeaderImpl(
        title = title,
        modifier = modifier,
        description = description,
        button = button,
        type = type,
    )
}

class BpkSectionHeaderButton(
    val text: String,
    val onClick: (() -> Unit),
)

enum class BpkSectionHeaderType { Default, OnDark }
