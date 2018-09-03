package net.skyscanner.backpack.dummytwo

import android.content.Context
import android.util.AttributeSet
import android.view.View

open class DummyTwo(
        context: Context,
        attrs: AttributeSet?,
defStyleAttr: Int) : View(context, attrs, defStyleAttr) {

  constructor(context: Context) : this(context, null)
  constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, R.style.Bpk_dummytwo)

}
