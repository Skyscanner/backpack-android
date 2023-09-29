package net.skyscanner.backpack.compose.slider.internal

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.progressSemantics
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.SliderDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.disabled
import androidx.compose.ui.semantics.invisibleToUser
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.setProgress
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.util.lerp
import net.skyscanner.backpack.compose.R
import net.skyscanner.backpack.compose.flare.BpkFlarePointerDirection
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkBorderRadius
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.compose.utils.FlareShape
import kotlin.math.abs

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun BpkRangeSliderImpl(
    value: ClosedFloatingPointRange<Float>,
    onValueChange: (ClosedFloatingPointRange<Float>) -> Unit,
    enabled: Boolean,
    minValue: Float,
    maxValue: Float,
    steps: Int,
    onValueChangeFinished: (() -> Unit)?,
    modifier: Modifier = Modifier,
    lowerThumbLabel: String? = null,
    upperThumbLabel: String? = null,
) {
    val startInteractionSource = remember { MutableInteractionSource() }
    val endInteractionSource = remember { MutableInteractionSource() }
    RangeSlider(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        enabled = enabled,
        startInteractionSource = startInteractionSource,
        endInteractionSource = endInteractionSource,
        valueRange = minValue..maxValue,
        steps = steps,
        onValueChangeFinished = onValueChangeFinished,
        startThumb = {
            lowerThumbLabel?.let {
                SlideRangeLabel(
                    label = it,
                    enabled = enabled,
                    interactionSource = startInteractionSource,
                    value = value.start,
                    valueRange = minValue..maxValue,
                    steps = steps,
                    sliderRangeType = stringResource(id = R.string.range_start),
                    onValueChange = { changedVal -> onValueChange(changedVal..value.endInclusive) },
                    onValueChangeFinished = onValueChangeFinished,
                )
            } ?: SliderDefaults.Thumb(
                interactionSource = startInteractionSource,
                colors = sliderColors(),
                enabled = enabled,
            )
        },
        endThumb = {
            upperThumbLabel?.let {
                SlideRangeLabel(
                    label = it,
                    enabled = enabled,
                    interactionSource = endInteractionSource,
                    value = value.endInclusive,
                    valueRange = minValue..maxValue,
                    steps = steps,
                    sliderRangeType = stringResource(id = R.string.range_end),
                    onValueChange = { changedVal -> onValueChange(value.start..changedVal) },
                    onValueChangeFinished = onValueChangeFinished,
                )
            } ?: SliderDefaults.Thumb(
                interactionSource = endInteractionSource,
                colors = sliderColors(),
                enabled = enabled,
            )
        },
        colors = sliderColors(),
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun SlideRangeLabel(
    value: Float,
    enabled: Boolean,
    label: String,
    onValueChange: (Float) -> Unit,
    sliderRangeType: String,
    interactionSource: MutableInteractionSource,
    valueRange: ClosedFloatingPointRange<Float>,
    modifier: Modifier = Modifier,
    onValueChangeFinished: (() -> Unit)? = null,
    steps: Int = 0,
) {
    Column(
        modifier = modifier
            .sliderSemantics(
                value = value,
                enabled = enabled,
                description = "$sliderRangeType, $label",
                valueRange = valueRange,
                steps = steps,
                onValueChange = onValueChange,
                onValueChangeFinished = onValueChangeFinished,
            )
            .padding(bottom = BpkSpacing.Xl),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        BpkText(
            text = label,
            color = BpkTheme.colors.textPrimaryInverse,
            style = BpkTheme.typography.label2,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .background(
                    color = BpkTheme.colors.coreAccent,
                    shape = FlareShape(
                        borderRadius = BpkBorderRadius.Sm,
                        flareHeight = BpkSpacing.Sm,
                        pointerDirection = BpkFlarePointerDirection.Down,
                    ),
                )
                .padding(top = BpkSpacing.Sm)
                .padding(bottom = BpkSpacing.Md)
                .padding(horizontal = BpkSpacing.Md)
                .semantics { invisibleToUser() },

        )
        Spacer(
            modifier = Modifier.height(BpkSpacing.Sm),
        )
        SliderDefaults.Thumb(
            interactionSource = interactionSource,
            colors = sliderColors(),
            enabled = enabled,
        )
    }
}

@Composable
internal fun sliderColors() = SliderDefaults.colors(
    thumbColor = BpkTheme.colors.coreAccent,
    activeTrackColor = BpkTheme.colors.coreAccent,
    inactiveTrackColor = BpkTheme.colors.line,
    activeTickColor = BpkTheme.colors.coreAccent,
    inactiveTickColor = BpkTheme.colors.line,
)

private fun Modifier.sliderSemantics(
    value: Float,
    enabled: Boolean,
    description: String,
    onValueChange: (Float) -> Unit,
    onValueChangeFinished: (() -> Unit)? = null,
    valueRange: ClosedFloatingPointRange<Float>,
    steps: Int = 0,
): Modifier {
    val coerced = value.coerceIn(valueRange.start, valueRange.endInclusive)
    return semantics {
        if (!enabled) disabled()
        this.stateDescription = description
        setProgress(
            action = { targetValue ->
                var newValue = targetValue.coerceIn(valueRange.start, valueRange.endInclusive)
                val originalVal = newValue
                val resolvedValue = if (steps > 0) {
                    var distance: Float = newValue
                    for (i in 0..steps + 1) {
                        val stepValue = lerp(
                            valueRange.start,
                            valueRange.endInclusive,
                            i.toFloat() / (steps + 1),
                        )
                        if (abs(stepValue - originalVal) <= distance) {
                            distance = abs(stepValue - originalVal)
                            newValue = stepValue
                        }
                    }
                    newValue
                } else {
                    newValue
                }
                if (resolvedValue == coerced) {
                    false
                } else {
                    onValueChange(resolvedValue)
                    onValueChangeFinished?.invoke()
                    true
                }
            },
        )
    }.progressSemantics(value, valueRange, steps)
}
