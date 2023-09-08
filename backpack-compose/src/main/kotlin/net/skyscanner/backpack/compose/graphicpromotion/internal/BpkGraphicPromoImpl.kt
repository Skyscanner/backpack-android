package net.skyscanner.backpack.compose.graphicpromotion.internal

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import net.skyscanner.backpack.compose.graphicpromotion.Sponsor
import net.skyscanner.backpack.compose.graphicpromotion.Variant
import net.skyscanner.backpack.compose.graphicpromotion.VerticalAlignment
import net.skyscanner.backpack.compose.overlay.BpkOverlay
import net.skyscanner.backpack.compose.overlay.BpkOverlayType
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkBorderRadius
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.compose.utils.clickable

@Composable
internal fun BpkGraphicPromoImpl(
    headline: String,
    image: @Composable BoxScope.() -> Unit,
    modifier: Modifier = Modifier,
    kicker: String? = null,
    subHeadline: String? = null,
    overlayType: BpkOverlayType? = null,
    variant: Variant = Variant.OnDark,
    verticalAlignment: VerticalAlignment = VerticalAlignment.Top,
    sponsor: Sponsor? = null,
    sponsorLogo: @Composable () -> Unit? = {},
    tapAction: () -> Unit = {},
) {
    val roundedCornerShape = RoundedCornerShape(BpkBorderRadius.Sm)
    val contentDescription = listOfNotNull(kicker, headline, subHeadline, sponsor?.accessibilityLabel)
        .joinToString(separator = ", ")

    Box(
        modifier = modifier
            .aspectRatio(RATIO_PORTRAIT)
            .clip(roundedCornerShape)
            .clickable { tapAction() }
            .semantics(mergeDescendants = true) {
                role = Role.Button
                this.contentDescription = contentDescription
            },
    ) {
        overlayType?.let {
            BpkOverlay(
                modifier = Modifier.fillMaxSize(),
                overlayType = it,
                foregroundContent = {
                    ForegroundContent(
                        headline = headline,
                        kicker = kicker,
                        subHeadline = subHeadline,
                        variant = variant,
                        verticalAlignment = verticalAlignment,
                        sponsor = sponsor,
                        sponsorLogo = sponsorLogo,
                    )
                },
            ) {
                image()
            }
        } ?: run {
            image()
            ForegroundContent(
                headline = headline,
                kicker = kicker,
                subHeadline = subHeadline,
                variant = variant,
                verticalAlignment = verticalAlignment,
                sponsor = sponsor,
                sponsorLogo = sponsorLogo,
            )
        }
    }
}

@Composable
private fun SponsorOverlayView(
    textColor: Color,
    sponsor: Sponsor?,
    sponsorLogo: @Composable () -> Unit? = {},
) {
    if (sponsor != null) {
        BpkText(
            text = sponsor.title,
            style = BpkTheme.typography.heading5,
            color = textColor,
        )

        Box(
            modifier = Modifier
                .heightIn(0.dp, SPONSOR_LOGO_HEIGHT.dp),
        ) {
            sponsorLogo()
        }
    }
}

@Composable
private fun MessageOverlay(
    headline: String,
    kicker: String?,
    subHeadline: String?,
    textColor: Color,
) {
    if (!kicker.isNullOrBlank()) {
        BpkText(
            text = kicker,
            style = BpkTheme.typography.label1,
            color = textColor,
        )
    }

    BpkText(
        text = headline,
        style = BpkTheme.typography.heading2,
        color = textColor,
    )
    if (!subHeadline.isNullOrBlank()) {
        BpkText(
            text = subHeadline,
            style = BpkTheme.typography.heading5,
            color = textColor,
        )
    }
}

@Composable
private fun ForegroundContent(
    headline: String,
    kicker: String? = null,
    subHeadline: String? = null,
    variant: Variant = Variant.OnDark,
    verticalAlignment: VerticalAlignment = VerticalAlignment.Top,
    sponsor: Sponsor? = null,
    sponsorLogo: @Composable () -> Unit? = {},
) {
    val textColor: Color = when (variant) {
        Variant.OnDark -> BpkTheme.colors.textOnDark
        Variant.OnLight -> BpkTheme.colors.textOnLight
    }

    Column(modifier = Modifier.padding(BpkSpacing.Lg)) {
        when (verticalAlignment) {
            VerticalAlignment.Top -> {
                MessageOverlay(
                    headline = headline,
                    kicker = kicker,
                    subHeadline = subHeadline,
                    textColor = textColor,
                )

                Spacer(Modifier.weight(1f))

                SponsorOverlayView(
                    sponsor = sponsor,
                    textColor = textColor,
                    sponsorLogo = sponsorLogo,
                )
            }
            VerticalAlignment.Bottom -> {
                SponsorOverlayView(
                    sponsor = sponsor,
                    textColor = textColor,
                    sponsorLogo = sponsorLogo,
                )

                Spacer(Modifier.weight(1f))

                MessageOverlay(
                    headline = headline,
                    kicker = kicker,
                    subHeadline = subHeadline,
                    textColor = textColor,
                )
            }
        }
    }
}

const val RATIO_PORTRAIT: Float = 3 / 4f
const val SPONSOR_LOGO_HEIGHT = 60
