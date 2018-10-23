package net.skyscanner.backpack.button

import android.content.Context
import androidx.appcompat.content.res.AppCompatResources
import androidx.test.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import net.skyscanner.backpack.R
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BpkButtonTest {

  private lateinit var context: Context

  @Before
  fun beforeAll() {
    context = InstrumentationRegistry.getInstrumentation().targetContext
  }

  @Test
  fun test_message() {
    val button = BpkButton(context).apply {
      text = "Message"
    }
    Assert.assertEquals("Message", button.text.toString())
  }

  // The drawables are set as start,top,end,bottom and are accessible in the compoundDrawables array
  @Test
  fun test_icon_end() {
    val trainIcon = AppCompatResources.getDrawable(context, R.drawable.bpk_train)
    val button = BpkButton(context).apply {
      icon = trainIcon
      iconPosition = BpkButton.END
    }
    Assert.assertEquals(button.compoundDrawables[2], trainIcon)
  }

  @Test
  fun test_icon_start() {
    val trainIcon = AppCompatResources.getDrawable(context, R.drawable.bpk_train)
    val button = BpkButton(context).apply {
      icon = trainIcon
      iconPosition = BpkButton.START
    }
    Assert.assertEquals(button.compoundDrawables[0], trainIcon)
  }

  @Test
  fun test_enabled_state() {
    val button = BpkButton(context).apply {
      isEnabled = true
      type = BpkButton.Type.Primary
    }
    val newState = false
    val expectedBackgroundState = button.disabledBackground
    button.isEnabled = newState

    Assert.assertEquals(button.isEnabled, newState)
    Assert.assertEquals(button.background, expectedBackgroundState)
  }
}
