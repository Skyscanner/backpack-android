/**
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2018 Skyscanner Ltd
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.skyscanner.backpack.starrating

import android.content.Context
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.verifyNoInteractions

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
    verifyNoInteractions(listener)
  }

  @Test
  fun test_listenerIsNotInvoked_whenMaxRatingChanged() {
    val listener = mock(BpkInteractiveStarRating.OnRatingChangedListener::class.java)
    subject.maxRating = 5
    subject.onRatingChangedListener = listener
    subject.maxRating = 6
    verifyNoInteractions(listener)
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
    verifyNoInteractions(listener)
  }
}
