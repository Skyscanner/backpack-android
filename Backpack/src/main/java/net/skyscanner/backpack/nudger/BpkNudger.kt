package net.skyscanner.backpack.nudger

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.LinearLayoutCompat
import kotlin.math.max
import kotlin.math.min
import net.skyscanner.backpack.R
import net.skyscanner.backpack.util.unsafeLazy
import net.skyscanner.backpack.util.use
import net.skyscanner.backpack.util.wrapContextWithDefaults

open class BpkNudger @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : LinearLayoutCompat(wrapContextWithDefaults(context), attrs, defStyleAttr) {

  private val decrementButton by unsafeLazy<View> {
    findViewById(R.id.bpk_nudger_decrement)
  }

  private val incrementButton by unsafeLazy<View> {
    findViewById(R.id.bpk_nudger_increment)
  }

  private val label by unsafeLazy<TextView> {
    findViewById(R.id.bpk_nudger_label)
  }

  var value: Int = 0
    set(value) {
      if (field != value) {
        if (value < minimumValue || value > maximumValue) {
          return
        }
        field = value
        update()
      }
    }

  var minimumValue: Int = 0
    set(minimumValue) {
      if (minimumValue > maximumValue) {
        throw IllegalArgumentException("Cannot set minimumValue $minimumValue when maximumValue is $maximumValue")
      }
      field = minimumValue
      value = max(value, minimumValue)
      update()
    }

  var maximumValue: Int = 100
    set(maximumValue) {
      if (maximumValue < minimumValue) {
        throw IllegalArgumentException("Cannot set maximumValue $maximumValue when minimumValue is $minimumValue")
      }
      field = maximumValue
      value = min(value, maximumValue)
      update()
    }

  var onChangeListener: ((Int) -> Unit)? = null

  init {
    LayoutInflater.from(context).inflate(R.layout.view_bpk_nudger, this, true)
    context.theme.obtainStyledAttributes(
      attrs,
      R.styleable.BpkNudger,
      defStyleAttr, 0
    ).use {
      minimumValue = it.getInt(R.styleable.BpkNudger_nudgerMinimumValue, minimumValue)
      maximumValue = it.getInt(R.styleable.BpkNudger_nudgerMaximumValue, maximumValue)
      value = it.getInt(R.styleable.BpkNudger_nudgerValue, value)
    }

    decrementButton.setOnClickListener {
      value -= 1
      onChangeListener?.invoke(value)
    }
    incrementButton.setOnClickListener {
      value += 1
      onChangeListener?.invoke(value)
    }
  }

  private fun update() {
    decrementButton.isEnabled = value > minimumValue
    incrementButton.isEnabled = value < maximumValue
    label.text = value.toString()
  }
}
