package net.skyscanner.backpack.horisontalnav

import android.content.Context
import android.content.res.ColorStateList
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.util.AttributeSet
import androidx.annotation.AttrRes
import androidx.annotation.ColorRes
import com.google.android.material.tabs.TabLayout
import net.skyscanner.backpack.R
import net.skyscanner.backpack.text.BpkFontSpan
import net.skyscanner.backpack.text.BpkText
import net.skyscanner.backpack.util.*
import net.skyscanner.backpack.util.use
import net.skyscanner.backpack.util.wrapContextWithDefaults

open class BpkHorizontalNav @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : TabLayout(wrapContextWithDefaults(context), attrs, defStyleAttr) {

  enum class Appearance(
    internal val id: Int,
    @AttrRes internal val styleAttribute: Int,
    @ColorRes internal val defaultTextColor: Int,
    @ColorRes internal val defaultTextSelectedColor: Int,
    @ColorRes internal val defaultIndicatorColor: Int
  ) {
    Normal(
      id = 0,
      styleAttribute = R.attr.bpkHorizontalNavStyle,
      defaultTextColor = R.color.bpkGray700,
      defaultTextSelectedColor = R.color.bpkBlue500,
      defaultIndicatorColor = R.color.bpkBlue500
    ),
    Alternate(
      id = 1,
      styleAttribute = R.attr.bpkHorizontalNavStyleAlternate,
      defaultTextColor = R.color.bpkGray50,
      defaultTextSelectedColor = R.color.bpkWhite,
      defaultIndicatorColor = R.color.bpkWhite
    )
  }

  private val fontSpan = BpkFontSpan(context, BpkText.SM, BpkText.Weight.EMPHASIZED)

  private var _appearance: Appearance = Appearance.Normal

  var appearance: Appearance
    get() = _appearance
    set(value) {
      if (_appearance != value) {
        _appearance = value
        update(null, 0)
      }
    }

  init {
    _appearance = initialize(attrs, defStyleAttr)
    update(attrs, defStyleAttr)
  }

  private fun initialize(attrs: AttributeSet?, defStyleAttr: Int) =
    context.theme.obtainStyledAttributes(
      attrs,
      R.styleable.BpkHorizontalNav,
      defStyleAttr,
      0
    ).use {
      it.getInt(R.styleable.BpkHorizontalNav_horizontalNavAppearance, 0)
        .let { id -> Appearance.values().find { it.id == id } }
    } ?: Appearance.Normal

  private fun update(attrs: AttributeSet?, defStyleAttr: Int) {
    var textColor: Int = BpkTheme.getColor(context, appearance.defaultTextColor)
    var textSelectedColor: Int = BpkTheme.getColor(context, appearance.defaultTextSelectedColor)
    var indicatorColor: Int = BpkTheme.getColor(context, appearance.defaultIndicatorColor)

    val stylisedContext = createContextThemeWrapper(context, attrs, appearance.styleAttribute)
    stylisedContext
      .theme
      .obtainStyledAttributes(attrs, R.styleable.BpkHorizontalNav, defStyleAttr, 0)
      .use {
        textColor = it.getColor(R.styleable.BpkHorizontalNav_horizontalNavTextColor, textColor)
        textSelectedColor = it.getColor(R.styleable.BpkHorizontalNav_horizontalNavSelectedTextColor, textSelectedColor)
        indicatorColor = it.getColor(R.styleable.BpkHorizontalNav_horizontalNavIndicatorColor, indicatorColor)
      }

    @Suppress("DEPRECATION")
    setSelectedTabIndicatorHeight(resources.getDimensionPixelSize(R.dimen.bpkBorderSizeLg))
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
