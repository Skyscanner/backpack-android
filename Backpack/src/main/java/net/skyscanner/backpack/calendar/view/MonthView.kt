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

@file:Suppress("DEPRECATION")

package net.skyscanner.backpack.calendar.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Paint.Align
import android.graphics.Paint.Style
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.annotation.VisibleForTesting
import net.skyscanner.backpack.R
import net.skyscanner.backpack.calendar.model.CalendarCellStyle
import net.skyscanner.backpack.calendar.model.CalendarDrawingParams
import net.skyscanner.backpack.calendar.model.CalendarRange
import net.skyscanner.backpack.calendar.model.ColoredBucket
import net.skyscanner.backpack.calendar.presenter.BpkCalendarController
import net.skyscanner.backpack.text.BpkText
import net.skyscanner.backpack.util.ResourcesUtil
import org.threeten.bp.DayOfWeek
import org.threeten.bp.LocalDate
import org.threeten.bp.temporal.WeekFields

/**
 * A calendar-like view displaying a specified month and the appropriate selectable day numbers
 * within the specified month.
 */
internal class MonthView @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyle: Int = 0,
) : View(context, attrs, defStyle) {

  private val monthNumberFont = BpkText.getFont(context, BpkText.TextStyle.Heading5)

  private val monthLabelFont = BpkText.getFont(context, BpkText.TextStyle.Heading4)

  private val colouredParams = mutableMapOf<LocalDate, ColoredBucket>()
  private var isDateDisabled: (LocalDate) -> Boolean = { false }

  @VisibleForTesting
  internal lateinit var calendarDrawingParams: CalendarDrawingParams

  private var dayOfWeekStart = 0
  private var today = DEFAULT_SELECTED_DAY
  private var weekStart = DEFAULT_WEEK_START
  private var numberOfDaysInAWeek = DEFAULT_NUM_DAYS
  private var numberOfDaysInMonth = DEFAULT_NUM_DAYS
  private var numberOfRows = DEFAULT_NUM_ROWS
  private var monthHeaderString = ""

  private val defaultTextColor: Int = context.getColor(R.color.bpkTextPrimary)
  private val defaultTextColorLight: Int = context.getColor(R.color.bpkTextOnLight)
  private val defaultTextColorDark: Int = context.getColor(R.color.bpkTextOnDark)
  private val disabledTextColor: Int = context.getColor(R.color.bpkTextDisabled)

  private val miniDayNumberTextSize: Int = monthNumberFont.fontSize
  private val monthLabelTextSize: Int = monthLabelFont.fontSize
  private val selectedDayCircleRadius: Int = ResourcesUtil.dpToPx(18, context)
  private val monthHeaderSize: Int = ResourcesUtil.dpToPx(52, context)

  private var rowHeight: Int = context.resources.getDimensionPixelSize(R.dimen.bpkSpacingXl) * 2
  private var rowHeightLabeled: Int = context.resources.getDimensionPixelSize(R.dimen.bpkSpacingXxl) +
    context.resources.getDimensionPixelSize(R.dimen.bpkSpacingXl) +
    context.resources.getDimensionPixelSize(R.dimen.bpkSpacingSm)

  private var labelsYOffset: Int = context.resources.getDimensionPixelSize(R.dimen.bpkSpacingBase)
  private var viewWidth = 0

  private var isRtl: Boolean = layoutDirection == LAYOUT_DIRECTION_RTL

  private val selectedTextColor: Int
  private val selectedDayCircleFillColor: Int
  private val selectedDaySameDayCircleFillColor: Int
  private val rangeBackgroundColor: Int
  private val rangeTextColor: Int

  init {
    @SuppressLint("CustomViewStyleable") // We're accessing the calendar style from the parent view here
    val a = this.context.obtainStyledAttributes(attrs, R.styleable.BpkCalendar, R.attr.bpkCalendarStyle, 0)

    selectedDayCircleFillColor = a.getColor(
      R.styleable.BpkCalendar_calendarDateSelectedBackgroundColor,
      context.getColor(R.color.bpkCoreAccent)
    )

    selectedDaySameDayCircleFillColor = a.getColor(
      R.styleable.BpkCalendar_calendarDateSelectedSameDayBackgroundColor,
      context.getColor(R.color.bpkLine)
    )

    rangeBackgroundColor = a.getColor(
      R.styleable.BpkCalendar_calendarDateSelectedRangeBackgroundColor,
      context.getColor(R.color.bpkSurfaceHighlight)
    )

    selectedTextColor = a.getColor(
      R.styleable.BpkCalendar_calendarDateSelectedTextColor,
      context.getColor(R.color.bpkTextPrimaryInverse)
    )

    rangeTextColor = a.getColor(
      R.styleable.BpkCalendar_calendarRangeTextColor,
      context.getColor(R.color.bpkTextPrimary)
    )

    a.recycle()
  }

  private val monthNumberPaint = Paint().apply {
    isAntiAlias = true
    isFakeBoldText = false
    style = Style.FILL
    textAlign = Align.CENTER
    monthNumberFont.applyTo(this)
  }

  private val monthTitlePaint = Paint().apply {
    isAntiAlias = true
    isFakeBoldText = false
    color = defaultTextColor
    style = Style.FILL
    textAlign = Align.LEFT
    monthLabelFont.applyTo(this)
  }

  private val selectedCirclePaint = Paint().apply {
    isFakeBoldText = true
    isAntiAlias = true
    color = selectedDayCircleFillColor
    textAlign = Align.CENTER
    style = Style.FILL
  }

  private val selectedSameDayCirclePaint = Paint().apply {
    isFakeBoldText = true
    isAntiAlias = true
    color = selectedDaySameDayCircleFillColor
    textAlign = Align.CENTER
    style = Style.FILL
  }

  private val rangeBackPaint = Paint().apply {
    isFakeBoldText = true
    isAntiAlias = true
    color = rangeBackgroundColor
    style = Style.FILL
  }

  private val colouredBucketPaint = Paint().apply {
    isAntiAlias = true
    isFakeBoldText = true
    style = Style.FILL
  }

  var onDayClickListener: OnDayClickListener? = null
  var controller: BpkCalendarController? = null
    set(value) {
      if (value != null) {
        field = value
        labelsViewModel.update(value.calendarLabels)
        isRtl = layoutDirection == LAYOUT_DIRECTION_RTL
        requestLayout()
      }
    }

  override fun onRtlPropertiesChanged(layoutDirection: Int) {
    super.onRtlPropertiesChanged(layoutDirection)
    isRtl = layoutDirection == LAYOUT_DIRECTION_RTL
    requestLayout()
  }

  private val labelsViewModel = BpkCalendarLabelsViewModel(context)

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
      labelsViewModel.cellWidth = (width / DEFAULT_NUM_DAYS.toFloat()) * LABEL_WIDTH_PERCENT
      drawMonthTitle(canvas)
      drawDaysInMonth(it, canvas)
      contentDescription = monthHeaderString
    }
  }

  override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
    val rowHeight = if (labelsViewModel.isEmpty()) rowHeight else rowHeightLabeled
    setMeasuredDimension(
      MeasureSpec.getSize(widthMeasureSpec),
      rowHeight * numberOfRows + monthHeaderSize + selectedDayCircleRadius
    )
  }

  override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
    viewWidth = w
  }

  fun setMonthParams(params: CalendarDrawingParams) {
    // Allocate space for caching the day numbers and focus values
    today = -1
    calendarDrawingParams = params
    colouredParams.clear()
    params.calendarColoring?.coloredBuckets?.forEach { bucket ->
      bucket.days.forEach {
        colouredParams[it] = bucket
      }
    }

    labelsViewModel.update(params.labels)
    requestLayout()

    isDateDisabled = params.disabledDatesDefinition

    val localDate = LocalDate.of(params.year, params.month, 1)
    dayOfWeekStart = localDate.dayOfWeek.value
    weekStart = WeekFields.of(controller?.locale).firstDayOfWeek.value
    monthHeaderString = controller?.getLocalizedDate(localDate, MONTH_HEADLINE_PATTERN) ?: ""

    numberOfDaysInMonth = getDaysInMonth(params.month, params.year)
    for (i in 0 until numberOfDaysInMonth - getNonDrawnDaysOffset()) {
      val day = i + 1
      if (controller?.isToday(params.year, params.month, day) == true) {
        this.today = day
      }
    }
    numberOfRows = calculateNumRows()
  }

  @VisibleForTesting
  internal fun getNonDrawnDaysOffset(): Int {
    controller?.let {
      val shouldApplyOffset = calendarDrawingParams.year == it.startDate.year &&
        calendarDrawingParams.month == it.startDate.month.value &&
        it.startDate.dayOfMonth > numberOfDaysInAWeek
      if (shouldApplyOffset) {
        val fieldISO = WeekFields.of(it.locale).dayOfWeek()
        return it.startDate.with(fieldISO, 1).dayOfMonth - 1
      }
    }
    return 0
  }

  fun reuse() {
    numberOfRows = DEFAULT_NUM_ROWS
    requestLayout()
  }

  fun getYear(): Int {
    return calendarDrawingParams.year
  }

  private fun drawMonthTitle(canvas: Canvas) {
    val dayWidthHalf = viewWidth / (numberOfDaysInAWeek * 3)
    val monthTitleX = (dayWidthHalf).toFloat()
    val monthTitleY = ((monthHeaderSize) / 2 + monthLabelTextSize / 3).toFloat()

    drawText(canvas, monthHeaderString, monthTitleX, monthTitleY, monthTitlePaint)
  }

  private fun drawDaysInMonth(controller: BpkCalendarController, canvas: Canvas) {
    val rowHeight = if (labelsViewModel.isEmpty()) rowHeight else rowHeightLabeled
    var y = (rowHeight + miniDayNumberTextSize) / 2 + monthHeaderSize
    val dayWidthHalf = (viewWidth) / (numberOfDaysInAWeek * 2.0f)
    var j = findDayOffset()
    for (dayNumber in (getNonDrawnDaysOffset() + 1)..numberOfDaysInMonth) {
      val x = ((2 * j + 1) * dayWidthHalf).toInt()

      val startX = (x - dayWidthHalf).toInt()
      val stopX = (x + dayWidthHalf).toInt()

      val calendarDay = LocalDate.of(calendarDrawingParams.year, calendarDrawingParams.month, dayNumber)

      val disabled = isDisabledDate(calendarDay)
      drawDayCellForRange(
        controller,
        canvas,
        calendarDay,
        x,
        y,
        startX,
        stopX,
        disabled,
      )

      j++
      if (j == numberOfDaysInAWeek) {
        j = 0
        y += rowHeight
      }
    }
  }

  private fun drawDayCellForRange(
    controller: BpkCalendarController,
    canvas: Canvas,
    calendarDay: LocalDate,
    x: Int,
    y: Int,
    startX: Int,
    stopX: Int,
    isDisabled: Boolean,
  ) {
    var overrideTextColor: Int? = null

    val type = controller.selectedRange.getDrawType(calendarDay)
    val startYBase = y - miniDayNumberTextSize / 3
    if (!isDisabled) {
      when (type) {
        CalendarRange.DrawType.SELECTED -> {
          selectedCirclePaint.color = selectedDayCircleFillColor
          if (controller.selectedRange.isRange && !controller.selectedRange.isOnTheSameDate) {
            val paddingX = (stopX - startX) / 2
            drawEdgeCircles(canvas, calendarDay, controller.selectedRange, paddingX, x, y)
            val nextDay = calendarDay.plusDays(1)
            if (controller.selectedRange.getDrawType(nextDay) != CalendarRange.DrawType.NONE) {
              drawRect(
                canvas,
                startX - 1 + paddingX,
                startYBase - selectedDayCircleRadius,
                stopX + 1,
                startYBase + selectedDayCircleRadius
              )
            } else {
              drawRect(
                canvas,
                startX - 1,
                startYBase - selectedDayCircleRadius,
                stopX - paddingX + 1,
                startYBase + selectedDayCircleRadius
              )
            }
          }

          overrideTextColor = selectedTextColor
          if (controller.selectedRange.isOnTheSameDate) {
            selectedCirclePaint.alpha = 255
            drawSameDayCircles(
              canvas,
              miniDayNumberTextSize / 3,
              x,
              y,
              selectedDayCircleRadius
            )
            selectedCirclePaint.alpha = 255
          } else {
            selectedCirclePaint.style = Style.FILL

            drawCircle(
              canvas,
              x,
              startYBase,
              selectedDayCircleRadius,
              selectedCirclePaint
            )
          }
        }
        CalendarRange.DrawType.RANGE -> {
          val halfCellWidth = (stopX - startX) / 2
          drawRect(canvas, startX - 1, startYBase - selectedDayCircleRadius, stopX + 1, startYBase + selectedDayCircleRadius)
          drawEdgeCircles(canvas, calendarDay, controller.selectedRange, halfCellWidth, x, y)
        }
        CalendarRange.DrawType.NONE -> {
          if (colouredParams.containsKey(calendarDay)) {
            colouredBucketPaint.color = colouredParams[calendarDay]?.calendarCellStyle?.color(context) ?: Color.TRANSPARENT
            drawCircle(
              canvas,
              x,
              startYBase,
              selectedDayCircleRadius,
              colouredBucketPaint
            )
          }
        }
      }
    } else {
      if (type == CalendarRange.DrawType.RANGE) {
        val halfCellWidth = (stopX - startX) / 2
        drawRect(canvas, startX - 1, startYBase - selectedDayCircleRadius, stopX + 1, startYBase + selectedDayCircleRadius)
        drawEdgeCircles(canvas, calendarDay, controller.selectedRange, halfCellWidth, x, y)
      }
    }

    monthNumberPaint.color = when {
      isDisabled -> when (type) {
        CalendarRange.DrawType.RANGE -> rangeTextColor
        else -> disabledTextColor
      }
      overrideTextColor != null -> overrideTextColor
      type == CalendarRange.DrawType.SELECTED -> defaultTextColor
      type == CalendarRange.DrawType.RANGE -> rangeTextColor
      else -> when (colouredParams[calendarDay]?.calendarCellStyle?.textStyle(context)) {
        CalendarCellStyle.TextStyle.Light -> defaultTextColorLight
        CalendarCellStyle.TextStyle.Dark -> defaultTextColorDark
        null -> defaultTextColor
      }
    }

    drawText(
      canvas,
      String.format(controller.locale, "%d", calendarDay.dayOfMonth),
      x.toFloat(),
      y.toFloat(),
      monthNumberPaint
    )

    labelsViewModel.draw(
      canvas = canvas,
      date = calendarDay,
      x = x.toFloat(),
      y = y.toFloat() + labelsYOffset,
      disabled = isDisabled,
    )
  }

  private fun drawSameDayCircles(canvas: Canvas, padding: Int, x: Int, y: Int, radius: Int) {
    if (!isRtl) {
      drawCircle(canvas, (x - padding * 1.5).toInt(), y - padding, radius, selectedSameDayCirclePaint)
      drawCircle(canvas, x, y - padding, radius, selectedCirclePaint)
    } else {
      drawCircle(canvas, (x + padding * 1.5).toInt(), y - padding, radius, selectedSameDayCirclePaint)
      drawCircle(canvas, x, y - padding, radius, selectedCirclePaint)
    }
  }

  private fun drawEdgeCircles(
    canvas: Canvas,
    day: LocalDate,
    range: CalendarRange,
    paddingX: Int,
    x: Int,
    y: Int,
  ) {
    if (day.dayOfMonth == 1 && day != range.start) {
      drawCircle(canvas, x - paddingX, y - miniDayNumberTextSize / 3, selectedDayCircleRadius, rangeBackPaint)
    }
    if (day.dayOfMonth == getDaysInMonth(day.monthValue, day.year) && day != range.end) {
      drawCircle(canvas, x + paddingX, y - miniDayNumberTextSize / 3, selectedDayCircleRadius, rangeBackPaint)
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
      x1.toFloat(), startY.toFloat(), x2.toFloat(), stopY.toFloat(), rangeBackPaint
    )
  }

  private fun findDayOffset() =
    if (getNonDrawnDaysOffset() > 0) {
      0
    } else {
      var dayOffset = dayOfWeekStart - weekStart
      if (dayOfWeekStart < weekStart) {
        dayOffset += numberOfDaysInAWeek
      }
      dayOffset
    }

  private fun getDayFromLocation(x: Float, y: Float): Int {
    val day = getInternalDayFromLocation(x, y)
    return if (day < 1 || day > numberOfDaysInMonth) -1 else day
  }

  private fun getInternalDayFromLocation(x: Float, y: Float): Int {
    if (x < 0 || x > viewWidth) {
      return -1
    }
    val rowHeight = if (labelsViewModel.isEmpty()) rowHeight else rowHeightLabeled
    val row = (y - monthHeaderSize).toInt() / rowHeight
    val column = (x * numberOfDaysInAWeek / viewWidth).toInt()
    var day = column - findDayOffset() + 1
    day += row * numberOfDaysInAWeek + getNonDrawnDaysOffset()
    return day
  }

  private fun onDayClick(day: Int) {
    if (isDisabledDate(LocalDate.of(calendarDrawingParams.year, calendarDrawingParams.month, day))) return

    onDayClickListener?.onDayClick(this, LocalDate.of(calendarDrawingParams.year, calendarDrawingParams.month, day))
  }

  private fun isOutOfRange(calendarDay: LocalDate) =
    isBeforeMin(calendarDay.year, calendarDay.month.value, calendarDay.dayOfMonth) || isAfterMax(
      calendarDay.year,
      calendarDay.month.value,
      calendarDay.dayOfMonth
    )

  private fun isDisabledDate(calendarDay: LocalDate) =
    isOutOfRange(calendarDay) || isDateDisabled(calendarDay)

  private fun isBeforeMin(year: Int, month: Int, day: Int) =
    controller?.startDate?.let { minDate ->
      LocalDate.of(year, month, day).isBefore(minDate)
    } ?: false

  private fun isAfterMax(year: Int, month: Int, day: Int) =
    controller?.endDate?.let { maxDate ->
      LocalDate.of(year, month, day).isAfter(maxDate)
    } ?: false

  private fun getDaysInMonth(month: Int, year: Int) =
    LocalDate.of(year, month, 1).lengthOfMonth()

  private fun calculateNumRows(): Int {
    return if (getNonDrawnDaysOffset() >= numberOfDaysInAWeek) {
      (numberOfDaysInMonth - getNonDrawnDaysOffset()) / numberOfDaysInAWeek + 1
    } else {
      val offset = findDayOffset()
      val dividend = (offset + numberOfDaysInMonth) / numberOfDaysInAWeek
      val remainder = (offset + numberOfDaysInMonth) % numberOfDaysInAWeek
      dividend + if (remainder > 0) 1 else 0
    }
  }

  interface OnDayClickListener {
    fun onDayClick(view: MonthView?, day: LocalDate)
  }

  private companion object {
    const val MONTH_HEADLINE_PATTERN = "MMMM"

    const val DEFAULT_SELECTED_DAY = -1
    const val DEFAULT_NUM_DAYS = 7
    const val DEFAULT_NUM_ROWS = 6
    const val LABEL_WIDTH_PERCENT = 0.8f

    val DEFAULT_WEEK_START = DayOfWeek.MONDAY.value
  }
}
