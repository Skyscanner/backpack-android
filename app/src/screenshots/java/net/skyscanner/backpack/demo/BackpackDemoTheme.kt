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

package net.skyscanner.backpack.demo

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Indication
import androidx.compose.foundation.IndicationInstance
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import net.skyscanner.backpack.compose.theme.BpkTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BackpackDemoTheme(
  content: @Composable () -> Unit,
) {
  BpkTheme(
    fontFamily = FontFamily(
      Font(R.font.skyscanner_relative_android_book),
      Font(R.font.skyscanner_relative_android_bold, weight = FontWeight.Bold),
    ),
  ) {

    CompositionLocalProvider(
      LocalIndication provides NoIndication,
      LocalRippleTheme provides NoRippleTheme,
      LocalOverscrollConfiguration provides null,
      content = content,
    )
  }
}

private object NoIndication : Indication {
  private object NoIndicationInstance : IndicationInstance {
    override fun ContentDrawScope.drawIndication() {
      drawContent()
    }
  }

  @Composable
  override fun rememberUpdatedInstance(interactionSource: InteractionSource): IndicationInstance {
    return NoIndicationInstance
  }
}

private object NoRippleTheme : RippleTheme {

  @Composable
  override fun defaultColor(): Color = Color.Transparent

  @Composable
  override fun rippleAlpha(): RippleAlpha =
    RippleAlpha(0f, 0f, 0f, 0f)

}
