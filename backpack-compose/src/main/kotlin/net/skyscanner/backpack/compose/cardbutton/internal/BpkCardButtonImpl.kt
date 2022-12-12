package net.skyscanner.backpack.compose.cardbutton.internal

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.semantics.Role
import net.skyscanner.backpack.compose.cardbutton.BpkCardButtonSize
import net.skyscanner.backpack.compose.cardbutton.BpkCardButtonStyle
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.icon.BpkIconSize
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.compose.tokens.Heart
import net.skyscanner.backpack.compose.tokens.HeartOutline
import net.skyscanner.backpack.compose.tokens.ShareIos
import net.skyscanner.backpack.compose.utils.clickable

internal enum class BpkCardButtonState {
  Default,
  Transition
}

@Composable
fun BpkSaveCardButtonImpl(
  checked: Boolean,
  contentDescription: String,
  style: BpkCardButtonStyle,
  size: BpkCardButtonSize,
  onCheckedChange: (Boolean) -> Unit
) {
  var state by remember { mutableStateOf(BpkCardButtonState.Default) }
  val scaleAnimation by animateFloatAsState(
    targetValue = if (state == BpkCardButtonState.Transition) 30f / 24f else 1f,
    animationSpec = spring(
      dampingRatio = 0.4f,
      stiffness = 800f,
    ),
    finishedListener = { state = BpkCardButtonState.Default }
  )
  val colorAnimation by animateColorAsState(
    targetValue = when (style) {
      BpkCardButtonStyle.OnDark -> BpkTheme.colors.textOnDark
      else -> if (checked) BpkTheme.colors.coreAccent else BpkTheme.colors.textPrimary
    }
  )
  Box(
    modifier = Modifier.size(BpkSpacing.Xxl + BpkSpacing.Md),
    contentAlignment = Alignment.Center
  ) {
    Box(
      modifier = Modifier
        .clip(shape = CircleShape)
        .size(if (size == BpkCardButtonSize.Default) BpkSpacing.Xxl else BpkSpacing.Xl)
        .background(
          color = when (style) {
            BpkCardButtonStyle.Contained -> BpkTheme.colors.surfaceDefault.copy(alpha = 0.5F)
            else -> BpkTheme.colors.textOnDark.copy(alpha = 0.0F)
          },
        )
        .toggleable(
          value = checked,
          role = Role.Switch,
          onValueChange = {
            if (it) state = BpkCardButtonState.Transition
            onCheckedChange.invoke(it)
          },
        ),
      contentAlignment = Alignment.Center,
    ) {
      Box(modifier = Modifier.scale(scaleAnimation)) {
        BpkIcon(
          icon = if (checked) BpkIcon.Heart else BpkIcon.HeartOutline,
          contentDescription = contentDescription,
          size = if (size == BpkCardButtonSize.Default) BpkIconSize.Large else BpkIconSize.Small,
          tint = colorAnimation
        )
      }
    }
  }
}


@Composable
fun BpkShareCardButtonImpl(
  contentDescription: String,
  style: BpkCardButtonStyle,
  size: BpkCardButtonSize,
  onClick: () -> Unit
) {
  Box(
    modifier = Modifier.size(BpkSpacing.Xxl + BpkSpacing.Md),
    contentAlignment = Alignment.Center
  ) {
    Box(
      modifier = Modifier
        .clip(shape = CircleShape)
        .size(if (size == BpkCardButtonSize.Default) BpkSpacing.Xxl else BpkSpacing.Xl)
        .background(
          color = when (style) {
            BpkCardButtonStyle.Contained -> BpkTheme.colors.surfaceDefault.copy(alpha = 0.5F)
            else -> BpkTheme.colors.textOnDark.copy(alpha = 0.0F)
          },
        )
        .clickable(onClick = onClick),
      contentAlignment = Alignment.Center,
    ) {
      BpkIcon(
        modifier = Modifier,
        icon = BpkIcon.ShareIos,
        contentDescription = contentDescription,
        size = if (size == BpkCardButtonSize.Default) BpkIconSize.Large else BpkIconSize.Small,
        tint = when (style) {
          BpkCardButtonStyle.OnDark -> BpkTheme.colors.textOnDark
          else -> BpkTheme.colors.textPrimary
        }
      )
    }
  }
}
