package net.skyscanner.backpack.dialog.internal

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout

internal class DialogInsetsLayout @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

  init {
    fitsSystemWindows = true
  }
}
