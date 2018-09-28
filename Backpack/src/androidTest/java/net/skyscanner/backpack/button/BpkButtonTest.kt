package net.skyscanner.backpack.button

import android.content.Context
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import android.support.v7.content.res.AppCompatResources
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
  fun givenBpkButtonInAnyTypeAndFormWhenIsEnabledIsSetThenSetupIsCalled() {
    // Given
    val subjectUnderTest = BpkButton(context).apply {
      isEnabled = true
      type = BpkButton.Type.Primary
    }
    val newState = false
    val disabledBackgroundColor = R.color.bpkGray100

    // When
    subjectUnderTest.isEnabled = newState

    // Then
    Assert.assertEquals(subjectUnderTest.isEnabled, newState)
    Assert.assertEquals(subjectUnderTest.drawingCacheBackgroundColor, disabledBackgroundColor)
  }
}
