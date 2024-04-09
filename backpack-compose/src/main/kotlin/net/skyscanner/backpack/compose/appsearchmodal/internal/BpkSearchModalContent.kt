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

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import net.skyscanner.backpack.compose.appsearchmodal.BpkAppSearchModalResult
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.compose.utils.BpkBehaviouralEventWrapper

@Composable
internal fun BpkSearchModalContent(
    results: BpkAppSearchModalResult.Content,
    modifier: Modifier = Modifier,
    behaviouralEventWrapper: BpkBehaviouralEventWrapper? = null,
) {
    LazyColumn(modifier = modifier) {
        results.shortcuts?.let {
            item {
                BpkShortcuts(it)
            }
        }
        results.sections.forEach { section ->
            section.headings?.let {
                item {
                    BpkSectionHeading(
                        sectionHeading = it,
                        modifier = Modifier
                            .padding(top = BpkSpacing.Base)
                            .padding(horizontal = BpkSpacing.Base),
                    )
                }
            }
            items(section.items) {
                if (behaviouralEventWrapper != null) {
                    behaviouralEventWrapper(it, Modifier) {
                        BpkSectionItem(
                            item = it,
                            modifier = Modifier.padding(BpkSpacing.Base),
                            clickHandleScope = this,
                        )
                    }
                } else {
                    BpkSectionItem(
                        item = it,
                        modifier = Modifier.padding(BpkSpacing.Base),
                    )
                }
            }
        }
    }
}
