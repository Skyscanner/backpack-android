/*
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

package net.skyscanner.backpack.compose.fieldset

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import net.skyscanner.backpack.compose.fieldset.internal.BpkFieldSetImpl

internal val LocalFieldStatus = staticCompositionLocalOf<BpkFieldStatus> { BpkFieldStatus.Default }

sealed interface BpkFieldStatus {

    data object Default : BpkFieldStatus

    data object Disabled : BpkFieldStatus

    data class Error(val text: String) : BpkFieldStatus

    data object Validated : BpkFieldStatus
}

@Composable
fun BpkFieldSet(
    modifier: Modifier = Modifier,
    label: String? = null,
    description: String? = null,
    status: BpkFieldStatus = BpkFieldStatus.Default,
    content: @Composable ColumnScope.(BpkFieldStatus) -> Unit,
) {
    BpkFieldSetImpl(
        modifier = modifier,
        label = label,
        description = description,
        status = status,
        content = content,
    )
}
