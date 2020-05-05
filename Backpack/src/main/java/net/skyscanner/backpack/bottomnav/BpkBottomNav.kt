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

package net.skyscanner.backpack.bottomnav

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.TextPaint
import android.text.style.CharacterStyle
import android.util.AttributeSet
import android.view.Menu
import android.view.MenuItem
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomnavigation.LabelVisibilityMode
import net.skyscanner.backpack.R
import net.skyscanner.backpack.text.BpkText

open class BpkBottomNav @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : BottomNavigationView(context, attrs, defStyleAttr) {

  private val listeners = ListenersDelegate(menu).also {
    super.setOnNavigationItemReselectedListener(it)
    super.setOnNavigationItemSelectedListener(it)
  }

  private val fontSpan = BottomNavSpan(context)

  init {
    labelVisibilityMode = LabelVisibilityMode.LABEL_VISIBILITY_LABELED
    background = ContextCompat.getDrawable(context, R.drawable.bpk_bottom_nav_background)
    minimumHeight = resources.getDimensionPixelSize(R.dimen.bpk_bottom_nav_height)
    itemTextColor = ContextCompat.getColorStateList(context, R.color.bpk_bottom_nav_selector)
    itemIconTintList = itemTextColor
    ViewCompat.setElevation(this, resources.getDimension(R.dimen.bpkElevationLg))
  }

  fun addItem(id: Int, title: String, icon: Drawable): MenuItem =
    menu.add(Menu.NONE,
      id,
      menu.size(),
      SpannableStringBuilder().append(title, fontSpan, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
    )
      .setIcon(icon)

  fun addItem(id: Int, @StringRes title: Int, @DrawableRes icon: Int): MenuItem =
    addItem(id, resources.getString(title), ContextCompat.getDrawable(context, icon)!!)

  fun addOnNavigationItemSelectedListener(listener: (MenuItem, Int) -> Unit) {
    listeners.selected += listener
  }

  fun addOnNavigationItemReselectedListener(listener: (MenuItem, Int) -> Unit) {
    listeners.reselected += listener
  }

  fun removeOnNavigationItemSelectedListener(listener: (MenuItem, Int) -> Unit) {
    listeners.selected -= listener
  }

  fun removeOnNavigationItemReselectedListener(listener: (MenuItem, Int) -> Unit) {
    listeners.reselected -= listener
  }

  @Deprecated("Use add/remove OnNavigationItemSelectedListener instead",
    replaceWith = ReplaceWith("addOnNavigationItemSelectedListener"))
  override fun setOnNavigationItemSelectedListener(listener: OnNavigationItemSelectedListener?) {
    TODO("Not supported")
  }

  @Deprecated("Use add/remove OnNavigationItemSelectedListener instead",
    replaceWith = ReplaceWith("addOnNavigationItemReselectedListener"))
  override fun setOnNavigationItemReselectedListener(listener: OnNavigationItemReselectedListener?) {
    TODO("Not supported")
  }

  private class ListenersDelegate(private val menu: Menu) : OnNavigationItemSelectedListener, OnNavigationItemReselectedListener {

    val reselected = mutableListOf<(MenuItem, Int) -> Unit>()
    val selected = mutableListOf<(MenuItem, Int) -> Unit>()

    override fun onNavigationItemReselected(item: MenuItem) {
      val index = findIndexOf(item)
      if (index != -1) {
        reselected.forEach { it.invoke(item, index) }
      }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
      val index = findIndexOf(item)
      if (index != -1) {
        selected.forEach { it.invoke(item, index) }
      }
      return true
    }

    private fun findIndexOf(item: MenuItem): Int {
      for (i in 0 until menu.size()) {
        if (menu.getItem(i) == item) {
          return i
        }
      }
      return -1
    }
  }

  // We have to create a custom span as a BpkFontSpan causes some animation glitches as it sets text size as well.
  private class BottomNavSpan(private val font: BpkText.FontDefinition) : CharacterStyle() {

    constructor(context: Context, textStyle: Int = BpkText.BASE, weight: BpkText.Weight = BpkText.Weight.NORMAL) :
      this(BpkText.getFont(context, textStyle, weight))

    override fun updateDrawState(tp: TextPaint) {
      tp.typeface = font.typeface
    }
  }
}
