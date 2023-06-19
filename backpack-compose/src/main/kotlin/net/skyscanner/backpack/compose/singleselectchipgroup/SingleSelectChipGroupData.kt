package net.skyscanner.backpack.compose.singleselectchipgroup

import net.skyscanner.backpack.compose.icon.BpkIcon

object SingleSelectChipGroupData {
    enum class ChipGroupType {
        RAIL,
        WRAP,
    }

    data class ChipItem(val text: String, val icon: BpkIcon? = null)
}
