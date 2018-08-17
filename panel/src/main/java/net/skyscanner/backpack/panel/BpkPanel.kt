package net.skyscanner.backpack.panel

import android.content.Context
import android.support.annotation.Dimension
import android.support.v7.widget.LinearLayoutCompat
import android.util.AttributeSet
import android.widget.LinearLayout


class BpkPanel(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int) : LinearLayoutCompat(context, attrs, defStyleAttr) {

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, R.style.Bpk_panel)

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
          }
        }

    private fun draw() {
        this.background = context.getDrawable(R.drawable.border)
        if (this.padding) {
          this.setPadding(paddingSize, paddingSize, paddingSize, paddingSize)
        }
        val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        params.setMargins(paddingSize, paddingSize, paddingSize, paddingSize)
        this.layoutParams = params
    }


    private fun initialize(context: Context, attrs: AttributeSet?, defStyleAttr: Int) {

        val a = context.obtainStyledAttributes(attrs, R.styleable.panel, R.attr.bpk_padding, defStyleAttr)
        if (a.hasValue(R.styleable.panel_bpk_padding)) {
            //default value of padding is true
            padding = a.getBoolean(R.styleable.panel_bpk_padding, true)
        }
        draw()
        a.recycle()
    }

}
