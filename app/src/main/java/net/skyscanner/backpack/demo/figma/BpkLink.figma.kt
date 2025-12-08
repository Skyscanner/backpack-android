import androidx.compose.runtime.Composable
import com.figma.code.connect.Figma
import com.figma.code.connect.FigmaConnect
import com.figma.code.connect.FigmaProperty
import com.figma.code.connect.FigmaType
import net.skyscanner.backpack.compose.link.BpkLink
import net.skyscanner.backpack.compose.link.BpkLinkStyle
import kotlin.String

@FigmaConnect("https://www.figma.com/design/KXf2gHNLDe2cXWUoHl4cTX/Backpack%E2%80%A8Foundations---Components/?node-id=10885-9743")
public class BpkLinkDoc {
    @FigmaProperty(FigmaType.Text, "Text")
    public val text: String = "Link"

    @FigmaProperty(FigmaType.Enum, "Style")
    public val style: BpkLinkStyle = Figma.mapping(
        "Default" to BpkLinkStyle.Default,
        "On Contrast" to BpkLinkStyle.OnContrast,
    )

    @Composable
    public fun ComponentExample() {
        BpkLink(
            text = text,
            style = style,
            onLinkClicked = { /* Handle link click */ },
        )
    }
}
