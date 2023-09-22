package net.skyscanner.backpack.compose.sponsoredbanner.internal

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.sponsoredbanner.BpkSponsoredBannerCTA
import net.skyscanner.backpack.compose.sponsoredbanner.BpkSponsoredBannerVariant
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.compose.tokens.InformationCircle
import net.skyscanner.backpack.compose.utils.clickable

@Composable
internal fun BpkSponsoredBannerImpl(
    backgroundColor: Color,
    variant: BpkSponsoredBannerVariant,
    title: String?,
    subHeadline: String?,
    callToAction: BpkSponsoredBannerCTA?,
    body: String?,
    modifier: Modifier = Modifier,
    content: @Composable (() -> Unit)?,
) {
    val showBody = !body.isNullOrBlank()
    var isBodyVisible by remember { mutableStateOf(showBody) }
    val toggleShowBody = {
        isBodyVisible = !isBodyVisible
    }

    Column(
        modifier = modifier
            .background(
                color = BpkTheme.colors.canvasContrast,
                shape = RoundedCornerShape(BpkSpacing.Md),
            ),
    ) {
        BpkSponsoredBannerHeader(
            modifier = Modifier
                .background(
                    color = backgroundColor,
                    shape = if (isBodyVisible) {
                        RoundedCornerShape(
                            topStart = BpkSpacing.Md,
                            topEnd = BpkSpacing.Md,
                            bottomStart = BpkSpacing.None,
                            bottomEnd = BpkSpacing.None,
                        )
                    } else {
                        RoundedCornerShape(BpkSpacing.Md)
                    },
                )
                .clickable(enabled = showBody, onClick = toggleShowBody),
            variant = variant,
            title = title,
            subHeadline = subHeadline,
            callToAction = callToAction,
            content = content,
        )
        if (showBody) {
            body?.let {
                AnimatedVisibility(
                    visible = isBodyVisible,
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(BpkSpacing.Base),
                    ) {
                        BpkText(
                            text = body,
                            style = BpkTheme.typography.bodyDefault,
                        )
                    }
                }
            }
        }
    }
}

@Composable
internal fun BpkSponsoredBannerHeader(
    variant: BpkSponsoredBannerVariant,
    modifier: Modifier = Modifier,
    title: String? = null,
    subHeadline: String? = null,
    callToAction: BpkSponsoredBannerCTA? = null,
    content: @Composable (() -> Unit)?,
) {
    Row(
        modifier = modifier
            .padding(horizontal = BpkSpacing.Base),
        horizontalArrangement = Arrangement.spacedBy(BpkSpacing.Md),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        content?.let {
            Box(
                modifier = Modifier
                    .padding(vertical = BpkSpacing.Base)
                    .heightIn(0.dp, 36.dp),
            ) {
                content()
            }
        }
        Column(
            modifier = Modifier
                .padding(vertical = BpkSpacing.Base)
                .weight(1f),
            verticalArrangement = Arrangement.Center,
        ) {
            if (!title.isNullOrBlank()) {
                BpkText(
                    text = title,
                    style = BpkTheme.typography.label2,
                    color = getTextColor(variant),
                )
            }
            if (!subHeadline.isNullOrBlank()) {
                BpkText(
                    text = subHeadline,
                    style = BpkTheme.typography.caption,
                    color = getTextColor(variant),
                )
            }
        }
        callToAction?.let {
            BpkText(
                text = callToAction.text,
                style = BpkTheme.typography.caption,
                color = getTextColor(variant),
            )
            BpkIcon(
                icon = BpkIcon.InformationCircle,
                contentDescription = callToAction.accessibilityLabel,
                tint = getTintColor(variant),
            )
        }
    }
}

@Composable
private fun getTextColor(variant: BpkSponsoredBannerVariant): Color =
    when (variant) {
        BpkSponsoredBannerVariant.OnLight -> BpkTheme.colors.textOnLight
        BpkSponsoredBannerVariant.OnDark -> BpkTheme.colors.textOnDark
    }

@Composable
private fun getTintColor(variant: BpkSponsoredBannerVariant): Color =
    when (variant) {
        BpkSponsoredBannerVariant.OnLight -> BpkTheme.colors.textOnLight
        BpkSponsoredBannerVariant.OnDark -> BpkTheme.colors.textOnDark
    }
