package net.skyscanner.backpack.calendar2

import net.skyscanner.backpack.util.ExperimentalBackpackApi
import org.threeten.bp.YearMonth

@ExperimentalBackpackApi
typealias CalendarOnScrollListener = (YearMonth) -> Unit
