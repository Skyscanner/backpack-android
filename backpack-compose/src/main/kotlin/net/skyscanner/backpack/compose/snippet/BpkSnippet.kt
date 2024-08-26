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

package net.skyscanner.backpack.compose.snippet

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import net.skyscanner.backpack.compose.snippet.internal.BpkSnippetImpl

@Composable
fun BpkSnippet(
    modifier: Modifier = Modifier,
    imageOrientation: ImageOrientation = ImageOrientation.Landscape,
    headline: String? = null,
    subHeading: String? = null,
    bodyText: String? = null,
    accessibilityHeaderTagEnabled: Boolean? = true,
    onClick: (() -> Unit)? = null,
    content: @Composable (() -> Unit),
) {
    BpkSnippetImpl(
        modifier = modifier,
        imageOrientation = imageOrientation,
        headline = headline,
        subHeading = subHeading,
        bodyText = bodyText,
        accessibilityHeaderTagEnabled = accessibilityHeaderTagEnabled,
        onClick = onClick,
        content = content,
    )
}

enum class ImageOrientation {
    Landscape, Square, Portrait
}
