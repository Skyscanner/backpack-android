package net.skyscanner.backpack.text.internal

import android.content.Context
import android.graphics.Typeface
import android.view.ContextThemeWrapper

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import net.skyscanner.backpack.test.R
import net.skyscanner.backpack.text.BpkText
import net.skyscanner.backpack.text.FontCache
import net.skyscanner.backpack.util.BpkTheme
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FontFamilyResolverTest {
  private lateinit var context: Context

  @Before
  fun setUp() {
    context = InstrumentationRegistry.getInstrumentation().targetContext
  }

  @Test
  fun test_default_font() {
    val subject = FontFamilyResolver(BpkTheme.wrapContextWithDefaults(context))

    val serif = Typeface.create("sans-serif", Typeface.NORMAL)
    val serifMedium = Typeface.create("sans-serif-medium", Typeface.NORMAL)
    val serifBlack = Typeface.create("sans-serif-black", Typeface.NORMAL)

    Assert.assertEquals(serif, subject.getForWeight(BpkText.Weight.NORMAL))
    Assert.assertEquals(serifMedium, subject.getForWeight(BpkText.Weight.EMPHASIZED))
    Assert.assertEquals(serifBlack, subject.getForWeight(BpkText.Weight.HEAVY))
  }

  @Test
  fun test_custom_font() {
    val subject = FontFamilyResolver(ContextThemeWrapper(context, R.style.TestTextCustomFont))

    val shadows = FontCache[R.font.shadows_into_light, context]
    val cursive = Typeface.create("cursive", Typeface.NORMAL)
    val casual = Typeface.create("casual", Typeface.NORMAL)

    Assert.assertEquals(shadows, subject.getForWeight(BpkText.Weight.NORMAL))
    Assert.assertEquals(cursive, subject.getForWeight(BpkText.Weight.EMPHASIZED))
    Assert.assertEquals(casual, subject.getForWeight(BpkText.Weight.HEAVY))
  }

  @Test
  fun test_with_font_not_defined() {
    val subject = FontFamilyResolver(context)

    Assert.assertNotNull(subject.getForWeight(BpkText.Weight.NORMAL))
    Assert.assertNotNull(subject.getForWeight(BpkText.Weight.EMPHASIZED))
    Assert.assertNotNull(subject.getForWeight(BpkText.Weight.HEAVY))
  }
}
