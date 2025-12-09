import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import com.figma.code.connect.Figma
import com.figma.code.connect.FigmaConnect
import com.figma.code.connect.FigmaProperty
import com.figma.code.connect.FigmaType
import net.skyscanner.backpack.compose.panel.BpkPanel
import net.skyscanner.backpack.compose.panel.BpkPanelPadding

@FigmaConnect("FIGMA_PANEL")
public class BpkPanelDoc {
    @FigmaProperty(FigmaType.Enum, "Keyline?")
    public val propagateMinConstraints: Boolean = Figma.mapping(
        "Yes" to true,
        "No" to false,
    )

    @Composable
    public fun ComponentExample() {
        BpkPanel(
            propagateMinConstraints = propagateMinConstraints, // example usage of the property
            padding = BpkPanelPadding.Base, // example padding
            contentAlignment = Alignment.TopStart, // example alignment
            content = {
                // Content goes here
            },
        )
    }
}
