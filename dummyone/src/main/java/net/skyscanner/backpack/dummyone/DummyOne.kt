package net.skyscanner.backpack.dummyone

import android.content.Context
import android.util.AttributeSet
import android.view.View

open class DummyOne(
        context: Context,
        attrs: AttributeSet?,
defStyleAttr: Int) : View(context, attrs, defStyleAttr) {

  constructor(context: Context) : this(context, null)
  constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, R.style.Bpk_dummyone)

}
