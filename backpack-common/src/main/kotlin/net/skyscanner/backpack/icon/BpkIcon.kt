/*
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2018 - 2025 Skyscanner Ltd
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

package net.skyscanner.backpack.icon

import androidx.annotation.DrawableRes
import net.skyscanner.backpack.icon.tokens.values

/**
 * Represents a Backpack icon with small and large variants.
 */
class BpkIcon internal constructor(
    val name: String,
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

fun BpkIcon.Companion.findByName(name: String): BpkIcon? =
    BpkIcon.values.find { it.name == name }

fun BpkIcon.Companion.findBySmall(@DrawableRes resId: Int): BpkIcon? =
    BpkIcon.values.find { it.small == resId }

operator fun BpkIcon.get(size: BpkIconSize): Int =
    when (size) {
        BpkIconSize.Small -> small
        BpkIconSize.Large -> large
    }
