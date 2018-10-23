package net.skyscanner.backpack.spinner

import android.content.Context
import android.widget.ProgressBar
import androidx.core.content.res.ResourcesCompat
import androidx.test.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import net.skyscanner.backpack.R
import org.junit.Assert
import org.junit.Before
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
    Assert.assertEquals(getColor(R.color.bpkBlue500), subject.getColor())
  }

  @Test
  fun test_light() {
    subject.type = BpkSpinner.Type.LIGHT
    Assert.assertEquals(getColor(R.color.bpkWhite), subject.getColor())
  }

  @Test
  fun test_dark() {
    subject.type = BpkSpinner.Type.DARK
    Assert.assertEquals(getColor(R.color.bpkGray700), subject.getColor())
  }

  @Test
  fun test_small() {
    val prevHeight = progressBar().minimumHeight
    subject.small = true
    Assert.assertTrue(progressBar().minimumHeight < prevHeight)
  }

  private fun progressBar() = subject.getChildAt(0) as ProgressBar

  private fun getColor(color: Int): Int {
    return ResourcesCompat.getColor(context.resources, color, context.theme)
  }
}
