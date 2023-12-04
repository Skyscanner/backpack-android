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

package net.skyscanner.backpack.compose.utils

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.IntrinsicMeasureScope
import androidx.compose.ui.layout.layout
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.invisibleToUser
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntRect
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.round
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

internal fun Modifier.hideContentIf(hide: Boolean): Modifier = layout { measurable, constraints ->
    val placeable = measurable.measure(constraints)
    layout(placeable.width, placeable.height) {
        if (!hide) {
            placeable.place(0, 0)
        }
    }
}

@OptIn(ExperimentalContracts::class)
internal inline fun Modifier.applyIf(predicate: Boolean, block: Modifier.() -> Modifier): Modifier {
    contract {
        callsInPlace(block, InvocationKind.AT_MOST_ONCE)
    }
    return if (predicate) block() else this
}

internal fun Modifier.clickable(
    enabled: Boolean = true,
    bounded: Boolean = true,
    role: Role? = null,
    onClick: () -> Unit,
): Modifier = composed {
    clickable(
        enabled = enabled,
        interactionSource = remember { MutableInteractionSource() },
        indication = rememberRipple(bounded = bounded),
        role = role,
        onClick = onClick,
    )
}

internal fun Modifier.inset(inset: IntrinsicMeasureScope.(bounds: IntRect) -> IntRect): Modifier =
    layout { measurable, constraints ->
        val rect = inset(IntRect(left = 0, right = constraints.maxWidth, top = 0, bottom = constraints.maxHeight))
        val placeable = measurable.measure(Constraints.fixed(rect.width, rect.height))
        layout(constraints.maxWidth, constraints.maxHeight) {
            placeable.place(x = rect.left, y = rect.top)
        }
    }

internal fun Modifier.offsetWithSize(offset: IntrinsicMeasureScope.(size: IntSize) -> IntOffset): Modifier =
    layout { measurable, constraints ->
        val placeable = measurable.measure(constraints)
        val size = IntSize(placeable.width, placeable.height)
        layout(size.width, size.height) {
            val offsetValue = offset(size)
            placeable.place(offsetValue.x, offsetValue.y)
        }
    }

internal fun Modifier.alignBy(anchor: Offset, alignment: Alignment): Modifier = offsetWithSize { size ->
    anchor.round() - alignment.align(IntSize.Zero, size, layoutDirection)
}

@OptIn(ExperimentalComposeUiApi::class)
fun Modifier.invisibleSemantic(): Modifier =
    semantics { invisibleToUser() }
