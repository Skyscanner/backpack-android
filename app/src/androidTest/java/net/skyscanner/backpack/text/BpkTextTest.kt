package net.skyscanner.backpack.text


import android.content.Context
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.facebook.testing.screenshot.Screenshot
import com.facebook.testing.screenshot.ViewHelpers
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)

class BpkTextTest {
  private lateinit var testContext: Context

  @Before
  fun setup() {
    testContext = InstrumentationRegistry.getTargetContext()
  }

  @Test
  fun screenshotTestTextDefault() {
    val text = BpkText(testContext)
    text.text = "Message"
    ViewHelpers.setupView(text)
      .layout()
    Screenshot.snap(text)
      .record()
  }

  @Test
  fun screenshotTestTextLg() {
    val text = BpkText(testContext)
    text.text = "Message"
    text.textStyle = BpkText.LG
    ViewHelpers.setupView(text)
      .layout()
    Screenshot.snap(text)
      .record()
  }

  @Test
  fun screenshotTestTextBase() {
    val text = BpkText(testContext)
    text.text = "Message"
    text.textStyle = BpkText.BASE
    ViewHelpers.setupView(text)
      .layout()
    Screenshot.snap(text)
      .record()
  }
  @Test
  fun screenshotTestTextSm() {
    val text = BpkText(testContext)
    text.text = "Message"
    text.textStyle = BpkText.SM
    text.emphasize = false
    ViewHelpers.setupView(text)
      .layout()
    Screenshot.snap(text)
      .record()
  }
  @Test
  fun screenshotTestTextXl() {
    val text = BpkText(testContext)
    text.text = "Message"
    text.textStyle = BpkText.XL
    text.emphasize = false
    ViewHelpers.setupView(text)
      .layout()
    Screenshot.snap(text)
      .record()
  }
  @Test
  fun screenshotTestTextXs() {
    val text = BpkText(testContext)
    text.text = "Message"
    text.textStyle = BpkText.XS
    text.emphasize = false
    ViewHelpers.setupView(text)
      .layout()
    Screenshot.snap(text)
      .record()
  }

  @Test
  fun screenshotTestTextXXL() {
    val text = BpkText(testContext)
    text.text = "Message"
    text.textStyle = BpkText.XXL
    text.emphasize = false
    ViewHelpers.setupView(text)
      .layout()
    Screenshot.snap(text)
      .record()
  }
  @Test
  fun screenshotTestTextXXLEmphasize() {
    val text = BpkText(testContext)
    text.text = "Message"
    text.textStyle = BpkText.XXL
    text.emphasize = true
    ViewHelpers.setupView(text)
      .layout()
    Screenshot.snap(text)
      .record()
  }

  @Test
  fun screenshotTestTextLgEmphasize() {
    val text = BpkText(testContext)
    text.text = "Message"
    text.textStyle = BpkText.LG
    text.emphasize = true
    ViewHelpers.setupView(text)
      .layout()
    Screenshot.snap(text)
      .record()
  }

  @Test
  fun screenshotTestTextBaseEmphasize() {
    val text = BpkText(testContext)
    text.text = "Message"
    text.textStyle = BpkText.BASE
    text.emphasize = true
    ViewHelpers.setupView(text)
      .layout()
    Screenshot.snap(text)
      .record()
  }
  @Test
  fun screenshotTestTextSmEmphasize() {
    val text = BpkText(testContext)
    text.text = "Message"
    text.textStyle = BpkText.SM
    text.emphasize = true
    ViewHelpers.setupView(text)
      .layout()
    Screenshot.snap(text)
      .record()
  }
  @Test
  fun screenshotTestTextXlEmphasize() {
    val text = BpkText(testContext)
    text.text = "Message"
    text.textStyle = BpkText.XL
    text.emphasize = true
    ViewHelpers.setupView(text)
      .layout()
    Screenshot.snap(text)
      .record()
  }
  @Test
  fun screenshotTestTextXsEmphasize() {
    val text = BpkText(testContext)
    text.text = "Message"
    text.textStyle = BpkText.XS
    text.emphasize = true
    ViewHelpers.setupView(text)
      .layout()
    Screenshot.snap(text)
      .record()
  }
}
