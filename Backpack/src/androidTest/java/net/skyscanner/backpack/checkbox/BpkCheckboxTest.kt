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

package net.skyscanner.backpack.checkbox

import androidx.test.ext.junit.runners.AndroidJUnit4
import net.skyscanner.backpack.R
import net.skyscanner.backpack.util.TestContext
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BpkCheckboxTest {

    private val emptyText: String = ""
    private val testString: String = "Test"
    private val context = TestContext
    private val expectedPadding = context.resources.getDimensionPixelSize(R.dimen.bpkSpacingSm)

    @Test
    fun text_paddingApplied_whenInitialisedWithText() {
        val subject = BpkCheckbox(context).apply {
            text = testString
        }

        Assert.assertEquals(testString, subject.text)
        Assert.assertEquals(expectedPadding, subject.paddingStart)
    }

    @Test
    fun text_noPaddingApplied_whenInitialisedWithoutText() {
        val subject = BpkCheckbox(context)

        Assert.assertEquals(emptyText, subject.text)
        Assert.assertEquals(0, subject.paddingStart)
    }

    @Test
    fun text_paddingApplied_whenTextAppliedAfterInitialisation() {
        val subject = BpkCheckbox(context)

        subject.text = testString

        Assert.assertEquals(testString, subject.text)
        Assert.assertEquals(expectedPadding, subject.paddingStart)
    }
}
