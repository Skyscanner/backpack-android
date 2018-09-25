package net.skyscanner.backpack.button

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import android.support.v7.content.res.AppCompatResources
import net.skyscanner.backpack.R
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BpkButtonTest {

  @Test
  fun test_message() {
    val context = InstrumentationRegistry.getInstrumentation().targetContext
    val button = BpkButton(context).apply {
      text = "Message"

    }
    Assert.assertEquals("Message", button.text.toString())
  }

  // The drawables are set as start,top,end,bottom and are accessible in the compoundDrawables array

  @Test
  fun test_icon_end() {
    val context = InstrumentationRegistry.getInstrumentation().targetContext
    val trainIcon = AppCompatResources.getDrawable(context, R.drawable.bpk_train)
    val button = BpkButton(context).apply {
      iconEnd = trainIcon
    }
    Assert.assertEquals(button.compoundDrawables[2],trainIcon)
  }

  @Test
  fun test_icon_start() {
    val context = InstrumentationRegistry.getInstrumentation().targetContext
    val trainIcon = AppCompatResources.getDrawable(context, R.drawable.bpk_train)
    val button = BpkButton(context).apply {
      iconStart = trainIcon
    }
    Assert.assertEquals(button.compoundDrawables[0],trainIcon)
  }
}
