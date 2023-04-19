package net.skyscanner.backpack.compose.bottomnav.internal

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.layout.LastBaseline
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.MeasureResult
import androidx.compose.ui.layout.MeasureScope
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import net.skyscanner.backpack.compose.LocalContentColor
import net.skyscanner.backpack.compose.theme.BpkTheme
import kotlin.math.max

@Composable
internal fun BottomNavigation(
    elevation: Dp,
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit,
) {
    Surface(
        color = BpkTheme.colors.surfaceDefault,
        contentColor = BpkTheme.colors.textSecondary,
        shadowElevation = elevation,
        modifier = modifier,
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .height(BottomNavigationHeight)
                .selectableGroup(),
            horizontalArrangement = Arrangement.SpaceBetween,
            content = content,
        )
    }
}

@Composable
internal fun RowScope.BottomNavigationItem(
    selected: Boolean,
    onClick: () -> Unit,
    icon: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    label: @Composable () -> Unit,
) {
    // The color of the Ripple should always the selected color, as we want to show the color
    // before the item is considered selected, and hence before the new contentColor is
    // provided by BottomNavigationTransition.
    val ripple = rememberRipple(bounded = false, color = BpkTheme.colors.textLink)

    Box(
        modifier
            .selectable(
                selected = selected,
                onClick = onClick,
                role = Role.Tab,
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple,
            )
            .weight(1f),
        contentAlignment = Alignment.Center,
    ) {

        val color = lerp(
            start = BpkTheme.colors.textSecondary,
            stop = BpkTheme.colors.textLink,
            fraction = animateFloatAsState(
                targetValue = if (selected) 1f else 0f,
                animationSpec = BottomNavigationAnimationSpec,
            ).value,
        )

        CompositionLocalProvider(
            LocalContentColor provides color,
        ) {
            BottomNavigationItemBaselineLayout(
                icon = icon,
                label = label,
            )
        }
    }
}

@Composable
private fun BottomNavigationItemBaselineLayout(
    icon: @Composable () -> Unit,
    label: @Composable (() -> Unit),
) {
    Layout(
        {
            Box(Modifier.layoutId("icon")) { icon() }
            Box(
                Modifier
                    .layoutId("label")
                    .padding(horizontal = BottomNavigationItemHorizontalPadding),
            ) { label() }
        },
    ) { measurables, constraints ->
        val iconPlaceable = measurables.first { it.layoutId == "icon" }.measure(constraints)

        val labelPlaceable = measurables.first { it.layoutId == "label" }.measure(
            // Measure with loose constraints for height as we don't want the label to take up more
            // space than it needs
            constraints.copy(minHeight = 0),
        )

        // If there is no label, just place the icon.
        placeLabelAndIcon(
            labelPlaceable,
            iconPlaceable,
            constraints,
        )
    }
}

private fun MeasureScope.placeLabelAndIcon(
    labelPlaceable: Placeable,
    iconPlaceable: Placeable,
    constraints: Constraints,
): MeasureResult {
    val height = constraints.maxHeight

    // TODO: consider multiple lines of text here, not really supported by spec but we should
    // have a better strategy than overlapping the icon and label
    val baseline = labelPlaceable[LastBaseline]

    val baselineOffset = CombinedItemTextBaseline.roundToPx()

    // Label should be [baselineOffset] from the bottom
    val labelY = height - baseline - baselineOffset

    // Icon should be [baselineOffset] from the text baseline, which is itself
    // [baselineOffset] from the bottom
    val selectedIconY = height - (baselineOffset * 2) - iconPlaceable.height

    val containerWidth = max(labelPlaceable.width, iconPlaceable.width)

    val labelX = (containerWidth - labelPlaceable.width) / 2
    val iconX = (containerWidth - iconPlaceable.width) / 2

    return layout(containerWidth, height) {
        labelPlaceable.placeRelative(labelX, labelY)
        iconPlaceable.placeRelative(iconX, selectedIconY)
    }
}

private val BottomNavigationAnimationSpec = TweenSpec<Float>(
    durationMillis = 300,
    easing = FastOutSlowInEasing,
)

private val BottomNavigationHeight = 56.dp
private val BottomNavigationItemHorizontalPadding = 12.dp
private val CombinedItemTextBaseline = 12.dp
