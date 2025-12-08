import androidx.compose.runtime.Composable
import com.figma.code.connect.Figma
import com.figma.code.connect.FigmaConnect
import com.figma.code.connect.FigmaProperty
import com.figma.code.connect.FigmaType
import com.figma.code.connect.FigmaVariant
import net.skyscanner.backpack.compose.graphicpromotion.BpkGraphicPromo
import net.skyscanner.backpack.compose.graphicpromotion.BpkGraphicPromoVerticalAlignment
import net.skyscanner.backpack.compose.graphicpromotion.BpkGraphicsPromoSponsor
import net.skyscanner.backpack.compose.overlay.BpkOverlayType

@FigmaConnect("https://www.figma.com/design/KXf2gHNLDe2cXWUoHl4cTX/Backpack%E2%80%A8Foundations---Components/?node-id=10872-2409")
@FigmaVariant(key = "Size", value = "Mobile")
public class BpkGraphicPromoDoc {
    @FigmaProperty(FigmaType.Boolean, "Overlay?")
    public val overlay: BpkOverlayType? = Figma.mapping(
        true to BpkOverlayType.BottomLow, // Example overlay type
        false to null,
    )

    @FigmaProperty(FigmaType.Boolean, "Kicker?")
    public val kicker: String? = Figma.mapping(
        true to "Kicker",
        false to null,
    )

    @FigmaProperty(FigmaType.Enum, "Type")
    public val sponsored: BpkGraphicsPromoSponsor? = Figma.mapping(
        "Default" to null,
        "Sponsored" to BpkGraphicsPromoSponsor(
            title = "Sponsored by Skyscanner",
            logo = "https://image.skyscanner.net/test-logo.png",
            accessibilityLabel = "Skyscanner logo",
        ),
    )

    @FigmaProperty(FigmaType.Enum, "Alignment")
    public val alignment: BpkGraphicPromoVerticalAlignment = Figma.mapping(
        "Top" to BpkGraphicPromoVerticalAlignment.Top,
        "Bottom" to BpkGraphicPromoVerticalAlignment.Bottom,
    )

    @Composable
    public fun ComponentExample() {
        BpkGraphicPromo(
            headline = "Three Peaks Challenge",
            image = {
                // Placeholder for image content
            },
            kicker = kicker,
            subHeadline = "How to complete the climb in 3 days",
            overlayType = overlay,
            verticalAlignment = alignment,
            sponsor = sponsored,
            tapAction = { /* Handle tap action */ },

        )
    }
}
