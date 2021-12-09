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

package net.skyscanner.backpack.demo.compose

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkDimension
import net.skyscanner.backpack.demo.BackpackDemoTheme

@Preview
@Composable
fun HeroStyleStory() {
  BackpackDemoTheme {
    val styles = listOf(
      BpkTheme.typography.hero1 to "Hero 1",
      BpkTheme.typography.hero2 to "Hero 2",
      BpkTheme.typography.hero3 to "Hero 3",
      BpkTheme.typography.hero4 to "Hero 4",
      BpkTheme.typography.hero5 to "Hero 5",
    )
    TypographyStylesStory(styles = styles)
  }
}

@Preview
@Composable
fun HeadingStyleStory() {
  BackpackDemoTheme {
    val styles = listOf(
      BpkTheme.typography.heading1 to "Heading 1",
      BpkTheme.typography.heading2 to "Heading 2",
      BpkTheme.typography.heading3 to "Heading 3",
      BpkTheme.typography.heading4 to "Heading 4",
      BpkTheme.typography.heading5 to "Heading 5",
    )
    TypographyStylesStory(styles = styles)
  }
}

@Preview
@Composable
fun BodyStyleStory() {
  BackpackDemoTheme {
    val styles = listOf(
      BpkTheme.typography.subheading to "Subheading",
      BpkTheme.typography.bodyLongform to "Body Longform",
      BpkTheme.typography.bodyDefault to "Body Default",
      BpkTheme.typography.footnote to "Footnote",
      BpkTheme.typography.caption to "Caption",
      BpkTheme.typography.label1 to "Label 1",
      BpkTheme.typography.label2 to "Label 2",
    )
    TypographyStylesStory(styles = styles)
  }
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
fun DefaultText() {
  BackpackDemoTheme {
    BpkText(text = "Sample")
  }
}

@Preview
@Composable
fun ColoredText() {
  BackpackDemoTheme {
    BpkText(
      text = "Sample",
      color = BpkTheme.colors.primary,
    )
  }
}

@Preview
@Composable
fun StyledText() {
  BackpackDemoTheme {
    BpkText(
      text = "Sample",
      style = BpkTheme.typography.heading4,
    )
  }
}

@Preview
@Composable
fun AnnotatedText() {
  BackpackDemoTheme {
    BpkText(
      text = buildAnnotatedString {
        append("Sample ")
        withStyle(style = SpanStyle(color = BpkTheme.colors.primary)) {
          append("Text")
        }
      }
    )
  }
}
