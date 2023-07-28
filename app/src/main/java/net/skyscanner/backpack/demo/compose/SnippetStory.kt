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

package net.skyscanner.backpack.demo.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import net.skyscanner.backpack.compose.snippet.BpkSnippet
import net.skyscanner.backpack.demo.components.SnippetComponent
import net.skyscanner.backpack.demo.meta.ComposeStory

@Composable
@SnippetComponent
@ComposeStory
fun SnippetStory(modifier: Modifier = Modifier) {
    Column(modifier) {
        DefaultSnippetSample()
    }
}

@Composable
internal fun DefaultSnippetSample(modifier: Modifier = Modifier) {
    BpkSnippet(
        modifier = modifier,
    )
}
