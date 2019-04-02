package net.skyscanner.backpack.calendar.view

import android.content.Context
import android.view.ViewGroup
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.jakewharton.threetenabp.AndroidThreeTen
import net.skyscanner.backpack.R
import net.skyscanner.backpack.text.BpkText
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.Locale

@RunWith(AndroidJUnit4::class)
class WeekdayViewHeaderTest {

  private lateinit var context: Context

  @Before
  fun setUp() {
    context = InstrumentationRegistry.getInstrumentation().targetContext
    AndroidThreeTen.init(context)
  }

  @Test
  fun test_initializeWithLocale() {
    listOf(
      "pt-br" to arrayOf("dom", "seg", "ter", "qua", "qui", "sex", "sáb"),
      "en" to arrayOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat")).forEach {
      val locale = it.first
      val expected = it.second

      val view = WeekdayHeaderView(context).apply {
        initializeWithLocale(Locale.forLanguageTag(locale))
      }

      val allDays = getAllLabels(view)

      Assert.assertArrayEquals(
        allDays,
        expected)
    }
  }

  private fun getAllLabels(view: ViewGroup): Array<CharSequence> {
    return arrayOf(
      R.id.first_weekday_label,
      R.id.second_weekday_label,
      R.id.third_weekday_label,
      R.id.fourth_weekday_label,
      R.id.fifth_weekday_label,
      R.id.sixth_weekday_label,
      R.id.seventh_weekday_label).map {
      view.findViewById<BpkText>(it).text
    }.toTypedArray()
  }
}
