package net.skyscanner.backpack.compose.sponsoredbanner.internal

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import net.skyscanner.backpack.compose.sponsoredbanner.CallToAction
import net.skyscanner.backpack.compose.sponsoredbanner.Variant
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.compose.tokens.InformationCircle
import net.skyscanner.backpack.compose.utils.clickable

@Composable
internal fun BpkSponsoredBannerImpl(
    backgroundColor: Color,
    variant: Variant,
    title: String?,
    subheadline: String?,
    callToAction: CallToAction?,
    body: String?,
    modifier: Modifier = Modifier,
    content: @Composable (() -> Unit)?,
) {
    var isBodyVisible by remember { mutableStateOf(false) }
    val toggleShowBody = {
        isBodyVisible = !isBodyVisible
    }
    val modifier = modifier.background(color = backgroundColor, shape = RoundedCornerShape(BpkSpacing.Md))

    @Composable
    fun header(modifier: Modifier = Modifier, onClick: (() -> Unit)? = null) = BpkSponsoredBannerHeader(
        variant = variant,
        onClick = onClick,
        modifier = modifier,
        title = title,
        subheadline = subheadline,
        callToAction = callToAction,
        content = content,
    )

    if (body.isNullOrBlank()) {
        header(modifier = modifier)
    } else {
        Column(
            modifier = modifier,
        ) {
            header(onClick = toggleShowBody)
            AnimatedVisibility(
                visible = isBodyVisible,
            ) {
                Box(
                    modifier = Modifier
                        .background(
                            color = BpkTheme.colors.canvasContrast,
                            shape = RoundedCornerShape(
                                topStart = BpkSpacing.None,
                                topEnd = BpkSpacing.None,
                                bottomStart = BpkSpacing.Md,
                                bottomEnd = BpkSpacing.Md,
                            ),
                        )
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

@Composable
private fun BpkSponsoredBannerHeader(
    variant: Variant,
    onClick: (() -> Unit)?,
    modifier: Modifier = Modifier,
    title: String? = null,
    subheadline: String? = null,
    callToAction: CallToAction? = null,
    content: @Composable (() -> Unit)?,
) {
    Row(
        modifier = modifier
            .padding(BpkSpacing.Base)
//            .let { if (onClick != null) it.clickable(onClick = onClick) else it },
            .clickable { onClick },
        horizontalArrangement = Arrangement.spacedBy(BpkSpacing.Md),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        content?.let {
            Box(
                modifier = Modifier.heightIn(max = 22.dp),
            ) {
                content()
            }
            Spacer(modifier = Modifier.size(BpkSpacing.Md))
        }
        Column(
            modifier = Modifier.weight(1f).fillMaxWidth(),
        ) {
            if (!title.isNullOrBlank()) {
                BpkText(
                    text = title,
                    style = BpkTheme.typography.label2,
                    color = if (variant == Variant.OnDark) BpkTheme.colors.textOnDark else BpkTheme.colors.textOnLight,
                )
            }
            if (!subheadline.isNullOrBlank()) {
                BpkText(
                    text = subheadline,
                    style = BpkTheme.typography.caption,
                    color = if (variant == Variant.OnDark) BpkTheme.colors.textOnDark else BpkTheme.colors.textOnLight,
                )
            }
        }
//        Spacer(modifier = Modifier.weight(1f))
        callToAction?.let {
            BpkText(
                text = callToAction.text,
                style = BpkTheme.typography.caption,
                color = if (variant == Variant.OnDark) BpkTheme.colors.textOnDark else BpkTheme.colors.textOnLight,
            )
            BpkIcon(
                icon = BpkIcon.InformationCircle,
                contentDescription = callToAction.accessibilityLabel,
                tint = if (variant == Variant.OnDark) BpkTheme.colors.textOnDark else BpkTheme.colors.textOnLight,
            )
        }
    }
}
