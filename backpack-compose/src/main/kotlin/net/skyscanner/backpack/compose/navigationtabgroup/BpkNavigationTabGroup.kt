package net.skyscanner.backpack.compose.navigationtabgroup

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.navigationtabgroup.internal.BpkNavigationTabGroupImpl

data class BpkNavigationTabItem(val text: String, val icon: BpkIcon? = null)

enum class BpkNavigationTabGroupStyle {
    CanvasDefault,
    SurfaceContrast,
}

@Composable
fun BpkNavigationTabGroup(
    tabs: List<BpkNavigationTabItem>,
    selectedIndex: Int,
    onItemClicked: (BpkNavigationTabItem) -> Unit,
    modifier: Modifier = Modifier,
    style: BpkNavigationTabGroupStyle = BpkNavigationTabGroupStyle.CanvasDefault,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    BpkNavigationTabGroupImpl(
        tabs = tabs,
        selectedIndex = selectedIndex,
        onItemClicked = onItemClicked,
        modifier = modifier,
        style = style,
        contentPadding = contentPadding,
    )
}
