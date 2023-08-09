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

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import net.skyscanner.backpack.compose.snippet.BpkSnippet
import net.skyscanner.backpack.compose.snippet.ImageOrientation
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.demo.components.SnippetComponent
import net.skyscanner.backpack.demo.meta.ComposeStory

@Composable
@SnippetComponent
@ComposeStory("Landscape")
fun SnippetLandscapeStory(modifier: Modifier = Modifier) {
    DefaultSnippetSample(
        modifier = modifier,
        imageOrientation = ImageOrientation.Landscape,
        headline = stringResource(R.string.snippet_headline),
        subHeading = stringResource(R.string.snippet_sub_heading),
        bodyText = stringResource(R.string.snippet_body),
    )
}

@Composable
@SnippetComponent
@ComposeStory("Square")
fun SnippetSquareStory(modifier: Modifier = Modifier) {
    DefaultSnippetSample(
        modifier = modifier,
        imageOrientation = ImageOrientation.Square,
        headline = stringResource(R.string.snippet_headline),
        subHeading = stringResource(R.string.snippet_sub_heading),
        bodyText = stringResource(R.string.snippet_body),
    )
}

@Composable
@SnippetComponent
@ComposeStory("Portrait")
fun SnippetPortraitStory(modifier: Modifier = Modifier) {
    DefaultSnippetSample(
        modifier = modifier,
        imageOrientation = ImageOrientation.Portrait,
        headline = stringResource(R.string.snippet_headline),
        subHeading = stringResource(R.string.snippet_sub_heading),
        bodyText = stringResource(R.string.snippet_body),
    )
}

@Composable
internal fun DefaultSnippetSample(
    imageOrientation: ImageOrientation,
    modifier: Modifier = Modifier,
    headline: String? = null,
    subHeading: String? = null,
    bodyText: String? = null,
) {
    BpkSnippet(
        modifier = modifier
            .width(MOBILE_WIDTH.dp)
            .padding(BpkSpacing.Base),
        imageOrientation = imageOrientation,
        headline = headline,
        subHeading = subHeading,
        bodyText = bodyText,
    ) {
        Image(
            painter = painterResource(R.drawable.snippet_placeholder_1),
            contentDescription = stringResource(R.string.snippet_image_content_description),
            contentScale = ContentScale.Crop,
        )
    }
}

private const val MOBILE_WIDTH = 250
