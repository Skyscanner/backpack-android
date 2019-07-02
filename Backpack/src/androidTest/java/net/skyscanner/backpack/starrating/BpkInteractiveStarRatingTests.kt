package net.skyscanner.backpack.starrating

import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*

@RunWith(AndroidJUnit4::class)
class BpkInteractiveStarRatingTests {

  private lateinit var subject: BpkInteractiveStarRating
  private lateinit var context: Context

  @Before
  fun setUp() {
    context = InstrumentationRegistry.getInstrumentation().targetContext
    subject = BpkInteractiveStarRating(context)
  }

  @Test
  fun test_listenerIsNotInvoked_onSet() {
    val listener = mock(BpkInteractiveStarRating.OnRatingChangedListener::class.java)
    subject.onRatingChangedListener = listener
    verifyZeroInteractions(listener)
  }

  @Test
  fun test_listenerIsNotInvoked_whenMaxRatingChanged() {
    val listener = mock(BpkInteractiveStarRating.OnRatingChangedListener::class.java)
    subject.maxRating = 5
    subject.onRatingChangedListener = listener
    subject.maxRating = 6
    verifyZeroInteractions(listener)
  }

  @Test
  fun test_listenerIsInvoked_whenRatingChanged() {
    var lastCurrent = 0f
    var lastMax = 0f
    var invocationsCount = 0
    val listener = { current: Float, max: Float ->
      invocationsCount++
      lastCurrent = current
      lastMax = max
    }
    subject.rating = 2f
    subject.maxRating = 5
    subject.onRatingChangedListener = listener
    subject.rating = 3f
    assertEquals(3f, lastCurrent)
    assertEquals(5f, lastMax)
    assertEquals(1, invocationsCount)
  }

  @Test
  fun test_listenerIsNotInvoked_whenRatingChangedLessThenThreshold() {
    val listener = mock(BpkInteractiveStarRating.OnRatingChangedListener::class.java)
      subject.rating = 2.1f
    subject.maxRating = 5
    subject.onRatingChangedListener = listener
    subject.rating = 2.2f
    verifyZeroInteractions(listener)
  }
}
