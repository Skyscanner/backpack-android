package net.skyscanner.backpack.compose.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.MotionDurationScale

@Composable
internal fun getAnimationScaleFactor(): Float {
  val scope = rememberCoroutineScope()
  return scope.coroutineContext[MotionDurationScale]?.scaleFactor ?: 1f
}
