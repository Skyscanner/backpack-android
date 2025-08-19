package net.skyscanner.backpack.configuration

object BpkConfiguration {
    sealed class BpkExperimentalComponent {

        data object BpkButton : BpkExperimentalComponent()

        data object BpkText : BpkExperimentalComponent()

        data object BpkCard : BpkExperimentalComponent()

        data object BpkChip : BpkExperimentalComponent()
    }

    private var _hasSet: Boolean = false

    fun setConfigs(
        chipConfig: Boolean = false,
        buttonConfig: Boolean = false,
        textConfig: Boolean = false,
        cardConfig: Boolean = false,
    ) {
        if (_hasSet) {
            throw IllegalStateException("BpkConfiguration has already been set")
        }
        _hasSet = true
        if (chipConfig) {
            this.chipConfig = BpkExperimentalComponent.BpkChip
        }
        if (buttonConfig) {
            this.buttonConfig = BpkExperimentalComponent.BpkButton
        }
        if (textConfig) {
            this.textConfig = BpkExperimentalComponent.BpkText
        }
        if (cardConfig) {
            this.cardConfig = BpkExperimentalComponent.BpkCard
        }
    }

    var chipConfig: BpkExperimentalComponent.BpkChip? = null
        private set

    var buttonConfig: BpkExperimentalComponent.BpkButton? = null
        private set

    var textConfig: BpkExperimentalComponent.BpkText? = null
        private set

    var cardConfig: BpkExperimentalComponent.BpkCard? = null
        private set
}
