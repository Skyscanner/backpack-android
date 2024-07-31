package net.skyscanner.backpack.compose.banneralert.internal

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import net.skyscanner.backpack.compose.banneralert.BPKBannerAlertStyle
import net.skyscanner.backpack.compose.banneralert.BPKBannerAlertType
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkBorderRadius
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.compose.tokens.CloseCircle
import net.skyscanner.backpack.compose.tokens.ExclamationCircle
import net.skyscanner.backpack.compose.tokens.InformationCircle
import net.skyscanner.backpack.compose.tokens.TickCircle

@Composable
internal fun BannerAlert(
    type: BPKBannerAlertType,
    message: String,
    style: BPKBannerAlertStyle,
    modifier: Modifier = Modifier,
    icon: BpkIcon? = null,
    iconContentDescription: String? = null,
) {

    val background = when (style) {
        BPKBannerAlertStyle.Default -> BpkTheme.colors.canvasContrast
        BPKBannerAlertStyle.OnContrast -> BpkTheme.colors.canvas
    }

    val iconFinal = icon ?: when (type) {
        BPKBannerAlertType.Info -> BpkIcon.InformationCircle
        BPKBannerAlertType.Success -> BpkIcon.TickCircle
        BPKBannerAlertType.Warning -> BpkIcon.ExclamationCircle
        BPKBannerAlertType.Error -> BpkIcon.CloseCircle
    }

    val tint = when (type) {
        BPKBannerAlertType.Info -> BpkTheme.colors.textSecondary
        BPKBannerAlertType.Success -> BpkTheme.colors.statusSuccessSpot
        BPKBannerAlertType.Warning -> BpkTheme.colors.statusWarningSpot
        BPKBannerAlertType.Error -> BpkTheme.colors.statusDangerSpot
    }

    Row(
        modifier = modifier
            .background(background)
            .padding(horizontal = BpkSpacing.Base, vertical = BpkSpacing.Md)
            .clip(RoundedCornerShape(BpkBorderRadius.Sm)),
    ) {
        BpkIcon(
            modifier = Modifier.padding(horizontal = BpkSpacing.Md),
            icon = iconFinal,
            tint = tint,
            contentDescription = iconContentDescription,
        )
        BpkText(
            text = message,
            textAlign = TextAlign.Start,
            color = BpkTheme.colors.textPrimary,
        )
    }
}

@Preview
@Composable
private fun BannerAlertPreviewOnContrast(
    @PreviewParameter(BannerAlertPreviewParamsProvider::class) preview: Pair<BPKBannerAlertType, BPKBannerAlertStyle>,
) {
    BannerAlert(
        type = preview.first,
        message = """Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt laborum.""",
        icon = null,
        style = preview.second,
    )
}

private class BannerAlertPreviewParamsProvider : PreviewParameterProvider<Pair<BPKBannerAlertType, BPKBannerAlertStyle>> {
    override val values: Sequence<Pair<BPKBannerAlertType, BPKBannerAlertStyle>>
        get() = sequenceOf(
            Pair(BPKBannerAlertType.Info, BPKBannerAlertStyle.Default),
            Pair(BPKBannerAlertType.Info, BPKBannerAlertStyle.OnContrast),

            Pair(BPKBannerAlertType.Success, BPKBannerAlertStyle.Default),
            Pair(BPKBannerAlertType.Success, BPKBannerAlertStyle.OnContrast),

            Pair(BPKBannerAlertType.Warning, BPKBannerAlertStyle.Default),
            Pair(BPKBannerAlertType.Warning, BPKBannerAlertStyle.OnContrast),

            Pair(BPKBannerAlertType.Error, BPKBannerAlertStyle.Default),
            Pair(BPKBannerAlertType.Error, BPKBannerAlertStyle.OnContrast),
        )
}
