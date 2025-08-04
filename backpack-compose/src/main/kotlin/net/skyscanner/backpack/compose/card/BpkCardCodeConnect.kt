package net.skyscanner.backpack.compose.card

import androidx.compose.runtime.Composable
import com.figma.code.connect.FigmaConnect

@FigmaConnect(url = "https://www.figma.com/design/irZ3YBx8vOm16ICkAr7mB3/Backpack-Components?m=auto&node-id=4395-2506")
class BpkCardCodeConnect {

    @Composable
    fun CardExample() {
        BpkCard(
            onClick = {
                // Handle card click
            },
            content = {
                // Add your card content here
            },
        )
    }
}
