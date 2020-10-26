/**
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2018-2020 Skyscanner Ltd
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
import androidx.test.ext.junit.runners.AndroidJUnit4
import net.skyscanner.backpack.BpkSnapshotTest
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.text.BpkText
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)

class BpkFlareTest : BpkSnapshotTest() {

  private val subject = BpkFlare(testContext).apply {
    layoutParams = ViewGroup.LayoutParams(300, 100)
  }

  private val imageView = ImageView(testContext).apply {
    setImageDrawable(AppCompatResources.getDrawable(context, R.drawable.canadian_rockies_canada))
    layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    scaleType = ImageView.ScaleType.CENTER_CROP
  }

  @Before
  fun setup() {
    setDimensions(100, 300)
  }

  @Test
  fun screenshotTestFlareDefault() {
    snap(
      subject.apply {
        addView(imageView)
      }
    )
  }

  @Test
  fun screenshotTestFlareRounded() {
    snap(
      subject.apply {
        addView(imageView)
        round = true
      }
    )
  }

  @Test
  fun screenshotTestFlarePointerPositionStart() {
    snap(
      subject.apply {
        addView(imageView)
        pointerPosition = BpkFlare.PointerPosition.START
      }
    )
  }

  @Test
  fun screenshotTestFlarePointerPositionEnd() {
    snap(
      subject.apply {
        addView(imageView)
        pointerPosition = BpkFlare.PointerPosition.END
      }
    )
  }

  @Test
  fun screenshotTestFlarePointerPositionStart_RTL() {
    snap(
      subject.apply {
        pointerPosition = BpkFlare.PointerPosition.START
        layoutDirection = View.LAYOUT_DIRECTION_RTL
        addView(imageView)
      }
    )
  }

  @Test
  fun screenshotTestFlarePointerPositionEnd_RTL() {
    snap(
      subject.apply {
        pointerPosition = BpkFlare.PointerPosition.END
        layoutDirection = View.LAYOUT_DIRECTION_RTL
        addView(imageView)
      }
    )
  }

  @Test
  fun screenshotTestFlareInsetPaddingMode() {
    snap(
      setupViewForInsetPaddingTest(testContext) {
        subject.insetPaddingMode = BpkFlare.InsetPaddingMode.BOTTOM
      }
    )
  }

  @Test
  fun screenshotTestFlareInsetPaddingMode_withRelativePaddingRTL() {
    val wrapContent = ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)

    snap(
      FrameLayout(testContext).apply {
        layoutDirection = View.LAYOUT_DIRECTION_RTL

        addView(
          subject.apply {
            layoutParams = wrapContent
            insetPaddingMode = BpkFlare.InsetPaddingMode.BOTTOM

            addView(
              LinearLayoutCompat(context).apply {
                layoutParams = wrapContent
                background = ColorDrawable(Color.LTGRAY)
                setPaddingRelative(0, 10, 40, 0)

                addView(
                  BpkText(testContext).apply {
                    layoutParams = wrapContent
                    text = testContext.resources.getString(R.string.stub_sm)
                  }
                )
              }
            )
          }
        )
      }
    )
  }

  @Test
  fun screenshotTestFlare_withPointerDirectionUP() {
    snap(
      subject.apply {
        addView(imageView)
        pointerDirection = BpkFlare.PointerDirection.UP
      }
    )
  }

  @Test
  fun screenshotTestFlare_withPaddingModeTop_andPointerDirectionUP() {
    snap(
      setupViewForInsetPaddingTest(testContext) {
        subject.pointerDirection = BpkFlare.PointerDirection.UP
        subject.insetPaddingMode = BpkFlare.InsetPaddingMode.TOP
      }
    )
  }

  private fun setupViewForInsetPaddingTest(context: Context, configSubject: () -> Unit): View {
    val wrapContent = ViewGroup.LayoutParams(
      ViewGroup.LayoutParams.WRAP_CONTENT,
      ViewGroup.LayoutParams.WRAP_CONTENT
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
                }
              )
            }
          )
        }
      )
    }
  }
}
