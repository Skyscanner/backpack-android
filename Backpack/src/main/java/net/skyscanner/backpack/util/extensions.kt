package net.skyscanner.backpack.util

import android.animation.AnimatorInflater
import android.os.Build
import android.view.View
import androidx.annotation.DrawableRes

fun View.isStateListAnimatorSupported() =
  if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP) {
    false
  } else isSpecificDeviceSupported()

private fun isSpecificDeviceSupported() = Build.MODEL != "GT-I9505"

fun View.loadStateListAnimator(@DrawableRes animator: Int) {
  stateListAnimator = AnimatorInflater.loadStateListAnimator(context, animator)
}
