package net.skyscanner.backpack.pagetitle.internal

import androidx.annotation.DrawableRes
import com.google.android.material.appbar.AppBarLayout
import net.skyscanner.backpack.R
import net.skyscanner.backpack.pagetitle.BpkPageTitle

internal val BpkPageTitle.NavMode.xmlId
  get() = when (this) {
    BpkPageTitle.NavMode.None -> 0
    BpkPageTitle.NavMode.Back -> 1
    BpkPageTitle.NavMode.Close -> 2
  }

@get:DrawableRes
internal val BpkPageTitle.NavMode.drawableRes
  get() = when (this) {
    BpkPageTitle.NavMode.None -> 0
    BpkPageTitle.NavMode.Back -> R.drawable.bpk_native_android__back
    BpkPageTitle.NavMode.Close -> R.drawable.bpk_native_android__close
  }

internal fun mapXmlToNavMode(id: Int) =
  BpkPageTitle.NavMode.values().find { it.xmlId == id }!!

internal val PAGE_TITLE_COLLAPSING_LAYOUT_PARAMS = AppBarLayout.LayoutParams(AppBarLayout.LayoutParams.MATCH_PARENT, AppBarLayout.LayoutParams.MATCH_PARENT).apply {
  scrollFlags = AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL or AppBarLayout.LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED
}
