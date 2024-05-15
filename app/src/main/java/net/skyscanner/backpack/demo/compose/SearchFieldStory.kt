package net.skyscanner.backpack.demo.compose

import SearchField
import SearchTextFieldShape
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import net.skyscanner.backpack.compose.textfield.BpkClearAction
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.demo.components.SearchFieldComponent
import net.skyscanner.backpack.demo.meta.ComposeStory

@Composable
@SearchFieldComponent
@ComposeStory
fun SearchFieldStory(
    modifier: Modifier = Modifier,
) {
    Column(
        Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
    ) {
        SearchFieldStoryInner(onContrast = false, Modifier.fillMaxWidth())
        Column(
            Modifier
                .background(BpkTheme.colors.surfaceContrast)
                .fillMaxWidth()
                .padding(vertical = BpkSpacing.Base),
        ) {
            SearchFieldStoryInner(onContrast = true, Modifier.fillMaxWidth())
        }
    }
}

@Composable
fun SearchFieldStoryInner(
    onContrast: Boolean,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier
            .padding(BpkSpacing.Base),
        verticalArrangement = Arrangement.spacedBy(BpkSpacing.Sm),
    ) {
        SearchField(
            value = "Top - Input", onValueChange = {},
            modifier = Modifier.fillMaxWidth(),
            clearAction = BpkClearAction(stringResource(id = R.string.text_field_clear_action_description)) {},
            shape = SearchTextFieldShape.Top,
            onContrast = onContrast,
        )
        SearchField(
            value = "Middle - Input", onValueChange = {},
            modifier = Modifier.fillMaxWidth(),
            clearAction = BpkClearAction(stringResource(id = R.string.text_field_clear_action_description)) {},
            shape = SearchTextFieldShape.Middle,
            onContrast = onContrast,
        )
        SearchField(
            value = "Bottom - Input", onValueChange = {},
            modifier = Modifier.fillMaxWidth(),
            clearAction = BpkClearAction(stringResource(id = R.string.text_field_clear_action_description)) {},
            shape = SearchTextFieldShape.Bottom,
            onContrast = onContrast,
        )
        Spacer(modifier = Modifier.height(BpkSpacing.Base))
        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {

            SearchField(
                value = "Top Left -Input", onValueChange = {},
                modifier = Modifier.weight(1f),
                clearAction = BpkClearAction(stringResource(id = R.string.text_field_clear_action_description)) {},
                shape = SearchTextFieldShape.TopLeft,
                onContrast = onContrast,
            )
            Spacer(modifier = Modifier.width(BpkSpacing.Sm))
            SearchField(
                value = "Top Right - Input", onValueChange = {},
                modifier = Modifier.weight(1f),
                clearAction = BpkClearAction(stringResource(id = R.string.text_field_clear_action_description)) {},
                shape = SearchTextFieldShape.TopRight,
                onContrast = onContrast,
            )
        }
        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
            SearchField(
                value = "Bottom Left - Input", onValueChange = {},
                modifier = Modifier.weight(1f),
                clearAction = BpkClearAction(stringResource(id = R.string.text_field_clear_action_description)) {},
                shape = SearchTextFieldShape.BottomLeft,
                onContrast = onContrast,
            )
            Spacer(modifier = Modifier.width(BpkSpacing.Sm))
            SearchField(
                value = "Bottom Right - Input", onValueChange = {},
                modifier = Modifier.weight(1f),
                clearAction = BpkClearAction(stringResource(id = R.string.text_field_clear_action_description)) {},
                shape = SearchTextFieldShape.BottomRight,
                onContrast = onContrast,
            )
        }
        Spacer(modifier = Modifier.height(BpkSpacing.Base))
        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
            SearchField(
                value = "Left - Input", onValueChange = {},
                modifier = Modifier.weight(1f),
                clearAction = BpkClearAction(stringResource(id = R.string.text_field_clear_action_description)) {},
                shape = SearchTextFieldShape.Left,
                onContrast = onContrast,
            )
            Spacer(modifier = Modifier.width(BpkSpacing.Sm))
            SearchField(
                value = "Right - Input", onValueChange = {},
                modifier = Modifier.weight(1f),
                clearAction = BpkClearAction(stringResource(id = R.string.text_field_clear_action_description)) {},
                shape = SearchTextFieldShape.Right,
                onContrast = onContrast,
            )
        }
        Spacer(modifier = Modifier.height(BpkSpacing.Base))
        SearchField(
            value = "Float - Input", onValueChange = {},
            modifier = Modifier.fillMaxWidth(),
            clearAction = BpkClearAction(stringResource(id = R.string.text_field_clear_action_description)) {},
            shape = SearchTextFieldShape.Float,
            onContrast = onContrast,
        )
    }
}
