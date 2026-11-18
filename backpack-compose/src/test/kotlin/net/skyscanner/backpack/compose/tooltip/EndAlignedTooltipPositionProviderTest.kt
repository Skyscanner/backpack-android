package net.skyscanner.backpack.compose.tooltip

import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntRect
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.window.PopupPositionProvider
import org.junit.Assert.assertEquals
import org.junit.Test

class EndAlignedTooltipPositionProviderTest {
    private val provider = EndAlignedTooltipPositionProvider(object : PopupPositionProvider {
        override fun calculatePosition(
            anchorBounds: IntRect,
            windowSize: IntSize,
            layoutDirection: LayoutDirection,
            popupContentSize: IntSize,
        ) = IntOffset(0, 150)
    })

    @Test
    fun `given a wide anchor when positioning below end then popup follows reading direction`() {
        val anchor = IntRect(24, 50, 376, 100)
        val window = IntSize(400, 800)
        val popup = IntSize(200, 100)
        assertEquals(IntOffset(176, 150), provider.calculatePosition(anchor, window, LayoutDirection.Ltr, popup))
        assertEquals(IntOffset(24, 150), provider.calculatePosition(anchor, window, LayoutDirection.Rtl, popup))
    }

    @Test
    fun `given popup extends past window when positioning then horizontal bounds stay visible`() {
        val window = IntSize(400, 800)
        val popup = IntSize(300, 100)
        assertEquals(IntOffset(0, 150), provider.calculatePosition(IntRect(0, 0, 100, 50), window, LayoutDirection.Ltr, popup))
        assertEquals(IntOffset(100, 150), provider.calculatePosition(IntRect(350, 0, 400, 50), window, LayoutDirection.Rtl, popup))
    }
}
