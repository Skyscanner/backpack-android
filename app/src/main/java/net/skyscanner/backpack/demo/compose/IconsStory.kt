/*
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

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import com.google.accompanist.flowlayout.FlowRow
import net.skyscanner.backpack.compose.checkbox.BpkCheckbox
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.icon.BpkIconSize
import net.skyscanner.backpack.compose.radiobutton.BpkRadioButton
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.compose.tokens.values
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.toast.BpkToast

@Composable
@Preview
fun IconsStoryCompose() {
  Column(
    modifier = Modifier.padding(BpkSpacing.Base),
    verticalArrangement = Arrangement.spacedBy(BpkSpacing.Base),
    horizontalAlignment = Alignment.CenterHorizontally,
  ) {

    var size by remember { mutableStateOf(BpkIconSize.Small) }
    var layoutDirection by remember { mutableStateOf(LayoutDirection.Ltr) }

    Row(
      horizontalArrangement = Arrangement.spacedBy(BpkSpacing.Base),
      verticalAlignment = Alignment.CenterVertically,
    ) {

      BpkRadioButton(
        text = BpkIconSize.Small.toString(),
        selected = size == BpkIconSize.Small,
        onClick = { size = BpkIconSize.Small },
      )

      BpkRadioButton(
        text = BpkIconSize.Large.toString(),
        selected = size == BpkIconSize.Large,
        onClick = { size = BpkIconSize.Large },
      )

      BpkCheckbox(
        text = stringResource(R.string.icons_force_rtl),
        checked = layoutDirection == LayoutDirection.Rtl,
        onCheckedChange = {
          layoutDirection = when {
            it -> LayoutDirection.Rtl
            else -> LayoutDirection.Ltr
          }
        },
      )
    }

    val context = LocalContext.current
    val clipboardManager = LocalClipboardManager.current

    val autoMirrorBackground = BpkTheme.colors.statusSuccessSpot
    val autoMirrorAlpha by animateFloatAsState(
      targetValue = when (layoutDirection) {
        LayoutDirection.Ltr -> 0f
        LayoutDirection.Rtl -> 1f
      },
    )

    BpkText(
      text = stringResource(R.string.icons_story_guide),
      style = BpkTheme.typography.caption,
      color = BpkTheme.colors.textSecondary,
    )

    FlowRow(Modifier.verticalScroll(rememberScrollState())) {
      BpkIcon.values.forEach { icon ->

        CompositionLocalProvider(LocalLayoutDirection provides layoutDirection) {

          Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
              .border(Dp.Hairline, BpkTheme.colors.line)
              .drawBehind {
                drawRect(color = autoMirrorBackground, alpha = if (icon.autoMirror) autoMirrorAlpha else 0f)
              }
              .clickable {
                clipboardManager.setText(AnnotatedString(icon.name))
                BpkToast.makeText(
                  context,
                  context.getString(R.string.copied_to_clipboard, icon.name),
                  BpkToast.LENGTH_SHORT,
                ).show()
              }
              .requiredSize(BpkSpacing.Lg)
          ) {

            BpkIcon(
              icon = icon,
              contentDescription = stringResource(R.string.icons_story_content_description, icon.name),
              size = size,
            )
          }
        }
      }
    }
  }
}
