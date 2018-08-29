package net.skyscanner.backpack.panel

import android.content.Context
import android.support.annotation.Dimension
import android.support.annotation.Nullable
import android.support.v7.widget.LinearLayoutCompat
import android.util.AttributeSet
import android.widget.LinearLayout


open class BpkPanel(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int) : LinearLayoutCompat(context, attrs, defStyleAttr) {

    constructor(context: Context) : this(context, null)
    constructor(context: Context,@Nullable attrs: AttributeSet?) : this(context, attrs, R.style.Bpk_panel)

    @Dimension
    private var paddingSize = resources.getDimensionPixelOffset(R.dimen.bpkSpacingSm)

    init {
        paddingSize = resources.getDimensionPixelOffset(R.dimen.bpkSpacingSm)
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
          }else{
            this.setPadding(0,0,0,0)
          }
        }

    private fun initialize(context: Context, attrs: AttributeSet?, defStyleAttr: Int) {

        val a = context.obtainStyledAttributes(attrs, R.styleable.panel, R.attr.bpk_padding, defStyleAttr)
        padding = a.getBoolean(R.styleable.panel_bpk_padding, true)
        this.background = context.getDrawable(R.drawable.border)
        a.recycle()
    }
}
