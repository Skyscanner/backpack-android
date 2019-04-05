package net.skyscanner.backpack

import android.content.Context
import android.view.ContextThemeWrapper
import androidx.annotation.StyleRes
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.util.ThemeOverlayEnforcement

internal fun createThemedContext(base: Context, @StyleRes style: Int = R.style.LondonTheme): Context {
  return ThemeOverlayEnforcement(ContextThemeWrapper(base, style))
}
