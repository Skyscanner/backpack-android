package net.skyscanner.backpack.calendar.view

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import net.skyscanner.backpack.R
import net.skyscanner.backpack.text.BpkText
import java.text.SimpleDateFormat
import java.util.*

internal class WeekdayHeaderView @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyle: Int = 0
) : FrameLayout(context, attrs, defStyle) {

  private val headerLabelTextColor: Int by lazy { ContextCompat.getColor(context, R.color.bpkGray500) }

  private val firstWeekdayHeaderView: BpkText by lazy {
    BpkText(context).apply {
      id = R.id.firstWeekdayHeaderLabel
      setTextColor(headerLabelTextColor)
    }
  }
  private val secondWeekdayHeaderView: BpkText by lazy {
    BpkText(context).apply {
      id = R.id.secondWeekdayHeaderLabel
      setTextColor(headerLabelTextColor)
    }
  }
  private val thirdWeekdayHeaderView: BpkText by lazy {
    BpkText(context).apply {
      id = R.id.thirdWeekdayHeaderLabel
      setTextColor(headerLabelTextColor)
    }
  }
  private val fourthWeekdayHeaderView: BpkText by lazy {
    BpkText(context).apply {
      id = R.id.fourthWeekdayHeaderLabel
      setTextColor(headerLabelTextColor)
    }
  }
  private val fifthWeekdayHeaderView: BpkText by lazy {
    BpkText(context).apply {
      id = R.id.fifthWeekdayHeaderLabel
      setTextColor(headerLabelTextColor)
    }
  }
  private val sixthWeekdayHeaderView: BpkText by lazy {
    BpkText(context).apply {
      id = R.id.sixthWeekdayHeaderLabel
      setTextColor(headerLabelTextColor)
    }
  }
  private val seventhWeekdayHeaderView: BpkText by lazy {
    BpkText(context).apply {
      id = R.id.seventhWeekdayHeaderLabel
      setTextColor(headerLabelTextColor)
    }
  }

  init {

    val containerLayout = ConstraintLayout(context)
      .apply {
        addView(firstWeekdayHeaderView)
        addView(secondWeekdayHeaderView)
        addView(thirdWeekdayHeaderView)
        addView(fourthWeekdayHeaderView)
        addView(fifthWeekdayHeaderView)
        addView(sixthWeekdayHeaderView)
        addView(seventhWeekdayHeaderView)
      }.also { addView(it) }

    val constraintSet = ConstraintSet()
    constraintSet.clone(containerLayout)
    constraintSet.createHorizontalChain(
      ConstraintSet.PARENT_ID, ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT,
      intArrayOf(
        firstWeekdayHeaderView.id, secondWeekdayHeaderView.id, thirdWeekdayHeaderView.id,
        fourthWeekdayHeaderView.id, fifthWeekdayHeaderView.id, sixthWeekdayHeaderView.id,
        seventhWeekdayHeaderView.id
      ),
      null, ConstraintSet.CHAIN_SPREAD_INSIDE
    )

    constraintSet.connect(firstWeekdayHeaderView.id, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START)
    constraintSet.connect(firstWeekdayHeaderView.id, ConstraintSet.END, secondWeekdayHeaderView.id, ConstraintSet.START)

    constraintSet.connect(secondWeekdayHeaderView.id, ConstraintSet.START, firstWeekdayHeaderView.id, ConstraintSet.END)
    constraintSet.connect(secondWeekdayHeaderView.id, ConstraintSet.END, thirdWeekdayHeaderView.id, ConstraintSet.START)

    constraintSet.connect(thirdWeekdayHeaderView.id, ConstraintSet.START, secondWeekdayHeaderView.id, ConstraintSet.END)
    constraintSet.connect(thirdWeekdayHeaderView.id, ConstraintSet.END, fourthWeekdayHeaderView.id, ConstraintSet.START)

    constraintSet.connect(fourthWeekdayHeaderView.id, ConstraintSet.START, thirdWeekdayHeaderView.id, ConstraintSet.END)
    constraintSet.connect(fourthWeekdayHeaderView.id, ConstraintSet.END, fifthWeekdayHeaderView.id, ConstraintSet.START)

    constraintSet.connect(fifthWeekdayHeaderView.id, ConstraintSet.START, fourthWeekdayHeaderView.id, ConstraintSet.END)
    constraintSet.connect(fifthWeekdayHeaderView.id, ConstraintSet.END, sixthWeekdayHeaderView.id, ConstraintSet.START)

    constraintSet.connect(sixthWeekdayHeaderView.id, ConstraintSet.START, fifthWeekdayHeaderView.id, ConstraintSet.END)
    constraintSet.connect(
      sixthWeekdayHeaderView.id,
      ConstraintSet.END,
      seventhWeekdayHeaderView.id,
      ConstraintSet.START
    )

    constraintSet.connect(
      seventhWeekdayHeaderView.id,
      ConstraintSet.START,
      sixthWeekdayHeaderView.id,
      ConstraintSet.START
    )
    constraintSet.connect(seventhWeekdayHeaderView.id, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END)
    constraintSet.applyTo(containerLayout)

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
        0 -> firstWeekdayHeaderView.text = calendarDayName
        1 -> secondWeekdayHeaderView.text = calendarDayName
        2 -> thirdWeekdayHeaderView.text = calendarDayName
        3 -> fourthWeekdayHeaderView.text = calendarDayName
        4 -> fifthWeekdayHeaderView.text = calendarDayName
        5 -> sixthWeekdayHeaderView.text = calendarDayName
        6 -> seventhWeekdayHeaderView.text = calendarDayName
      }
    }
  }
}
