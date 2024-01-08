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

package net.skyscanner.backpack.flare

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.LinearLayoutCompat
import net.skyscanner.backpack.BpkSnapshotTest
import net.skyscanner.backpack.BpkTestVariant
import net.skyscanner.backpack.Variants
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.text.BpkText
import org.junit.Test

class BpkFlareTest : BpkSnapshotTest() {

    private val subject = BpkFlare(testContext).apply {
        layoutParams = ViewGroup.LayoutParams(300, 100)
    }

    private val imageView = ImageView(testContext).apply {
        setImageDrawable(AppCompatResources.getDrawable(context, R.drawable.canadian_rockies_canada))
        layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        scaleType = ImageView.ScaleType.CENTER_CROP
    }

    @Test
    fun default() {
        snap(
            subject.apply {
                addView(imageView)
            },
        )
    }

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.Rtl)
    fun up() {
        snap(
            setupViewForInsetPaddingTest(testContext) {
                subject.pointerDirection = BpkFlare.PointerDirection.UP
                subject.insetPaddingMode = BpkFlare.InsetPaddingMode.TOP
            },
            width = 300,
        )
    }

    @Test
    @Variants(BpkTestVariant.Default)
    fun radius() {
        snap(
            subject.apply {
                addView(imageView)
                round = true
            },
        )
    }

    @Test
    @Variants(BpkTestVariant.Default)
    fun contentPadding() {
        snap(
            setupViewForInsetPaddingTest(testContext) {
                subject.insetPaddingMode = BpkFlare.InsetPaddingMode.BOTTOM
            },
            width = 300,
        )
    }

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.Rtl)
    fun pointerPositionStart() {
        snap(
            subject.apply {
                addView(imageView)
                pointerPosition = BpkFlare.PointerPosition.START
            },
        )
    }

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.Rtl)
    fun pointerPositionEnd() {
        snap(
            subject.apply {
                addView(imageView)
                pointerPosition = BpkFlare.PointerPosition.END
            },
        )
    }

    private fun setupViewForInsetPaddingTest(context: Context, configSubject: () -> Unit): View {
        val wrapContent = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT,
        )

        return FrameLayout(testContext).apply {
            addView(
                subject.apply {
                    layoutParams = wrapContent
                    configSubject()

                    addView(
                        LinearLayoutCompat(context).apply {
                            layoutParams = wrapContent
                            background = ColorDrawable(Color.LTGRAY)

                            addView(
                                BpkText(testContext).apply {
                                    layoutParams = wrapContent
                                    text = testContext.resources.getString(R.string.stub_sm)
                                },
                            )
                        },
                    )
                },
            )
        }
    }
}
