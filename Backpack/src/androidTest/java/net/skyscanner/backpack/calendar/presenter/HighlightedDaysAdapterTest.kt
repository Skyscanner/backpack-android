package net.skyscanner.backpack.calendar.presenter

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.jakewharton.threetenabp.AndroidThreeTen
import net.skyscanner.backpack.R
import net.skyscanner.backpack.calendar.presenter.HighlightedDaysAdapter.HighlightedDay
import net.skyscanner.backpack.calendar.view.HighlightedDaysMonthFooter
import net.skyscanner.backpack.text.BpkText
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.instanceOf
import org.junit.Assert.assertArrayEquals
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertThat
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.threeten.bp.LocalDate
import java.util.*

@RunWith(AndroidJUnit4::class)
class HighlightedDaysAdapterTest {

  private lateinit var subject: HighlightedDaysAdapter
  private lateinit var holidays: Map<String, Set<HighlightedDay>>

  @Before
  fun setup() {
    val context = InstrumentationRegistry.getInstrumentation().targetContext
    AndroidThreeTen.init(context)

    holidays = mapOf(
      "2020-1" to setOf(HighlightedDay(
        LocalDate.of(2020, 1, 1), "New Year's Day")),
      "2020-12" to setOf(
        HighlightedDay(
          LocalDate.of(2020, 12, 25), "Christmas Day"),
        HighlightedDay(
          LocalDate.of(2020, 12, 31), "New Year's Eve"))
    )

    subject = HighlightedDaysAdapter(
      context,
      Locale.UK,
      holidays.values.flatten().toSet()
    )
  }

  @Test
  fun test_hasFooterForMonth() {
    assertTrue(subject.hasFooterForMonth(1, 2020))
    assertTrue(subject.hasFooterForMonth(12, 2020))

    assertFalse(subject.hasFooterForMonth(2, 2020))
    assertFalse(subject.hasFooterForMonth(11, 2020))
  }

  @Test
  fun test_onCreateView() {
    assertThat(
      subject.onCreateView(1, 2020),
      `is`(instanceOf(HighlightedDaysMonthFooter::class.java)))

    assertThat(
      subject.onCreateView(12, 2020),
      `is`(instanceOf(HighlightedDaysMonthFooter::class.java)))
  }

  @Test
  fun test_onBindView() {
    val context = InstrumentationRegistry.getInstrumentation().targetContext
    val view = HighlightedDaysMonthFooter(context) { it.toString() }

    subject.onBindView(view, 1, 2020)
    assertNotNull(view.holidays)
    assertArrayEquals(
      holidays["2020-1"]?.toTypedArray(),
      view.holidays?.toTypedArray())

    subject.onBindView(view, 12, 2020)
    assertNotNull(view.holidays)
    assertArrayEquals(
      holidays["2020-12"]?.toTypedArray(),
      view.holidays?.toTypedArray())
  }

  @Test
  fun test_date_formatter() {
    val view = subject.onCreateView(1, 2020) as HighlightedDaysMonthFooter
    subject.onBindView(view, 1, 2020)

    val dateView = view.getChildAt(0).findViewById<BpkText>(R.id.date)
    assertEquals("01 Jan", dateView.text)
  }

  @Test
  fun test_date_formatter_custom_locale() {
    val context = InstrumentationRegistry.getInstrumentation().targetContext

    val subject = HighlightedDaysAdapter(
      context,
      Locale.forLanguageTag("pt-BR"),
      holidays.values.flatten().toSet()
    )
    val view = subject.onCreateView(12, 2020) as HighlightedDaysMonthFooter
    subject.onBindView(view, 12, 2020)

    val dateView = view.getChildAt(0).findViewById<BpkText>(R.id.date)
    assertEquals("25 dez", dateView.text)
  }
}
