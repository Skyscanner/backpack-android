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

package net.skyscanner.backpack.compose.sponsoredbanner

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import net.skyscanner.backpack.compose.R
import net.skyscanner.backpack.compose.annotation.BpkPreviews
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.compose.sponsoredbanner.internal.BpkSponsoredBannerHeaderImpl

@Composable
fun BpkSponsoredBanner(
    variant: Variant,
    backgroundColor: Color,
    modifier: Modifier = Modifier,
    title: String? = null,
    subheadline: String? = null,
    callToAction: CallToAction? = null,
    body: String? = null,
    content: @Composable () -> Unit,
) {
    BpkSponsoredBannerHeaderImpl(
        backgroundColor = backgroundColor,
        variant = variant,
        title = title,
        subheadline = subheadline,
        callToAction = callToAction,
        body = body,
        modifier = modifier,
        content = content,
    )
}

data class CallToAction(val text: String, val accessibilityLabel: String)
enum class Variant { OnDark, OnLight }

@BpkPreviews
@Composable
private fun BpkSponsoredBannerPreview() {
    BpkTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(BpkSpacing.Base)
                .background(Color.White),
            verticalArrangement = Arrangement.Center,
        ) {
            BpkSponsoredBanner(
                variant = Variant.OnDark,
                backgroundColor = Color(0xFFFF6601),
                modifier = Modifier,
                title = "Title",
                subheadline = "Subheadline",
                callToAction = CallToAction("Sponsored", "More information"),
                body = "You can change your destination, date of travel, or both, with no change fee. Valid for all new bookings made up to 31 May for travel between now and 31 December 2020.",
            ) {
                Image(
                    modifier = Modifier.heightIn(22.dp),
                    painter = painterResource(R.drawable.bpk_logout),
                    contentDescription = "image description",
                    contentScale = ContentScale.Fit,
                )
            }
        }
    }
}
