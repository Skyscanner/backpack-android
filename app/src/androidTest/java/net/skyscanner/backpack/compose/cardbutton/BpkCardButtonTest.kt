package net.skyscanner.backpack.compose.cardbutton

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntSize
import net.skyscanner.backpack.BpkSnapshotTest
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class BpkCardButtonTest(flavor: Flavor) : BpkSnapshotTest() {

  private val size: BpkCardButtonSize = flavor.size
  private val checked = flavor.checked

  @Test
  fun defaultSaveButton() {
    capture(
      background = { BpkTheme.colors.textPrimaryInverse }
    ) {
      BpkSaveButton(
        checked = checked,
        contentDescription = "",
        style = BpkCardButtonStyle.Default,
        size = size,
        onCheckedChange = {}
      )
    }
  }

  @Test
  fun onDarkSaveButton() {
    capture(
      background = { BpkTheme.colors.surfaceContrast }
    ) {
      BpkSaveButton(
        checked = checked,
        contentDescription = "",
        style = BpkCardButtonStyle.OnDark,
        size = size,
        onCheckedChange = {}
      )
    }
  }

  @Test
  fun containedSaveButton() {
    capture(
      background = { BpkTheme.colors.surfaceContrast }
    ) {
      BpkSaveButton(
        checked = checked,
        contentDescription = "",
        style = BpkCardButtonStyle.Contained,
        size = size,
        onCheckedChange = {}
      )
    }
  }

  @Test
  fun defaultShareButton() {
    capture(
      background = { BpkTheme.colors.textPrimaryInverse }
    ) {
      BpkShareButton(
        contentDescription = "",
        style = BpkCardButtonStyle.Default,
        size = size,
        onClick = {}
      )
    }
  }

  @Test
  fun onDarkShareButton() {
    capture(
      background = { BpkTheme.colors.textPrimaryInverse }
    ) {
      BpkShareButton(
        contentDescription = "",
        style = BpkCardButtonStyle.OnDark,
        size = size,
        onClick = {}
      )
    }
  }

  @Test
  fun containedShareButton() {
    capture(
      background = { BpkTheme.colors.textPrimaryInverse }
    ) {
      BpkShareButton(
        contentDescription = "",
        style = BpkCardButtonStyle.Contained,
        size = size,
        onClick = {}
      )
    }
  }

  private fun capture(
    background: @Composable () -> Color = { Color.Unspecified },
    content: @Composable () -> Unit,
  ) {
    composed(
      size = IntSize(100, 100),
      tags = listOf(size, checked),
    ) {
      Box(
        Modifier
          .fillMaxSize()
          .background(background())
          .padding(BpkSpacing.Md),
        contentAlignment = Alignment.TopStart,
      ) {
        content()
      }
    }
  }

  companion object {
    @JvmStatic
    @Parameterized.Parameters(name = "{0} Screenshot")
    fun flavours(): List<Flavor> = BpkCardButtonSize.values().map { size ->
      listOf(Flavor(size, true), Flavor(size, false))
    }.flatten()
  }
}

data class Flavor(
  val size: BpkCardButtonSize,
  val checked: Boolean
)
