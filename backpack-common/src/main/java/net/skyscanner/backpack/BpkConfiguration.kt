package net.skyscanner.backpack

import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.FontRes
import androidx.compose.ui.unit.Dp

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
        globalConfig: BpkExperimentalComponent.Global? = null,
        chipConfig: BpkExperimentalComponent.BpkChip? = null,
        buttonConfig: BpkExperimentalComponent.BpkButton? = null,
        textConfig: BpkExperimentalComponent.BpkText? = null,
        cardConfig: BpkExperimentalComponent.BpkCard? = null,
    ) {
        if (_hasSet) {
            throw IllegalStateException("BpkConfiguration has already been set")
        }
        _hasSet = true
        this.globalConfig = globalConfig
        this.chipConfig = chipConfig
        this.buttonConfig = buttonConfig
        this.textConfig = textConfig
        this.cardConfig = cardConfig
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
