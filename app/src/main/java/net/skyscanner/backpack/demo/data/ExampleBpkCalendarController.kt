package net.skyscanner.backpack.demo.data

import android.content.Context
import android.text.TextUtils.getLayoutDirectionFromLocale
import android.widget.Toast
import androidx.core.view.ViewCompat.LAYOUT_DIRECTION_RTL
import net.skyscanner.backpack.calendar.model.CalendarRange
import net.skyscanner.backpack.calendar.presenter.BpkCalendarController
import java.util.*

class ExampleBpkCalendarController(private val context: Context) : BpkCalendarController() {
  override fun onRangeSelected(range: CalendarRange) {
    Toast.makeText(context, String.format("%s - %s", range.start.toString(), range.end.toString()), Toast.LENGTH_SHORT)
      .show()
  }

  override val isRtl: Boolean = getLayoutDirectionFromLocale(Locale.getDefault()) == LAYOUT_DIRECTION_RTL
  override val locale: Locale = Locale.getDefault()
}
