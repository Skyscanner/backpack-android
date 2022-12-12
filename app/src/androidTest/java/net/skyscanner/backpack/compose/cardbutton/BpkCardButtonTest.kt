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
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class BpkCardButtonTest(flavor: Flavor) : BpkSnapshotTest() {

  private val type: BpkCardButtonType = flavor.type
  private val size: BpkCardButtonSize = flavor.size
  private val style: BpkCardButtonStyle = flavor.style

  @Test
  fun all() {
    capture {
      BpkCardButton(type = type, contentDescription = "", style = style, size = size)
    }
  }

  private fun capture(
    background: @Composable () -> Color = { Color.Unspecified },
    content: @Composable () -> Unit,
  ) {
    composed(
      size = IntSize(160, 64),
      tags = listOf(type, size),
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
    fun flavours(): List<Flavor> = BpkCardButtonSize.values().flatMap { size ->
      BpkCardButtonStyle.values().map { style ->
        Flavor(BpkCardButtonType.Save(checked = false, onCheckedChange = {}), size, style)
      }
    } + BpkCardButtonSize.values().flatMap { size ->
      BpkCardButtonStyle.values().map { style ->
        Flavor(BpkCardButtonType.Save(checked = true, onCheckedChange = {}), size, style)
      }
    } + BpkCardButtonSize.values().flatMap { size ->
      BpkCardButtonStyle.values().map { style ->
        Flavor(BpkCardButtonType.Share(onClick = {}), size, style)
      }
    }
  }
}

data class Flavor(
  val type: BpkCardButtonType,
  val size: BpkCardButtonSize,
  val style: BpkCardButtonStyle,
)
