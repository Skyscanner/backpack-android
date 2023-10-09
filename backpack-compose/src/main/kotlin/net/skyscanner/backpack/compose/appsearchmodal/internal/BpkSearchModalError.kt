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

package net.skyscanner.backpack.compose.appsearchmodal.internal

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import net.skyscanner.backpack.compose.appsearchmodal.AppSearchModalResult
import net.skyscanner.backpack.compose.button.BpkButton
import net.skyscanner.backpack.compose.button.BpkButtonSize
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkSpacing

private const val ImageHeight = 200
private const val ImageWidth = 277

@Composable
internal fun BpkSearchModalError(
    results: AppSearchModalResult.Error,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(BpkSpacing.Lg),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Image(
            modifier = Modifier
                .height(ImageHeight.dp)
                .width(ImageWidth.dp),
            painter = painterResource(id = results.image), contentDescription = null,
        )
        BpkText(
            text = results.title,
            style = BpkTheme.typography.heading4,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = BpkSpacing.Xxl),
        )
        BpkText(
            text = results.description,
            modifier = Modifier.padding(vertical = BpkSpacing.Base),
            textAlign = TextAlign.Center,
        )
        BpkButton(
            modifier = Modifier.fillMaxWidth(),
            size = BpkButtonSize.Large,
            text = results.action.text, onClick = results.action.onActionSelected,
        )
    }
}
