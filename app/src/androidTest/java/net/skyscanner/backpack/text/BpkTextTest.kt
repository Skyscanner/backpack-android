package net.skyscanner.backpack.text

import android.support.test.runner.AndroidJUnit4
import net.skyscanner.backpack.BpkSnapshotTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)

class BpkTextTest : BpkSnapshotTest() {

  @Before
  fun setup() {
    setDimensions(72, 180)
  }

  @Test
  fun screenshotTestTextDefault() {
    val text = BpkText(testContext)
    text.text = "Message"
    snap(text)
  }

  @Test
  fun screenshotTestTextLg() {
    val text = BpkText(testContext)
    text.text = "Message"
    text.textStyle = BpkText.LG
    snap(text)
  }

  @Test
  fun screenshotTestTextBase() {
    val text = BpkText(testContext)
    text.text = "Message"
    text.textStyle = BpkText.BASE
    snap(text)
  }

  @Test
  fun screenshotTestTextSm() {
    val text = BpkText(testContext)
    text.text = "Message"
    text.textStyle = BpkText.SM
    text.emphasize = false
    snap(text)
  }

  @Test
  fun screenshotTestTextXl() {
    val text = BpkText(testContext)
    text.text = "Message"
    text.textStyle = BpkText.XL
    text.emphasize = false
    snap(text)
  }

  @Test
  fun screenshotTestTextXs() {
    val text = BpkText(testContext)
    text.text = "Message"
    text.textStyle = BpkText.XS
    text.emphasize = false
    snap(text)
  }

  @Test
  fun screenshotTestTextXXL() {
    val text = BpkText(testContext)
    text.text = "Message"
    text.textStyle = BpkText.XXL
    text.emphasize = false
    snap(text)
  }

  @Test
  fun screenshotTestTextXXLEmphasize() {
    val text = BpkText(testContext)
    text.text = "Message"
    text.textStyle = BpkText.XXL
    text.emphasize = true
    snap(text)
  }

  @Test
  fun screenshotTestTextLgEmphasize() {
    val text = BpkText(testContext)
    text.text = "Message"
    text.textStyle = BpkText.LG
    text.emphasize = true
    snap(text)
  }

  @Test
  fun screenshotTestTextBaseEmphasize() {
    val text = BpkText(testContext)
    text.text = "Message"
    text.textStyle = BpkText.BASE
    text.emphasize = true
    snap(text)
  }

  @Test
  fun screenshotTestTextSmEmphasize() {
    val text = BpkText(testContext)
    text.text = "Message"
    text.textStyle = BpkText.SM
    text.emphasize = true
    snap(text)
  }

  @Test
  fun screenshotTestTextXlEmphasize() {
    val text = BpkText(testContext)
    text.text = "Message"
    text.textStyle = BpkText.XL
    text.emphasize = true
    snap(text)
  }

  @Test
  fun screenshotTestTextXsEmphasize() {
    val text = BpkText(testContext)
    text.text = "Message"
    text.textStyle = BpkText.XS
    text.emphasize = true
    snap(text)
  }
}
