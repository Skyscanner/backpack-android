/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.skyscanner.backpack.calendar.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Paint.Align
import android.graphics.Paint.Style
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.core.content.ContextCompat
import net.skyscanner.backpack.R
import net.skyscanner.backpack.calendar.model.CalendarDay
import net.skyscanner.backpack.calendar.model.CalendarRange
import net.skyscanner.backpack.calendar.presenter.BpkCalendarController
import net.skyscanner.backpack.util.ResourcesUtil
import java.security.InvalidParameterException
import java.util.Calendar
import java.util.TimeZone

/**
 * A calendar-like view displaying a specified month and the appropriate selectable day numbers
 * within the specified month.
 */
internal class MonthView @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyle: Int = 0
) : View(context, attrs, defStyle) {

  private val monthNumberPaint: Paint by lazy {
    Paint().apply {
      isAntiAlias = true
      isFakeBoldText = false
      style = Style.FILL
      textAlign = Align.CENTER
      textSize = miniDayNumberTextSize.toFloat()
    }
  }
  private val monthTitlePaint: Paint by lazy {
    Paint().apply {
      isAntiAlias = true
      isFakeBoldText = false
      color = defaultTextColor
      style = Style.FILL
      textAlign = Align.LEFT
      textSize = monthLabelTextSize.toFloat()
      typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD)
    }
  }
  private val selectedCirclePaint: Paint by lazy {
    Paint().apply {
      isFakeBoldText = true
      isAntiAlias = true
      color = selectedDayCircleFillColor
      textAlign = Align.CENTER
      style = Style.FILL_AND_STROKE
    }
  }
  private val todayCirclePaint: Paint by lazy {
    Paint().apply {
      isFakeBoldText = true
      isAntiAlias = true
      color = todayCircleColor
      textAlign = Align.CENTER
      style = Style.STROKE
      strokeWidth = todayCircleStrokeWidth.toFloat()
    }
  }
  private val rangeBackPaint: Paint by lazy {
    Paint().apply {
      isFakeBoldText = true
      isAntiAlias = true
      color = rangeColor
      style = Style.FILL
    }
  }

  private var month: Int = 0
  private var year: Int = 0

  private var dayOfWeekStart = 0
  private var hasToday = false
  private var selectedDay = -1
  private var today = DEFAULT_SELECTED_DAY
  private var weekStart = DEFAULT_WEEK_START
  private var numberOfDays = DEFAULT_NUM_DAYS
  private var numberOfCells = DEFAULT_NUM_DAYS
  private var numberOfRows = DEFAULT_NUM_ROWS

  private val calendar: Calendar by lazy {
    Calendar.getInstance().also { it.timeZone = TimeZone.getTimeZone("UTC") }
  }

  private val defaultTextColor: Int by lazy { ContextCompat.getColor(context, R.color.bpkGray900) }
  private val disabledTextColor: Int by lazy { ContextCompat.getColor(context, R.color.bpkGray100) }
  private val selectedDayCircleFillColor: Int by lazy { ContextCompat.getColor(context, R.color.bpkBlue500) }
  private val rangeColor: Int by lazy { ContextCompat.getColor(context, R.color.bpkBlue400) }
  private val todayCircleColor: Int by lazy { ContextCompat.getColor(context, R.color.bpkGray100) }
  private val todayCircleStrokeWidth: Int by lazy { ResourcesUtil.dpToPx(1, context) }
  private val sameDayCircleStrokeWidth: Int by lazy { ResourcesUtil.dpToPx(2, context) }
  private val miniDayNumberTextSize: Int by lazy { ResourcesUtil.dpToPx(14, context) }
  private val monthLabelTextSize: Int by lazy { ResourcesUtil.dpToPx(20, context) }
  private val selectedDayCircleRadius: Int by lazy { ResourcesUtil.dpToPx(20, context) }
  private val monthHeaderSize: Int by lazy { ResourcesUtil.dpToPx(52, context) }

  private var rowHeight: Int = ResourcesUtil.dpToPx(45, context)
  private var viewWidth = 0

  private val calendarDay: CalendarDay by lazy { CalendarDay() }
  private var isRtl: Boolean = false

  var onDayClickListener: OnDayClickListener? = null
  var controller: BpkCalendarController? = null
    set(value) {
      if (value != null) {
        field = value
        isRtl = value.isRtl
      }
    }

  @SuppressLint("ClickableViewAccessibility")
  override fun onTouchEvent(event: MotionEvent): Boolean {
    if (event.action == MotionEvent.ACTION_UP) {
        var x = event.x
        if (isRtl) {
          x = viewWidth - x
        }
        val day = getDayFromLocation(x, event.y)
        if (day >= 0) {
          onDayClick(day)
        }
    }
    return true
  }

  override fun onDraw(canvas: Canvas) {
    controller?.let {
      drawMonthTitle(it, canvas)
      drawDaysInMonth(it, canvas)
    }
  }

  override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
    setMeasuredDimension(
      View.MeasureSpec.getSize(widthMeasureSpec),
      rowHeight * numberOfRows + monthHeaderSize + selectedDayCircleRadius
    )
  }

  override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
    viewWidth = w
  }

  fun setMonthParams(params: HashMap<String, Int>) {
    if (!params.containsKey(VIEW_PARAMS_MONTH) && !params.containsKey(VIEW_PARAMS_YEAR)) {
      throw InvalidParameterException("You must specify month and year for this view")
    }

    tag = params
    // We keep the current value for any params not present
    if (params.containsKey(VIEW_PARAMS_HEIGHT)) {
      rowHeight = params[VIEW_PARAMS_HEIGHT]!!
      if (rowHeight < MIN_HEIGHT) {
        rowHeight = MIN_HEIGHT
      }
    }
    if (params.containsKey(VIEW_PARAMS_SELECTED_DAY)) {
      selectedDay = params[VIEW_PARAMS_SELECTED_DAY]!!
    }

    // Allocate space for caching the day numbers and focus values
    month = params[VIEW_PARAMS_MONTH]!!
    year = params[VIEW_PARAMS_YEAR]!!

    hasToday = false
    today = -1

    calendar.set(Calendar.MONTH, month)
    calendar.set(Calendar.YEAR, year)
    calendar.set(Calendar.DAY_OF_MONTH, 1)
    dayOfWeekStart = calendar.get(Calendar.DAY_OF_WEEK)

    weekStart = calendar.firstDayOfWeek

    numberOfCells = getDaysInMonth(month, year)
    for (i in 0 until numberOfCells) {
      val day = i + 1
      if (controller?.isToday(year, month, day) ?: sameDay(day, CalendarDay())) {
        hasToday = true
        this.today = day
      }
    }
    numberOfRows = calculateNumRows()
  }

  fun reuse() {
    numberOfRows = DEFAULT_NUM_ROWS
    requestLayout()
  }

  fun getYear(): Int {
    return year
  }

  private fun drawMonthTitle(controller: BpkCalendarController, canvas: Canvas) {
    val dayWidthHalf = viewWidth / (numberOfDays * 3)
    val monthTitleX = (dayWidthHalf).toFloat()
    val monthTitleY = ((monthHeaderSize) / 2 + monthLabelTextSize / 3).toFloat()
    val monthHeaderString = controller.getLocalizedDate(calendar.time, MONTH_HEADLINE_PATTERN)

    drawText(canvas, monthHeaderString, monthTitleX, monthTitleY, monthTitlePaint)
  }

  private fun drawDaysInMonth(controller: BpkCalendarController, canvas: Canvas) {
    var y = (rowHeight + miniDayNumberTextSize) / 2 + monthHeaderSize
    val dayWidthHalf = (viewWidth) / (numberOfDays * 2.0f)
    var j = findDayOffset()
    for (dayNumber in 1..numberOfCells) {
      val x = ((2 * j + 1) * dayWidthHalf).toInt()

      val yRelativeToDay = (rowHeight + miniDayNumberTextSize) / 2

      val startX = (x - dayWidthHalf).toInt()
      val stopX = (x + dayWidthHalf).toInt()
      val startY = y - yRelativeToDay
      val stopY = startY + rowHeight

      drawDayCell(controller, canvas, year, month, dayNumber, x, y, startX, stopX, startY, stopY)

      j++
      if (j == numberOfDays) {
        j = 0
        y += rowHeight
      }
    }
  }

  private fun drawDayCell(
    controller: BpkCalendarController,
    canvas: Canvas,
    year: Int,
    month: Int,
    day: Int,
    x: Int,
    y: Int,
    startX: Int,
    stopX: Int,
    startY: Int,
    stopY: Int
  ) {
    var overrideTextColor: Int? = null
    calendarDay.setDay(year, month, day)

    val isOutOfRange = isOutOfRange(year, month, day)

    if (!isOutOfRange) {
      val type = controller.selectedRange.getDrawType(year, month, day)
      if (type == CalendarRange.DrawType.SELECTED) {
        selectedCirclePaint.color = selectedDayCircleFillColor
        if (controller.selectedRange.isRange && !controller.selectedRange.isOnTheSameDate) {
          val padding = (stopX - startX) / 2
          drawEdgeCircles(canvas, calendarDay, controller.selectedRange, padding, x, y, startX, startY, stopX, stopY)
          calendarDay.add(Calendar.DAY_OF_MONTH, 1)
          if (controller.selectedRange.getDrawType(calendarDay.year, calendarDay.month, calendarDay.day) != CalendarRange.DrawType.NONE) {
            drawRect(canvas, startX + padding, startY, stopX + padding, stopY)
          } else {
            drawRect(canvas, startX, startY, stopX - padding, stopY)
          }
        }

        if (controller.selectedRange.isOnTheSameDate) {
          overrideTextColor = Color.WHITE
          selectedCirclePaint.alpha = 255
          drawSameDayCircles(
            canvas,
            selectedCirclePaint,
            miniDayNumberTextSize / 3,
            x, y,
            selectedDayCircleRadius
          )
          selectedCirclePaint.alpha = 255
        } else {
          overrideTextColor = Color.WHITE
          // TODO: review/remove this. This if is only true when the user selects the
          // last day in the calendar as the range's end. Removing the if does not seem
          // to make any difference
          if (controller.selectedRange.isInRange(controller.selectedDay, month, day)) {
            selectedCirclePaint.style = Paint.Style.FILL
            drawCircle(
              canvas,
              x,
              y - miniDayNumberTextSize / 3,
              selectedDayCircleRadius,
              selectedCirclePaint
            )
          } else {
            selectedCirclePaint.style = Paint.Style.FILL
            selectedCirclePaint.color = selectedDayCircleFillColor
            drawCircle(
              canvas,
              x,
              y - miniDayNumberTextSize / 3,
              selectedDayCircleRadius,
              selectedCirclePaint
            )
            drawCircle(
              canvas,
              x,
              y - miniDayNumberTextSize / 3,
              selectedDayCircleRadius / 2,
              selectedCirclePaint
            )
          }
        }
      } else if (type == CalendarRange.DrawType.RANGE) {
        drawRange(canvas, calendarDay, controller.selectedRange, x, y, startX, startY, stopX, stopY)
        overrideTextColor = Color.WHITE
      } else {
        drawTodayCircle(canvas, x, y - miniDayNumberTextSize / 3, selectedDayCircleRadius, todayCirclePaint, day)
      }
    }

    when {
      isOutOfRange -> monthNumberPaint.color = disabledTextColor
      overrideTextColor != null -> monthNumberPaint.color = overrideTextColor
      else -> monthNumberPaint.color = defaultTextColor
    }

    drawText(canvas, String.format(controller.locale, "%d", day), x.toFloat(), y.toFloat(), monthNumberPaint)
  }

  private fun drawRange(
    canvas: Canvas,
    day: CalendarDay,
    range: CalendarRange,
    x: Int,
    y: Int,
    startX: Int,
    startY: Int,
    stopX: Int,
    stopY: Int
  ) {
    val halfCellWidth = (stopX - startX) / 2
    drawRect(canvas, startX - 10, startY, stopX + 10, stopY)
    drawEdgeCircles(canvas, day, range, halfCellWidth, x, y, startX, startY, stopX, stopY)
  }

  private fun drawSameDayCircles(canvas: Canvas, paint: Paint, padding: Int, x: Int, y: Int, radius: Int) {
    paint.strokeWidth = sameDayCircleStrokeWidth.toFloat()
    paint.style = Paint.Style.STROKE
    paint.color = selectedDayCircleFillColor

    drawCircle(canvas, x - padding, y - padding, radius, paint)

    paint.style = Paint.Style.FILL

    drawCircle(canvas, x + padding, y - padding, radius, paint)
  }

  private fun drawTodayCircle(canvas: Canvas, x: Int, y: Int, radius: Int, paint: Paint, givenDay: Int) {
    if (hasToday && today == givenDay) {
      drawCircle(canvas, x, y, radius, paint)
    }
  }

  private fun drawEdgeCircles(
    canvas: Canvas,
    day: CalendarDay,
    range: CalendarRange,
    padding: Int,
    x: Int,
    y: Int,
    startX: Int,
    startY: Int,
    stopX: Int,
    stopY: Int
  ) {
    if (isFirstDayInMonth(day.day) && day.date != range.start?.date) {
      drawRect(canvas, startX + padding, startY, stopX - padding, stopY)
      drawCircle(canvas, x - padding, y - miniDayNumberTextSize / 3, selectedDayCircleRadius, rangeBackPaint)
    }
    if (isLastDayInMonth(day.day, day.month, day.year) && day.date != range.end?.date) {
      drawRect(canvas, startX + padding, startY, stopX - padding, stopY)
      drawCircle(canvas, x + padding, y - miniDayNumberTextSize / 3, selectedDayCircleRadius, rangeBackPaint)
    }
  }

  private fun drawText(canvas: Canvas, localizedText: String, x: Float, y: Float, paint: Paint) {
    val originalAlignment = paint.textAlign
    if (isRtl) {
      paint.textAlign = when (paint.textAlign) {
        Align.LEFT -> Align.RIGHT
        Align.RIGHT -> Align.LEFT
        else -> Align.CENTER
      }
    }

    canvas.drawText(localizedText, if (isRtl) viewWidth - x else x, y, paint)

    if (isRtl) paint.textAlign = originalAlignment
  }

  private fun drawCircle(canvas: Canvas, cX: Int, cY: Int, cSize: Int, paint: Paint) {
    val x = if (isRtl) viewWidth - cX else cX
    canvas.drawCircle(x.toFloat(), cY.toFloat(), cSize.toFloat(), paint)
  }

  private fun drawRect(canvas: Canvas, startX: Int, startY: Int, stopX: Int, stopY: Int) {
    val x1 = if (isRtl) viewWidth - stopX else startX
    val x2 = if (isRtl) viewWidth - startX else stopX
    canvas.drawRect(
      x1.toFloat(), (startY + miniDayNumberTextSize / 3).toFloat(), x2.toFloat(), stopY.toFloat(), rangeBackPaint
    )
  }

  private fun findDayOffset() =
    (if (dayOfWeekStart < weekStart) dayOfWeekStart + numberOfDays else dayOfWeekStart) - weekStart

  private fun getDayFromLocation(x: Float, y: Float): Int {
    val day = getInternalDayFromLocation(x, y)
    return if (day < 1 || day > numberOfCells) -1 else day
  }

  private fun getInternalDayFromLocation(x: Float, y: Float): Int {
    if (x < 0 || x > viewWidth) {
      return -1
    }
    val row = (y - monthHeaderSize).toInt() / rowHeight
    val column = (x * numberOfDays / viewWidth).toInt()
    var day = column - findDayOffset() + 1
    day += row * numberOfDays
    return day
  }

  private fun onDayClick(day: Int) {
    if (isOutOfRange(year, month, day)) return

    onDayClickListener?.onDayClick(this, CalendarDay(year, month, day))
  }

  private fun isOutOfRange(year: Int, month: Int, day: Int) =
    isBeforeMin(year, month, day) || isAfterMax(year, month, day)

  private fun isFirstDayInMonth(day: Int) = day == 1

  private fun isLastDayInMonth(day: Int, month: Int, year: Int) = day == getDaysInMonth(month, year)

  private fun isBeforeMin(year: Int, month: Int) =
    controller?.startDate?.let { minDate ->
      when {
        year < minDate.get(Calendar.YEAR) -> true
        year > minDate.get(Calendar.YEAR) -> false
        month < minDate.get(Calendar.MONTH) -> true
        month > minDate.get(Calendar.MONTH) -> false
        else -> false
      }
    } ?: false

  private fun isBeforeMin(year: Int, month: Int, day: Int) =
    controller?.startDate?.let { minDate ->
      val minYear = minDate.get(Calendar.YEAR)
      val minMonth = minDate.get(Calendar.MONTH)
      val minDay = minDate.get(Calendar.DAY_OF_MONTH)
      if (isBeforeMin(year, month)) true else year == minYear && month == minMonth && day < minDay
    } ?: false

  private fun isAfterMax(year: Int, month: Int, day: Int) =
    controller?.endDate?.let { maxDate ->
      when {
        year > maxDate.get(Calendar.YEAR) -> true
        year < maxDate.get(Calendar.YEAR) -> false
        month > maxDate.get(Calendar.MONTH) -> true
        month < maxDate.get(Calendar.MONTH) -> false
        else -> day > maxDate.get(Calendar.DAY_OF_MONTH)
      }
    } ?: false

  private fun getDaysInMonth(month: Int, year: Int) =
    when (month) {
      Calendar.JANUARY, Calendar.MARCH, Calendar.MAY, Calendar.JULY, Calendar.AUGUST, Calendar.OCTOBER, Calendar.DECEMBER -> 31
      Calendar.APRIL, Calendar.JUNE, Calendar.SEPTEMBER, Calendar.NOVEMBER -> 30
      Calendar.FEBRUARY -> if (year % 4 == 0) 29 else 28
      else -> throw IllegalArgumentException("Invalid Month")
    }

  private fun calculateNumRows(): Int {
    val offset = findDayOffset()
    val dividend = (offset + numberOfCells) / numberOfDays
    val remainder = (offset + numberOfCells) % numberOfDays
    return dividend + if (remainder > 0) 1 else 0
  }

  private fun sameDay(day: Int, today: CalendarDay) = year == today.year && month == today.month && day == today.day

  interface OnDayClickListener {
    fun onDayClick(view: MonthView?, day: CalendarDay)
  }

  internal companion object {
    private const val MONTH_HEADLINE_PATTERN = "MMMM"

    const val VIEW_PARAMS_HEIGHT = "height"
    const val VIEW_PARAMS_MONTH = "month"
    const val VIEW_PARAMS_YEAR = "year"
    const val VIEW_PARAMS_SELECTED_DAY = "selected_day"

    private const val DEFAULT_SELECTED_DAY = -1
    private const val DEFAULT_WEEK_START = Calendar.MONDAY
    private const val DEFAULT_NUM_DAYS = 7
    private const val DEFAULT_NUM_ROWS = 6

    private const val MIN_HEIGHT = 10
  }
}
