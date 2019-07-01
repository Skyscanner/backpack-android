package net.skyscanner.backpack.horisontalnav

import android.content.Context
import android.content.res.ColorStateList
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.util.AttributeSet
import com.google.android.material.tabs.TabLayout
import net.skyscanner.backpack.R
import net.skyscanner.backpack.text.BpkFontSpan
import net.skyscanner.backpack.text.BpkText
import net.skyscanner.backpack.util.BpkTheme
import net.skyscanner.backpack.util.ResourcesUtil
import net.skyscanner.backpack.util.createContextThemeWrapper
import net.skyscanner.backpack.util.use

open class BpkHorizontalNav @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : TabLayout(
  createContextThemeWrapper(
    createContextThemeWrapper(context, attrs, com.google.android.material.R.attr.tabStyle),
    attrs, R.attr.bpkHorizontalNavStyle
  ), attrs, defStyleAttr) {

  private val fontSpan = BpkFontSpan(context, BpkText.SM, BpkText.Weight.EMPHASIZED)

  init {
    initialize(attrs, defStyleAttr)
  }

  private fun initialize(attrs: AttributeSet?, defStyleAttr: Int) {
    var textColor: Int = BpkTheme.getColor(context, R.color.bpkGray700)
    var textSelectedColor: Int = BpkTheme.getColor(context, R.color.bpkBlue500)
    var indicatorColor: Int = BpkTheme.getColor(context, R.color.bpkBlue500)

    context.theme.obtainStyledAttributes(
      attrs,
      R.styleable.BpkHorizontalNav,
      defStyleAttr,
      0
    ).use {
      textColor = it.getColor(R.styleable.BpkHorizontalNav_horizontalNavTextColor, textColor)
      textSelectedColor = it.getColor(R.styleable.BpkHorizontalNav_horizontalNavSelectedTextColor, textSelectedColor)
      indicatorColor = it.getColor(R.styleable.BpkHorizontalNav_horizontalNavIndicatorColor, indicatorColor)
    }

    @Suppress("DEPRECATION")
    setSelectedTabIndicatorHeight(ResourcesUtil.dpToPx(2, context))
    setTabTextColors(textColor, textSelectedColor)
    setSelectedTabIndicatorColor(indicatorColor)
  }

  override fun setTabTextColors(textColor: ColorStateList?) {
    super.setTabTextColors(textColor)
    tabIconTint = tabTextColors
  }

  override fun setTabTextColors(normalColor: Int, selectedColor: Int) {
    super.setTabTextColors(normalColor, selectedColor)
    tabIconTint = tabTextColors
  }

  override fun addTab(tab: Tab) {
    addTab(tab, isEmpty())
  }

  override fun addTab(tab: Tab, position: Int) {
    addTab(tab, position, isEmpty())
  }

  override fun addTab(tab: Tab, setSelected: Boolean) {
    addTab(tab, tabCount, setSelected)
  }

  override fun addTab(tab: Tab, position: Int, setSelected: Boolean) {
    if (tab.text != null) {
      tab.text = SpannableStringBuilder().append(tab.text, fontSpan, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
    }
    super.addTab(tab, position, setSelected)
  }

  private fun isEmpty() = tabCount == 0
}
