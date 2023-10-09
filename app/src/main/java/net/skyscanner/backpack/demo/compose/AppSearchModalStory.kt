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

package net.skyscanner.backpack.demo.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import net.skyscanner.backpack.compose.appsearchmodal.Action
import net.skyscanner.backpack.compose.appsearchmodal.AppSearchModalResult
import net.skyscanner.backpack.compose.appsearchmodal.BpkAppSearchModal
import net.skyscanner.backpack.compose.appsearchmodal.Item
import net.skyscanner.backpack.compose.appsearchmodal.Section
import net.skyscanner.backpack.compose.appsearchmodal.SectionHeading
import net.skyscanner.backpack.compose.appsearchmodal.Shortcut
import net.skyscanner.backpack.compose.button.BpkButton
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.tokens.Airports
import net.skyscanner.backpack.compose.tokens.City
import net.skyscanner.backpack.compose.tokens.Landmark
import net.skyscanner.backpack.compose.tokens.UseLocation
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.demo.components.AppSearchModalComponent
import net.skyscanner.backpack.demo.meta.ComposeStory

@Composable
@AppSearchModalComponent
@ComposeStory("Content")
fun AppSearchModalStoryContent(modifier: Modifier = Modifier) {
    AppSearchModalStory(result = contentResult)
}

@Composable
@AppSearchModalComponent
@ComposeStory("Loading")
fun AppSearchModalStoryLoading(modifier: Modifier = Modifier) {
    AppSearchModalStory(result = loadingResult)
}

@Composable
@AppSearchModalComponent
@ComposeStory("Error")
fun AppSearchModalStoryError(modifier: Modifier = Modifier) {
    AppSearchModalStory(result = errorResult)
}

@Composable
private fun AppSearchModalStory(
    result: AppSearchModalResult,
    modifier: Modifier = Modifier,
) {
    Column(modifier.fillMaxSize()) {
        DefaultAppSearchModalSample(result = result)
    }
}

@Composable
internal fun DefaultAppSearchModalSample(
    result: AppSearchModalResult,
    modifier: Modifier = Modifier,
) {
    val showModal = rememberSaveable { mutableStateOf(true) }
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        BpkButton(
            text = stringResource(R.string.generic_show),
            onClick = { showModal.value = true },
        )
    }
    if (showModal.value) {
        BpkAppSearchModal(
            modifier = modifier,
            title = "Destination",
            inputText = "",
            inputHint = "Enter your next destination",
            results = result,
            closeAccessibilityLabel = "",
            onClose = { showModal.value = false },
            onInputChanged = {},
        )
    }
}

internal val contentResult = AppSearchModalResult.Content(
    sections = listOf(
        Section(
            items = listOf(
                Item(
                    title = "Current Location",
                    subTitle = "Use current location",
                    icon = BpkIcon.UseLocation,
                ) {},
            ),
        ),
        Section(
            headings = SectionHeading(
                title = "Recent Searches",
                action = Action(text = "View More") {},
            ),
            items = listOf(
                Item(
                    title = "London",
                    subTitle = "City, United Kingdom",
                    icon = BpkIcon.City,
                    onItemSelected = {},

                ),
                Item(
                    title = "London Heathrow",
                    subTitle = "Airport, United Kingdom",
                    icon = BpkIcon.Airports,
                    onItemSelected = {},

                ),
                Item(
                    title = "Cardiff",
                    subTitle = "City, United Kingdom",
                    icon = BpkIcon.Airports,
                    onItemSelected = {},

                ),
            ),
        ),
        Section(
            headings = SectionHeading(
                title = "Popular Destination",
            ),
            items = listOf(
                Item(
                    title = "Shenzhen",
                    subTitle = "City, China",
                    icon = BpkIcon.City,
                    onItemSelected = {},

                ),
                Item(
                    title = "Paris",
                    subTitle = "City, France",
                    icon = BpkIcon.City,
                    onItemSelected = {},

                ),
                Item(
                    title = "Algiers",
                    subTitle = "City, Algeria",
                    icon = BpkIcon.City,
                    onItemSelected = {},

                ),
                Item(
                    title = "Madrid",
                    subTitle = "City, Spain",
                    icon = BpkIcon.City,
                    onItemSelected = {},

                ),
            ),
        ),
    ),
    shortcuts = listOf(
        Shortcut(
            "Natural History Museum",
            icon = BpkIcon.Landmark,
        ) {},
        Shortcut(
            "Big Ben",
            icon = BpkIcon.Landmark,
        ) {},
        Shortcut(
            "London Eye",
            icon = BpkIcon.Landmark,
        ) {},
    ),
)

internal val errorResult = AppSearchModalResult.Error(
    title = "Looks like you drifted away",
    description = "Please check your internet connection to find your way back",
    image = R.drawable.ic_launcher_foreground,
    action = Action(text = "Try Again", onActionSelected = {}),

)

internal val loadingResult = AppSearchModalResult.Loading(accessibilityLabel = "Content is Loading ")
