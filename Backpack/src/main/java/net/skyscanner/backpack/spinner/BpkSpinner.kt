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

package net.skyscanner.backpack.spinner

import android.content.Context
import android.os.Build
import android.provider.Settings.Global
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ProgressBar
import androidx.annotation.ColorInt
import androidx.core.graphics.BlendModeColorFilterCompat
import androidx.core.graphics.BlendModeCompat
import net.skyscanner.backpack.R
import net.skyscanner.backpack.util.createContextThemeWrapper
import net.skyscanner.backpack.util.use

private const val INVALID_RES = -1

/**
 * BpkSpinner is designed to indicate that a part of the product is loading or performing a task
 * when the amount of time needed is unknown.
 *
 * To further customize the visual of the progress `progressBarStyle` and
 * `progressBarStyleSmall` should be used:
 *
 * ```xml
 * <style name="AppTheme" parent="Theme.AppCompat.Light.NoActionBar">
 *   <item name="android:progressBarStyle">@style/CustomProgress</item>
 * </style>
 *
 * <style name="CustomProgress" parent="@style/Widget.AppCompat.ProgressBar">
 *   <item name="android:minWidth">200dp</item>
 * </style>
 * ```
 *
 * @see [BpkSpinner.Type]
 */
open class BpkSpinner @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

  private val colors = arrayOf(
    R.color.bpkPrimary,
    R.color.bpkWhite,
    R.color.bpkSkyGrayTint01,
    R.color.bpkTextPrimary,
    R.color.bpkTextDisabled,
    R.color.bpkTextOnDark,
  )

  private val animationsEnabled =
    Global.getFloat(context.contentResolver, Global.ANIMATOR_DURATION_SCALE, 1f) != 0f

  private var progressBar: ProgressBar? = null
  @ColorInt
  private var themePrimaryColor: Int = INVALID_RES

  /**
   * Updates the Spinner's type.
   * @see [BpkSpinner.Type]
   */
  @Suppress("DEPRECATION")
  var type = Type.PRIMARY
    set(value) {
      field = value
      updateColor()
    }

  /**
   * Toggles the small version of the Spinner
   */
  var small = false
    set(value) {
      field = value
      updateSize()
    }

  init {
    initialize(attrs, defStyleAttr)
  }

  @ColorInt
  fun getColor(): Int {
    @Suppress("DEPRECATION")
    if (type === Type.PRIMARY && themePrimaryColor != INVALID_RES) {
      return themePrimaryColor
    }
    return context.getColor(colors[type.ordinal])
  }

  private fun initialize(attrs: AttributeSet?, defStyleAttr: Int) {
    val wrappedContext = createContextThemeWrapper(context, attrs, R.attr.bpkSpinnerPrimaryStyle)
    wrappedContext.obtainStyledAttributes(attrs, R.styleable.BpkSpinner, defStyleAttr, 0).use {
      themePrimaryColor = it.getColor(R.styleable.BpkSpinner_spinnerColor, INVALID_RES)
      this.small = it.getBoolean(R.styleable.BpkSpinner_small, false)
      this.type = Type.values()[it.getInt(R.styleable.BpkSpinner_type, 0)]
    }
  }

  private fun updateColor() {
    progressBar?.indeterminateDrawable?.mutate()?.colorFilter =
      BlendModeColorFilterCompat.createBlendModeColorFilterCompat(getColor(), BlendModeCompat.SRC_IN)
  }

  private fun updateSize() {
    // Progress animation causes a timeout error in espresso tests:
    //   - Perhaps the main thread has not gone idle within a reasonable amount of time? There could be an animation or something constantly repainting the screen.
    //
    // Since this component only makes sense with animations we simple don't add the progress bar when animations are disabled.

    // td
    if (animationsEnabled || Build.VERSION.SDK_INT >= 29) {
      val style = if (small) android.R.attr.progressBarStyleSmall else android.R.attr.progressBarStyle
      progressBar = ProgressBar(context, null, style)

      removeAllViews()
      addView(
        progressBar,
        ViewGroup.LayoutParams(
          ViewGroup.LayoutParams.MATCH_PARENT,
          ViewGroup.LayoutParams.MATCH_PARENT
        )
      )

      updateColor()
    }
  }

  enum class Type {
    @Deprecated(message = DeprecatedMessage, replaceWith = ReplaceWith("TextPrimary")) PRIMARY,
    @Deprecated(message = DeprecatedMessage, replaceWith = ReplaceWith("OnDarkSurface")) LIGHT,
    @Deprecated(message = DeprecatedMessage, replaceWith = ReplaceWith("TextPrimary")) DARK,
    TextPrimary,
    Disabled,
    OnDarkSurface,
    ;
    private companion object {
      const val DeprecatedMessage = "These styles are not supported anymore and will be removed soon"
    }
  }
}
