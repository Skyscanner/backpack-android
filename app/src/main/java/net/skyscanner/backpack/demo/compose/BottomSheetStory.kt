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
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import net.skyscanner.backpack.compose.bottomsheet.BpkBottomSheet
import net.skyscanner.backpack.compose.bottomsheet.BpkBottomSheetValue
import net.skyscanner.backpack.compose.bottomsheet.BpkModalBottomSheet
import net.skyscanner.backpack.compose.bottomsheet.rememberBpkBottomSheetState
import net.skyscanner.backpack.compose.bottomsheet.rememberBpkModalBottomSheetState
import net.skyscanner.backpack.compose.button.BpkButton
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.demo.components.BottomSheetComponent
import net.skyscanner.backpack.demo.meta.ComposeStory
import net.skyscanner.backpack.demo.ui.ListItem

@Composable
@BottomSheetComponent
@ComposeStory
fun BottomSheetStory(
    modifier: Modifier = Modifier,
    initialValue: BpkBottomSheetValue = BpkBottomSheetValue.Collapsed,
) {
    val state = rememberBpkBottomSheetState(initialValue)
    BpkBottomSheet(
        modifier = modifier,
        state = state,
        peekHeight = 56.dp * 3,
        sheetContent = { SheetContent() },
        content = { contentPadding ->
            Spacer(
                modifier = Modifier
                    .fillMaxSize()
                    .background(BpkTheme.colors.canvasContrast)
                    .padding(contentPadding),
            )
        },
    )
}

@Composable
@BottomSheetComponent
@ComposeStory(name = "Modal")
fun ModalBottomSheetStory(
    modifier: Modifier = Modifier,
) {
    var openBottomSheet by rememberSaveable { mutableStateOf(true) }
    val state = rememberBpkModalBottomSheetState()
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(top = BpkSpacing.Xxl),
        contentAlignment = Alignment.Center,
    ) {
        BpkButton(text = stringResource(R.string.generic_show)) {
            openBottomSheet = true
        }
    }
    if (openBottomSheet) {
        BpkModalBottomSheet(
            state = state,
            content = { SheetContent() },
            onDismissRequest = { openBottomSheet = false },
        )
    }
}

@Composable
fun SheetContent(modifier: Modifier = Modifier) {
    LazyColumn(modifier) {
        items(100) {
            ListItem(title = stringResource(R.string.generic_list_item, it), showDivider = false)
        }
    }
}
