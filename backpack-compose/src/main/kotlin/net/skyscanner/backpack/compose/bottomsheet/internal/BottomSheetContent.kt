package net.skyscanner.backpack.compose.bottomsheet.internal

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun bottomSheetContent(
    dragHandleStyle: BpkDragHandleStyle,
    content: @Composable (ColumnScope.() -> Unit),
): @Composable (ColumnScope.() -> Unit) = {
    when (dragHandleStyle) {
        BpkDragHandleStyle.Default -> content()
        is BpkDragHandleStyle.OnImage -> Box {
            Column { content() }
            BpkBottomSheetHandle(modifier = Modifier.align(Alignment.TopCenter), dragHandleStyle)
        }
    }
}
