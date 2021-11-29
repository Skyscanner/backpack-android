package net.skyscanner.backpack.compose

import androidx.compose.material.Text
import androidx.test.ext.junit.runners.AndroidJUnit4
import net.skyscanner.backpack.BpkComposeTest
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BpkTextTest : BpkComposeTest() {

  @Test
  fun default() = composed {
    Text(text = "Sample")
  }
}
