package net.skyscanner.backpack.util

import androidx.annotation.StyleRes
import net.skyscanner.backpack.R
import net.skyscanner.backpack.configuration.BpkConfiguration

object BpkTypographySetManager {

    /**
     * Returns the appropriate theme resource based on the current typography set configuration
     */
    @StyleRes
    fun getTypographyTheme(): Int {
        return when (BpkConfiguration.typographySet) {
            BpkConfiguration.BpkTypographySet.DEFAULT -> R.style.BpkTheme
            BpkConfiguration.BpkTypographySet.VDL_2 -> R.style.BpkTheme_Typography_Vdl2
        }
    }

    /**
     * Creates a theme resource based on a base theme and the current typography set
     */
    @StyleRes
    fun applyTypographySetToTheme(@StyleRes baseTheme: Int): Int {
        return when (BpkConfiguration.typographySet) {
            BpkConfiguration.BpkTypographySet.DEFAULT -> baseTheme
            BpkConfiguration.BpkTypographySet.VDL_2 -> {
                R.style.BpkTheme_Typography_Vdl2
            }
        }
    }
}
