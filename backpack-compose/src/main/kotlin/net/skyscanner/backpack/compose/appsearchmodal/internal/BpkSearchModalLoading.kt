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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import net.skyscanner.backpack.compose.appsearchmodal.BpkAppSearchModalResult
import net.skyscanner.backpack.compose.sleketon.BpkBodyTextSkeleton
import net.skyscanner.backpack.compose.tokens.BpkSpacing

private const val SkeletonCount = 10
private const val SkeletonItemWidth = 200

@Composable
internal fun BpkSearchModalLoading(results: BpkAppSearchModalResult.Loading) {
    Column(modifier = Modifier.semantics(mergeDescendants = true) {
        contentDescription = results.accessibilityLabel
    }) {
        for (i in 0..SkeletonCount) {
            BpkBodyTextSkeleton(
                modifier = Modifier
                    .padding(BpkSpacing.Base)
                    .width(SkeletonItemWidth.dp),
            )
        }
    }
}
