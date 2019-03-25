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
import net.skyscanner.backpack.calendar.model.CalendarDrawingParams
import net.skyscanner.backpack.calendar.model.CalendarRange
import net.skyscanner.backpack.calendar.presenter.BpkCalendarController
import net.skyscanner.backpack.calendar.presenter.SelectionType
import net.skyscanner.backpack.util.ResourcesUtil
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
      color = rangeColorNonColored
      style = Style.FILL
    }
  }
  private val backgroundPaint: Paint by lazy {
    Paint().apply {
      color = Color.WHITE
      style = Style.FILL
    }
  }
  private var coloredCirclePaints = mapOf<CalendarDay, Paint>()
  private var coloredSelectedPaints = mapOf<CalendarDay, Paint>()

  private lateinit var calendarDrawingParams: CalendarDrawingParams

  private var dayOfWeekStart = 0
  private var hasToday = false
  private var today = DEFAULT_SELECTED_DAY
  private var weekStart = DEFAULT_WEEK_START
  private var numberOfDays = DEFAULT_NUM_DAYS
  private var numberOfCells = DEFAULT_NUM_DAYS
  private var numberOfRows = DEFAULT_NUM_ROWS
  private var monthHeaderString = ""

  private val calendar: Calendar by lazy {
    Calendar.getInstance().also { it.timeZone = TimeZone.getTimeZone("UTC") }
  }

  private val defaultTextColor: Int by lazy { ContextCompat.getColor(context, R.color.bpkGray900) }
  private val disabledTextColor: Int by lazy { ContextCompat.getColor(context, R.color.bpkGray100) }
  private val selectedDayCircleFillColor: Int by lazy { ContextCompat.getColor(context, R.color.bpkBlue500) }
  private val rangeColorNonColored: Int by lazy { ContextCompat.getColor(context, R.color.bpkBlue400) }
  private val rangeColorColored: Int by lazy { ContextCompat.getColor(context, R.color.bpkGray100) }
  private val rangeTextColorColored: Int by lazy { ContextCompat.getColor(context, R.color.bpkGray700) }
  private val todayCircleColor: Int by lazy { ContextCompat.getColor(context, R.color.bpkGray100) }
  private val todayCircleStrokeWidth: Int by lazy { ResourcesUtil.dpToPx(1, context) }
  private val sameDayCircleStrokeWidth: Int by lazy { ResourcesUtil.dpToPx(1, context) }
  private val miniDayNumberTextSize: Int by lazy { ResourcesUtil.dpToPx(14, context) }
  private val monthLabelTextSize: Int by lazy { ResourcesUtil.dpToPx(20, context) }
  private val selectedDayCircleRadius: Int by lazy { ResourcesUtil.dpToPx(20, context) }
  private val monthHeaderSize: Int by lazy { ResourcesUtil.dpToPx(52, context) }
  private val coloredCircleStrokeWidth: Int by lazy { ResourcesUtil.dpToPx(3, context) }

  private var rowHeight: Int = ResourcesUtil.dpToPx(45, context)
  private var viewWidth = 0

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
      drawMonthTitle(canvas)
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

  fun setMonthParams(params: CalendarDrawingParams) {
    // Allocate space for caching the day numbers and focus values
    hasToday = false
    today = -1
    calendarDrawingParams = params
    coloredCirclePaints = params.toDrawingPaintMap(isSelectedColor = false)
    coloredSelectedPaints = params.toDrawingPaintMap(isSelectedColor = true)

    calendar.set(Calendar.MONTH, params.month)
    calendar.set(Calendar.YEAR, params.year)
    calendar.set(Calendar.DAY_OF_MONTH, 1)
    dayOfWeekStart = calendar.get(Calendar.DAY_OF_WEEK)

    weekStart = calendar.firstDayOfWeek

    numberOfCells = getDaysInMonth(params.month, params.year)
    for (i in 0 until numberOfCells) {
      val day = i + 1
      if (controller?.isToday(params.year, params.month, day) == true) {
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
    return calendarDrawingParams.year
  }

  private fun drawMonthTitle(canvas: Canvas) {
    val dayWidthHalf = viewWidth / (numberOfDays * 3)
    val monthTitleX = (dayWidthHalf).toFloat()
    val monthTitleY = ((monthHeaderSize) / 2 + monthLabelTextSize / 3).toFloat()

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

      val calendarDay = CalendarDay(calendarDrawingParams.year, calendarDrawingParams.month, dayNumber)

      val isOutOfRange = isOutOfRange(calendarDay)
      when (controller.selectionType) {
        SelectionType.SINGLE -> drawDayCellForSingle(controller, canvas, calendarDay, x, y, stopY, isOutOfRange)
        SelectionType.RANGE -> drawDayCellForRange(
          controller,
          canvas,
          calendarDay,
          x,
          y,
          startX,
          stopX,
          startY,
          stopY,
          isOutOfRange
        )
      }

      j++
      if (j == numberOfDays) {
        j = 0
        y += rowHeight
      }
    }
  }

  private fun drawColoredSmallCircle(
    calendarDay: CalendarDay,
    canvas: Canvas,
    x: Int,
    stopY: Int,
    paddingFromBottom: Int
  ) {
    if (coloredCirclePaints.keys.contains(calendarDay)) {
      drawCircle(canvas, x, (stopY - paddingFromBottom), coloredCircleStrokeWidth + todayCircleStrokeWidth, backgroundPaint)
      drawCircle(canvas, x, (stopY - paddingFromBottom), coloredCircleStrokeWidth, coloredCirclePaints.getValue(calendarDay))
    }
  }

  private fun drawDayCellForSingle(
    controller: BpkCalendarController,
    canvas: Canvas,
    calendarDay: CalendarDay,
    x: Int,
    y: Int,
    stopY: Int,
    isOutOfRange: Boolean
  ) {
    var overrideTextColor: Int? = null

    val rowPadding = if (isColoredCalendar()) (rowHeight * 0.1).toInt() else 0

    if (!isOutOfRange) {
      val type = controller.selectedRange.getDrawType(calendarDay)
      when (type) {
        CalendarRange.DrawType.SELECTED -> {
          selectedCirclePaint.color =
            if (isColoredCalendar() && coloredSelectedPaints.keys.contains(calendarDay)) coloredSelectedPaints.getValue(calendarDay).color else selectedDayCircleFillColor

          overrideTextColor = if (isColoredCalendar()) null else Color.WHITE
          selectedCirclePaint.style = Paint.Style.FILL

          drawCircle(
            canvas,
            x,
            y - miniDayNumberTextSize / 3,
            selectedDayCircleRadius - rowPadding,
            selectedCirclePaint
          )

          if (isColoredCalendar()) {
            drawCircle(
              canvas,
              x,
              y - miniDayNumberTextSize / 3,
              selectedDayCircleRadius - rowPadding - sameDayCircleStrokeWidth,
              backgroundPaint
            )
          }
        }
        CalendarRange.DrawType.NONE -> {
          drawColoredSmallCircle(calendarDay, canvas, x, stopY, rowPadding)
        }
      }
    }
    if (hasToday && today == calendarDay.day) {
      if (isColoredCalendar()) {
        overrideTextColor = selectedDayCircleFillColor
      } else {
        drawCircle(canvas, x, y - miniDayNumberTextSize / 3, selectedDayCircleRadius - rowPadding, todayCirclePaint)
      }
    }

    monthNumberPaint.color = when {
      isOutOfRange -> disabledTextColor
      overrideTextColor != null -> overrideTextColor
      else -> defaultTextColor
    }

    drawText(canvas, String.format(controller.locale, "%d", calendarDay.day), x.toFloat(), y.toFloat(), monthNumberPaint)
  }

  private fun drawDayCellForRange(
    controller: BpkCalendarController,
    canvas: Canvas,
    calendarDay: CalendarDay,
    x: Int,
    y: Int,
    startX: Int,
    stopX: Int,
    startY: Int,
    stopY: Int,
    isOutOfRange: Boolean
  ) {
    var overrideTextColor: Int? = null

    val rowPadding = if (isColoredCalendar()) (rowHeight * 0.1).toInt() else 0

    if (!isOutOfRange) {
      val type = controller.selectedRange.getDrawType(calendarDay)
      when (type) {
        CalendarRange.DrawType.SELECTED -> {
          selectedCirclePaint.color =
            if (isColoredCalendar() && coloredSelectedPaints.keys.contains(calendarDay)) coloredSelectedPaints.getValue(calendarDay).color else selectedDayCircleFillColor
          if (controller.selectedRange.isRange && !controller.selectedRange.isOnTheSameDate) {
            val paddingX = (stopX - startX) / 2
            drawEdgeCircles(canvas, calendarDay, controller.selectedRange, paddingX, rowPadding, x, y)
            val nextDay = calendarDay.addDays(1)
            if (controller.selectedRange.getDrawType(nextDay) != CalendarRange.DrawType.NONE) {
              drawRect(canvas, startX - 1 + paddingX, startY + rowPadding, stopX + 1, stopY - rowPadding)
            } else {
              drawRect(canvas, startX - 1, startY + rowPadding, stopX - paddingX + 1, stopY - rowPadding)
            }
          }

          overrideTextColor = if (isColoredCalendar()) null else Color.WHITE
          if (controller.selectedRange.isOnTheSameDate) {
            selectedCirclePaint.alpha = 255
            drawSameDayCircles(
              canvas,
              selectedCirclePaint,
              miniDayNumberTextSize / 3,
              x,
              y,
              selectedDayCircleRadius - rowPadding
            )
            selectedCirclePaint.alpha = 255
          } else {
            selectedCirclePaint.style = Paint.Style.FILL

            drawCircle(
              canvas,
              x,
              y - miniDayNumberTextSize / 3,
              selectedDayCircleRadius - rowPadding,
              selectedCirclePaint
            )

            if (isColoredCalendar()) {
              drawCircle(
                canvas,
                x,
                y - miniDayNumberTextSize / 3,
                selectedDayCircleRadius - rowPadding - sameDayCircleStrokeWidth,
                backgroundPaint
              )
            }
          }
        }
        CalendarRange.DrawType.RANGE -> {
          val halfCellWidth = (stopX - startX) / 2
          drawRect(canvas, startX - 1, startY + rowPadding, stopX + 1, stopY - rowPadding)
          drawEdgeCircles(canvas, calendarDay, controller.selectedRange, halfCellWidth, rowPadding, x, y)
          drawColoredSmallCircle(calendarDay, canvas, x, stopY, rowPadding)
          overrideTextColor = if (isColoredCalendar()) rangeTextColorColored else Color.WHITE
        }
        CalendarRange.DrawType.NONE -> {
          drawColoredSmallCircle(calendarDay, canvas, x, stopY, rowPadding)
        }
      }
    }
    if (hasToday && today == calendarDay.day) {
      if (isColoredCalendar()) {
        overrideTextColor = selectedDayCircleFillColor
      } else {
        drawCircle(canvas, x, y - miniDayNumberTextSize / 3, selectedDayCircleRadius - rowPadding, todayCirclePaint)
      }
    }

    monthNumberPaint.color = when {
      isOutOfRange -> disabledTextColor
      overrideTextColor != null -> overrideTextColor
      else -> defaultTextColor
    }

    drawText(canvas, String.format(controller.locale, "%d", calendarDay.day), x.toFloat(), y.toFloat(), monthNumberPaint)
  }

  private fun drawSameDayCircles(canvas: Canvas, paint: Paint, padding: Int, x: Int, y: Int, radius: Int) {
    paint.strokeWidth = sameDayCircleStrokeWidth.toFloat()
    paint.style = Paint.Style.STROKE

    if (isColoredCalendar()) {
      drawCircle(canvas, (x - padding * 1.5).toInt(), y - padding, radius, paint)
      drawCircle(canvas, x, y - padding, radius, backgroundPaint)
      drawCircle(canvas, x, y - padding, radius, paint)
    } else {
      drawCircle(canvas, x - padding, y - padding, radius, paint)
      paint.style = Paint.Style.FILL
      drawCircle(canvas, x + padding, y - padding, radius, paint)
    }
  }

  private fun drawEdgeCircles(
    canvas: Canvas,
    day: CalendarDay,
    range: CalendarRange,
    paddingX: Int,
    paddingY: Int,
    x: Int,
    y: Int
  ) {
    rangeBackPaint.color = if (isColoredCalendar()) rangeColorColored else rangeColorNonColored
    if (isFirstDayInMonth(day.day) && day != range.start) {
      drawCircle(canvas, x - paddingX, y - miniDayNumberTextSize / 3, selectedDayCircleRadius - paddingY, rangeBackPaint)
    }
    if (isLastDayInMonth(day.day, day.month, day.year) && day != range.end) {
      drawCircle(canvas, x + paddingX, y - miniDayNumberTextSize / 3, selectedDayCircleRadius - paddingY, rangeBackPaint)
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
    rangeBackPaint.color = if (isColoredCalendar()) rangeColorColored else rangeColorNonColored
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
    if (isOutOfRange(CalendarDay(calendarDrawingParams.year, calendarDrawingParams.month, day))) return

    onDayClickListener?.onDayClick(this, CalendarDay(calendarDrawingParams.year, calendarDrawingParams.month, day))
  }

  private fun isOutOfRange(calendarDay: CalendarDay) =
    isBeforeMin(calendarDay.year, calendarDay.month, calendarDay.day) || isAfterMax(calendarDay.year, calendarDay.month, calendarDay.day)

  private fun isFirstDayInMonth(day: Int) = day == 1

  private fun isLastDayInMonth(day: Int, month: Int, year: Int) = day == getDaysInMonth(month, year)

  private fun isBeforeMin(year: Int, month: Int) =
    controller?.startDate?.let { minDate ->
      when {
        year < minDate.year -> true
        year > minDate.year -> false
        month < minDate.month -> true
        month > minDate.month -> false
        else -> false
      }
    } ?: false

  private fun isBeforeMin(year: Int, month: Int, day: Int) =
    controller?.startDate?.let { minDate ->
      val minYear = minDate.year
      val minMonth = minDate.month
      val minDay = minDate.day
      if (isBeforeMin(year, month)) true else year == minYear && month == minMonth && day < minDay
    } ?: false

  private fun isAfterMax(year: Int, month: Int, day: Int) =
    controller?.endDate?.let { maxDate ->
      when {
        year > maxDate.year -> true
        year < maxDate.year -> false
        month > maxDate.month -> true
        month < maxDate.month -> false
        else -> day > maxDate.day
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

  private fun isColoredCalendar() = calendarDrawingParams.calendarColoring != null

  interface OnDayClickListener {
    fun onDayClick(view: MonthView?, day: CalendarDay)
  }

  internal companion object {
    private const val MONTH_HEADLINE_PATTERN = "MMMM"

    private const val DEFAULT_SELECTED_DAY = -1
    private const val DEFAULT_WEEK_START = Calendar.MONDAY
    private const val DEFAULT_NUM_DAYS = 7
    private const val DEFAULT_NUM_ROWS = 6
  }
}

internal fun CalendarDrawingParams.toDrawingPaintMap(isSelectedColor: Boolean): Map<CalendarDay, Paint> {
  return mutableMapOf<CalendarDay, Paint>().also { resultMap ->
    this.calendarColoring?.coloredBuckets?.forEach { bucket ->
      val bucketColor = if (isSelectedColor) bucket.selectedColor else bucket.color
      if (bucketColor != null) {
        val paint = Paint().apply {
          style = Style.FILL_AND_STROKE
          color = bucketColor
        }
        bucket.days.forEach { day ->
          resultMap[day] = paint
        }
      }
    }
  }.toMap()
}
