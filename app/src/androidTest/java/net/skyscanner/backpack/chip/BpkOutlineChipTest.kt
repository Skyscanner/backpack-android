package net.skyscanner.backpack.chip

import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.test.ext.junit.runners.AndroidJUnit4
import net.skyscanner.backpack.BpkSnapshotTest
import net.skyscanner.backpack.demo.R
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BpkOutlineChipTest : BpkSnapshotTest() {
  @Before
  fun setup() {
    setDimensions(36, 100)
  }

  @Test
  fun screenshotTestDefault() {
    val view = LinearLayout(testContext).apply {
      setBackgroundColor(ContextCompat.getColor(testContext, R.color.bpkSkyBlueShade03))
      addView(BpkOutlineChip(testContext).apply { text = "tag" })
    }
    snap(view)
  }
}
