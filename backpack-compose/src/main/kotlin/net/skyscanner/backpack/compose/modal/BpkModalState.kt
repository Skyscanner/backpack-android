package net.skyscanner.backpack.compose.modal

import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

@Composable
fun rememberBpkModalState(): BpkModalState {
    val isVisible = remember {
        MutableTransitionState(false).apply { targetState = true }
    }

    return remember(isVisible) {
        BpkModalState(isVisible)
    }
}

class BpkModalState(
    internal val isVisible: MutableTransitionState<Boolean>,
) {
    fun hide() {
        isVisible.targetState = false
    }
}
