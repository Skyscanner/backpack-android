package net.skyscanner.backpack.pagetitle

import android.content.Context
import android.content.res.Resources
import android.graphics.drawable.ColorDrawable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.ViewGroup
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.annotation.DimenRes
import androidx.appcompat.view.ContextThemeWrapper
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import net.skyscanner.backpack.R
import net.skyscanner.backpack.text.BpkText
import net.skyscanner.backpack.text.internal.FontFamilyResolver
import net.skyscanner.backpack.util.createContextThemeWrapper
import net.skyscanner.backpack.util.use

class BpkPageTitle @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : AppBarLayout(createContextThemeWrapper(context, attrs, R.attr.bpkPageTitleStyle), attrs) {

  private val collapsingLayout = CollapsingToolbarLayout(this.context)

  val toolbar: Toolbar

  @ColorInt
  var expandedTitleColor: Int = ContextCompat.getColor(this.context, R.color.bpkGray900)
    set(value) {
      field = value
      collapsingLayout.setExpandedTitleColor(value)
    }

  @ColorInt
  var collapsedTitleColor: Int = ContextCompat.getColor(this.context, R.color.bpkGray900)
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

  init {
    var toolbarStyle = this.context.theme.resolveId(R.attr.toolbarStyle)
    var backgroundColor = ContextCompat.getColor(this.context, R.color.bpkWhite)
    var expandedTitleColor = expandedTitleColor
    var collapsedTextColor = collapsedTitleColor
    var title: CharSequence? = null

    this.context.theme.obtainStyledAttributes(
      attrs,
      R.styleable.BpkPageTitle,
      defStyleAttr, 0
    ).use {
      toolbarStyle = it.getResourceId(R.styleable.BpkPageTitle_pageTitleToolbarStyle, toolbarStyle)
      backgroundColor = it.getColor(R.styleable.BpkPageTitle_pageTitleBackgroundColor, backgroundColor)
      expandedTitleColor = it.getColor(R.styleable.BpkPageTitle_pageTitleExpandedTextColor, expandedTitleColor)
      collapsedTextColor = it.getColor(R.styleable.BpkPageTitle_pageTitleCollapsedTextColor, collapsedTextColor)
      title = it.getString(R.styleable.BpkPageTitle_pageTitleText)
    }

    this.toolbar = Toolbar(ContextThemeWrapper(this.context, toolbarStyle))
    setupToolbar()

    setupCollapsingLayout()

    this.expandedTitleColor = expandedTitleColor
    this.collapsedTitleColor = collapsedTextColor
    this.background = ColorDrawable(backgroundColor)
    this.title = title
  }

  private fun setupCollapsingLayout() {
    collapsingLayout.also {
      it.setExpandedTitleTextAppearance(context.theme.resolveId(R.attr.bpkTextXxxlHeavyAppearance))
      it.setExpandedTitleTypeface(FontFamilyResolver.invoke(it.context, BpkText.Weight.HEAVY))

      it.setCollapsedTitleTextAppearance(context.theme.resolveId(R.attr.bpkTextBaseEmphasizedAppearance))
      it.setCollapsedTitleTypeface(FontFamilyResolver.invoke(it.context, BpkText.Weight.EMPHASIZED))

      it.expandedTitleMarginStart = resources.getDimensionPixelSize(R.dimen.bpk_page_title_expanded_spacing_horizontal)
      it.expandedTitleMarginEnd = resources.getDimensionPixelSize(R.dimen.bpk_page_title_expanded_spacing_horizontal)
      it.expandedTitleMarginTop = resources.getDimensionPixelSize(R.dimen.bpk_page_title_expanded_spacing_top)
      it.expandedTitleMarginBottom = resources.getDimensionPixelSize(R.dimen.bpk_page_title_expanded_spacing_bottom)

      it.setScrimsShown(false)

      addView(it, COLLAPSING_LAYOUT_PARAMS)
    }
  }

  override fun setLayoutParams(params: ViewGroup.LayoutParams?) {
    params?.let {
      if (it.height == LayoutParams.WRAP_CONTENT || it.height == LayoutParams.MATCH_PARENT) {
        it.height = resources.getDimensionPixelSize(R.dimen.bpk_page_title_min_height)
      }
    }
    super.setLayoutParams(params)
  }

  private fun setupToolbar() {
    toolbar.background = null

    val toolbarHeight = context.theme.resolveDimen(android.R.attr.actionBarSize, R.dimen.bpk_page_title_toolbar_height)
    val params = CollapsingToolbarLayout.LayoutParams(LayoutParams.MATCH_PARENT, toolbarHeight).apply {
      collapseMode = CollapsingToolbarLayout.LayoutParams.COLLAPSE_MODE_PIN
    }

    collapsingLayout.addView(toolbar, params)
  }

  private companion object {

    private fun Resources.Theme.resolveId(@AttrRes id: Int, fallback: Int = 0): Int {
      val tv = TypedValue()
      if (resolveAttribute(id, tv, true)) {
        return tv.resourceId
      }
      return fallback
    }

    private fun Resources.Theme.resolveDimen(@AttrRes id: Int, @DimenRes fallback: Int = 0): Int {
      val tv = TypedValue()
      if (resolveAttribute(id, tv, true)) {
        return TypedValue.complexToDimensionPixelSize(tv.data, resources.displayMetrics)
      }
      return resources.getDimensionPixelSize(fallback)
    }

    private val COLLAPSING_LAYOUT_PARAMS = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT).apply {
      scrollFlags = LayoutParams.SCROLL_FLAG_SCROLL or LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED
    }
  }
}
