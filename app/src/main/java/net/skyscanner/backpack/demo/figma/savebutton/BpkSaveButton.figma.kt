import androidx.compose.runtime.Composable
import com.figma.code.connect.Figma
import com.figma.code.connect.FigmaConnect
import com.figma.code.connect.FigmaProperty
import com.figma.code.connect.FigmaType
import net.skyscanner.backpack.compose.cardbutton.BpkCardButtonSize
import net.skyscanner.backpack.compose.cardbutton.BpkCardButtonStyle
import net.skyscanner.backpack.compose.cardbutton.BpkSaveButton

@FigmaConnect("FIGMA_SAVE_BUTTON")
public class BpkSaveButtonDoc {
    @FigmaProperty(FigmaType.Enum, "Style")
    public val style: BpkCardButtonStyle = Figma.mapping(
        "Default" to BpkCardButtonStyle.Default,
        "Contained" to BpkCardButtonStyle.Contained,
        "On Dark" to BpkCardButtonStyle.OnDark,
    )

    @FigmaProperty(FigmaType.Enum, "Size")
    public val size: BpkCardButtonSize = Figma.mapping(
        "Default" to BpkCardButtonSize.Default,
        "Small" to BpkCardButtonSize.Small,
    )

    @FigmaProperty(FigmaType.Enum, "State")
    public val checked: Boolean = Figma.mapping(
        "Default" to false,
        "Unsaved" to false,
        "Saved" to true,
        "Transition" to true,
        "Hover" to false,
    )

    @Composable
    public fun ComponentExample() {
        BpkSaveButton(
            checked = checked,
            contentDescription = "Save", // Example content description for accessibility
            onCheckedChange = {},
            size = BpkCardButtonSize.Default,
            style = BpkCardButtonStyle.Default,

        )
    }
}
