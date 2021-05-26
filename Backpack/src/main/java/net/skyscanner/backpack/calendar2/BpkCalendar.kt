package net.skyscanner.backpack.calendar2

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.isActive
import net.skyscanner.backpack.calendar2.adapter.CalendarAdapter
import net.skyscanner.backpack.calendar2.adapter.CalendarSpanSizeLookup
import net.skyscanner.backpack.calendar2.data.CalendarStateMachine
import net.skyscanner.backpack.calendar2.view.CalendarHeaderView
import net.skyscanner.backpack.util.ResourcesUtil

class BpkCalendar @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0,
) : LinearLayoutCompat(context, attrs, defStyleAttr), CalendarComponent {

  private var scope: CoroutineScope? = null
  private var fsm: CalendarStateMachine? = null
  private val headerView = CalendarHeaderView(context)
  private val recyclerView = RecyclerView(context)
  private val layoutManager = GridLayoutManager(context, 7)

  private var lastParams: CalendarParams? = null

  var footerAdapter: CalendarFooterAdapter<RecyclerView.ViewHolder> = DefaultCalendarFooterAdapter

  init {
    orientation = VERTICAL
    recyclerView.layoutManager = layoutManager
    addView(headerView, LayoutParams(LayoutParams.MATCH_PARENT, ResourcesUtil.dpToPx(50, context)))
    addView(recyclerView, LayoutParams(LayoutParams.MATCH_PARENT, 0, 1f))
  }

  override fun onAttachedToWindow() {
    super.onAttachedToWindow()
    lastParams?.let(::invalidate)
  }

  override fun setParams(value: CalendarParams) {
    this.lastParams = value
    invalidate(value)
  }

  override fun onDetachedFromWindow() {
    super.onDetachedFromWindow()
    cleanup()
  }

  override val state: StateFlow<CalendarState>?
    get() = fsm?.state

  private fun invalidate(
    params: CalendarParams,
  ) {
    if (!isAttachedToWindow) return

    if (scope == null || scope?.isActive == false || fsm == null) {
      val scope = CoroutineScope(Job() + Dispatchers.Main)
      val fsm = CalendarStateMachine(scope, params)

      layoutManager.spanSizeLookup = CalendarSpanSizeLookup(scope, fsm.state)
      recyclerView.adapter = CalendarAdapter(scope, fsm.state, footerAdapter) {
        fsm.onClick(it.date.dayOfMonth, it.date.monthValue, it.date.year)
      }

      fsm.state.onEach {
        headerView.invoke(it)
      }.launchIn(scope)

      cleanup()
      this.scope = scope
      this.fsm = fsm
    } else {
      fsm!!.setParams(params)
    }
  }

  private fun cleanup() {
    scope?.cancel()
    fsm = null
    scope = null
  }
}
