import androidx.compose.runtime.Composable
import com.figma.code.connect.FigmaConnect
import com.figma.code.connect.FigmaVariant
import net.skyscanner.backpack.compose.dividedcard.BpkDividedCard

@FigmaConnect("FIGMA_DIVIDED_CARD")
@FigmaVariant(key = "Size", value = "Mobile")
public class BpkDividedCardDoc {

    @Composable
    public fun ComponentExample() {
        BpkDividedCard(
            primaryContent = {
                // Primary content goes here
            },
            secondaryContent = {
                // Secondary content goes here
            },
            onClick = {},

        )
    }
}
