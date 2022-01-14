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

package net.skyscanner.backpack.icon

import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.graphics.drawable.VectorDrawable
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.ImageView
import androidx.appcompat.content.res.AppCompatResources
import net.skyscanner.backpack.BpkSnapshotTest
import net.skyscanner.backpack.BpkTestVariant
import net.skyscanner.backpack.R
import org.junit.Assume.assumeTrue
import org.junit.Before
import org.junit.Test

class BpkIconTest : BpkSnapshotTest() {

  @Before
  fun setup() {
    setDimensions(600, 300)
  }

  @Test
  fun testAllSmallIcons() {
    assumeTrue(BpkTestVariant.current == BpkTestVariant.Default)

    val icons = getIcons(true)
    val layout = setupLayout(icons)

    snap(layout)
  }

  @Test
  fun testAllLargeIcons() {
    assumeTrue(BpkTestVariant.current == BpkTestVariant.Default)

    val icons = getIcons(false)
    val layout = setupLayout(icons)

    snap(layout)
  }

  private fun setupLayout(icons: List<Drawable>): GridLayout {
    val layout = GridLayout(testContext).apply {
      columnCount = 12
      layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
    }
    icons.forEach { icon ->
      val imageView = ImageView(testContext).apply { setImageDrawable(icon) }
      layout.addView(imageView)
    }
    return layout
  }

  private fun getIcons(small: Boolean): List<Drawable> {
    return R.drawable::class.java.fields.mapNotNull { field ->
      if (field.name.startsWith("bpk_") && field.name.endsWith("_sm") == small) {
        try {
          AppCompatResources.getDrawable(testContext, field.getInt(null))?.takeIf { it is VectorDrawable }
        } catch (e: Resources.NotFoundException) {
          null
        }
      } else {
        null
      }
    }
  }
}
