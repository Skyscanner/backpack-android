import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.figma.code.connect.FigmaConnect
import net.skyscanner.backpack.compose.carousel.BpkCarousel
import net.skyscanner.backpack.compose.carousel.rememberBpkCarouselState
import net.skyscanner.backpack.compose.tokens.BpkSpacing

@FigmaConnect("https://www.figma.com/design/KXf2gHNLDe2cXWUoHl4cTX/Backpack%E2%80%A8Foundations---Components/?node-id=10858-50288")
public class BpkCarouselDoc {
    @Composable
    public fun ComponentExample() {
        val pagerState = rememberBpkCarouselState(
            totalImages = 5, // number of images in the carousel
            initialImage = 0, // starting image index
        )
        BpkCarousel(
            modifier = Modifier
                .aspectRatio(1.9f)
                .padding(vertical = BpkSpacing.Base),
            state = pagerState,
        ) {
            // content goes here, could be glide image, painterResource, depending on source of images
        }
    }
}
