package net.skyscanner.backpack.compose.modal

import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import kotlinx.coroutines.delay

@Composable
fun rememberBpkModalState() = remember {
    BpkModalState(MutableTransitionState(false).apply { targetState = true })
}

class BpkModalState internal constructor(
    internal val isVisible: MutableTransitionState<Boolean>,
) {
    suspend fun show() {
        isVisible.targetState = true
        delay(ModalAnimationDurationMs.toLong())
    }

    suspend fun hide() {
        isVisible.targetState = false
        delay(ModalAnimationDurationMs.toLong())
    }
}
