package net.skyscanner.backpack.util

import android.content.Context
import android.graphics.Color
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
  private val grey50 by lazy { ContextCompat.getColor(context, R.color.bpkGray50) }
  private val grey100 by lazy { ContextCompat.getColor(context, R.color.bpkGray100) }
  private val grey300 by lazy { ContextCompat.getColor(context, R.color.bpkGray300) }
  private val grey500 by lazy { ContextCompat.getColor(context, R.color.bpkGray500) }
  private val grey700 by lazy { ContextCompat.getColor(context, R.color.bpkGray700) }
  private val grey900 by lazy { ContextCompat.getColor(context, R.color.bpkGray900) }

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
      listOf("grey50", grey50, customGrey50, { c: Context -> ThemesUtil.getGrey50Color(c) }),
      listOf("grey100", grey100, customGrey100, { c: Context -> ThemesUtil.getGrey100Color(c) }),
      listOf("grey300", grey300, customGrey300, { c: Context -> ThemesUtil.getGrey300Color(c) }),
      listOf("grey500", grey500, customGrey500, { c: Context -> ThemesUtil.getGrey500Color(c) }),
      listOf("grey700", grey700, customGrey700, { c: Context -> ThemesUtil.getGrey700Color(c) }),
      listOf("grey900", grey900, customGrey900, { c: Context -> ThemesUtil.getGrey900Color(c) })
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
}
