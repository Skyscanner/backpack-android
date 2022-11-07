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
 *
 */

package net.skyscanner.backpack.demo.compose

import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import net.skyscanner.backpack.compose.badge.BpkBadge
import net.skyscanner.backpack.compose.badge.BpkBadgeType
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkDimension
import net.skyscanner.backpack.demo.ComponentDetailActivity
import net.skyscanner.backpack.demo.ComponentDetailFragment
import net.skyscanner.backpack.demo.R

@Composable
fun ComponentsTitle(title: String, modifier: Modifier = Modifier) {
  BpkText(
    text = title.uppercase(),
    modifier = modifier
      .fillMaxWidth()
      .padding(
        vertical = BpkDimension.Spacing.Base,
        horizontal = BpkDimension.Spacing.Lg
      ),
    color = BpkTheme.colors.textSecondary,
    style = BpkTheme.typography.label2,
  )
}

@Composable
fun ComponentItem(
  title: String,
  modifier: Modifier = Modifier,
  showComposeBadge: Boolean = false,
) {
  val context = LocalContext.current
  ListItem(
    title = title,
    modifier = modifier
      .clickable {
        val intent = Intent(context, ComponentDetailActivity::class.java)
        intent.putExtra(ComponentDetailFragment.ARG_ITEM_ID, title)
        context.startActivity(intent)
      },
  ) {
    if (showComposeBadge) {
      BpkBadge(
        text = stringResource(R.string.story_badge_compose),
        type = BpkBadgeType.Success,
        modifier = Modifier.padding(start = BpkDimension.Spacing.Base)
      )
    }
  }
}
