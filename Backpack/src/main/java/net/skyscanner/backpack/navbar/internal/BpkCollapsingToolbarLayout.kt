package net.skyscanner.backpack.navbar.internal

import android.content.Context
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import com.google.android.material.appbar.CollapsingToolbarLayout
import net.skyscanner.backpack.R
import net.skyscanner.backpack.text.BpkText
import net.skyscanner.backpack.text.internal.FontFamilyResolver
import net.skyscanner.backpack.util.resolveThemeId

internal class BpkCollapsingToolbarLayout @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : CollapsingToolbarLayout(context, attrs, defStyleAttr) {

  init {
    setExpandedTitleTextAppearance(resolveThemeId(context, R.attr.bpkTextXxxlHeavyAppearance))
    setExpandedTitleTypeface(FontFamilyResolver.invoke(context, BpkText.Weight.HEAVY))

    setCollapsedTitleTextAppearance(resolveThemeId(context, R.attr.bpkTextBaseEmphasizedAppearance))
    setCollapsedTitleTypeface(FontFamilyResolver.invoke(context, BpkText.Weight.EMPHASIZED))

    expandedTitleMarginStart = resources.getDimensionPixelSize(R.dimen.bpk_nav_bar_expanded_spacing_horizontal_small)
    expandedTitleMarginEnd = resources.getDimensionPixelSize(R.dimen.bpk_nav_bar_expanded_spacing_horizontal_small)
    expandedTitleMarginTop = resources.getDimensionPixelSize(R.dimen.bpk_nav_bar_expanded_spacing_top)
    expandedTitleMarginBottom = resources.getDimensionPixelSize(R.dimen.bpk_nav_bar_expanded_spacing_bottom)

    setScrimsShown(false)
    contentScrim = ContextCompat.getDrawable(context, R.drawable.navbar_content_scrim_background)
  }
}
