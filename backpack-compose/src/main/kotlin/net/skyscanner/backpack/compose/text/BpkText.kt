/**
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2018-2021 Skyscanner Ltd
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

package net.skyscanner.backpack.compose.text

import androidx.compose.material.LocalTextStyle
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import net.skyscanner.backpack.compose.theme.BpkTheme

@Composable
fun BpkText(
  text: String,
  modifier: Modifier = Modifier,
  color: Color = Color.Unspecified,
  textDecoration: TextDecoration? = null,
  textAlign: TextAlign? = null,
  overflow: TextOverflow = TextOverflow.Clip,
  softWrap: Boolean = true,
  maxLines: Int = Int.MAX_VALUE,
  onTextLayout: (TextLayoutResult) -> Unit = {},
  style: TextStyle = LocalTextStyle.current,
) {
  BpkText(
    text = AnnotatedString(text),
    modifier = modifier,
    color = color,
    textDecoration = textDecoration,
    textAlign = textAlign,
    overflow = overflow,
    softWrap = softWrap,
    maxLines = maxLines,
    onTextLayout = onTextLayout,
    style = style,
  )
}

@Composable
fun BpkText(
  text: AnnotatedString,
  modifier: Modifier = Modifier,
  color: Color = BpkTheme.colors.textPrimary,
  textDecoration: TextDecoration? = null,
  textAlign: TextAlign? = null,
  overflow: TextOverflow = TextOverflow.Clip,
  softWrap: Boolean = true,
  maxLines: Int = Int.MAX_VALUE,
  onTextLayout: (TextLayoutResult) -> Unit = {},
  style: TextStyle = LocalTextStyle.current,
) {
  Text(
    text = text,
    modifier = modifier,
    color = color,
    textDecoration = textDecoration,
    textAlign = textAlign,
    overflow = overflow,
    softWrap = softWrap,
    maxLines = maxLines,
    onTextLayout = onTextLayout,
    style = style,
  )
}
