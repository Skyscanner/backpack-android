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

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import net.skyscanner.backpack.compose.bottomsheet.BpkBottomSheet
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.demo.R

@Preview
@Composable
fun BottomSheetStory(
  modifier: Modifier = Modifier,
) {
  BpkBottomSheet(
    sheetPeekHeight = 56.dp * 3,
    sheetContent = {
      LazyColumn {
        items(100) {
          ListItem(title = stringResource(R.string.generic_list_item, it), showDivider = false)
        }
      }
    },
    content = { contentPadding ->
      Spacer(
        modifier = Modifier
          .fillMaxSize()
          .background(BpkTheme.colors.surfaceHighlight)
          .padding(contentPadding),
      )
    }
  )
}
