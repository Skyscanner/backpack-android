package net.skyscanner.backpack.compose.appsearchmodal.internal

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import net.skyscanner.backpack.compose.appsearchmodal.Item
import net.skyscanner.backpack.compose.appsearchmodal.Section
import net.skyscanner.backpack.compose.appsearchmodal.SectionHeading
import net.skyscanner.backpack.compose.button.BpkButton
import net.skyscanner.backpack.compose.button.BpkButtonType
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.icon.BpkIconSize
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.compose.utils.clickable

@Composable
internal fun BpkSection(
    section: Section,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
    ) {
        section.headings?.let {
            BpkSectionHeading(
                sectionHeading = it,
            )
        }
        section.items.forEach { item ->
            BpkSectionItem(item = item)
        }
    }
}

@Composable
internal fun BpkSectionHeading(
    sectionHeading: SectionHeading,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        BpkText(
            text = sectionHeading.title,
            style = BpkTheme.typography.label1,
        )
        sectionHeading.action?.let {
            BpkButton(
                type = BpkButtonType.Link,
                text = it.text,
                onClick = it.onActionSelected,
            )
        }
    }
}

@Composable
internal fun BpkSectionItem(item: Item, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = item.onItemSelected)
            .padding(vertical = BpkSpacing.Base),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(BpkSpacing.Base),
    ) {
        BpkIcon(
            icon = item.icon,
            contentDescription = null,
            size = BpkIconSize.Large,

        )
        Column {
            BpkText(text = item.title)
            BpkText(text = item.subTitle)
        }
    }
}
