/**
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2018 - 2026 Skyscanner Ltd
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

package net.skyscanner.backpack.compose.modal

import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

@Composable
fun rememberBpkModalState(): BpkModalState {
    val modalState = remember {
        BpkModalState(MutableTransitionState(false).apply { targetState = true })
    }
    val scope = rememberCoroutineScope()
    LaunchedEffect(Unit) {
        modalState.setCoroutineScope(scope)
    }

    return modalState
}

class BpkModalState internal constructor(
    internal val isVisible: MutableTransitionState<Boolean>,
) {

    private var _pendingHideAnimationCallback: (() -> Unit)? = null
    private var _scope: CoroutineScope? = null

    internal fun setCoroutineScope(scope: CoroutineScope) {
        _scope = scope
    }

    fun show() {
        animateState(true)
    }

    fun hide(onHidden: (() -> Unit)? = null) {
        animateState(false)
        onHidden?.let { callback ->
            _pendingHideAnimationCallback = callback
            _scope?.launch {
                snapshotFlow { isVisible.isIdle && !isVisible.currentState }.distinctUntilChanged().filter { it }.collect {
                    callback.invoke()
                    _pendingHideAnimationCallback = null
                }
            }
        }
    }

    private fun animateState(newVisibility: Boolean) {
        isVisible.targetState = newVisibility
        if (isVisible.currentState == newVisibility) {
            return
        }
    }
}
