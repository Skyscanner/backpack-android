import androidx.compose.runtime.Composable
import com.figma.code.connect.FigmaConnect
import com.figma.code.connect.FigmaVariant
import net.skyscanner.backpack.compose.dividedcard.BpkDividedCard

@FigmaConnect("https://www.figma.com/design/KXf2gHNLDe2cXWUoHl4cTX/Backpack%E2%80%A8Foundations---Components/?node-id=10858-49338")
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
