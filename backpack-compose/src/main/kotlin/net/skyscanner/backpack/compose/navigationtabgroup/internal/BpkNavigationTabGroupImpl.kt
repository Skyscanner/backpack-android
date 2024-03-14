package net.skyscanner.backpack.compose.navigationtabgroup.internal

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import net.skyscanner.backpack.compose.navigationtabgroup.BpkNavigationTabGroupStyle
import net.skyscanner.backpack.compose.navigationtabgroup.BpkNavigationTabItem
import net.skyscanner.backpack.compose.tokens.BpkSpacing

@Composable
internal fun BpkNavigationTabGroupImpl(
    tabs: List<BpkNavigationTabItem>,
    selectedIndex: Int,
    onItemClicked: (BpkNavigationTabItem) -> Unit,
    style: BpkNavigationTabGroupStyle,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    LazyRow(
        modifier = modifier.selectableGroup(),
        contentPadding = contentPadding,
        state = rememberLazyListState(),
        horizontalArrangement = Arrangement.spacedBy(BpkSpacing.Sm),
    ) {
        itemsIndexed(items = tabs) { index, tab ->
            NavigationTabItem(
                tab = tab,
                selected = index == selectedIndex,
                style = style,
            ) {
                onItemClicked.invoke(tabs[index])
            }
        }
    }
}

@Composable
private fun NavigationTabItem(
    tab: BpkNavigationTabItem,
    selected: Boolean,
    style: BpkNavigationTabGroupStyle,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    val tabStyle = when (style) {
        BpkNavigationTabGroupStyle.SurfaceContrast -> BpkNavigationTabStyle.SurfaceContrast
        BpkNavigationTabGroupStyle.CanvasDefault -> BpkNavigationTabStyle.CanvasDefault
    }
    BpkNavigationTab(
        modifier = modifier.semantics { role = Role.RadioButton },
        text = tab.text,
        icon = tab.icon,
        selected = selected,
        style = tabStyle,
        onClick = onClick,
    )
}
