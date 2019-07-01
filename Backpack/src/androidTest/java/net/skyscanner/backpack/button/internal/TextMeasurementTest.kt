package net.skyscanner.backpack.button.internal

import android.text.TextPaint
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TextMeasurementTest {

  private val textPaint = mock<TextPaint> {}

  @Before
  fun setUp() {
    textPaint.reset()
  }

  @Test
  fun test_findLongest_empty() {
    val textMeasurement = TextMeasurement(textPaint)
    Assert.assertEquals("", textMeasurement.findLongest(""))
  }

  @Test
  fun test_findLongest_single_line() {
    val textMeasurement = TextMeasurement(textPaint)
    Assert.assertEquals("some text", textMeasurement.findLongest("some text"))
  }

  @Test
  fun test_findLongest_multiple_lines() {
    val textMeasurement = TextMeasurement(textPaint)
    val text = """
      SOME TEXT
      This is the biggest line
      some more
    """.trimIndent()
    Assert.assertEquals("This is the biggest line", textMeasurement.findLongest(text))
  }

  @Test
  fun test_getWidth() {
    val textMeasurement = TextMeasurement(textPaint)
    textMeasurement.getTextWidth("Some text")

    verify(textPaint).getTextBounds(eq("SOME TEXT"), eq(0), eq(9), any())

    textMeasurement.getTextWidth("""
      Some text
      Another line
    """.trimIndent())

    verify(textPaint).getTextBounds(eq("ANOTHER LINE"), eq(0), eq(12), any())
  }
}
