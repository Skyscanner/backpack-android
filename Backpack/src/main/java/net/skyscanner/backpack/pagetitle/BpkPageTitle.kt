package net.skyscanner.backpack.pagetitle

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.annotation.*
import androidx.appcompat.view.ContextThemeWrapper
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import net.skyscanner.backpack.R
import net.skyscanner.backpack.pagetitle.internal.PAGE_TITLE_COLLAPSING_LAYOUT_PARAMS
import net.skyscanner.backpack.pagetitle.internal.drawableRes
import net.skyscanner.backpack.pagetitle.internal.mapXmlToNavMode
import net.skyscanner.backpack.pagetitle.internal.xmlId
import net.skyscanner.backpack.text.BpkText
import net.skyscanner.backpack.text.internal.FontFamilyResolver
import net.skyscanner.backpack.util.createContextThemeWrapper
import net.skyscanner.backpack.util.resolveThemeDimen
import net.skyscanner.backpack.util.resolveThemeId
import net.skyscanner.backpack.util.use

class BpkPageTitle @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : AppBarLayout(createContextThemeWrapper(context, attrs, R.attr.bpkPageTitleStyle), attrs) {

  enum class NavMode {
    None,
    Back,
    Close
  }

  private val collapsingLayout = CollapsingToolbarLayout(this.context)

  val toolbar: Toolbar

  @ColorInt
  var expandedTitleColor: Int = ContextCompat.getColor(this.context, R.color.bpkTextPrimary)
    set(value) {
      field = value
      collapsingLayout.setExpandedTitleColor(value)
    }

  @ColorInt
  var collapsedTitleColor: Int = ContextCompat.getColor(this.context, R.color.bpkTextPrimary)
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

  var navMode: NavMode = NavMode.None
    set(value) {
      if (field != value) {
        field = value
        toolbar.setNavigationIcon(field.drawableRes)
      }
    }

  var navAction: () -> Unit = {}
    set(value) {
      field = value
      toolbar.setNavigationOnClickListener { value.invoke() }
    }

  init {
    var toolbarStyle = resolveThemeId(this.context, R.attr.toolbarStyle)
    var backgroundColor = ContextCompat.getColor(this.context, R.color.bpkBackground)
    var expandedTitleColor = expandedTitleColor
    var collapsedTextColor = collapsedTitleColor
    var title: CharSequence? = null
    var navMode = NavMode.None

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
      navMode = it.getInt(R.styleable.BpkPageTitle_pageTitleNavMode, navMode.xmlId).let(::mapXmlToNavMode)
    }

    this.toolbar = Toolbar(ContextThemeWrapper(this.context, toolbarStyle))
    setupToolbar()

    setupCollapsingLayout()

    this.expandedTitleColor = expandedTitleColor
    this.collapsedTitleColor = collapsedTextColor
    this.background = ColorDrawable(backgroundColor)
    this.title = title
    this.navMode = navMode
    this.navAction = navAction
  }

  private fun setupCollapsingLayout() {
    collapsingLayout.also {
      it.setExpandedTitleTextAppearance(resolveThemeId(context, R.attr.bpkTextXxxlHeavyAppearance))
      it.setExpandedTitleTypeface(FontFamilyResolver.invoke(it.context, BpkText.Weight.HEAVY))

      it.setCollapsedTitleTextAppearance(resolveThemeId(context, R.attr.bpkTextBaseEmphasizedAppearance))
      it.setCollapsedTitleTypeface(FontFamilyResolver.invoke(it.context, BpkText.Weight.EMPHASIZED))

      it.expandedTitleMarginStart = resources.getDimensionPixelSize(R.dimen.bpk_page_title_expanded_spacing_start)
      it.expandedTitleMarginEnd = resources.getDimensionPixelSize(R.dimen.bpk_page_title_expanded_spacing_end)
      it.expandedTitleMarginTop = resources.getDimensionPixelSize(R.dimen.bpk_page_title_expanded_spacing_top)
      it.expandedTitleMarginBottom = resources.getDimensionPixelSize(R.dimen.bpk_page_title_expanded_spacing_bottom)

      it.setScrimsShown(false)

      addView(it, PAGE_TITLE_COLLAPSING_LAYOUT_PARAMS)
    }
  }

  override fun setLayoutParams(params: ViewGroup.LayoutParams?) {
    params?.let {
      if (it.height == LayoutParams.WRAP_CONTENT) {
        it.height = resources.getDimensionPixelSize(R.dimen.bpk_page_title_min_height)
      }
    }
    super.setLayoutParams(params)
  }

  private fun setupToolbar() {
    toolbar.background = null

    val toolbarHeight = resolveThemeDimen(context, android.R.attr.actionBarSize, R.dimen.bpk_page_title_toolbar_height)
    val params = CollapsingToolbarLayout.LayoutParams(LayoutParams.MATCH_PARENT, toolbarHeight).apply {
      collapseMode = CollapsingToolbarLayout.LayoutParams.COLLAPSE_MODE_PIN
    }

    collapsingLayout.addView(toolbar, params)
  }
}
