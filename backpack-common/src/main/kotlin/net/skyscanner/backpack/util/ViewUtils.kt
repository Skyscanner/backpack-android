package net.skyscanner.backpack.util

import android.graphics.Rect
import android.view.View
import android.view.ViewTreeObserver
import androidx.core.view.doOnAttach

/**
 * Runs [block] immediately if the View is currently visible on screen.
 * Returns true if executed, false otherwise.
 */
inline fun View.runIfCurrentlyVisible(crossinline block: () -> Unit): Boolean {
    if (!isAttachedToWindow || !isShown || width == 0 || height == 0) return false

    val visibleBounds = Rect()
    if (!getGlobalVisibleRect(visibleBounds) ||
        visibleBounds.width() <= 0 ||
        visibleBounds.height() <= 0
    ) {
        return false
    }

    block()
    return true
}

/**
 * Runs [block] once, the first time this View is both attached and visible on screen.
 * If already visible, executes immediately.
 */
inline fun View.runOnFirstVisible(crossinline block: () -> Unit) {
    // Fast path: already visible
    if (runIfCurrentlyVisible(block)) return

    doOnAttach {
        val viewTreeObserverRef = viewTreeObserver

        val preDrawListener = object : ViewTreeObserver.OnPreDrawListener {
            override fun onPreDraw(): Boolean {
                if (!viewTreeObserverRef.isAlive) return true

                if (runIfCurrentlyVisible(block)) {
                    viewTreeObserverRef.removeOnPreDrawListener(this)
                }
                return true // don't block rendering
            }
        }

        viewTreeObserverRef.addOnPreDrawListener(preDrawListener)

        // Clean up if detached before becoming visible
        addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener {
            override fun onViewDetachedFromWindow(v: View) {
                if (viewTreeObserverRef.isAlive) {
                    viewTreeObserverRef.removeOnPreDrawListener(preDrawListener)
                }
                v.removeOnAttachStateChangeListener(this)
            }

            override fun onViewAttachedToWindow(v: View) = Unit
        })
    }
}
