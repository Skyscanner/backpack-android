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

package net.skyscanner.backpack.compose.snippet

import androidx.compose.foundation.Image
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import net.skyscanner.backpack.compose.BpkSnapshotTest
import net.skyscanner.backpack.demo.R
import org.junit.Test

class BpkSnippetTest : BpkSnapshotTest() {

    @Test
    fun default() = snap {
        BpkSnippet {
            Image(
                painter = painterResource(R.drawable.snippet_placeholder_1),
                contentDescription = stringResource(R.string.snippet_image_content_description),
                contentScale = ContentScale.Crop,
            )
        }
    }

    @Test
    fun defaultHeadline() = snap {
        BpkSnippet(
            imageOrientation = ImageOrientation.Landscape,
            headline = stringResource(R.string.snippet_headline),
        ) {
            Image(
                painter = painterResource(R.drawable.snippet_placeholder_1),
                contentDescription = stringResource(R.string.snippet_image_content_description),
                contentScale = ContentScale.Crop,
            )
        }
    }

    @Test
    fun defaultHeadlineDescription() = snap {
        BpkSnippet(
            imageOrientation = ImageOrientation.Landscape,
            headline = stringResource(R.string.snippet_headline),
            subHeading = stringResource(R.string.snippet_sub_heading),
        ) {
            Image(
                painter = painterResource(R.drawable.snippet_placeholder_1),
                contentDescription = stringResource(R.string.snippet_image_content_description),
                contentScale = ContentScale.Crop,
            )
        }
    }

    @Test
    fun defaultHeadlineDescriptionBody() = snap {
        BpkSnippet(
            imageOrientation = ImageOrientation.Landscape,
            headline = stringResource(R.string.snippet_headline),
            subHeading = stringResource(R.string.snippet_sub_heading),
            bodyText = stringResource(R.string.snippet_body),
        ) {
            Image(
                painter = painterResource(R.drawable.snippet_placeholder_1),
                contentDescription = stringResource(R.string.snippet_image_content_description),
                contentScale = ContentScale.Crop,
            )
        }
    }

    @Test
    fun squareHeadlineDescriptionBody() = snap {
        BpkSnippet(
            imageOrientation = ImageOrientation.Square,
            headline = stringResource(R.string.snippet_headline),
            subHeading = stringResource(R.string.snippet_sub_heading),
            bodyText = stringResource(R.string.snippet_body),
        ) {
            Image(
                painter = painterResource(R.drawable.snippet_placeholder_1),
                contentDescription = stringResource(R.string.snippet_image_content_description),
                contentScale = ContentScale.Crop,
            )
        }
    }

    @Test
    fun portraitHeadlineDescriptionBody() = snap {
        BpkSnippet(
            imageOrientation = ImageOrientation.Portrait,
            headline = stringResource(R.string.snippet_headline),
            subHeading = stringResource(R.string.snippet_sub_heading),
            bodyText = stringResource(R.string.snippet_body),
        ) {
            Image(
                painter = painterResource(R.drawable.snippet_placeholder_1),
                contentDescription = stringResource(R.string.snippet_image_content_description),
                contentScale = ContentScale.Crop,
            )
        }
    }
}
