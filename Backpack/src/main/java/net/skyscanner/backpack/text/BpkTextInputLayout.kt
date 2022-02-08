/**
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2018 Skyscanner Ltd
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.skyscanner.backpack.text

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.isVisible
import net.skyscanner.backpack.R
import net.skyscanner.backpack.util.colorStateList
import net.skyscanner.backpack.util.unsafeLazy
import net.skyscanner.backpack.util.use

open class BpkTextInputLayout @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = R.attr.bpkTextInputLayoutStyle,
) : LinearLayout(context, attrs, defStyleAttr) {

  private val labelView by unsafeLazy<BpkText> {
    findViewById(R.id.bpk_input_label)
  }

  private val indicatorView by unsafeLazy<BpkText> {
    findViewById(R.id.bpk_input_indicator)
  }

  private val iconSize: Int = resources.getDimensionPixelSize(R.dimen.bpk_icon_size_small)

  private val errorTextColor: ColorStateList
  private val helperTextColor: ColorStateList

  var editText: BpkTextField? = null

  var errorIcon: Drawable? = null
    set(value) {
      field = value?.mutate()
      field?.setBounds(0, 0, iconSize, iconSize)
      updateErrorIconTint()
      updateIndicator()
    }

  var label: CharSequence?
    get() = labelView.text
    set(value) {
      labelView.text = value
      labelView.isVisible = value != null
    }

  var error: String? = null
    set(value) {
      field = value
      if (value != null) {
        errorEnabled = true
      }
      editText?.hasError = value != null
      updateIndicator()
    }

  var helperText: String? = null
    set(value) {
      field = value
      updateIndicator()
    }

  /**
   * Whether the error functionality is enabled or not in this layout.
   * Enabling this functionality before setting an error message via setting error, will mean that this layout will not
   * change size when an error is displayed.
   */
  var errorEnabled: Boolean = false
    set(value) {
      field = value
      updateIndicator()
    }

  init {
    orientation = VERTICAL
    inflate(context, R.layout.view_bpk_text_input_layout, this)
    context.obtainStyledAttributes(
      attrs,
      R.styleable.BpkTextInputLayout,
      defStyleAttr, 0
    ).use {
      errorTextColor = it.getColorStateList(R.styleable.BpkTextInputLayout_textInputErrorTextColor)
        ?: context.getColorStateList(R.color.bpkPanjin)
      helperTextColor = it.getColorStateList(R.styleable.BpkTextInputLayout_textInputHelperTextColor)
        ?: context.getColorStateList(R.color.__textInputLayoutHelperColor)

      errorEnabled = it.getBoolean(R.styleable.BpkTextInputLayout_textInputErrorEnabled, errorEnabled)

      label = it.getString(R.styleable.BpkTextInputLayout_android_label)
      error = it.getString(R.styleable.BpkTextInputLayout_textInputError)
      helperText = it.getString(R.styleable.BpkTextInputLayout_textInputHelperText)

      errorIcon = it.getDrawable(R.styleable.BpkTextInputLayout_textInputErrorIcon)
        ?: AppCompatResources.getDrawable(context, R.drawable.bpk_information_circle_sm)
      labelView.setTextColor(
        colorStateList(
          color = context.getColor(R.color.bpkTextPrimary),
          disabledColor = context.getColor(R.color.__textFieldTextDisabled)
        )
      )
      updateErrorIconTint()
    }
  }

  override fun childDrawableStateChanged(child: View?) {
    super.childDrawableStateChanged(child)
    if (child?.id == R.id.bpk_input_placeholder) {
      labelView.isEnabled = editText?.isEnabled ?: true
    }
  }

  override fun setEnabled(enabled: Boolean) {
    super.setEnabled(enabled)
    editText?.isEnabled = enabled
  }

  override fun addView(child: View?, index: Int, params: ViewGroup.LayoutParams?) {
    if (child is BpkTextField) {
      if (editText != null) {
        throw IllegalStateException("Only one TextField supported")
      }
      editText = child
      child.hasError = error != null
      findViewById<FrameLayout>(R.id.bpk_input_placeholder).addView(child, params)
    } else {
      super.addView(child, index, params)
    }
  }

  private fun updateErrorIconTint() {
    errorIcon?.setTintList(errorTextColor)
  }

  private fun updateIndicator() {
    when {
      error != null && errorEnabled -> {
        indicatorView.isVisible = true
        indicatorView.text = error
        indicatorView.textStyle = BpkText.TextStyle.LABEL2
        indicatorView.setTextColor(errorTextColor)
        indicatorView.setCompoundDrawablesRelative(errorIcon, null, null, null)
      }
      helperText != null -> {
        indicatorView.isVisible = true
        indicatorView.text = helperText
        indicatorView.textStyle = BpkText.TextStyle.FOOTNOTE
        indicatorView.setTextColor(helperTextColor)
        indicatorView.setCompoundDrawablesRelative(null, null, null, null)
      }
      else -> {
        indicatorView.visibility = if (errorEnabled) View.INVISIBLE else View.GONE
      }
    }
  }
}
