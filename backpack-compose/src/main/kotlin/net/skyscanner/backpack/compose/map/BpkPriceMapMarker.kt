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
import androidx.compose.foundation.border
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
import net.skyscanner.backpack.compose.flare.BpkFlarePointerDirection
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkBorderRadius
import net.skyscanner.backpack.compose.tokens.BpkBorderSize
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.compose.tokens.internal.BpkMapMarkerColors
import net.skyscanner.backpack.compose.utils.FlareShape
import net.skyscanner.backpack.compose.utils.rememberCapturedComposeBitmapDescriptor

enum class BpkPriceMarkerStatus {
    Default,
    Focused,
    Viewed,
    Disabled,
}

@Deprecated("Use BpkPriceMapMarkerV2 instead.", ReplaceWith("BpkPriceMapMarkerV2"), DeprecationLevel.WARNING)
@Composable
fun BpkPriceMapMarker(
    title: String,
    status: BpkPriceMarkerStatus = BpkPriceMarkerStatus.Default,
    state: MarkerState = rememberMarkerState(),
    tag: Any? = null,
    visible: Boolean = true,
    zIndex: Float? = null,
    onClick: (Marker) -> Boolean = { false },
) {
    val icon = rememberCapturedComposeBitmapDescriptor(title, status) {
        PriceMarkerLayout(title = title, status = status)
    }

    MarkerInfoWindow(
        state = state,
        tag = tag,
        title = title,
        visible = visible,
        zIndex = if (status == BpkPriceMarkerStatus.Focused && zIndex == null) 1.0f else zIndex ?: 0.0f,
        icon = icon,
        onClick = onClick,
    ) {}
}

@Composable
@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
fun PriceMarkerLayout(title: String, status: BpkPriceMarkerStatus, modifier: Modifier = Modifier) {
    val textColor = when (status) {
        BpkPriceMarkerStatus.Default -> BpkTheme.colors.textPrimaryInverse
        BpkPriceMarkerStatus.Focused -> BpkTheme.colors.coreAccent
        BpkPriceMarkerStatus.Viewed -> BpkMapMarkerColors.viewedForeground
        BpkPriceMarkerStatus.Disabled -> BpkTheme.colors.textDisabled
    }

    val flareColor = when (status) {
        BpkPriceMarkerStatus.Disabled -> BpkTheme.colors.surfaceDefault
        else -> BpkTheme.colors.coreAccent
    }

    val backgroundFillColor = when (status) {
        BpkPriceMarkerStatus.Focused -> BpkTheme.colors.canvas
        else -> flareColor
    }

    val borderRadius = when (status) {
        BpkPriceMarkerStatus.Focused -> FocusedPadding
        else -> BpkBorderRadius.Xs
    }

    val labelStyle = when (status) {
        BpkPriceMarkerStatus.Focused -> BpkTheme.typography.label1
        else -> BpkTheme.typography.label2
    }

    Box(
        modifier = modifier
            .background(
                color = flareColor,
                shape = FlareShape(
                    borderRadius = borderRadius,
                    flareHeight = FlareHeight,
                    pointerDirection = BpkFlarePointerDirection.Down,
                ),
            )
            .padding(bottom = FlareHeight),
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .height(if (status == BpkPriceMarkerStatus.Focused) FocusedMarkerHeight else DefaultMarkerHeight)
                .border(
                    width = BpkBorderSize.Lg,
                    color = flareColor,
                    shape = RoundedCornerShape(borderRadius),
                )
                .background(
                    color = backgroundFillColor,
                    shape = RoundedCornerShape(borderRadius),
                )
                .padding(horizontal = BpkSpacing.Md),
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
}

private val FocusedMarkerHeight = 32.dp
private val DefaultMarkerHeight = 24.dp

private val FocusedPadding = 6.dp
private val FlareHeight = 6.dp
