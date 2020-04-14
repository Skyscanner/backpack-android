package net.skyscanner.backpack.util

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

internal abstract class ItemHolder<T>(
  protected val view: View
) : RecyclerView.ViewHolder(view), Consumer<T> {

  constructor(parent: ViewGroup, @LayoutRes layout: Int) :
    this(LayoutInflater.from(parent.context).inflate(layout, parent, false))

  protected var model: T? = null

  final override fun invoke(model: T) {
    if (this.model != model) {
      this.model = model
      bind(model)
    }
  }

  abstract fun bind(model: T)

  fun <T : View> findViewById(@IdRes id: Int): T =
    view.findViewById(id)
}
