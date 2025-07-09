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

import android.util.Log
import androidx.annotation.RestrictTo
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.Marker
import com.google.maps.android.compose.MarkerInfoWindow
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberMarkerState
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.icon.BpkIconSize
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkBorderRadius
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.compose.tokens.Heart
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
    prefixIcon: BpkIcon? = null,
) {
    val icon = rememberCapturedComposeBitmapDescriptor(title, status.name) {
        Log.d("MarkerDebug", "Recomputing bitmap for $title ${status.name}")
        PriceMarkerV2Layout(title = title, status = status, prefixIcon = prefixIcon)
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
fun PriceMarkerV2Layout(
    title: String,
    status: BpkPriceMarkerV2Status,
    modifier: Modifier = Modifier,
    prefixIcon: BpkIcon? = null,
) {
    val textColor = when (status) {
        BpkPriceMarkerV2Status.Unselected -> BpkTheme.colors.textOnLight
        BpkPriceMarkerV2Status.Selected -> BpkTheme.colors.textOnDark
        BpkPriceMarkerV2Status.PreviousSelected -> BpkTheme.colors.textOnLight
    }

    val backgroundFillColor = when (status) {
        BpkPriceMarkerV2Status.Unselected -> BpkTheme.colors.textOnDark
        BpkPriceMarkerV2Status.Selected -> BpkTheme.colors.corePrimary
        BpkPriceMarkerV2Status.PreviousSelected -> BpkMapMarkerColors.mapPreviousSelection
    }

    // Specify a different color for the heart icon according to the design spec.
    val prefixIconColor = if (prefixIcon == BpkIcon.Heart && status == BpkPriceMarkerV2Status.Unselected) {
        BpkTheme.colors.surfaceHero
    } else {
        textColor
    }

    val labelStyle = BpkTheme.typography.label3

    Row(
        horizontalArrangement = Arrangement.spacedBy(BpkSpacing.Md, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .height(BpkSpacing.Lg)
            .shadow(elevation = MarkerElevation, shape = RoundedCornerShape(BpkBorderRadius.Sm))
            .background(color = backgroundFillColor)
            .padding(horizontal = BpkSpacing.Md, vertical = BpkSpacing.Sm),
    ) {
        prefixIcon?.let {
            BpkIcon(
                icon = it,
                contentDescription = null,
                tint = prefixIconColor,
                size = BpkIconSize.Small,
            )
        }
        BpkText(
            text = title,
            color = textColor,
            style = labelStyle,
            textAlign = TextAlign.Center,
            maxLines = 1,
        )
    }
}

private val MarkerElevation = 3.dp
