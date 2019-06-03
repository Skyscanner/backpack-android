package net.skyscanner.backpack.util

/**
 * The same semantic as [lazy] with [LazyThreadSafetyMode.NONE], but inlines the functor.
 */
inline fun <T> unsafeLazy(crossinline init: () -> T) = object : Lazy<T> {

  private var initialized = false
  private var _value: T? = null

  override val value: T
    get() {
      if (!initialized) {
        initialized = true
        _value = init()
      }
      return _value!!
    }

  override fun isInitialized() = initialized
}
