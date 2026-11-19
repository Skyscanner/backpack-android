package net.skyscanner.backpack.compose.tooltip

import androidx.compose.runtime.Composable
import net.skyscanner.backpack.util.ExperimentalBackpackApi

@Composable
@OptIn(ExperimentalBackpackApi::class)
private fun TooltipTrailingLambdaCompileFixture(state: BpkTooltipState) {
    BpkTooltip(
        title = "Title",
        text = "Text",
        closeAccessibilityLabel = "Close",
        state = state,
        onDismissRequest = {},
    ) { }
}
