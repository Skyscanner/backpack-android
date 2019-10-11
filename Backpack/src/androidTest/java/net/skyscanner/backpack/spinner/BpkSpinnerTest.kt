package net.skyscanner.backpack.spinner

import android.content.Context
import android.widget.ProgressBar
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import net.skyscanner.backpack.R
import net.skyscanner.backpack.util.ResourcesUtil.getColor
import org.junit.Assert
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BpkSpinnerTest {

  private lateinit var subject: BpkSpinner
  private lateinit var context: Context

  @Before
  fun setUp() {
    context = InstrumentationRegistry.getInstrumentation().targetContext
    subject = BpkSpinner(context)
  }

  @Test
  fun test_default() {
    Assert.assertFalse(subject.small)
    Assert.assertEquals(BpkSpinner.Type.PRIMARY, subject.type)
    Assert.assertEquals(getColor(subject, R.color.bpkSkyBlue), subject.getColor())
  }

  @Test
  fun test_light() {
    subject.type = BpkSpinner.Type.LIGHT
    Assert.assertEquals(getColor(subject, R.color.bpkWhite), subject.getColor())
  }

  @Test
  fun test_dark() {
    subject.type = BpkSpinner.Type.DARK
    Assert.assertEquals(getColor(subject, R.color.bpkSkyGrayTint01), subject.getColor())
  }

  @Test
  @Ignore("TODO: Figure out how to mock ANIMATOR_DURATION_SCALE during tests")
  fun test_small() {
    val prevHeight = progressBar().minimumHeight
    subject.small = true
    Assert.assertTrue(progressBar().minimumHeight < prevHeight)
  }

  private fun progressBar() = subject.getChildAt(0) as ProgressBar
}
