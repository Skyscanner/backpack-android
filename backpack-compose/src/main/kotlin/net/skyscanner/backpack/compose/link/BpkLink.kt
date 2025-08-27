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

package net.skyscanner.backpack.compose.link

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import net.skyscanner.backpack.compose.LocalTextStyle
import net.skyscanner.backpack.compose.link.internal.BpkLinkImpl

sealed class TextType {
    abstract val value: String

    data class RawText(override val value: String) : TextType()
    data class LinkText(override val value: String) : TextType()
}

enum class BpkLinkStyle {
    Default,
    OnContrast,
}

@Composable
fun BpkLink(
    text: List<TextType>,
    onLinkClicked: (Int) -> Unit,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = LocalTextStyle.current,
    style: BpkLinkStyle = BpkLinkStyle.Default,
) {
    BpkLinkImpl(
        listOfTexts = text,
        onLinkClicked = onLinkClicked,
        modifier = modifier,
        style = textStyle,
        linkStyle = style,
    )
}

@Composable
fun BpkLink(
    text: String,
    onLinkClicked: () -> Unit,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = LocalTextStyle.current,
    style: BpkLinkStyle = BpkLinkStyle.Default,
) {
    BpkLinkImpl(
        text = text,
        onLinkClicked = onLinkClicked,
        modifier = modifier,
        style = textStyle,
        linkStyle = style,
    )
}
