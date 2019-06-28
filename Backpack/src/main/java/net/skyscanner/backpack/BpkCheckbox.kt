package net.skyscanner.backpack

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.core.widget.CompoundButtonCompat
import net.skyscanner.backpack.text.BpkText
import net.skyscanner.backpack.util.BpkTheme
import net.skyscanner.backpack.util.createContextThemeWrapper
import net.skyscanner.backpack.util.use

open class BpkCheckbox @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : AppCompatCheckBox(
  createContextThemeWrapper(
    createContextThemeWrapper(context, attrs, androidx.appcompat.R.attr.checkboxStyle),
    attrs, R.attr.bpkCheckboxStyle
  ), attrs, defStyleAttr) {

  private lateinit var disabledTint: ColorStateList
  private lateinit var enabledTint: ColorStateList

  init {
    initialize(attrs, defStyleAttr)
  }

  private fun initialize(attrs: AttributeSet?, defStyleAttr: Int) {
    var defaultColour = BpkTheme.getPrimaryColor(context)
    var checkedColour = BpkTheme.getPrimaryColor(context)
    var disabledColour = BpkTheme.getColor(context, R.color.bpkGray200)
    context.theme.obtainStyledAttributes(
      attrs,
      R.styleable.BpkCheckbox,
      defStyleAttr,
      0
    ).use {
      defaultColour = it.getInt(R.styleable.BpkCheckbox_checkboxColor, defaultColour)
      checkedColour = it.getInt(R.styleable.BpkCheckbox_checkboxCheckedColor, checkedColour)
      disabledColour = it.getInt(R.styleable.BpkCheckbox_checkboxDisabledColor, disabledColour)
    }

    this.disabledTint = ColorStateList.valueOf(disabledColour)
    this.enabledTint = ColorStateList(
      arrayOf(intArrayOf(android.R.attr.state_checked), intArrayOf()),
      intArrayOf(checkedColour, defaultColour)
    )
    updateTint()
    BpkText.getFont(context, BpkText.SM, BpkText.Weight.NORMAL).applyTo(this)
  }

  override fun setEnabled(enabled: Boolean) {
    super.setEnabled(enabled)
    updateTint()
  }

  private fun updateTint() {
    if (!isTintInitialized()) {
      return
    }
    CompoundButtonCompat.setButtonTintList(this, if (isEnabled) enabledTint else disabledTint)
  }

  private fun isTintInitialized() = ::disabledTint.isInitialized && ::enabledTint.isInitialized
}
