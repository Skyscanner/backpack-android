package net.skyscanner.backpack.compose.button

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntSize
import net.skyscanner.backpack.BpkSnapshotTest
import net.skyscanner.backpack.BpkTestVariant
import net.skyscanner.backpack.compose.icons.BpkIcons
import net.skyscanner.backpack.compose.icons.lg.LongArrowRight
import net.skyscanner.backpack.compose.icons.sm.LongArrowRight
import org.hamcrest.Matchers.isOneOf
import org.junit.Assume.assumeThat
import org.junit.Assume.assumeTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class BpkButtonTest(flavour: Flavor) : BpkSnapshotTest() {

  private val type: BpkButtonType = flavour.first
  private val size: BpkButtonSize = flavour.second

  private val icon
    @Composable get() = when (size) {
      BpkButtonSize.Default -> BpkIcons.Sm.LongArrowRight
      BpkButtonSize.Large -> BpkIcons.Lg.LongArrowRight
    }

  @Test
  fun text() = capture {
    assumeVariant(BpkTestVariant.Default, BpkTestVariant.DarkMode) // no need to test text on Rtl
    // we want to see colors of all types
    // different sizes have different text style

    BpkButton("Button", type = type, size = size, onClick = {})
  }

  @Test
  fun disabled() = capture {
    assumeVariant(BpkTestVariant.Default, BpkTestVariant.DarkMode) // we're testing just colors here – no rtl is needed
    assumeTrue(size == BpkButtonSize.Default) // colors will be the same on large size
    assumeThat(type, isOneOf(BpkButtonType.Primary, BpkButtonType.Link)) // colors are different only for link

    BpkButton("Button", type = type, size = size, enabled = false, onClick = {})
  }

  @Test
  fun loading() = capture {
    assumeVariant(BpkTestVariant.Default, BpkTestVariant.DarkMode) // we're testing just colors here – no rtl is needed
    assumeThat(type, isOneOf(BpkButtonType.Primary, BpkButtonType.Link)) // colors are different only for link
    // we need to run it on large size as well and the progress size will be different

    BpkButton("Button", type = type, size = size, loading = true, onClick = {})
  }

  @Test
  fun iconAtStart() = capture {
    assumeVariant(BpkTestVariant.Default, BpkTestVariant.Rtl) // this just tests layout, so RTL is required
    assumeTrue(type == BpkButtonType.Primary) // the layout the same across different button types
    // icon is bigger on large size, so we need to test this

    BpkButton("Button", icon, BpkButtonIconPosition.Start, type = type, size = size, onClick = {})
  }

  @Test
  fun iconAtEnd() = capture {
    assumeVariant(BpkTestVariant.Default, BpkTestVariant.Rtl) // this just tests layout, so RTL is required
    assumeTrue(type == BpkButtonType.Primary) // the layout the same across different button types
    // icon is bigger on large size, so we need to test this

    BpkButton("Button", icon, BpkButtonIconPosition.Start, type = type, size = size, onClick = {})
  }

  @Test
  fun iconOnly() = capture {
    assumeVariant(BpkTestVariant.Default) // since its only icon, RTL doesn't matter
    assumeTrue(type == BpkButtonType.Primary) // the layout the same across different button types
    // icon is bigger on large size, so we need to test this

    BpkButton(icon, "contentDescription", type = type, size = size, onClick = {})
  }

  private fun capture(content: @Composable () -> Unit) {
    composed(
      size = IntSize(160, 64),
      tags = listOf(type, size),
    ) {
      Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        content()
      }
    }
  }

  companion object {

    @JvmStatic
    @Parameterized.Parameters(name = "{0} Screenshot")
    fun flavours(): List<Flavor> = BpkButtonType.values().flatMap { type ->
      BpkButtonSize.values().map { size -> Flavor(type, size) }
    }
  }
}

private typealias Flavor = Pair<BpkButtonType, BpkButtonSize>
