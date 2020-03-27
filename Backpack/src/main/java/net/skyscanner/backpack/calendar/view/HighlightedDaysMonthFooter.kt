package net.skyscanner.backpack.calendar.view

import android.content.Context
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.annotation.ColorInt
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.graphics.drawable.DrawableCompat
import net.skyscanner.backpack.R
import net.skyscanner.backpack.calendar.presenter.DateFormatter
import net.skyscanner.backpack.calendar.presenter.HighlightedDaysAdapter
import net.skyscanner.backpack.text.BpkText
import net.skyscanner.backpack.util.unsafeLazy

open class HighlightedDaysMonthFooter(
  context: Context,
  val formatDate: DateFormatter
) : LinearLayout(context) {

  var holidays: Set<HighlightedDaysAdapter.HighlightedDay>? = null
    set(value) {
      field = value
      update()
    }

  private val spacingBase = context.resources.getDimension(R.dimen.bpkSpacingBase).toInt()
  private val spacingLg = context.resources.getDimension(R.dimen.bpkSpacingXxl).toInt()

  init {
    orientation = VERTICAL
    setPadding(spacingBase, 0, spacingBase, spacingLg)
  }

  private fun update() {
    removeAllViews()
    holidays?.forEach { holiday ->
      addView(
        HolidayView(context).apply {
          description = holiday.description
          date = formatDate(holiday.date)
          color = holiday.color
        },
        LayoutParams(
          LayoutParams.WRAP_CONTENT,
          LayoutParams.WRAP_CONTENT))
    }
  }

  private class HolidayView(context: Context) : ConstraintLayout(context) {
    private val dateView by unsafeLazy { findViewById<BpkText>(R.id.date) }
    private val descriptionView by unsafeLazy { findViewById<BpkText>(R.id.description) }

    init {
      LayoutInflater
        .from(this.context)
        .inflate(R.layout.view_bpk_calendar_holiday, this, true)
    }

    var date: String? = null
      set(value) {
        field = value
        dateView.text = value
      }

    var description: String? = null
      set(value) {
        field = value
        descriptionView.text = value
      }

    @ColorInt
    var color: Int? = null
      set(value) {
        field = value
        if (value != null) {
          val drawableStart = dateView.compoundDrawablesRelative[0]
          DrawableCompat.wrap(drawableStart.mutate()).let {
            DrawableCompat.setTint(it, value)
          }
        }
      }
  }
}
