package net.skyscanner.backpack.toggle

import android.content.Context
import android.content.res.ColorStateList
import androidx.test.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import net.skyscanner.backpack.R
import net.skyscanner.backpack.util.ResourcesUtil.getColor
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BpkSwitchTest {

  private lateinit var subject: BpkSwitch
  private lateinit var context: Context

  @Before
  fun setUp() {
    context = InstrumentationRegistry.getInstrumentation().targetContext
    subject = BpkSwitch(context)
  }

  @Test
  fun test_default() {
    Assert.assertEquals(ColorStateList.valueOf(getColor(subject, R.color.bpkGray100)), subject.trackTintList)
    Assert.assertEquals(
      getColor(subject, R.color.bpkBlue500),
      subject.thumbTintList?.getColorForState(intArrayOf(android.R.attr.state_checked), 0))
    Assert.assertEquals(
      getColor(subject, R.color.bpkGray50),
      subject.thumbTintList?.getColorForState(intArrayOf(-android.R.attr.state_checked), 0))
  }
}
