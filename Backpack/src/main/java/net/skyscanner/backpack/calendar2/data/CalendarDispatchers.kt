package net.skyscanner.backpack.calendar2.data

import androidx.annotation.VisibleForTesting
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import net.skyscanner.backpack.util.InternalBackpackApi

@InternalBackpackApi
@VisibleForTesting
object CalendarDispatchers {

  private var main: CoroutineDispatcher = Dispatchers.Main
  private var background: CoroutineDispatcher = Dispatchers.Default

  val Main: CoroutineDispatcher
    get() = main

  val Background: CoroutineDispatcher
    get() = background

  fun setMain(dispatcher: CoroutineDispatcher) {
    this.main = dispatcher
  }

  fun setBackground(dispatcher: CoroutineDispatcher) {
    this.background = dispatcher
  }
}
