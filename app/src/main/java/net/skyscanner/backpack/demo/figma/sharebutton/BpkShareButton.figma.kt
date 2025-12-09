import androidx.compose.runtime.Composable
import com.figma.code.connect.Figma
import com.figma.code.connect.FigmaConnect
import com.figma.code.connect.FigmaProperty
import com.figma.code.connect.FigmaType
import net.skyscanner.backpack.compose.cardbutton.BpkCardButtonSize
import net.skyscanner.backpack.compose.cardbutton.BpkCardButtonStyle
import net.skyscanner.backpack.compose.cardbutton.BpkShareButton

@FigmaConnect("FIGMA_SHARE_BUTTON")
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

    @Composable
    public fun ComponentExample() {
        BpkShareButton(
            contentDescription = "Share", // Example content description for accessibility
            size = BpkCardButtonSize.Default,
            style = BpkCardButtonStyle.Default,
            onClick = {},
        )
    }
}
