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

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
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
    verticalArrangement = Arrangement.Center,
  ) {
    val context = LocalContext.current
    BpkFab(
      onClick = { BpkToast.makeText(context, "Message", BpkToast.LENGTH_SHORT).show() },
      icon = BpkIcon.Search,
      contentDescription = stringResource(R.string.content_description),
    )
    Spacer(modifier = Modifier.height(BpkSpacing.Xxl))
    BpkFab(
      onClick = { BpkToast.makeText(context, "Message", BpkToast.LENGTH_SHORT).show() },
      icon = BpkIcon.Star,
      contentDescription = stringResource(R.string.content_description),
    )
    Spacer(modifier = Modifier.height(BpkSpacing.Xxl))
    BpkFab(
      onClick = { BpkToast.makeText(context, "Message", BpkToast.LENGTH_SHORT).show() },
      icon = BpkIcon.Flight,
      contentDescription = stringResource(R.string.content_description),
    )
  }
}
