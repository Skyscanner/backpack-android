package net.skyscanner.backpack.compose.sectionheader.internal

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import net.skyscanner.backpack.compose.button.BpkButton
import net.skyscanner.backpack.compose.sectionheader.BpkSectionHeaderButton
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.compose.utils.isTablet

@Composable
fun BpkSectionHeaderImpl(
    title: String,
    description: String?,
    button: BpkSectionHeaderButton?,
    modifier: Modifier = Modifier,
) {
    val isTablet = isTablet()

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(BpkSpacing.Lg),
        verticalAlignment = Alignment.Top,
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.Top),
            horizontalAlignment = Alignment.Start,
        ) {
            BpkText(
                text = title,
                style = if (isTablet) {
                    BpkTheme.typography.heading2
                } else {
                    BpkTheme.typography.heading3
                },
                color = BpkTheme.colors.textPrimary,
            )
            if (!description.isNullOrBlank()) {
                BpkText(
                    text = description,
                    style = BpkTheme.typography.bodyDefault,
                    color = BpkTheme.colors.textPrimary,
                )
            }
        }
        button?.let {
            Row {
                if (isTablet && it.buttonText.isNotBlank()) {
                    BpkButton(
                        text = button.buttonText,
                        onClick = button.onClickAction,
                    )
                } else {
                    BpkButton(
                        icon = button.icon.bpkIcon,
                        contentDescription = button.icon.contentDescription,
                        onClick = button.onClickAction,
                    )
                }
            }
        }
    }
}
