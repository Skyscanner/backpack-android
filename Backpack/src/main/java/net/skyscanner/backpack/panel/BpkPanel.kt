package net.skyscanner.backpack.panel

import android.content.Context
import android.util.AttributeSet
import androidx.annotation.Dimension
import androidx.annotation.Nullable
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.content.res.ResourcesCompat
import net.skyscanner.backpack.R

open class BpkPanel(
  context: Context,
  attrs: AttributeSet?,
  defStyleAttr: Int
) : LinearLayoutCompat(context, attrs, defStyleAttr) {

    constructor(context: Context) : this(context, null)
    constructor(context: Context, @Nullable attrs: AttributeSet?) : this(context, attrs, R.style.Bpk_panel)

    @Dimension
    private var paddingSize = resources.getDimensionPixelOffset(R.dimen.bpkSpacingBase)

    init {
        initialize(context, attrs, defStyleAttr)
    }

  /**
   * @property padding
   * padding for panel
   */
    var padding: Boolean = false
        set(value) {
            field = value
          if (this.padding) {
            this.setPadding(paddingSize, paddingSize, paddingSize, paddingSize)
          } else {
            this.setPadding(0, 0, 0, 0)
          }
        }

    private fun initialize(context: Context, attrs: AttributeSet?, defStyleAttr: Int) {

        val a = context.obtainStyledAttributes(attrs, R.styleable.BpkPanel, R.attr.padding, defStyleAttr)
        padding = a.getBoolean(R.styleable.BpkPanel_padding, true)
        this.background = ResourcesCompat.getDrawable(resources, R.drawable.border, null)
        a.recycle()
    }
}
