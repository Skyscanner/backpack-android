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

import android.content.res.Resources
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import net.skyscanner.backpack.compose.appsearchmodal.BpkAction
import net.skyscanner.backpack.compose.appsearchmodal.BpkAppSearchModal
import net.skyscanner.backpack.compose.appsearchmodal.BpkAppSearchModalResult
import net.skyscanner.backpack.compose.appsearchmodal.BpkItem
import net.skyscanner.backpack.compose.appsearchmodal.BpkSection
import net.skyscanner.backpack.compose.appsearchmodal.BpkSectionHeading
import net.skyscanner.backpack.compose.appsearchmodal.BpkShortcut
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
    AppSearchModalStory(result = contentResult(LocalContext.current.resources))
}

@Composable
@AppSearchModalComponent
@ComposeStory("Loading")
fun AppSearchModalStoryLoading(modifier: Modifier = Modifier) {
    AppSearchModalStory(result = loadingResult(LocalContext.current.resources))
}

@Composable
@AppSearchModalComponent
@ComposeStory("Error")
fun AppSearchModalStoryError(modifier: Modifier = Modifier) {
    AppSearchModalStory(result = errorResult(LocalContext.current.resources))
}

@Composable
private fun AppSearchModalStory(
    result: BpkAppSearchModalResult,
    modifier: Modifier = Modifier,
) {
    Column(modifier.fillMaxSize()) {
        DefaultAppSearchModalSample(result = result)
    }
}

@Composable
internal fun DefaultAppSearchModalSample(
    result: BpkAppSearchModalResult,
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
            title = stringResource(id = R.string.destination),
            inputText = "",
            inputHint = stringResource(id = R.string.text_field_hint),
            results = result,
            closeAccessibilityLabel = stringResource(id = R.string.navigation_close),
            onClose = { showModal.value = false },
            onInputChanged = {},
        )
    }
}

internal fun contentResult(resources: Resources) = BpkAppSearchModalResult.Content(
    sections = listOf(
        BpkSection(
            items = listOf(
                BpkItem(
                    title = buildAnnotatedString {
                        append(resources.getString(R.string.current_location_title))
                    },
                    subTitle = resources.getString(R.string.current_location_subtitle),
                    icon = BpkIcon.UseLocation,
                ) {},
            ),
        ),
        BpkSection(
            headings = BpkSectionHeading(
                title = resources.getString(R.string.recent_searches),
                action = BpkAction(text = resources.getString(R.string.view_more)) {},
            ),
            items = listOf(
                BpkItem(
                    title = buildAnnotatedString { append(resources.getString(R.string.city_london)) },
                    subTitle = resources.getString(R.string.search_modal_item_subtitle),
                    icon = BpkIcon.City,
                    onItemSelected = {},

                ),
                BpkItem(
                    title = buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append(resources.getString(R.string.city_london))
                        }
                        append(" Heathrow")
                    },
                    subTitle = resources.getString(R.string.search_modal_item_subtitle),
                    icon = BpkIcon.Airports,
                    onItemSelected = {},

                ),
                BpkItem(
                    title = buildAnnotatedString { append(resources.getString(R.string.city_rome)) },
                    subTitle = resources.getString(R.string.search_modal_item_subtitle),
                    icon = BpkIcon.Airports,
                    onItemSelected = {},

                ),
            ),
        ),
        BpkSection(
            headings = BpkSectionHeading(
                title = resources.getString(R.string.popular_destinations),
            ),
            items = listOf(
                BpkItem(
                    title = buildAnnotatedString { append(resources.getString(R.string.city_shenzhen)) },
                    subTitle = resources.getString(R.string.search_modal_item_subtitle),
                    icon = BpkIcon.City,
                    onItemSelected = {},

                ),
                BpkItem(
                    title = buildAnnotatedString { append(resources.getString(R.string.city_paris)) },
                    subTitle = resources.getString(R.string.search_modal_item_subtitle),
                    icon = BpkIcon.City,
                    onItemSelected = {},

                ),
                BpkItem(
                    title = buildAnnotatedString { append(resources.getString(R.string.city_algiers)) },
                    subTitle = resources.getString(R.string.search_modal_item_subtitle),
                    icon = BpkIcon.City,
                    onItemSelected = {},

                ),
                BpkItem(
                    title = buildAnnotatedString { append(resources.getString(R.string.city_madrid)) },
                    subTitle = resources.getString(R.string.search_modal_item_subtitle),
                    icon = BpkIcon.City,
                    onItemSelected = {},

                ),
            ),
        ),
    ),
    shortcuts = listOf(
        BpkShortcut(
            resources.getString(R.string.city_tokyo),
            icon = BpkIcon.Landmark,
        ) {},
        BpkShortcut(
            resources.getString(R.string.city_cairo),
            icon = BpkIcon.Landmark,
        ) {},
        BpkShortcut(
            resources.getString(R.string.city_long_name),
            icon = BpkIcon.Landmark,
        ) {},
    ),
)

private const val ImageHeight = 200
private const val ImageWidth = 277

internal fun errorResult(resource: Resources) = BpkAppSearchModalResult.Error(
    title = resource.getString(R.string.error_view_title),
    description = resource.getString(R.string.error_view_subtitle),
    image = {
        Image(
            modifier = Modifier
                .height(ImageHeight.dp)
                .width(ImageWidth.dp),
            painter = painterResource(id = R.drawable.ic_launcher_foreground), contentDescription = null,
        )
    },
    action = BpkAction(text = resource.getString(R.string.try_again), onActionSelected = {}),

)

internal fun loadingResult(resource: Resources) = BpkAppSearchModalResult.Loading(accessibilityLabel = resource.getString(R.string.content_is_loading))
