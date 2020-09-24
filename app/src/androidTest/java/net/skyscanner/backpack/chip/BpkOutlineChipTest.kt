package net.skyscanner.backpack.chip

import android.util.LayoutDirection
import android.widget.LinearLayout
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.test.ext.junit.runners.AndroidJUnit4
import net.skyscanner.backpack.BpkSnapshotTest
import net.skyscanner.backpack.createThemedContext
import net.skyscanner.backpack.demo.R
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BpkOutlineChipTest : BpkSnapshotTest() {
  @Before
  fun setup() {
    setDimensions(36, 100)
  }

  private fun getContainer(): LinearLayout = LinearLayout(testContext).apply {
    setBackgroundColor(ContextCompat.getColor(testContext, R.color.bpkSkyBlueShade03))
  }

  @Test
  fun screenshotTestDefault() {
    getContainer().apply {
      addView(BpkOutlineChip(testContext).apply { text = "tag" })
      snap(this)
    }
  }

  @Test
  fun screenshotTestDisabled() {
    val view = BpkOutlineChip(testContext)
    view.text = "tag"
    view.disabled = true
    getContainer().apply {
      addView(view)
      snap(this)
    }
  }

  @Test
  fun screenshotTestSelected() {
    val view = BpkOutlineChip(testContext)
    view.text = "tag"
    view.isSelected = true
    getContainer().apply {
      addView(view)
      snap(this)
    }
  }

  @Test
  fun screenshotTestSelected_withTheme() {
    val view = BpkOutlineChip(createThemedContext(testContext))
    view.text = "tag"
    view.isSelected = true
    getContainer().apply {
      addView(view)
      snap(this)
    }
  }

  @Test
  fun screenshotTestCustomSelectedBackground() {
    val view = BpkOutlineChip(testContext)
    view.text = "tag"
    view.selectedBackgroundColor = ContextCompat.getColor(testContext, R.color.bpkPanjin)
    view.toggle()
    getContainer().apply {
      addView(view)
      snap(this)
    }
  }

  @Test
  @Ignore("TODO: relative drawables do not work with the screenshot test lib")
  fun screenshotTestWithIcon() {
    val view = BpkOutlineChip(testContext)
    view.text = "tag"
    view.icon = AppCompatResources.getDrawable(testContext, R.drawable.bpk_account)
    getContainer().apply {
      addView(view)
      snap(this)
    }
  }

  @Test
  @Ignore("TODO: relative drawables do not work with the screenshot test lib")
  fun screenshotTestSelectedWithIcon() {
    val view = BpkOutlineChip(testContext)
    view.text = "tag"
    view.icon = AppCompatResources.getDrawable(testContext, R.drawable.bpk_account)
    view.isSelected = true
    getContainer().apply {
      addView(view)
      snap(this)
    }
  }

  @Test
  @Ignore("TODO: relative drawables do not work with the screenshot test lib")
  fun screenshotTestDisabledWithIcon() {
    val view = BpkOutlineChip(testContext)
    view.text = "tag"
    view.icon = AppCompatResources.getDrawable(testContext, R.drawable.bpk_account)
    view.disabled = true
    getContainer().apply {
      addView(view)
      snap(this)
    }
  }

  @Test
  @Ignore("TODO: relative drawables do not work with the screenshot test lib")
  fun screenshotTestWithIcon_RTL() {
    val view = BpkOutlineChip(testContext)
    view.text = "tag"
    view.icon = AppCompatResources.getDrawable(testContext, R.drawable.bpk_account)
    view.layoutDirection = LayoutDirection.RTL
    getContainer().apply {
      addView(view)
      snap(this)
    }
  }
}
