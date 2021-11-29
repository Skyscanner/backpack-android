package net.skyscanner.backpack.compose

import androidx.compose.material.Text
import androidx.test.ext.junit.runners.AndroidJUnit4
import net.skyscanner.backpack.BpkSnapshotTest
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BpkTextTest : BpkSnapshotTest() {

  @Test
  fun default() = composed {
    Text(text = "Sample")
  }
}
