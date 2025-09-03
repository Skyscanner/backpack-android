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
 *
 */

package net.skyscanner.backpack.compose.utils

import android.content.res.Resources
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalResources

@Stable
interface ContentDescriptionScope {

    fun stringResource(@StringRes id: Int): String

    fun stringResource(@StringRes id: Int, vararg formatArgs: Any): String
}

internal fun ContentDescriptionScope(resources: Resources): ContentDescriptionScope =
    object : ContentDescriptionScope {

        override fun stringResource(id: Int): String =
            resources.getString(id)

        override fun stringResource(id: Int, vararg formatArgs: Any): String =
            resources.getString(id, *formatArgs)
    }

@Composable
internal fun rememberContentDescriptionScope(): ContentDescriptionScope {
    val resources = LocalResources.current
    return remember(resources) {
        ContentDescriptionScope(resources)
    }
}
