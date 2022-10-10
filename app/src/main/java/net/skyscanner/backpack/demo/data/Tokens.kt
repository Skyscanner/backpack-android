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

package net.skyscanner.backpack.demo.data

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import net.skyscanner.backpack.compose.tokens.BpkBorderRadius
import net.skyscanner.backpack.compose.tokens.BpkColor
import net.skyscanner.backpack.compose.tokens.BpkColors
import net.skyscanner.backpack.compose.tokens.BpkElevation
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import kotlin.reflect.KProperty1
import kotlin.reflect.full.memberProperties
import kotlin.reflect.full.starProjectedType

val BpkSpacing.values: List<Token<Dp>>
  get() = values()

val BpkBorderRadius.values: List<Token<Dp>>
  get() = values()

val BpkElevation.values: List<Token<Dp>>
  get() = values()

val BpkColor.values: List<Token<Color>>
  get() = values()

val BpkColors.values: List<Token<Color>>
  get() = BpkColors::class.memberProperties.mapNotNull {
    if (it.returnType == Color::class.starProjectedType) Token(it.name, it.call(this) as Color) else null
  }

data class Token<T>(val name: String, val value: T)

private inline fun <reified T, reified R> T.values(): List<Token<R>> =
  T::class.members
    .filterIsInstance<KProperty1<*, *>>()
    .filter { it.returnType == R::class.starProjectedType }
    .map { Token(it.name, it.call(this) as R) }
