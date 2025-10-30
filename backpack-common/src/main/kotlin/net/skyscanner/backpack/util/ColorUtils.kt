package net.skyscanner.backpack.util

import android.content.Context
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb

@InternalBackpackApi
data class ColorPair(
    private val lightColorLong: Long,
    private val darkColorLong: Long,
) {
    val lightColor: Color = Color(lightColorLong)
    val darkColor: Color = Color(darkColorLong)

    fun toColor(context: Context): Color {
        return (if (context.isDarkMode()) darkColor else lightColor)
    }

    fun toArgb(context: Context): Int {
        return (if (context.isDarkMode()) darkColor else lightColor).toArgb()
    }
}

@InternalBackpackApi
internal fun Context.isDarkMode(): Boolean {
    return resources.configuration.uiMode and
        android.content.res.Configuration.UI_MODE_NIGHT_MASK ==
        android.content.res.Configuration.UI_MODE_NIGHT_YES
}
