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

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import net.skyscanner.backpack.compose.fab.BpkFab
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.compose.tokens.Flight
import net.skyscanner.backpack.compose.tokens.Search
import net.skyscanner.backpack.compose.tokens.Star
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.toast.BpkToast

@Composable
fun BpkFabStory() {
  Column(
    modifier = Modifier.padding(BpkSpacing.Xl),
    verticalArrangement = Arrangement.spacedBy(BpkSpacing.Xxl, Alignment.CenterVertically),
  ) {
    val context = LocalContext.current
    BpkFab(
      onClick = { showGenericMessageToast(context) },
      icon = BpkIcon.Search,
      contentDescription = stringResource(R.string.content_description),
    )

    BpkFab(
      onClick = { showGenericMessageToast(context) },
      icon = BpkIcon.Star,
      contentDescription = stringResource(R.string.content_description),
    )

    BpkFab(
      onClick = { showGenericMessageToast(context) },
      icon = BpkIcon.Flight,
      contentDescription = stringResource(R.string.content_description),
    )
  }
}

private fun showGenericMessageToast(context: Context) {
  BpkToast.makeText(context, context.getString(R.string.generic_message), BpkToast.LENGTH_SHORT).show()
}
