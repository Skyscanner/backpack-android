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

package net.skyscanner.backpack.compose.map

import androidx.annotation.RestrictTo
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.Marker
import com.google.maps.android.compose.MarkerInfoWindow
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberMarkerState
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkBorderRadius
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.compose.tokens.internal.BpkMapMarkerColors
import net.skyscanner.backpack.compose.utils.rememberCapturedComposeBitmapDescriptor

enum class BpkPriceMarkerV2Status {
    Unselected,
    Selected,
    PreviousSelected,
}

@Composable
fun BpkPriceMapMarkerV2(
    title: String,
    status: BpkPriceMarkerV2Status = BpkPriceMarkerV2Status.Unselected,
    state: MarkerState = rememberMarkerState(),
    tag: Any? = null,
    visible: Boolean = true,
    zIndex: Float? = null,
    onClick: (Marker) -> Boolean = { false },
) {
    val icon = rememberCapturedComposeBitmapDescriptor(title, status) {
        PriceMarkerV2Layout(title = title, status = status)
    }

    MarkerInfoWindow(
        state = state,
        tag = tag,
        title = title,
        visible = visible,
        zIndex = if (status == BpkPriceMarkerV2Status.Selected && zIndex == null) 1.0f else zIndex ?: 0.0f,
        icon = icon,
        onClick = onClick,
    ) {}
}

@Composable
@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
fun PriceMarkerV2Layout(title: String, status: BpkPriceMarkerV2Status, modifier: Modifier = Modifier) {
    val textColor = when (status) {
        BpkPriceMarkerV2Status.Unselected -> BpkTheme.colors.textPrimary
        BpkPriceMarkerV2Status.Selected -> BpkTheme.colors.textOnDark
        BpkPriceMarkerV2Status.PreviousSelected -> BpkTheme.colors.textOnLight
    }

    val backgroundFillColor = when (status) {
        BpkPriceMarkerV2Status.Unselected -> BpkTheme.colors.surfaceDefault
        BpkPriceMarkerV2Status.Selected -> BpkTheme.colors.corePrimary
        BpkPriceMarkerV2Status.PreviousSelected -> BpkMapMarkerColors.mapPreviousSelection
    }

    val borderRadius = BpkBorderRadius.Sm

    val labelStyle = BpkTheme.typography.label3

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .height(if (status == BpkPriceMarkerV2Status.Selected) FocusedMarkerHeight else DefaultMarkerHeight)
            .background(
                color = backgroundFillColor,
                shape = RoundedCornerShape(borderRadius),
            )
            .padding(horizontal = BpkSpacing.Md, vertical = VerticalPadding),
    ) {
        BpkText(
            text = title,
            color = textColor,
            style = labelStyle,
            textAlign = TextAlign.Center,
            maxLines = 1,
        )
    }
}

private val FocusedMarkerHeight = 26.dp
private val DefaultMarkerHeight = 24.dp

private val VerticalPadding = 1.dp
