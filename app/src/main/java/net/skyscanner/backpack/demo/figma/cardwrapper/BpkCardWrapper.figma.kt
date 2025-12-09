import androidx.compose.runtime.Composable
import com.figma.code.connect.FigmaConnect
import net.skyscanner.backpack.compose.card.BpkCardCorner
import net.skyscanner.backpack.compose.card.BpkCardElevation
import net.skyscanner.backpack.compose.card.BpkCardPadding
import net.skyscanner.backpack.compose.card.BpkCardStyle
import net.skyscanner.backpack.compose.cardwrapper.BpkCardWrapper
import net.skyscanner.backpack.compose.theme.BpkTheme

@FigmaConnect("FIGMA_WRAPPER_CARD")
public class BpkCardWrapperDoc {
    @Composable
    public fun ComponentExample() {
        BpkCardWrapper(
            backgroundColor = BpkTheme.colors.coreAccent,
            headerContent = {
                // Header content goes here
            },
            cardContent = {
                // Card content goes here
            },
            cardPadding = BpkCardPadding.None, // example padding
            corner = BpkCardCorner.Small, // example corner
            cardStyle = BpkCardStyle.onDefault, // example style
            elevation = BpkCardElevation.None, // example elevation

        )
    }
}
