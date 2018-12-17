package net.skyscanner.backpack.calendar.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import net.skyscanner.backpack.R
import net.skyscanner.backpack.text.BpkText
import net.skyscanner.backpack.util.ResourcesUtil
import java.text.SimpleDateFormat
import java.util.*

internal class WeekdayHeaderView @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyle: Int = 0
) : LinearLayout(context, attrs, defStyle) {

  private val headerLabelTextColor: Int by lazy { ContextCompat.getColor(context, R.color.bpkGray500) }
  private val separatorLineColor: Int by lazy { ContextCompat.getColor(context, R.color.bpkGray100) }
  private val baseSpacing: Int by lazy { context.resources.getDimensionPixelSize(R.dimen.bpkSpacingBase) }

  private val firstWeekdayView: BpkText by lazy {
    BpkText(context).apply {
      id = R.id.firstWeekdayLabel
      setTextColor(headerLabelTextColor)
    }
  }
  private val secondWeekdayView: BpkText by lazy {
    BpkText(context).apply {
      id = R.id.secondWeekdayLabel
      setTextColor(headerLabelTextColor)
    }
  }
  private val thirdWeekdayView: BpkText by lazy {
    BpkText(context).apply {
      id = R.id.thirdWeekdayLabel
      setTextColor(headerLabelTextColor)
    }
  }
  private val fourthWeekdayView: BpkText by lazy {
    BpkText(context).apply {
      id = R.id.fourthWeekdayLabel
      setTextColor(headerLabelTextColor)
    }
  }
  private val fifthWeekdayView: BpkText by lazy {
    BpkText(context).apply {
      id = R.id.fifthWeekdayLabel
      setTextColor(headerLabelTextColor)
    }
  }
  private val sixthWeekdayView: BpkText by lazy {
    BpkText(context).apply {
      id = R.id.sixthWeekdayLabel
      setTextColor(headerLabelTextColor)
    }
  }
  private val seventhWeekdayView: BpkText by lazy {
    BpkText(context).apply {
      id = R.id.seventhWeekdayLabel
      setTextColor(headerLabelTextColor)
    }
  }

  init {
    orientation = VERTICAL

    initWeekdayLabelViews()
  }

  private fun initWeekdayLabelViews() {
    val containerLayout = ConstraintLayout(context)
      .also {
        it.layoutParams =
          ConstraintLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT).apply {
            marginEnd = baseSpacing
            marginStart = baseSpacing
          }
      }
      .apply {
        addView(firstWeekdayView)
        addView(secondWeekdayView)
        addView(thirdWeekdayView)
        addView(fourthWeekdayView)
        addView(fifthWeekdayView)
        addView(sixthWeekdayView)
        addView(seventhWeekdayView)
      }.also {
        addView(it)
      }

    val constraintSet = ConstraintSet()
    constraintSet.clone(containerLayout)
    constraintSet.createHorizontalChain(
      ConstraintSet.PARENT_ID, ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT,
      intArrayOf(
        firstWeekdayView.id, secondWeekdayView.id, thirdWeekdayView.id, fourthWeekdayView.id, fifthWeekdayView.id,
        sixthWeekdayView.id, seventhWeekdayView.id
      ),
      null, ConstraintSet.CHAIN_SPREAD_INSIDE
    )

    constraintSet.connect(firstWeekdayView.id, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, baseSpacing)
    constraintSet.connect(firstWeekdayView.id, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START)
    constraintSet.connect(firstWeekdayView.id, ConstraintSet.END, secondWeekdayView.id, ConstraintSet.START)
    constraintSet.connect(firstWeekdayView.id, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM, baseSpacing)

    constraintSet.connect(secondWeekdayView.id, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, baseSpacing)
    constraintSet.connect(secondWeekdayView.id, ConstraintSet.START, firstWeekdayView.id, ConstraintSet.END)
    constraintSet.connect(secondWeekdayView.id, ConstraintSet.END, thirdWeekdayView.id, ConstraintSet.START)
    constraintSet.connect(secondWeekdayView.id, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM, baseSpacing)

    constraintSet.connect(thirdWeekdayView.id, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, baseSpacing)
    constraintSet.connect(thirdWeekdayView.id, ConstraintSet.START, secondWeekdayView.id, ConstraintSet.END)
    constraintSet.connect(thirdWeekdayView.id, ConstraintSet.END, fourthWeekdayView.id, ConstraintSet.START)
    constraintSet.connect(thirdWeekdayView.id, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM, baseSpacing)

    constraintSet.connect(fourthWeekdayView.id, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, baseSpacing)
    constraintSet.connect(fourthWeekdayView.id, ConstraintSet.START, thirdWeekdayView.id, ConstraintSet.END)
    constraintSet.connect(fourthWeekdayView.id, ConstraintSet.END, fifthWeekdayView.id, ConstraintSet.START)
    constraintSet.connect(fourthWeekdayView.id, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM, baseSpacing)

    constraintSet.connect(fifthWeekdayView.id, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, baseSpacing)
    constraintSet.connect(fifthWeekdayView.id, ConstraintSet.START, fourthWeekdayView.id, ConstraintSet.END)
    constraintSet.connect(fifthWeekdayView.id, ConstraintSet.END, sixthWeekdayView.id, ConstraintSet.START)
    constraintSet.connect(fifthWeekdayView.id, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM, baseSpacing)

    constraintSet.connect(sixthWeekdayView.id, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, baseSpacing)
    constraintSet.connect(sixthWeekdayView.id, ConstraintSet.START, fifthWeekdayView.id, ConstraintSet.END)
    constraintSet.connect(sixthWeekdayView.id, ConstraintSet.END, seventhWeekdayView.id, ConstraintSet.START)
    constraintSet.connect(sixthWeekdayView.id, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM, baseSpacing)

    constraintSet.connect(seventhWeekdayView.id, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, baseSpacing)
    constraintSet.connect(seventhWeekdayView.id, ConstraintSet.START, sixthWeekdayView.id, ConstraintSet.END)
    constraintSet.connect(seventhWeekdayView.id, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END)
    constraintSet.connect(seventhWeekdayView.id, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM, baseSpacing)

    constraintSet.applyTo(containerLayout)

    addView(
      View(context).also { it.setBackgroundColor(separatorLineColor) },
      LayoutParams(LayoutParams.MATCH_PARENT, ResourcesUtil.dpToPx(1, context))
    )
  }

  internal fun initializeWithLocale(locale: Locale) {
    val simpleDateFormat = SimpleDateFormat("ccc", locale)
    val calendar = Calendar.getInstance()
    val weekStart = calendar.firstDayOfWeek
    val numberOfDaysInWeek = 7

    for (i in 0 until numberOfDaysInWeek) {
      val calendarDay = (i + weekStart) % numberOfDaysInWeek
      calendar.set(Calendar.DAY_OF_WEEK, calendarDay)
      val calendarDayName = simpleDateFormat.format(calendar.time)

      when (i) {
        0 -> firstWeekdayView.text = calendarDayName
        1 -> secondWeekdayView.text = calendarDayName
        2 -> thirdWeekdayView.text = calendarDayName
        3 -> fourthWeekdayView.text = calendarDayName
        4 -> fifthWeekdayView.text = calendarDayName
        5 -> sixthWeekdayView.text = calendarDayName
        6 -> seventhWeekdayView.text = calendarDayName
      }
    }
  }
}
