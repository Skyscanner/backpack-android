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

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import net.skyscanner.backpack.compose.bottomsheet.BpkBottomSheet
import net.skyscanner.backpack.compose.bottomsheet.BpkBottomSheetValue
import net.skyscanner.backpack.compose.bottomsheet.BpkModalBottomSheet
import net.skyscanner.backpack.compose.bottomsheet.BpkModalBottomSheetCloseAction
import net.skyscanner.backpack.compose.bottomsheet.internal.BpkDragHandleStyle
import net.skyscanner.backpack.compose.bottomsheet.rememberBpkBottomSheetState
import net.skyscanner.backpack.compose.bottomsheet.rememberBpkModalBottomSheetState
import net.skyscanner.backpack.compose.button.BpkButton
import net.skyscanner.backpack.compose.navigationbar.TextAction
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.demo.components.BottomSheetComponent
import net.skyscanner.backpack.demo.meta.ComposeStory
import net.skyscanner.backpack.demo.ui.ListItem
import net.skyscanner.backpack.meta.StoryKind

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
@ComposeStory(kind = StoryKind.DemoOnly, name = "Image content sheet with Light drag handle")
fun ImageBottomSheetStory(
    modifier: Modifier = Modifier,
    initialValue: BpkBottomSheetValue = BpkBottomSheetValue.Collapsed,
    dragHandleStyle: BpkDragHandleStyle = BpkDragHandleStyle.OnImage(),
) {
    val state = rememberBpkBottomSheetState(initialValue)
    BpkBottomSheet(
        modifier = modifier,
        state = state,
        peekHeight = 96.dp * 3,
        sheetContent = { ImageContent(imageRes = R.drawable.swimming) },
        dragHandleStyle = dragHandleStyle,
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
fun ModalBottomSheetNoTopBarStory(
    modifier: Modifier = Modifier,
) {
    ModalBottomSheetStory(
        modifier = modifier,
    )
}

@Composable
@BottomSheetComponent
@ComposeStory(name = "Modal with TopBar")
fun ModalBottomSheetWithTopBarStory(
    modifier: Modifier = Modifier,
) {
    ModalBottomSheetStory(
        modifier = modifier,
        action = TextAction(text = stringResource(id = R.string.section_header_button_text)) {},
        title = stringResource(id = R.string.modal_bottom_sheet_title),
        closeButton = BpkModalBottomSheetCloseAction.Close(stringResource(id = R.string.navigation_close)),
    )
}

@Composable
@BottomSheetComponent
@ComposeStory(name = "Modal with TopBar and custom title content description", kind = StoryKind.DemoOnly)
fun ModalBottomSheetWithTopBarStoryTitleContentDesc(
    modifier: Modifier = Modifier,
) {
    ModalBottomSheetStory(
        modifier = modifier,
        action = TextAction(text = stringResource(id = R.string.section_header_button_text)) {},
        title = stringResource(id = R.string.modal_bottom_sheet_title),
        titleContentDescription = stringResource(id = R.string.modal_bottom_sheet_title_content_desc),
        closeButton = BpkModalBottomSheetCloseAction.Close(stringResource(id = R.string.navigation_close)),
    )
}

@Composable
internal fun ModalBottomSheetStory(
    modifier: Modifier = Modifier,
    title: String? = null,
    titleContentDescription: String? = null,
    action: TextAction? = null,
    closeButton: BpkModalBottomSheetCloseAction = BpkModalBottomSheetCloseAction.None,
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
            title = title,
            titleContentDescription = titleContentDescription,
            closeButton = closeButton,
            action = action,
            state = state,
            content = { SheetContent() },
            onDismissRequest = { openBottomSheet = false },
        )
    }
}

@Composable
@BottomSheetComponent
@ComposeStory(kind = StoryKind.DemoOnly, name = "Image Modal sheet with Dark drag handle")
fun ImageModalDarkBottomSheetNoTopBarStory(
    modifier: Modifier = Modifier,
    dragHandleStyle: BpkDragHandleStyle = BpkDragHandleStyle.OnImage(BpkDragHandleStyle.OnImage.Type.Dark),
) {
    ImageModalDarkBottomSheetStory(
        modifier = modifier,
        dragHandleStyle = dragHandleStyle,
    )
}

@Composable
@BottomSheetComponent
@ComposeStory(name = "Image Modal sheet with TopBar and Dark drag handle")
fun ImageModalDarkBottomSheetWithTopBarStory(
    modifier: Modifier = Modifier,
    dragHandleStyle: BpkDragHandleStyle = BpkDragHandleStyle.OnImage(BpkDragHandleStyle.OnImage.Type.Dark),
) {
    ImageModalDarkBottomSheetStory(
        modifier = modifier,
        dragHandleStyle = dragHandleStyle,
        action = TextAction(text = stringResource(id = R.string.section_header_button_text), {}),
        title = stringResource(id = R.string.modal_bottom_sheet_title),
        closeButton = BpkModalBottomSheetCloseAction.Close(stringResource(id = R.string.navigation_close)),
    )
}

@Composable
fun ImageModalDarkBottomSheetStory(
    modifier: Modifier = Modifier,
    dragHandleStyle: BpkDragHandleStyle = BpkDragHandleStyle.OnImage(BpkDragHandleStyle.OnImage.Type.Dark),
    title: String? = null,
    action: TextAction? = null,
    closeButton: BpkModalBottomSheetCloseAction = BpkModalBottomSheetCloseAction.None,
) {
    var openBottomSheet by rememberSaveable { mutableStateOf(true) }
    val state = rememberBpkModalBottomSheetState()
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(top = BpkSpacing.Xxl),
        contentAlignment = Alignment.Center,
    ) {
        BpkButton(text = stringResource(R.string.generic_show)) { openBottomSheet = true }
    }
    if (openBottomSheet) {
        BpkModalBottomSheet(
            state = state,
            title = title,
            closeButton = closeButton,
            action = action,
            content = {
                ImageContent(imageRes = R.drawable.beach)
            },
            dragHandleStyle = dragHandleStyle,
            onDismissRequest = { openBottomSheet = false },
            contentWindowInsets = { WindowInsets(0.dp) },
        )
    }
}

@Composable
fun SheetContent(modifier: Modifier = Modifier) {
    LazyColumn(modifier) {
        items(100) {
            ListItem(title = stringResource(R.string.generic_list_item, it), showDivider = false)
        }
        item {
            Spacer(Modifier.windowInsetsBottomHeight(WindowInsets.systemBars))
        }
    }
}

@Composable
fun ImageContent(@DrawableRes imageRes: Int, modifier: Modifier = Modifier) {
    Image(
        modifier = modifier
            .fillMaxWidth(),
        painter = painterResource(id = imageRes),
        contentScale = ContentScale.Crop,
        contentDescription = "",
    )
}
