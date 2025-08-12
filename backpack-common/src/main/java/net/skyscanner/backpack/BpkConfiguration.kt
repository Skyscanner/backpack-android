package net.skyscanner.backpack

import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.FontRes
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import net.skyscanner.backpack.common.R

sealed class BpkExperimentalComponent {

    data class Global(
        @FontRes
        val font: Int?,
        @ColorRes
        val colorResource: Int?,
        val dimension: Dp?,
        val radius: Int?,
        @DimenRes
        val dimensionResource: Int?,
        val behaviour: Boolean?,
    ) : BpkExperimentalComponent()

    data class BpkButton(
        @ColorRes
        val colorResource: Int?,
        val largeMinHeight: Dp?,
        @DimenRes
        val largeMinHeightResource: Int?,
        val radius: Int?,
        @DimenRes
        val radiusDimension: Int?,
    ) : BpkExperimentalComponent()

    data class BpkText(
        @FontRes
        val font: Int?,
    ) : BpkExperimentalComponent()

    data class BpkCard(
        val cornerRadius: Int?,
    ) : BpkExperimentalComponent()

    data class BpkChip(
        @ColorRes
        val colorResource: Int?,
        val height: Dp?,
        @DimenRes
        val heightDimension: Int?,
        val radius: Int?,
        @DimenRes
        val radiusDimension: Int?,
    ) : BpkExperimentalComponent()
}

object BpkConfiguration {

    private var _hasSet: Boolean = false

    fun setConfigs(
        globalConfig: Boolean = false,
        chipConfig: Boolean = false,
        buttonConfig: Boolean = false,
        textConfig: Boolean = false,
        cardConfig: Boolean = false,
    ) {
        if (_hasSet) {
            throw IllegalStateException("BpkConfiguration has already been set")
        }
        _hasSet = true
        if (globalConfig) {
        }
        if (chipConfig) {
            this.chipConfig = BpkExperimentalComponent.BpkChip(
                colorResource = R.color.bpkCoreAccent,
                height = 36.dp,
                heightDimension = R.dimen.bpk_new_chip_height,
                radius = 100,
                radiusDimension = R.dimen.bpkBorderRadiusFull,
            )
        }
        if (buttonConfig) {
            this.buttonConfig = BpkExperimentalComponent.BpkButton(
                colorResource = R.color.bpkCoreAccent,
                largeMinHeight = 56.dp,
                largeMinHeightResource = R.dimen.bpk_button_large_height_new,
                radius = 100,
                radiusDimension = R.dimen.bpkBorderRadiusFull,
            )
        }
        if (textConfig) {
        }
        if (cardConfig) {
        }
    }

    var globalConfig: BpkExperimentalComponent.Global? = null
        private set

    var chipConfig: BpkExperimentalComponent.BpkChip? = null
        private set

    var buttonConfig: BpkExperimentalComponent.BpkButton? = null
        private set

    var textConfig: BpkExperimentalComponent.BpkText? = null
        private set

    var cardConfig: BpkExperimentalComponent.BpkCard? = null
        private set
}
