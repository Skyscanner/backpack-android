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
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import net.skyscanner.backpack.compose.theme.BpkTheme

@Composable
fun TypographyStylesStory() {
  val styles = listOf(
    BpkTheme.typography.hero1 to "Hero 1",
    BpkTheme.typography.hero2 to "Hero 2",
    BpkTheme.typography.hero3 to "Hero 3",
    BpkTheme.typography.hero4 to "Hero 4",
    BpkTheme.typography.hero5 to "Hero 5",
    BpkTheme.typography.heading1 to "Heading 1",
    BpkTheme.typography.heading2 to "Heading 2",
    BpkTheme.typography.heading3 to "Heading 3",
    BpkTheme.typography.heading4 to "Heading 4",
    BpkTheme.typography.heading5 to "Heading 5",
    BpkTheme.typography.subheading to "Subheading",
    BpkTheme.typography.bodyLongform to "Body Longform",
    BpkTheme.typography.bodyDefault to "Body Default",
    BpkTheme.typography.footnote to "Footnote",
    BpkTheme.typography.caption to "Caption",
    BpkTheme.typography.label1 to "Label 1",
    BpkTheme.typography.label2 to "Label 2",
  )
  LazyColumn(modifier = Modifier.padding(16.dp)) {
    items(styles) { style ->
      Text(
        style = style.first,
        text = style.second
      )
    }
  }
}
