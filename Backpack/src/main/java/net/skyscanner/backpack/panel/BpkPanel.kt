package net.skyscanner.backpack.panel

import android.content.Context
import android.util.AttributeSet
import androidx.annotation.Dimension
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.content.res.ResourcesCompat
import net.skyscanner.backpack.R
import net.skyscanner.backpack.util.wrapContextWithDefaults

open class BpkPanel @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet?,
  defStyleAttr: Int = R.style.Bpk_panel
) : LinearLayoutCompat(wrapContextWithDefaults(context), attrs, defStyleAttr) {

    @Dimension
    private var paddingSize = resources.getDimensionPixelOffset(R.dimen.bpkSpacingBase)

    init {
        initialize(attrs, defStyleAttr)
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

    private fun initialize(attrs: AttributeSet?, defStyleAttr: Int) {
        val a = context.obtainStyledAttributes(attrs, R.styleable.BpkPanel, R.attr.padding, defStyleAttr)
        padding = a.getBoolean(R.styleable.BpkPanel_padding, true)
        this.background = ResourcesCompat.getDrawable(resources, R.drawable.border, context.theme)
        a.recycle()
    }
}
