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

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkDimension
import net.skyscanner.backpack.demo.R

@Preview
@Composable
fun HeroStyleStory() {
  val styles = listOf(
    BpkTheme.typography.hero1 to stringResource(R.string.text_hero1),
    BpkTheme.typography.hero2 to stringResource(R.string.text_hero2),
    BpkTheme.typography.hero3 to stringResource(R.string.text_hero3),
    BpkTheme.typography.hero4 to stringResource(R.string.text_hero4),
    BpkTheme.typography.hero5 to stringResource(R.string.text_hero5),
  )
  TypographyStylesStory(styles = styles)
}

@Preview
@Composable
fun HeadingStyleStory() {
  val styles = listOf(
    BpkTheme.typography.heading1 to stringResource(R.string.text_heading1),
    BpkTheme.typography.heading2 to stringResource(R.string.text_heading2),
    BpkTheme.typography.heading3 to stringResource(R.string.text_heading3),
    BpkTheme.typography.heading4 to stringResource(R.string.text_heading4),
    BpkTheme.typography.heading5 to stringResource(R.string.text_heading5),
  )
  TypographyStylesStory(styles = styles)
}

@Preview
@Composable
fun BodyStyleStory() {
  val styles = listOf(
    BpkTheme.typography.subheading to stringResource(R.string.text_subheading),
    BpkTheme.typography.bodyLongform to stringResource(R.string.text_body_longform),
    BpkTheme.typography.bodyDefault to stringResource(R.string.text_body_default),
    BpkTheme.typography.footnote to stringResource(R.string.text_footnote),
    BpkTheme.typography.caption to stringResource(R.string.text_caption),
    BpkTheme.typography.label1 to stringResource(R.string.text_label1),
    BpkTheme.typography.label2 to stringResource(R.string.text_label2),
    BpkTheme.typography.label3 to stringResource(R.string.text_label3),
  )
  TypographyStylesStory(styles = styles)
}

@Composable
private fun TypographyStylesStory(styles: List<Pair<TextStyle, String>>) {
  LazyColumn(modifier = Modifier.padding(BpkDimension.Spacing.Base)) {
    items(styles) { style ->
      BpkText(
        style = style.first,
        text = style.second
      )
    }
  }
}

@Preview
@Composable
fun DefaultTextExample() {
  BpkText(text = "Sample")
}

@Preview
@Composable
fun ColoredTextExample() {
  BpkText(
    text = "Sample",
    color = BpkTheme.colors.textLink,
  )
}

@Preview
@Composable
fun StyledTextExample() {
  BpkText(
    text = "Sample",
    style = BpkTheme.typography.heading4,
  )
}

@Preview
@Composable
fun AnnotatedTextExample() {
  BpkText(
    text = buildAnnotatedString {
      append("Sample ")
      withStyle(style = SpanStyle(color = BpkTheme.colors.textLink)) {
        append("Text")
      }
    }
  )
}
