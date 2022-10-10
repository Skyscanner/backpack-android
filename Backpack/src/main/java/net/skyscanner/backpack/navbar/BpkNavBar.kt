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

package net.skyscanner.backpack.navbar

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import androidx.annotation.ColorInt
import androidx.annotation.MenuRes
import androidx.appcompat.widget.Toolbar
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import net.skyscanner.backpack.R
import net.skyscanner.backpack.navbar.internal.BpkCollapsingToolbarLayout
import net.skyscanner.backpack.navbar.internal.BpkToolbar
import net.skyscanner.backpack.util.createContextThemeWrapper
import net.skyscanner.backpack.util.resolveThemeDimen
import net.skyscanner.backpack.util.use

class BpkNavBar @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : AppBarLayout(context, attrs) {

  private val collapsingLayout = BpkCollapsingToolbarLayout(context).also {
    addView(it, COLLAPSING_LAYOUT_PARAMS)
  }

  private val toolbar: Toolbar = BpkToolbar(context).also {
    val toolbarHeight = resolveThemeDimen(context, android.R.attr.actionBarSize, R.dimen.bpk_nav_bar_toolbar_height)
    val params = CollapsingToolbarLayout.LayoutParams(LayoutParams.MATCH_PARENT, toolbarHeight).apply {
      collapseMode = CollapsingToolbarLayout.LayoutParams.COLLAPSE_MODE_PIN
    }

    collapsingLayout.addView(it, params)
  }

  @ColorInt
  var expandedTitleColor: Int = context.getColor(R.color.bpkTextPrimary)
    set(value) {
      field = value
      collapsingLayout.setExpandedTitleColor(value)
    }

  @ColorInt
  var collapsedTitleColor: Int = context.getColor(R.color.bpkTextPrimary)
    set(value) {
      field = value
      toolbar.setTitleTextColor(value)
      collapsingLayout.setCollapsedTitleTextColor(value)
    }

  var title: CharSequence?
    get() = toolbar.title
    set(value) {
      toolbar.title = value
      collapsingLayout.title = value
    }

  var icon: Drawable? = null
    set(value) {
      if (field != value) {
        field = value
        toolbar.navigationIcon = value
        collapsingLayout.expandedTitleMarginStart = resources.getDimensionPixelSize(
          when (field) {
            null -> R.dimen.bpk_nav_bar_expanded_spacing_horizontal_small
            else -> R.dimen.bpk_nav_bar_expanded_spacing_horizontal_large
          }
        )
      }
    }

  var navAction: () -> Unit = {}
    set(value) {
      field = value
      toolbar.setNavigationOnClickListener { field.invoke() }
    }

  @get:MenuRes
  var menu: Int = 0
    set(@MenuRes value) {
      if (field != value) {
        field = value
        toolbar.menu.clear()
        if (value != 0) {
          toolbar.inflateMenu(value)
        }
      }
    }

  val menuItems: Menu
    get() = toolbar.menu

  var menuAction: (MenuItem) -> Unit = {}
    set(value) {
      field = value
      toolbar.setOnMenuItemClickListener {
        value.invoke(it)
        return@setOnMenuItemClickListener true
      }
    }

  init {
    var expandedTitleColor = expandedTitleColor
    var collapsedTextColor = collapsedTitleColor
    var title: CharSequence?
    var navIcon: Drawable?
    var menu = 0

    createContextThemeWrapper(context, attrs, R.attr.bpkNavBarStyle).theme.obtainStyledAttributes(
      attrs,
      R.styleable.BpkNavBar,
      defStyleAttr, 0
    ).use {
      expandedTitleColor = it.getColor(R.styleable.BpkNavBar_navBarExpandedTextColor, expandedTitleColor)
      collapsedTextColor = it.getColor(R.styleable.BpkNavBar_navBarCollapsedTextColor, collapsedTextColor)
      title = it.getString(R.styleable.BpkNavBar_navBarTitle)
      navIcon = it.getDrawable(R.styleable.BpkNavBar_navBarIcon)
      menu = it.getResourceId(R.styleable.BpkNavBar_navBarMenu, menu)
    }

    this.expandedTitleColor = expandedTitleColor
    this.collapsedTitleColor = collapsedTextColor
    this.background = ColorDrawable(context.getColor(R.color.bpkSurfaceDefault))
    this.title = title
    this.icon = navIcon
    this.navAction = navAction
    this.menu = menu
  }

  override fun setLayoutParams(params: ViewGroup.LayoutParams?) {
    params?.let {
      if (it.height == LayoutParams.WRAP_CONTENT) {
        it.height = resources.getDimensionPixelSize(R.dimen.bpk_nav_bar_min_height)
      }
    }
    super.setLayoutParams(params)
  }

  private companion object {

    val COLLAPSING_LAYOUT_PARAMS = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT).apply {
      scrollFlags = LayoutParams.SCROLL_FLAG_SCROLL or LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED
    }
  }
}
