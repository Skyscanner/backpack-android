package net.skyscanner.backpack.{{cookiecutter.pkg_name}}

import android.content.Context
import android.util.AttributeSet
import android.view.View

open class {{cookiecutter.component_name}}(
        context: Context,
        attrs: AttributeSet?,
defStyleAttr: Int) : View(context, attrs, defStyleAttr) {

  constructor(context: Context) : this(context, null)
  constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, R.style.Bpk_{{cookiecutter.pkg_name}})

}
