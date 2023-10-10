package net.skyscanner.backpack.compose.appsearchmodal.internal

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import net.skyscanner.backpack.compose.appsearchmodal.BpkAppSearchModalResult
import net.skyscanner.backpack.compose.tokens.BpkSpacing

@Composable
internal fun BpkSearchModalContent(
    results: BpkAppSearchModalResult.Content,
    modifier: Modifier = Modifier,
) {
    LazyColumn(modifier = modifier) {
        results.shortcuts?.let {
            item {
                BpkShortcuts(it)
            }
        }
        results.sections.forEach { section ->
            section.headings?.let {
                item {
                    BpkSectionHeading(
                        sectionHeading = it,
                        modifier = Modifier
                            .padding(top = BpkSpacing.Base)
                            .padding(horizontal = BpkSpacing.Base),
                    )
                }
            }
            items(section.items) {
                BpkSectionItem(
                    item = it,
                    modifier = Modifier.padding(BpkSpacing.Base),
                )
            }
        }
    }
}
