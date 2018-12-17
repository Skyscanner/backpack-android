package net.skyscanner.backpack.chip

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import net.skyscanner.backpack.R
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BpkChipTest {
  private lateinit var context: Context

  @Before
  fun beforeAll() {
    context = InstrumentationRegistry.getInstrumentation().targetContext
  }

  @Test
  fun test_message() {
    val chip = BpkChip(context).apply {
      text = "Message"
    }
    Assert.assertEquals("Message", chip.text.toString())
    Assert.assertEquals(ContextCompat.getColor(context, R.color.bpkGray700), chip.currentTextColor)
  }

  @Test
  fun test_selected_state() {
    val chip = BpkChip(context).apply {
      isSelected = true
    }
    Assert.assertEquals(ContextCompat.getColor(context, R.color.bpkWhite), chip.currentTextColor)
  }

  @Test
  fun test_disabled_state() {
    val chip = BpkChip(context).apply {
      disabled = true
    }
    Assert.assertEquals(ContextCompat.getColor(context, R.color.bpkGray300), chip.currentTextColor)
  }

  @Test
  fun test_select_event() {
    val chip = BpkChip(context).apply {
      isSelected = false
    }
    chip.performClick()
    Assert.assertEquals(ContextCompat.getColor(context, R.color.bpkWhite), chip.currentTextColor)
  }

  @Test
  fun test_unselect_event() {
    val chip = BpkChip(context).apply {
      isSelected = true
    }
    chip.performClick()
    Assert.assertEquals(ContextCompat.getColor(context, R.color.bpkGray700), chip.currentTextColor)
  }
}
