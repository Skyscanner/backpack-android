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

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.Marker
import com.google.maps.android.compose.MarkerInfoWindow
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberMarkerState
import net.skyscanner.backpack.compose.flare.BpkFlarePointerDirection
import net.skyscanner.backpack.compose.map.internal.rememberCapturedComposeBitmapDescriptor
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkBorderRadius
import net.skyscanner.backpack.compose.tokens.BpkElevation
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.compose.utils.FlareShape
import net.skyscanner.backpack.compose.utils.dynamicColorOf

enum class BpkPriceMarkerStatus {
    Default,
    Focused,
    Viewed,
    Disabled,
}

@Composable
fun BpkPriceMapMarker(
    title: String,
    status: BpkPriceMarkerStatus = BpkPriceMarkerStatus.Default,
    state: MarkerState = rememberMarkerState(),
    tag: Any? = null,
    visible: Boolean = true,
    zIndex: Float = 0.0f,
    onClick: (Marker) -> Unit = {},
) {
    val icon = rememberCapturedComposeBitmapDescriptor(key = listOf(title, status, "price").toString()) {
        PriceMarkerLayout(title = title, status = status)
    }

    MarkerInfoWindow(
        state = state,
        tag = tag,
        title = title,
        visible = visible,
        zIndex = zIndex,
        icon = icon,
        onClick = { onClick(it); false },
    ) {}
}

@Composable
fun PriceMarkerLayout(title: String, status: BpkPriceMarkerStatus, modifier: Modifier = Modifier) {
    val textColor = when (status) {
        BpkPriceMarkerStatus.Default -> BpkTheme.colors.textPrimaryInverse
        BpkPriceMarkerStatus.Focused -> BpkTheme.colors.coreAccent
        BpkPriceMarkerStatus.Viewed -> dynamicColorOf(Color.White.copy(alpha = 0.8f), Color.Black.copy(alpha = 0.8f))
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
        BpkPriceMarkerStatus.Focused -> 6.dp
        else -> BpkBorderRadius.Xs
    }

    val flareShape = FlareShape(
        borderRadius = borderRadius,
        flareHeight = 6.dp,
        pointerDirection = BpkFlarePointerDirection.Down,
    )

    val labelStyle = when (status) {
        BpkPriceMarkerStatus.Focused -> BpkTheme.typography.label1
        else -> BpkTheme.typography.label2
    }

    Box(
        modifier = modifier
            .shadow(elevation = BpkElevation.Sm, shape = flareShape)
            .background(color = flareColor, shape = flareShape)
            .padding(bottom = 6.dp),
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .height(if (status == BpkPriceMarkerStatus.Focused) 32.dp else 24.dp)
                .border(
                    width = 2.dp,
                    color = flareColor,
                    shape = RoundedCornerShape(borderRadius),
                )
                .background(
                    color = backgroundFillColor,
                    shape = RoundedCornerShape(borderRadius),
                )
                .padding(
                    horizontal = BpkSpacing.Md,
                ),
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
