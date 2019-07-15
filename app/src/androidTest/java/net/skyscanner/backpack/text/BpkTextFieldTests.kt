package net.skyscanner.backpack.text

import android.view.View
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

class BpkTextFieldTests : BpkSnapshotTest() {

  private val subject = BpkTextField(testContext).also(::init)

  private fun init(it: BpkTextField) {
    it.hint = "Hint"
    it.setText("Text")
  }

  @Before
  fun setup() {
    setDimensions(60, 200)
  }

  @Test
  fun screenshotTestTextField_Default() {
    snap(subject)
  }

  @Test
  fun screenshotTestTextField_Hint() {
    subject.setText("")
    snap(subject)
  }

  @Test
  @Ignore
  fun screenshotTestTextField_IconStart() {
    subject.iconStart = ContextCompat.getDrawable(testContext, R.drawable.bpk_search)
    snap(subject)
  }

  @Test
  @Ignore
  fun screenshotTestTextField_IconEnd() {
    subject.iconEnd = ContextCompat.getDrawable(testContext, R.drawable.bpk_search)
    snap(subject)
  }

  @Test
  @Ignore
  fun screenshotTestTextField_IconStart_withTint() {
    subject.setText("")
    subject.iconStart = ContextCompat.getDrawable(testContext, R.drawable.bpk_search)
    snap(subject)
  }

  @Test
  @Ignore
  fun screenshotTestTextField_IconEnd_withTint() {
    subject.setText("")
    subject.iconEnd = ContextCompat.getDrawable(testContext, R.drawable.bpk_search)
    snap(subject)
  }

  @Test
  fun screenshotTestTextField_withTheme() {
    val subject = BpkTextField(createThemedContext(testContext)).also(::init)
    snap(subject)
  }

  @Test
  fun screenshotTestTextField_Default_RTL() {
    subject.layoutDirection = View.LAYOUT_DIRECTION_RTL
    snap(subject)
  }

  @Test
  fun screenshotTestTextField_Hint_RTL() {
    subject.layoutDirection = View.LAYOUT_DIRECTION_RTL
    subject.setText("")
    snap(subject)
  }

  @Test
  fun screenshotTestTextField_IconStart_RTL() {
    subject.layoutDirection = View.LAYOUT_DIRECTION_RTL
    subject.iconStart = ContextCompat.getDrawable(testContext, R.drawable.bpk_search)
    snap(subject)
  }

  @Test
  fun screenshotTestTextField_IconEnd_RTL() {
    subject.layoutDirection = View.LAYOUT_DIRECTION_RTL
    subject.iconEnd = ContextCompat.getDrawable(testContext, R.drawable.bpk_search)
    snap(subject)
  }

  @Test
  fun screenshotTestTextField_IconStart_withTint_RTL() {
    subject.layoutDirection = View.LAYOUT_DIRECTION_RTL
    subject.setText("")
    subject.iconStart = ContextCompat.getDrawable(testContext, R.drawable.bpk_search)
    snap(subject)
  }

  @Test
  fun screenshotTestTextField_IconEnd_withTint_RTL() {
    subject.layoutDirection = View.LAYOUT_DIRECTION_RTL
    subject.setText("")
    subject.iconEnd = ContextCompat.getDrawable(testContext, R.drawable.bpk_search)
    snap(subject)
  }

  @Test
  fun screenshotTestTextField_withTheme_RTL() {
    val subject = BpkTextField(createThemedContext(testContext)).also(::init)
    subject.layoutDirection = View.LAYOUT_DIRECTION_RTL
    snap(subject)
  }
}
