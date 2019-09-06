package net.skyscanner.backpack.badge

import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.Gravity
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.annotation.Dimension
import androidx.core.content.ContextCompat
import net.skyscanner.backpack.R
import net.skyscanner.backpack.text.BpkText
import net.skyscanner.backpack.util.createContextThemeWrapper

open class BpkBadge @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : BpkText(createContextThemeWrapper(context, attrs, R.attr.bpkBadgeStyle), attrs, defStyleAttr) {

  private var initialized = false

  init {
    initialize(attrs, defStyleAttr)
    initialized = true
  }

  enum class Type(
    internal var id: Int,
    @ColorRes internal var bgColor: Int,
    @ColorRes internal var textColor: Int
  ) {
    /**
     * Style for badges with positive messages
     */
    Success(1, R.color.bpkGreen500, R.color.bpkGray700),
    /**
     *  Style for badges with warning messages
     */
    Warning(2, R.color.bpkYellow500, R.color.bpkGray700),
    /**
     * Style for badges with error messages
     */
    Destructive(3, R.color.bpkRed500, R.color.bpkWhite),
    /**
     *  Light themed style for badges
     */
    Light(4, R.color.bpkGray50, R.color.bpkGray700),
    /**
     *  Style for badges on dark themes
     */
    Inverse(5, R.color.bpkWhite, R.color.bpkGray700),
    /**
     * Style for badges with a thin white outline
     */
    Outline(6, R.color.bpkWhite, R.color.bpkWhite),

    /**
     * Style for badges with a dark background
     */
    Dark(7, R.color.bpkGray900, R.color.bpkWhite);

    internal companion object {

      internal fun fromId(id: Int): Type {
        for (f in values()) {
          if (f.id == id) return f
        }
        throw IllegalArgumentException()
      }
    }
  }

  /**
   * @property type
   * Type of badge. Default Type.Success
   */
  var type: Type = Type.Success
    set(value) {
      field = value
      if (initialized) setup()
    }
  /**
   * @property message
   * message on the badge
   */
  var message: String? = null
    set(value) {
      field = value
      this.text = message
    }

  private fun initialize(attrs: AttributeSet?, defStyleAttr: Int) {

    val a: TypedArray = context.theme.obtainStyledAttributes(
      attrs,
      R.styleable.BpkBadge,
      defStyleAttr, 0)

    type = Type.fromId(a.getInt(R.styleable.BpkBadge_badgeType, 1))
    message = a.getString(R.styleable.BpkBadge_message)
    includeFontPadding = a.getBoolean(R.styleable.BpkBadge_android_includeFontPadding, false)

    a.recycle()

    setup()
  }

  private fun setup() {

    this.text = message

    // set padding
    val paddingMd = resources.getDimension(R.dimen.bpkSpacingMd).toInt()
    val paddingSm = resources.getDimension(R.dimen.bpkSpacingSm).toInt()
    this.setPadding(paddingMd, paddingSm, paddingMd, paddingSm)

    // set Text color
    this.setTextColor(ContextCompat.getColor(context, type.textColor))

    // Set background color
    val border = GradientDrawable()
    border.setColor(ContextCompat.getColor(context, type.bgColor))

    // Set border
    if (type == Type.Outline) {
      border.setStroke(resources.getDimension(R.dimen.badge_border_size).toInt(), ContextCompat.getColor(context, R.color.bpkWhite))
      // set alpha for border
      border.setColor(ContextCompat.getColor(context, type.bgColor) and 0x32ffffff)
    }

    // set corner radius
    @Dimension
    val cornerRadius = context.resources.getDimension(R.dimen.bpkBorderRadiusSm)

    val radius = floatArrayOf(cornerRadius, cornerRadius,
      cornerRadius, cornerRadius,
      cornerRadius, cornerRadius,
      cornerRadius, cornerRadius)
    border.cornerRadii = radius
    this.background = border

    val params = ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    this.gravity = Gravity.CENTER
    this.layoutParams = params
  }
}
