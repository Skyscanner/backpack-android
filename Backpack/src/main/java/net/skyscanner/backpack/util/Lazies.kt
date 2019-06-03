package net.skyscanner.backpack.util

/**
 * The same semantic as [lazy] with [LazyThreadSafetyMode.NONE], but inlines the functor.
 */
fun <T> unsafeLazy(init: () -> T) = lazy(LazyThreadSafetyMode.NONE, init)
