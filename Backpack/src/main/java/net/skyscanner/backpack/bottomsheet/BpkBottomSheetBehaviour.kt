package net.skyscanner.backpack.bottomsheet

import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.google.android.material.bottomsheet.BottomSheetBehavior

class BpkBottomSheetBehaviour<V : View> : BottomSheetBehavior<V> {

  @Suppress("unused")
  constructor() : super()

  @Suppress("unused")
  constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
}
