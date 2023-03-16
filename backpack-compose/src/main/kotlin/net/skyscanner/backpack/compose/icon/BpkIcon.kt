/*
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

package net.skyscanner.backpack.compose.icon

import androidx.compose.foundation.layout.requiredSize
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import net.skyscanner.backpack.compose.tokens.BpkSpacing

@Immutable
class BpkIcon internal constructor(
    val name: String,
    val autoMirror: Boolean = false,
    internal val small: Int,
    internal val large: Int,
) {

    override fun equals(other: Any?): Boolean =
        this === other // all the icons are expected to be singletons

    override fun hashCode(): Int =
        name.hashCode() // all the names are expected to be unique

    override fun toString(): String =
        name

    companion object // the receiver for static extensions
}

enum class BpkIconSize {
    Small,
    Large,
}

val LocalBpkIconSize = staticCompositionLocalOf { BpkIconSize.Small }

@Composable
fun BpkIcon(
    icon: BpkIcon,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    size: BpkIconSize = LocalBpkIconSize.current,
    tint: Color = LocalContentColor.current, // todo: alpha was removed
) {
    Icon(
        painter = painterResource(id = icon[size]),
        contentDescription = contentDescription,
        tint = tint,
        modifier = modifier
            .defaultMinSize(size),
    )
}

private operator fun BpkIcon.get(size: BpkIconSize): Int =
    when (size) {
        BpkIconSize.Small -> small
        BpkIconSize.Large -> large
    }

private fun Modifier.defaultMinSize(size: BpkIconSize): Modifier =
    when (size) {
        BpkIconSize.Small -> requiredSize(BpkSpacing.Base, BpkSpacing.Base)
        BpkIconSize.Large -> requiredSize(BpkSpacing.Lg, BpkSpacing.Lg)
    }
