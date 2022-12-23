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

package net.skyscanner.backpack.compose.cardbutton

import androidx.compose.runtime.Composable
import net.skyscanner.backpack.compose.BpkSnapshotTest
import net.skyscanner.backpack.compose.theme.BpkTheme
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class BpkCardButtonTest(flavor: Flavor) : BpkSnapshotTest(listOf(flavor.size, flavor.style)) {

  private val size: BpkCardButtonSize = flavor.size
  private val style: BpkCardButtonStyle = flavor.style

  @Test
  fun defaultSaveButton() {
    snap(background = { style.background() }) {
      BpkSaveButton(
        checked = false,
        contentDescription = "",
        style = style,
        size = size,
        onCheckedChange = {}
      )
    }
  }

  @Test
  fun checkedSaveButton() {
    snap(background = { style.background() }) {
      BpkSaveButton(
        checked = true,
        contentDescription = "",
        style = style,
        size = size,
        onCheckedChange = {}
      )
    }
  }

  @Test
  fun defaultShareButton() {
    snap(background = { style.background() }) {
      BpkShareButton(
        contentDescription = "",
        style = style,
        size = size,
        onClick = {}
      )
    }
  }

  companion object {
    @JvmStatic
    @Parameterized.Parameters(name = "{0} Screenshot")
    fun flavours(): List<Flavor> = BpkCardButtonSize.values().map { size ->
      BpkCardButtonStyle.values().map { style -> Flavor(size, style) }
    }.flatten()
  }
}

data class Flavor(
  val size: BpkCardButtonSize,
  val style: BpkCardButtonStyle
)

@Composable
private fun BpkCardButtonStyle.background() =
  when (this) {
    BpkCardButtonStyle.Default -> BpkTheme.colors.surfaceDefault
    BpkCardButtonStyle.OnDark -> BpkTheme.colors.surfaceContrast
    BpkCardButtonStyle.Contained -> BpkTheme.colors.surfaceHighlight
  }
