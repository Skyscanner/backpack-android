package net.skyscanner.backpack.util

import android.content.Context
import android.content.ContextWrapper
import android.graphics.Color
import android.util.TypedValue
import android.view.ContextThemeWrapper
import androidx.core.content.ContextCompat
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import net.skyscanner.backpack.test.R
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ThemesUtilTest {
  private lateinit var context: Context

  private val blue500 by lazy { ContextCompat.getColor(context, R.color.bpkBlue500) }
  private val gray50 by lazy { ContextCompat.getColor(context, R.color.bpkGray50) }
  private val gray100 by lazy { ContextCompat.getColor(context, R.color.bpkGray100) }
  private val gray300 by lazy { ContextCompat.getColor(context, R.color.bpkGray300) }
  private val gray500 by lazy { ContextCompat.getColor(context, R.color.bpkGray500) }
  private val gray700 by lazy { ContextCompat.getColor(context, R.color.bpkGray700) }
  private val gray900 by lazy { ContextCompat.getColor(context, R.color.bpkGray900) }

  private val customBlue = Color.parseColor("#9B104A")
  private val customGrey50 = Color.parseColor("#9B104B")
  private val customGrey100 = Color.parseColor("#9B104C")
  private val customGrey300 = Color.parseColor("#9B104D")
  private val customGrey500 = Color.parseColor("#9B104E")
  private val customGrey700 = Color.parseColor("#9B104F")
  private val customGrey900 = Color.parseColor("#9B105A")

  @Before
  fun setUp() {
    context = InstrumentationRegistry.getInstrumentation().targetContext
  }

  @Test
  fun test_colors() {
    val allColors = listOf(
      listOf("primary", blue500, customBlue, { c: Context -> ThemesUtil.getPrimaryColor(c) }),
      listOf("gray50", gray50, customGrey50, { c: Context -> ThemesUtil.getGray50Color(c) }),
      listOf("gray100", gray100, customGrey100, { c: Context -> ThemesUtil.getGray100Color(c) }),
      listOf("gray300", gray300, customGrey300, { c: Context -> ThemesUtil.getGray300Color(c) }),
      listOf("gray500", gray500, customGrey500, { c: Context -> ThemesUtil.getGray500Color(c) }),
      listOf("gray700", gray700, customGrey700, { c: Context -> ThemesUtil.getGray700Color(c) }),
      listOf("gray900", gray900, customGrey900, { c: Context -> ThemesUtil.getGray900Color(c) })
    )

    allColors.forEach {
      val desc = it[0] as String
      val color = it[1] as Int
      val customColor = it[2] as Int
      val test = it[3] as (c: Context) -> Int

      Assert.assertEquals("$desc default", color, test(context))

      Assert.assertEquals(
        "$desc custom",
        customColor,
        test(ContextThemeWrapper(context, R.style.TestThemeUtilsCustomColors)))
    }
  }

  @Test
  fun test_wrapContextWithBackpackDefaults() {
    val t = TypedValue()

    Assert.assertFalse(context.theme.resolveAttribute(R.attr.bpkPrimaryColor, t, true))

    val newContext = ThemesUtil.wrapContextWithBackpackDefaults(context)
    newContext.theme.resolveAttribute(R.attr.bpkPrimaryColor, t, true)
    Assert.assertEquals(blue500, t.data)

    val withTheme = ContextThemeWrapper(context, R.style.TestThemeUtilsWrapWithDefaults)
    val withThemeAndDefault = ThemesUtil.wrapContextWithBackpackDefaults(withTheme)
    withThemeAndDefault.theme.resolveAttribute(R.attr.bpkPrimaryColor, t, true)

    Assert.assertEquals(customBlue, t.data)

    withThemeAndDefault.theme.resolveAttribute(R.attr.bpkGray50Color, t, true)
    Assert.assertEquals(gray50, t.data)
  }

  @Test
  fun test_applyBackpackDefaultsToContext() {
    var testContext = ContextWrapper(context)
    val t = TypedValue()

    Assert.assertFalse(context.theme.resolveAttribute(R.attr.bpkPrimaryColor, t, true))

    ThemesUtil.applyBackpackDefaultsToContext(testContext)
    testContext.theme.resolveAttribute(R.attr.bpkPrimaryColor, t, true)
    Assert.assertEquals(blue500, t.data)

    val withTheme = ContextThemeWrapper(context, R.style.TestThemeUtilsWrapWithDefaults)
    ThemesUtil.applyBackpackDefaultsToContext(withTheme)
    withTheme.theme.resolveAttribute(R.attr.bpkPrimaryColor, t, true)

    Assert.assertEquals(customBlue, t.data)

    withTheme.theme.resolveAttribute(R.attr.bpkGray50Color, t, true)
    Assert.assertEquals(gray50, t.data)
  }
}
