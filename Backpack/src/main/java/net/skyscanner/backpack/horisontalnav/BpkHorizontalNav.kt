/**
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2018 - 2025 Skyscanner Ltd
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

package net.skyscanner.backpack.horisontalnav

import android.content.Context
import android.content.res.ColorStateList
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.AttrRes
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.collection.SparseArrayCompat
import com.google.android.material.tabs.TabLayout
import net.skyscanner.backpack.R
import net.skyscanner.backpack.text.BpkFontSpan
import net.skyscanner.backpack.text.BpkText
import net.skyscanner.backpack.util.createContextThemeWrapper
import net.skyscanner.backpack.util.use

open class BpkHorizontalNav @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : TabLayout(context, attrs, defStyleAttr) {

    @Deprecated("Alternate styling is no longer supported - please remove usages")
    enum class Appearance(
        internal val id: Int,
        @AttrRes internal val styleAttribute: Int,
        @ColorRes internal val defaultTextColor: Int,
        @ColorRes internal val defaultTextSelectedColor: Int,
        @ColorRes internal val defaultIndicatorColor: Int,
    ) {
        Normal(
            id = 0,
            styleAttribute = R.attr.bpkHorizontalNavStyle,
            defaultTextColor = net.skyscanner.backpack.common.R.color.bpkTextPrimary,
            defaultTextSelectedColor = net.skyscanner.backpack.common.R.color.bpkTextLink,
            defaultIndicatorColor = net.skyscanner.backpack.common.R.color.bpkTextLink,
        ),
        Alternate(
            id = 1,
            styleAttribute = R.attr.bpkHorizontalNavStyleAlternate,
            defaultTextColor = net.skyscanner.backpack.common.R.color.bpkCanvasContrast,
            defaultTextSelectedColor = net.skyscanner.backpack.common.R.color.bpkTextOnDark,
            defaultIndicatorColor = net.skyscanner.backpack.common.R.color.bpkTextOnDark,
        ),
    }

    enum class Size(
        internal val id: Int,
        @DimenRes internal val value: Int,
    ) {
        Small(
            id = 0,
            value = R.dimen.bpk_horizontal_nav_size_small,
        ),
        Normal(
            id = 1,
            value = R.dimen.bpk_horizontal_nav_size_normal,
        ),
    }

    @Suppress("DEPRECATION")
    private var _appearance: Appearance = Appearance.Normal
    @Suppress("DEPRECATION")
    @Deprecated("Alternate styling is no longer supported - please remove usages")
    var appearance: Appearance
        get() = _appearance
        set(value) {
            if (_appearance != value) {
                _appearance = value
                updateAppearance()
            }
        }

    private var _size: Size = Size.Normal
    var size: Size
        get() = _size
        set(value) {
            if (_size != value) {
                _size = value
                updateSize()
            }
        }

    private val fontSpan = BpkFontSpan(context, BpkText.TextStyle.Label2)
    private val texts = SparseArrayCompat<CharSequence?>()

    init {
        initialize(attrs, defStyleAttr)
        updateAppearance(attrs, defStyleAttr)
        updateSize()
    }

    @Suppress("DEPRECATION")
    private fun initialize(attrs: AttributeSet?, defStyleAttr: Int) {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.BpkHorizontalNav,
            defStyleAttr,
            0,
        ).use {
            _appearance = it.getInt(R.styleable.BpkHorizontalNav_horizontalNavAppearance, appearance.id)
                .let { id -> Appearance.entries.find { it.id == id } }!!
            _size = it.getInt(R.styleable.BpkHorizontalNav_horizontalNavSize, size.id)
                .let { id -> Size.entries.find { it.id == id } }!!
        }
    }

    @Suppress("DEPRECATION")
    private fun updateAppearance(attrs: AttributeSet? = null, defStyleAttr: Int = 0) {
        var textColor: Int = context.getColor(appearance.defaultTextColor)
        var textSelectedColor: Int = context.getColor(appearance.defaultTextSelectedColor)
        var indicatorColor: Int = context.getColor(appearance.defaultIndicatorColor)

        val stylisedContext = createContextThemeWrapper(context, attrs, appearance.styleAttribute)
        stylisedContext
            .theme
            .obtainStyledAttributes(attrs, R.styleable.BpkHorizontalNav, defStyleAttr, 0)
            .use {
                textColor = it.getColor(R.styleable.BpkHorizontalNav_horizontalNavTextColor, textColor)
                textSelectedColor = it.getColor(R.styleable.BpkHorizontalNav_horizontalNavSelectedTextColor, textSelectedColor)
                indicatorColor = it.getColor(R.styleable.BpkHorizontalNav_horizontalNavIndicatorColor, indicatorColor)
            }

        setSelectedTabIndicatorHeight(resources.getDimensionPixelSize(net.skyscanner.backpack.common.R.dimen.bpkBorderSizeLg))
        setTabTextColors(textColor, textSelectedColor)
        setSelectedTabIndicatorColor(indicatorColor)
        isInlineLabel = true
        for (i in 0..<tabCount) {
            updateTab(i)
        }
    }

    private fun updateSize() {
        requestLayout()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val heightSpec = MeasureSpec.makeMeasureSpec(resources.getDimensionPixelSize(size.value), MeasureSpec.EXACTLY)
        super.onMeasure(widthMeasureSpec, heightSpec)
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
        texts.put(position, tab.text)
        super.addTab(tab.setCustomView(R.layout.view_bpk_tab), position, setSelected)
        updateTab(position)
    }

    private fun updateTab(position: Int) {
        val tab = getTabAt(position) ?: return
        if (texts.get(position) != null) {
            tab.text = SpannableStringBuilder().apply {
                append(texts.get(position), fontSpan, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
            }
        }
        tab.customView?.apply {
            findViewById<TextView>(android.R.id.text1)?.setTextColor(tabTextColors)
            findViewById<ImageView>(android.R.id.icon)?.imageTintList = tabTextColors
        }
    }

    private fun isEmpty() = tabCount == 0
}
