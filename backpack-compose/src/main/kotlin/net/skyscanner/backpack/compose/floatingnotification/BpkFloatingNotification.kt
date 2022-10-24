package net.skyscanner.backpack.compose.floatingnotification

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeightIn
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.requiredWidthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import net.skyscanner.backpack.compose.card.BpkCard
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.icon.BpkIconSize
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.compose.utils.clickable
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.DurationUnit

@Composable
fun BpkFloatingNotification(
  modifier: Modifier = Modifier,
  text: String,
  show: Boolean,
  icon: BpkIcon? = null,
  animation: Animation = Animation(),
  cta: CTA? = null
) {
  val (internalShow, setInternalShow) = remember { mutableStateOf(false) }
  if (show) {
    LaunchedEffect(key1 = Unit) {
      setInternalShow(true)
      delay(animation.showDuration)
      setInternalShow(false)
      animation.onFinished?.invoke()
    }
  }
  val animationDuration = if (animation.enabled) animation.transitionDuration.toInt(DurationUnit.MILLISECONDS) else 0
  val fadeAnimationSpec: FiniteAnimationSpec<Float> = tween(durationMillis = animationDuration, easing = LinearEasing)
  val slideAnimationSpec: FiniteAnimationSpec<IntOffset> = tween(durationMillis = animationDuration, easing = LinearEasing)
  AnimatedVisibility(
    visible = internalShow,
    enter = slideInVertically(slideAnimationSpec, initialOffsetY = { it / 2 }) + fadeIn(animationSpec = fadeAnimationSpec),
    exit = slideOutVertically(slideAnimationSpec, targetOffsetY = { it / 2 }) + fadeOut(animationSpec = fadeAnimationSpec)
  ) {
    Box(
      modifier = modifier
        .requiredHeightIn(min = 52.dp, max = 72.dp)
        .requiredWidthIn(min = 288.dp, max = 400.dp)
        .padding(start = BpkSpacing.Base, end = BpkSpacing.Base),
      contentAlignment = Alignment.BottomCenter
    ) {
      BpkCard(
        modifier = modifier.fillMaxWidth(),
        backgroundColor = BpkTheme.colors.corePrimary
      ) {
        Row(
          modifier = Modifier.wrapContentSize(),
          horizontalArrangement = Arrangement.spacedBy(BpkSpacing.Base),
          verticalAlignment = Alignment.CenterVertically
        ) {
          icon?.let { icon ->
            BpkIcon(
              modifier = Modifier,
              icon = icon,
              contentDescription = "",
              size = BpkIconSize.Small,
              tint = BpkTheme.colors.textOnDark
            )
          }
          BpkText(
            modifier = Modifier.weight(0.8f),
            text = text,
            color = BpkTheme.colors.textOnDark,
            style = BpkTheme.typography.footnote
          )
          cta?.let {
            Box(
              modifier = Modifier
                .requiredSize(BpkSpacing.Xxl)
                .clip(CircleShape)
                .weight(0.1f)
                .clickable { cta.onClick.invoke() },
              contentAlignment = Alignment.Center
            ) {
              BpkText(
                text = cta.text,
                color = BpkTheme.colors.textOnDark,
                style = BpkTheme.typography.label2
              )
            }
          }
        }
      }
    }
  }
}

data class CTA(
  val text: String,
  val onClick: (() -> Unit)
)
data class Animation(
  val enabled: Boolean = true,
  val showDuration: Duration = 4000.milliseconds,
  val transitionDuration: Duration = 400.milliseconds,
  val onFinished: (() -> Unit)? = null
)
