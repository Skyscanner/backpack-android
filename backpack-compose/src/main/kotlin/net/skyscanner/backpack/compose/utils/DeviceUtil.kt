/**
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2023 Skyscanner Ltd
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

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.dp

@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
internal fun isSmallTablet(): Boolean {
    val info = LocalWindowInfo.current.containerSize.width.dp
    return info > SMALL_TABLET_WIDTH.dp
}

@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
internal fun isTablet(): Boolean {
    val info = LocalWindowInfo.current.containerSize.width.dp
    return info > TABLET_WIDTH.dp
}

@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
internal fun isDesktop(): Boolean {
    val info = LocalWindowInfo.current.containerSize.width.dp
    return info > TABLET_WIDTH.dp
}

const val SMALL_TABLET_WIDTH = 513
const val TABLET_WIDTH = 769
const val DESKTOP_WIDTH = 1025
