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

package net.skyscanner.backpack.compose.text

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.IntSize
import androidx.test.ext.junit.runners.AndroidJUnit4
import net.skyscanner.backpack.BpkSnapshotTest
import net.skyscanner.backpack.BpkTestVariant
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.demo.compose.AnnotatedTextExample
import net.skyscanner.backpack.demo.compose.ColoredTextExample
import net.skyscanner.backpack.demo.compose.DefaultTextExample
import net.skyscanner.backpack.demo.compose.StyledTextExample
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BpkTextTest : BpkSnapshotTest() {

  @Test
  fun default() {
    composed {
      DefaultTextExample()
    }
  }

  @Test
  fun colored() = capture {
    ColoredTextExample()
  }

  @Test
  fun styled() = capture {
    StyledTextExample()
  }

  @Test
  fun annotated() = capture {
    AnnotatedTextExample()
  }

  @Test
  fun bodyDefault() = capture {
    BpkText(text = "Body Default", style = BpkTheme.typography.bodyDefault)
  }

  @Test
  fun bodyLongform() = capture {
    BpkText(text = "Body Longform", style = BpkTheme.typography.bodyLongform)
  }

  @Test
  fun footnote() = capture {
    BpkText(text = "Footnote", style = BpkTheme.typography.footnote)
  }

  @Test
  fun caption() = capture {
    BpkText(text = "Caption", style = BpkTheme.typography.caption)
  }

  @Test
  fun subheading() = capture {
    BpkText(text = "Subheading", style = BpkTheme.typography.subheading)
  }

  @Test
  fun label1() = capture {
    BpkText(text = "Label 1", style = BpkTheme.typography.label1)
  }

  @Test
  fun label2() = capture {
    BpkText(text = "Label 2", style = BpkTheme.typography.label2)
  }

  @Test
  fun label3() = capture {
    BpkText(text = "Label 3", style = BpkTheme.typography.label3)
  }

  @Test
  fun heading1() = capture(size = IntSize(200, 100)) {
    BpkText(text = "Heading 1", style = BpkTheme.typography.heading1)
  }

  @Test
  fun heading2() = capture(size = IntSize(200, 100)) {
    BpkText(text = "Heading 2", style = BpkTheme.typography.heading2)
  }

  @Test
  fun heading3() = capture(size = IntSize(200, 100)) {
    BpkText(text = "Heading 3", style = BpkTheme.typography.heading3)
  }

  @Test
  fun heading4() = capture(size = IntSize(200, 100)) {
    BpkText(text = "Heading 4", style = BpkTheme.typography.heading4)
  }

  @Test
  fun heading5() = capture(size = IntSize(200, 100)) {
    BpkText(text = "Heading 5", style = BpkTheme.typography.heading5)
  }

  @Test
  fun hero1() = capture(size = IntSize(400, 150)) {
    BpkText(text = "Hero 1", style = BpkTheme.typography.hero1)
  }

  @Test
  fun hero2() = capture(size = IntSize(400, 150)) {
    BpkText(text = "Hero 2", style = BpkTheme.typography.hero2)
  }

  @Test
  fun hero3() = capture(size = IntSize(400, 150)) {
    BpkText(text = "Hero 3", style = BpkTheme.typography.hero3)
  }

  @Test
  fun hero4() = capture(size = IntSize(400, 150)) {
    BpkText(text = "Hero 4", style = BpkTheme.typography.hero4)
  }

  @Test
  fun hero5() = capture(size = IntSize(400, 150)) {
    BpkText(text = "Hero 5", style = BpkTheme.typography.hero5)
  }

  private fun capture(size: IntSize = IntSize(150, 50), content: @Composable () -> Unit) {
    assumeVariant(BpkTestVariant.Default)
    composed(size = size) {
      content()
    }
  }
}
