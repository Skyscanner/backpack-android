package net.skyscanner.backpack.util

import android.content.Context
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

internal abstract class ItemHolder<T>(
  protected val view: View
) : RecyclerView.ViewHolder(view), Consumer<T> {

  val context: Context
    get() = itemView.context

  val resources: Resources
    get() = itemView.resources

  var model: T? = null
    private set

  constructor(parent: ViewGroup, @LayoutRes layout: Int) :
    this(LayoutInflater.from(parent.context).inflate(layout, parent, false))

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
