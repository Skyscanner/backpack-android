/**
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2018 - 2026 Skyscanner Ltd
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

package net.skyscanner.backpack.compose.blur

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import net.skyscanner.backpack.compose.blur.internal.bpkProgressiveBlurImpl
import net.skyscanner.backpack.compose.blur.internal.bpkUniformBlurImpl

/**
 * Applies a uniform blur effect to the composable.
 * Note this effect is only supported on Android 12 and above.
 * Attempts to use this Modifier on older Android versions will be ignored.
 */
fun Modifier.bpkUniformBlur() = bpkUniformBlurImpl()

/**
 * Applies a progressive blur effect to the bottom half of the composable.
 *
 * The blur starts at the vertical midpoint and increases towards the bottom edge,
 * creating a visually appealing transition. This modifier uses a custom AGSL shader
 * for optimal performance and appearance on supported Android versions (API 33+).
 *
 * - The effect is clipped to the composable bounds.
 * - The blur strength and direction are handled via uniforms for two-pass chaining (X then Y).
 * - Falls back to a uniform blur on unsupported platforms.
 *
 * This modifier is designed to be accessible and themable, following Backpack's
 * component standards. It does not interfere with touch targets, semantics, or
 * theming, and is compatible with both light and dark modes.
 */
@Composable
fun Modifier.bpkProgressiveBlur(): Modifier = bpkProgressiveBlurImpl()
