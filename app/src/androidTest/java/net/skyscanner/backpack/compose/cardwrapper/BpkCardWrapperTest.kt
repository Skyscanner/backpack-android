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

package net.skyscanner.backpack.compose.cardwrapper

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import net.skyscanner.backpack.BpkTestVariant
import net.skyscanner.backpack.Variants
import net.skyscanner.backpack.compose.BpkSnapshotTest
import net.skyscanner.backpack.compose.card.BpkCardCorner
import net.skyscanner.backpack.compose.card.BpkCardElevation
import net.skyscanner.backpack.compose.card.BpkCardPadding
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.demo.R
import org.junit.Test

class BpkCardWrapperTest : BpkSnapshotTest() {

    @Test
    fun default() {
        snap {
            BpkCardWrapper(
                modifier = Modifier.fillMaxWidth(),
                backgroundColor = BpkTheme.colors.coreEco,
                headerContent = { HeaderContent() },
                cardContent = { CardContent() },
            )
        }
    }

    @Test
    @Variants(BpkTestVariant.Default)
    fun largeRadius() {
        snap {
            BpkCardWrapper(
                modifier = Modifier.fillMaxWidth(),
                backgroundColor = BpkTheme.colors.coreEco,
                corner = BpkCardCorner.Large,
                headerContent = { HeaderContent() },
                cardContent = { CardContent() },
            )
        }
    }

    @Test
    @Variants(BpkTestVariant.Default)
    fun noPadding() {
        snap {
            BpkCardWrapper(
                modifier = Modifier.fillMaxWidth(),
                backgroundColor = BpkTheme.colors.coreEco,
                cardPadding = BpkCardPadding.None,
                headerContent = { HeaderContent() },
                cardContent = { CardContent() },
            )
        }
    }

    @Test
    @Variants(BpkTestVariant.Default)
    fun focus() {
        snap {
            BpkCardWrapper(
                modifier = Modifier.fillMaxWidth(),
                backgroundColor = BpkTheme.colors.coreEco,
                elevation = BpkCardElevation.Focus,
                headerContent = { HeaderContent() },
                cardContent = { CardContent() },
            )
        }
    }

    @Composable
    private fun CardContent(modifier: Modifier = Modifier) {
        Box(modifier) {
            Image(
                modifier = Modifier
                    .height(BpkSpacing.Xxl * 2)
                    .fillMaxWidth(),
                painter = painterResource(id = R.drawable.canadian_rockies_canada),
                contentDescription = "",
                contentScale = ContentScale.Crop,
            )
            BpkText(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = R.string.card_wrapper_card),
                style = BpkTheme.typography.bodyDefault,
                textAlign = TextAlign.Center,
            )
        }
    }

    @Composable
    private fun HeaderContent(modifier: Modifier = Modifier) {
        BpkText(
            modifier = modifier
                .fillMaxWidth()
                .height(BpkSpacing.Xxl),
            text = stringResource(id = R.string.card_wrapper_header),
            style = BpkTheme.typography.bodyDefault,
            textAlign = TextAlign.Center,
        )
    }
}
