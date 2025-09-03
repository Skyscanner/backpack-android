/**
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2018 - 2025 Skyscanner Ltd
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

package net.skyscanner.backpack.util

import android.graphics.Rect
import android.view.View
import android.view.ViewTreeObserver

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
    if (runIfCurrentlyVisible { post { block() } }) return

    lateinit var attachListener: View.OnAttachStateChangeListener

    val preDrawListener = object : ViewTreeObserver.OnPreDrawListener {
        override fun onPreDraw(): Boolean {
            // Always use the current VTO
            val vto = viewTreeObserver
            if (!vto.isAlive) return true

            if (runIfCurrentlyVisible { post { block() } }) {
                // Fire once, then clean up immediately
                vto.removeOnPreDrawListener(this)
                removeOnAttachStateChangeListener(attachListener)
            }
            return true // don't block rendering
        }
    }

    attachListener = object : View.OnAttachStateChangeListener {
        override fun onViewAttachedToWindow(v: View) {
            v.viewTreeObserver.addOnPreDrawListener(preDrawListener)
        }

        override fun onViewDetachedFromWindow(v: View) {
            v.viewTreeObserver.removeOnPreDrawListener(preDrawListener)
        }
    }

    // Keep the listeners across window changes
    addOnAttachStateChangeListener(attachListener)

    // If we're already attached, start listening now
    if (isAttachedToWindow) {
        viewTreeObserver.addOnPreDrawListener(preDrawListener)
    }
}
