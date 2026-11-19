package net.skyscanner.backpack.demo.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import kotlinx.coroutines.launch
import net.skyscanner.backpack.compose.button.BpkButton
import net.skyscanner.backpack.compose.tooltip.BpkTooltip
import net.skyscanner.backpack.compose.tooltip.rememberBpkTooltipState
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.demo.components.TooltipComponent
import net.skyscanner.backpack.demo.meta.ComposeStory
import net.skyscanner.backpack.demo.ui.LocalAutomationMode
import net.skyscanner.backpack.util.ExperimentalBackpackApi

@Composable
@OptIn(ExperimentalBackpackApi::class)
@TooltipComponent
@ComposeStory
fun TooltipStory(modifier: Modifier = Modifier) {
    val state = rememberBpkTooltipState()
    val scope = rememberCoroutineScope()
    val automationMode = LocalAutomationMode.current
    LaunchedEffect(automationMode) {
        if (automationMode) state.show()
    }
    Column(modifier = modifier.padding(BpkSpacing.Base)) {
        BpkTooltip(
            title = stringResource(R.string.generic_title),
            text = stringResource(R.string.generic_description),
            closeAccessibilityLabel = stringResource(R.string.navigation_close),
            state = state,
        ) { BpkButton(text = stringResource(R.string.generic_show), onClick = { scope.launch { state.show() } }) }
    }
}
