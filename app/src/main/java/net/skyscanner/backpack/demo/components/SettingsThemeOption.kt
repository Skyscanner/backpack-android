package net.skyscanner.backpack.demo.components

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import androidx.appcompat.widget.LinearLayoutCompat
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.text.BpkText

class SettingsThemeOption @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : LinearLayoutCompat(context, attrs, defStyleAttr) {

  init {
    inflate(context, R.layout.component_settings_theme_option, this)
    initialize(context, attrs, defStyleAttr)
  }

  var isCurrent: Boolean = false
    set(value) {
      if (field != value) {
        val img = findViewById<ImageView>(R.id.selected_indicator)
        if (value) {
          img.visibility = View.VISIBLE
        } else {
          img.visibility = View.GONE
        }
      }
      field = value
    }

  val text: CharSequence
    get() = findViewById<BpkText>(R.id.theme_name).text

  private fun initialize(context: Context, attrs: AttributeSet?, defStyleAttr: Int) {
    val a = context.obtainStyledAttributes(attrs, R.styleable.SettingsThemeOption, defStyleAttr, 0)
    val message = a.getString(R.styleable.SettingsThemeOption_themeName)
    findViewById<BpkText>(R.id.theme_name).text = message
    a.recycle()
  }
}
