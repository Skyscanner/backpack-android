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

package net.skyscanner.backpack.compose.link.internal

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withLink
import androidx.compose.ui.text.withStyle
import net.skyscanner.backpack.compose.LocalTextStyle
import net.skyscanner.backpack.compose.link.BpkLinkStyle
import net.skyscanner.backpack.compose.link.TextType
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.theme.BpkTheme

@Composable
internal fun BpkLinkImpl(
    listOfTexts: List<TextType>,
    onLinkClicked: (Int) -> Unit,
    modifier: Modifier = Modifier,
    style: TextStyle = LocalTextStyle.current,
    linkStyle: BpkLinkStyle = BpkLinkStyle.Default,
) {
    val textColor = when (linkStyle) {
        BpkLinkStyle.Default -> BpkTheme.colors.textPrimary
        BpkLinkStyle.OnContrast -> BpkTheme.colors.textOnDark
    }

    val annotatedString = buildAnnotatedString {
        listOfTexts.forEach { textType ->
            when (textType) {
                is TextType.RawText -> {
                    appendRawText(textColor, textType.value)
                }

                is TextType.LinkText -> {
                    val linkIndex = listOfTexts.filterIsInstance<TextType.LinkText>().indexOf(textType)
                    val linkAnnotation = LinkAnnotation.Clickable(
                        tag = "LINK_$linkIndex",
                        linkInteractionListener = {
                            onLinkClicked(linkIndex)
                        },
                    )
                    appendClickableLink(linkAnnotation, textColor, textType.value)
                }
            }
        }
    }

    BpkText(
        text = annotatedString,
        style = style,
        modifier = modifier,
    )
}

@Composable
internal fun BpkLinkImpl(
    text: String,
    onLinkClicked: () -> Unit,
    modifier: Modifier = Modifier,
    style: TextStyle = LocalTextStyle.current,
    linkStyle: BpkLinkStyle = BpkLinkStyle.Default,
) {
    val textColor = when (linkStyle) {
        BpkLinkStyle.Default -> BpkTheme.colors.textPrimary
        BpkLinkStyle.OnContrast -> BpkTheme.colors.textOnDark
    }

    val annotatedString = buildAnnotatedString {
        val linkAnnotation = LinkAnnotation.Clickable(
            tag = "LINK",
            linkInteractionListener = {
                onLinkClicked()
            },
        )
        appendClickableLink(linkAnnotation, textColor, text)
    }

    BpkText(
        text = annotatedString,
        style = style,
        modifier = modifier,
    )
}

@Composable
private fun AnnotatedString.Builder.appendRawText(
    textColor: Color,
    text: String,
) {
    withStyle(
        SpanStyle(color = textColor),
    ) {
        append(text)
    }
}

@Composable
private fun AnnotatedString.Builder.appendClickableLink(
    linkAnnotation: LinkAnnotation.Clickable,
    textColor: Color,
    text: String,
) {
    withLink(linkAnnotation) {
        withStyle(
            SpanStyle(
                color = textColor,
                textDecoration = TextDecoration.Underline,
            ),
        ) {
            append(text)
        }
    }
}
